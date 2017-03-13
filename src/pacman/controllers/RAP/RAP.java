package pacman.controllers.RAP;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * RAP execution
 */
public class RAP {
    // file to read in
    protected String file;
    // json of file
    private JsonArray json;

    // all raps from id to rap
    protected HashMap<String, RAPInstance> raps;
    // all non primitive raps
    protected ArrayList<String> nonPrimitives;

    // current goal
    protected String goal;
    // all possible goals
    protected ArrayList<String> goals;
    // priority queue for each goal
    protected Map<String, ArrayList<RAPInstance>> queuePerGoal;

    /**
     * Constructor
     * @param file file to parse
     */
    public RAP(String file) {
        this.file = file;
        this.raps = new HashMap<>();
        this.nonPrimitives = new ArrayList<>();
        this.queuePerGoal = new HashMap<>();
        parseFile();
        this.goal = "";
    }

    /**
     * Parse the file to create all of the possible raps
     */
    protected void parseFile() {
        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
            throw new RuntimeException("Could not open DT file " + e.getMessage());
        }
        JsonArray jsonArray = new JsonParser().parse(reader).getAsJsonArray();
        this.json = jsonArray;
        buildRaps();
    }

    /**
     * Build each RAP and save it
     */
    protected void buildRaps(){
        // for each json
        for(JsonElement j : this.json) {
            JsonObject rap = j.getAsJsonObject();
            int type = rap.get("type").getAsInt();
            // if type == 0 then primitive
            if (type == 0) {
                String id = rap.get("id").getAsString();
                String action = rap.get("action").getAsString();
                String target = rap.get("target").getAsString();
                String heuristic = rap.get("heuristic").getAsString();
                String post = rap.get("post").getAsString();
                JsonArray pre = rap.get("pre").getAsJsonArray();
                ArrayList<String> preArray = new ArrayList<String>();
                for(JsonElement e : pre) {
                    preArray.add(e.getAsString());
                }
                RAPInstance r;
                // create the correct primitive
                switch(action){
                    case "MOVEAWAY":
                        r = new MoveAway(target, heuristic, post, preArray);
                        break;
                    case "MOVETOWARD":
                        r = new MoveToward(target, heuristic, post, preArray);
                        break;
                    default:
                        throw new RuntimeException("Invalid action");
                }
                raps.put(id, r);
            } else if(type == 2){
                // if type == 2 then list of goals
                JsonArray goals = rap.get("goals").getAsJsonArray();
                ArrayList<String> goalArray = new ArrayList<String>();
                for(JsonElement e : goals) {
                    goalArray.add(e.getAsString());
                }
                this.goals = goalArray;
            } else {
                // else a non primitive rap
                String id = rap.get("id").getAsString();
                String post = rap.get("post").getAsString();
                String goal = rap.get("goal").getAsString();
                JsonArray pre = rap.get("pre").getAsJsonArray();
                JsonArray actions = rap.get("action").getAsJsonArray();
                ArrayList<String> preArray = new ArrayList<String>();
                ArrayList<String> actionsArray = new ArrayList<String>();
                int priority = rap.get("priority").getAsInt();
                for(JsonElement e : pre) {
                    preArray.add(e.getAsString());
                }
                for(JsonElement e : actions) {
                    actionsArray.add(e.getAsString());
                }

                RAPInstance r = new TaskNet(id, priority, goal, actionsArray, post, preArray);
                raps.put(id, r);
                nonPrimitives.add(id);
            }
        }
        // for each goal make a priority queue of the RAPs for it
        for(String goal : this.goals) {
            goal = goal.substring(goal.lastIndexOf(",") + 1);
            Map<Integer, ArrayList<RAPInstance>> priorityMap = new TreeMap<Integer, ArrayList<RAPInstance>>(new PriorityComparator());
            for(String rid : this.nonPrimitives) {
                RAPInstance rap = raps.get(rid);
                if(rap.getGoal().equals(goal)) {
                    int priority = rap.getPriority();
                    if(priorityMap.containsKey(priority)) {
                        ArrayList<RAPInstance> rapsForPriority = priorityMap.get(priority);
                        rapsForPriority.add(rap);
                        priorityMap.put(priority, rapsForPriority);
                    } else {
                        ArrayList<RAPInstance> rapsForPriority = new ArrayList<>();
                        rapsForPriority.add(rap);
                        priorityMap.put(priority, rapsForPriority);
                    }
                }
            }

            ArrayList<RAPInstance> queue = new ArrayList<>();
            for(Map.Entry<Integer, ArrayList<RAPInstance>> e : priorityMap.entrySet()) {
                queue.addAll(e.getValue());
            }
            this.queuePerGoal.put(goal, queue);
        }
    }

    /**
     * Get the next move
     * @param game current game state
     * @return a move of either up, down, left, right, or neutral
     */
    public Constants.MOVE execute(Game game) {
        // if goal is not set, determine goal by going through the list
        if(this.goal.equals("")) {
            for(String goal : goals) {
                if(goal.contains(",")) {
                    if (Util.checkValidityOfTest(goal.substring(0, goal.lastIndexOf(",")), game)) {
                        this.goal = goal.substring(goal.lastIndexOf(",") + 1);
                        break;
                    }
                } else {
                    this.goal = goal;
                }
            }
            // default to StayAlive
            if(this.goal.equals("")) {
                this.goal = "StayAlive";
            }
        }

        // get the queue for the current goal
        ArrayList<RAPInstance> queue = (ArrayList<RAPInstance>) this.queuePerGoal.get(this.goal).clone();
        while(true) {
            RAPInstance rap = queue.get(0);
            // get first rap, if primitive try to run it, if not add actions to the queue
            if(rap.isPrimitive()) {
                queue.remove(rap);
                if(rap.isValid(game)) {
                    RAPInstance parentRap = null;
                    // alert up to the parent rap
                    String parent = rap.getParent();
                    while(!parent.equals("")) {
                        parentRap = raps.get(parent);
                        parent = parentRap.getParent();
                    }
                    // set the goal to be the top parent's post condition
                    this.goal = parentRap.getPostCondition();
                    return rap.execute(game);
                }
            } else {
                // if not primitive, remove it, add all of its actions, and set the parent of the children
                // to the current rap
                queue.remove(rap);
                if(rap.isValid(game)) {
                    ArrayList<String> actions = rap.getActions();
                    int index = 0;
                    for(String s : actions) {
                        RAPInstance local = raps.get(s);
                        local.setParent(rap.getId());
                        queue.add(index, local);
                        index++;
                    }
                }
                queue.add(rap);
            }
        }
    }
}
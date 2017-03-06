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

public class RAP {
    String file;
    JsonArray json;

    HashMap<String, RAPInstance> raps;
    ArrayList<String> nonPrimitives;

    String goal;
    ArrayList<String> goals;


    public RAP(String file) {
        this.file = file;
        this.raps = new HashMap<>();
        this.nonPrimitives = new ArrayList<>();
        parseFile();
        this.goal = "";
    }

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

    protected void buildRaps(){
        for(JsonElement j : this.json) {
            JsonObject rap = j.getAsJsonObject();
            int type = rap.get("type").getAsInt();
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
                JsonArray goals = rap.get("goals").getAsJsonArray();
                ArrayList<String> goalArray = new ArrayList<String>();
                for(JsonElement e : goals) {
                    goalArray.add(e.getAsString());
                }
                this.goals = goalArray;
            } else {
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
    }

    public Constants.MOVE execute(Game game) {
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
            if(this.goal.equals("")) {
                this.goal = "StayAlive";
            }
        }

        // TODO simplify this with next loop, precompute these for each goal, bug near ghost spawn
        ArrayList<RAPInstance> possibleRaps = new ArrayList<>();
        for(String r : nonPrimitives) {
            RAPInstance rap = raps.get(r);
            if(rap.getGoal().equals(this.goal)) {
                possibleRaps.add(rap);
            }
        }

        Map<Integer,ArrayList<RAPInstance>> priorityMap = new TreeMap<Integer, ArrayList<RAPInstance>>(new PriorityComparator());
        for(RAPInstance r : possibleRaps) {
            int priority = r.getPriority();
            if(priorityMap.containsKey(priority)) {
                ArrayList<RAPInstance> rapsForPriority = priorityMap.get(priority);
                rapsForPriority.add(r);
                priorityMap.put(priority, rapsForPriority);
            } else {
                ArrayList<RAPInstance> rapsForPriority = new ArrayList<>();
                rapsForPriority.add(r);
                priorityMap.put(priority, rapsForPriority);
            }
        }

        ArrayList<RAPInstance> queue = new ArrayList<>();
        for(Map.Entry<Integer, ArrayList<RAPInstance>> e : priorityMap.entrySet()) {
            queue.addAll(e.getValue());
        }

        while(true) {
            RAPInstance rap = queue.get(0);
            if(rap.isPrimitive()) {
                queue.remove(rap);
                if(rap.isValid(game)) {
                    RAPInstance parentRap = null;
                    String parent = rap.getParent();
                    while(!parent.equals("")) {
                        parentRap = raps.get(parent);
                        parent = parentRap.getParent();
                    }
                    goal = parentRap.getPostCondition();
                    return rap.execute(game);
                }
            } else {
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

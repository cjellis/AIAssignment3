package pacman.controllers.RAP;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import pacman.game.Constants;
import pacman.game.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RAP {
    String file;
    JsonArray json;

    HashMap<String, RAPInstance> raps;

    ArrayList<RAPInstance> queue;
    ArrayList<RAPInstance> orig_queue;

    public RAP(String file) {
        this.file = file;
        this.raps = new HashMap<>();
        this.queue = new ArrayList<>();
        this.orig_queue = new ArrayList<>();
        parseFile();
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
        int index = 0;
        for(JsonElement j : this.json) {
            JsonObject rap = j.getAsJsonObject();
            int type = rap.get("type").getAsInt();
            if (type == 0) {
                String id = rap.get("id").getAsString();
                String action = rap.get("action").getAsString();
                String target = rap.get("target").getAsString();
                String heuristic = rap.get("heuristic").getAsString();
                RAPInstance r;
                switch(action){
                    case "MOVEAWAY":
                        r = new MoveAway(target, heuristic);
                        break;
                    case "MOVETOWARD":
                        r = new MoveToward(target, heuristic);
                        break;
                    default:
                        throw new RuntimeException("Invalid action");
                }
                raps.put(id, r);
            } else {
                String id = rap.get("id").getAsString();
                JsonArray success = rap.get("success").getAsJsonArray();
                JsonArray validity = rap.get("validity").getAsJsonArray();
                JsonArray actions = rap.get("action").getAsJsonArray();
                ArrayList<String> successArray = new ArrayList<String>();
                ArrayList<String> validityArray = new ArrayList<String>();
                ArrayList<String> actionsArray = new ArrayList<String>();
                for(JsonElement e : success) {
                    successArray.add(e.getAsString());
                }
                for(JsonElement e : validity) {
                    validityArray.add(e.getAsString());
                }
                for(JsonElement e : actions) {
                    actionsArray.add(e.getAsString());
                }

                RAPInstance r = new TaskNet(actionsArray, successArray, validityArray);
                raps.put(id, r);
                queue.add(index, r);
                orig_queue.add(index, r);
                index++;
            }
        }
    }

    public Constants.MOVE execute(Game game) {
        Constants.MOVE move;
        while(true) {
            RAPInstance rap = queue.get(0);
            if(rap.isPrimitive()) {
                queue = new ArrayList<>(orig_queue);
                move = rap.execute(game);
                break;
            } else {
                queue.remove(0);
                if(!rap.isSuccessful(game)){
                    if(rap.isValid(game)) {
                        ArrayList<String> actions = rap.getActions();
                        int index = 0;
                        for (String s : actions) {
                            queue.add(index, raps.get(s));
                            index++;
                        }
                    }
                    queue.add(rap);
                }
            }
        }
        return move;
    }
}

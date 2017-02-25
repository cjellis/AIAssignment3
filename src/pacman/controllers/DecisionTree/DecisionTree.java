package pacman.controllers.DecisionTree;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import pacman.game.Constants;
import pacman.game.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class DecisionTree {
    String file;
    String SUCCESS = "success";
    String FAILURE = "failure";
    String AWAY = "AWAY";
    String TOWARDS = "TOWARDS";

    JsonObject json;

    Node root;


    public DecisionTree(String file) {
        this.file = file;
        parseFile();
    }

    protected void parseFile() {
        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
            throw new RuntimeException("Could not open DT file " + e.getMessage());
        }
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        this.json = jsonObject;

        JsonObject current = jsonObject.get("1").getAsJsonObject();
        this.root = buildTree(current);
    }

    protected Node buildTree(JsonObject currentNode) {
        if (currentNode.get("type").getAsInt() == 0) {
            String heuristic = currentNode.get("heuristic").getAsString();
            String target = currentNode.get("target").getAsString();
            if (currentNode.get("direction").getAsString().equals(AWAY)) {
                return new MoveAway(heuristic, target);
            } else {
                return new MoveTowards(heuristic, target);
            }
        }

        String typeOfDecision = currentNode.get("name").getAsString();

        Node n;
        switch (typeOfDecision){
            case "EdibleGhostWithinDistance":
                JsonArray values = currentNode.get("values").getAsJsonArray();
                n = new EdibleGhostWithinDistance(
                        buildTree(this.json.get(currentNode.get(SUCCESS).getAsString()).getAsJsonObject()),
                        buildTree(this.json.get(currentNode.get(FAILURE).getAsString()).getAsJsonObject()),
                        values.get(0).getAsInt()
                );
                break;
            case "NonEdibleGhostWithinDistance":
                values = currentNode.get("values").getAsJsonArray();
                n = new NonEdibleGhostWithinDistance(
                        buildTree(this.json.get(currentNode.get(SUCCESS).getAsString()).getAsJsonObject()),
                        buildTree(this.json.get(currentNode.get(FAILURE).getAsString()).getAsJsonObject()),
                        values.get(0).getAsInt()
                );
                break;
            default:
                throw new RuntimeException("invalid decision type found in file");
        }

        return n;

    }

    public Constants.MOVE makeDecision(Game game) {
        return this.root.makeDecision(game);
    }
}

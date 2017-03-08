package pacman.controllers.DecisionTree;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import pacman.game.Constants;
import pacman.game.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Decision Tree
 * Reads in a decision tree file, creates decision tree, then can be called
 * to determine a move based on game state
 */
public class DecisionTree {
    // string constants
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String AWAY = "AWAY";
    private static final String TOWARDS = "TOWARDS";

    // file to read
    private String file;
    // json object read from file
    private JsonObject json;
    // root node of the tree
    private Node root;

    /**
     * Creates a decision tree by parsing the file
     * @param file file to parse to create the tree
     */
    public DecisionTree(String file) {
        this.file = file;
        parseFile();
    }

    /**
     * Parses the file given in the constructor
     * Creates a decision tree from the information in the file
     */
    protected void parseFile() {
        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
            throw new RuntimeException("Could not open DT file " + e.getMessage());
        }
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        this.json = jsonObject;

        // set current to id 1 which is always the root
        JsonObject current = jsonObject.get("1").getAsJsonObject();
        // build the tree off of the root node
        this.root = buildTree(current);
    }

    /**
     * Builds a decision tree based off the root node
     * @param currentNode the root node of the tree
     * @return the root node populated with its children
     */
    protected Node buildTree(JsonObject currentNode) {
        // if type is 0 then primitive
        if (currentNode.get("type").getAsInt() == 0) {
            String heuristic = currentNode.get("heuristic").getAsString();
            String target = currentNode.get("target").getAsString();
            // depending on direction, create one of the two possible primitive nodes
            if (currentNode.get("direction").getAsString().equals(AWAY)) {
                return new MoveAway(heuristic, target);
            } else {
                return new MoveTowards(heuristic, target);
            }
        }

        // we now know it is not a primitive
        String typeOfDecision = currentNode.get("name").getAsString();

        Node n;
        // create the correct non primitive decision node
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

    /**
     * Make a move decision based on the game state
     * @param game the current game state
     * @return a move of either up, down, left, right, or neutral
     */
    public Constants.MOVE makeDecision(Game game) {
        // calls makeDecision on root node which will run recursively until a primitive is hit
        return this.root.makeDecision(game);
    }
}
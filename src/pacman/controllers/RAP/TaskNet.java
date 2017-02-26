package pacman.controllers.RAP;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class TaskNet implements RAPInstance {
    ArrayList<String> actions;
    ArrayList<String> success;
    ArrayList<String> validity;

    public TaskNet(ArrayList<String> actions, ArrayList<String> success, ArrayList<String> validity){
        this.actions = actions;
        this.success = success;
        this.validity = validity;
    }

    public boolean isPrimitive(){
        return false;
    }

    public boolean isSuccessful(Game game) {
        for(String test : success) {
            if(!checkIfSuccessful(test, game)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfSuccessful(String test, Game game) {
        boolean isNot = test.startsWith("!");
        if(isNot) {
            test = test.substring(1);
        }
        boolean result;
        switch (test){
            case "false":
                return false;
            case "edibleGhostsAlive":
                result = false;
                for(Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) > 0) {
                        result = true;
                    }
                }
                if(isNot) {
                    return !result;
                } else {
                    return result;
                }
            default:
                return false;
        }
    }

    public boolean isValid(Game game) {
        for(String test : validity) {
            if(!checkValidityOfTest(test, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> getActions() {
        return this.actions;
    }

    @Override
    public Constants.MOVE execute(Game game) {
        return null;
    }

    public boolean checkValidityOfTest(String test, Game game) {
        boolean isNot = test.startsWith("!");
        if(isNot) {
            test = test.substring(1);
        }
        boolean result;

        String values = "";

        if(test.contains(",")) {
            values = test.substring(test.indexOf(",")+1);
            test = test.substring(0,test.indexOf(","));
        }
        switch(test){
            case "nonEdibleGhostWithinDistance":
                result = false;
                int distance = Integer.valueOf(values);
                for(Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) == 0) {
                        int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                                game.getGhostCurrentNodeIndex(ghost));
                        if (d != -1 && d < distance) {
                            result = true;
                        }
                    }
                }
                if(isNot) {
                    return !result;
                } else {
                    return result;
                }
            case "edibleGhostWithinDistance":
                result = false;
                distance = Integer.valueOf(values);
                for(Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) > 0) {
                        int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                                game.getGhostCurrentNodeIndex(ghost));
                        if (d != -1 && d < distance) {
                            result = true;
                        }
                    }
                }
                if(isNot) {
                    return !result;
                } else {
                    return result;
                }
            case "true":
                return true;
            default:
                return false;
        }
    }
}

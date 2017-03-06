package pacman.controllers;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class Util {

    public static int getLocalTarget(Game game, String target, Constants.DM heuristic) {
        int local_target = -1;
        switch(target){
            case "ClosestNonEdibleGhost":
                int d = Integer.MAX_VALUE;
                for(Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) == 0) {
                        int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                                game.getGhostCurrentNodeIndex(ghost));
                        if (distance < d) {
                            d = distance;
                            local_target = game.getGhostCurrentNodeIndex(ghost);
                        }
                    }
                }
                break;
            case "ClosestEdibleGhost":
                d = Integer.MAX_VALUE;
                for(Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) > 0) {
                        int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                                game.getGhostCurrentNodeIndex(ghost));
                        if (distance < d) {
                            d = distance;
                            local_target = game.getGhostCurrentNodeIndex(ghost);
                        }
                    }
                }
                break;
            case "ClosestAnyPill":
                int[] pills=game.getPillIndices();
                int[] powerPills=game.getPowerPillIndices();

                ArrayList<Integer> targets=new ArrayList<Integer>();

                for(int i=0;i<pills.length;i++) {
                    if (game.isPillStillAvailable(i)) {
                        targets.add(pills[i]);
                    }
                }

                for(int i=0;i<powerPills.length;i++) {
                    if (game.isPowerPillStillAvailable(i)) {
                        targets.add(powerPills[i]);
                    }
                }

                int[] targetsArray=new int[targets.size()];

                for(int i=0;i<targetsArray.length;i++) {
                    targetsArray[i] = targets.get(i);
                }

                local_target = game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(),
                        targetsArray, heuristic);
                break;
            case "ClosestPowerPill":
                powerPills=game.getPowerPillIndices();

                targets=new ArrayList<Integer>();

                for(int i=0;i<powerPills.length;i++) {
                    if (game.isPowerPillStillAvailable(i)) {
                        targets.add(powerPills[i]);
                    }
                }

                targetsArray=new int[targets.size()];

                for(int i=0;i<targetsArray.length;i++) {
                    targetsArray[i] = targets.get(i);
                }

                local_target = game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(),
                        targetsArray, heuristic);
                break;

            default:
                local_target = game.getPacmanCurrentNodeIndex(); //TODO change this
        }
        return local_target;
    }

    public static boolean checkValidityOfTest(String test, Game game) {
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
            case "powerPillWithinDistance":
                result = false;
                distance = Integer.valueOf(values);
                int[] powerPills=game.getPowerPillIndices();

                for(int i=0;i<powerPills.length;i++) {
                    if (game.isPowerPillStillAvailable(i)) {
                        if (game.getDistance(game.getPacmanCurrentNodeIndex(),
                                powerPills[i], Constants.DM.PATH) < distance) {
                            result = true;
                            break;
                        }
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
}

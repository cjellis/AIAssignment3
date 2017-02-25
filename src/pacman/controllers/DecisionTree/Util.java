package pacman.controllers.DecisionTree;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class Util {

    public static int getLocalTarget(Game game, String target, Constants.DM heuristic) {
        int local_target = -1;
        switch(target){
            case "ClosestGhost":
                int d = Integer.MAX_VALUE;
                for(Constants.GHOST ghost : Constants.GHOST.values()) {
                    int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                            game.getGhostCurrentNodeIndex(ghost));
                    if (distance < d) {
                        d = distance;
                        local_target = game.getGhostCurrentNodeIndex(ghost);
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
                local_target = 0; //TODO change this
        }
        return local_target;
    }
}

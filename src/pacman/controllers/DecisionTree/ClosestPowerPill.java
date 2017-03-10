package pacman.controllers.DecisionTree;


import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

/**
 * Node for making a decision on whether a power pill is within a certain distance
 */
public class ClosestPowerPill implements Node{
    private Node success;
    private Node failure;
    private int max;

    /**
     * Constructor for the node
     * @param success success node
     * @param failure failure node
     * @param max value to check max distance for a ghost
     */
    protected ClosestPowerPill(Node success, Node failure, int max) {
        this.success = success;
        this.failure = failure;
        this.max = max;
    }

    /**
     * Decides if a power pill is within the distance and passes call onto success or failure node
     * @param game current game state
     * @return move of either up, down, left, right, neutral
     */
    public Constants.MOVE makeDecision(Game game){
        int[] powerPills=game.getPowerPillIndices();

        for(int i=0;i<powerPills.length;i++) {
            if (game.isPowerPillStillAvailable(i)) {
                if (game.getDistance(game.getPacmanCurrentNodeIndex(),
                        powerPills[i], Constants.DM.PATH) < max) {
                    return success.makeDecision(game);
                }
            }
        }
        return failure.makeDecision(game);
    }
}
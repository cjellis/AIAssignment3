package pacman.controllers.DecisionTree;

import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Move Towards primitive
 */
public class MoveTowards implements Node {
    private Constants.DM heuristic;
    private String target;

    /**
     * Constructor for move towards primitive
     * @param heuristic heuristic to use when determining distance
     * @param target target to move towards
     */
    protected MoveTowards(String heuristic, String target) {
        this.heuristic = Constants.DM.valueOf(heuristic);
        this.target = target;
    }

    /**
     * Determines the next move to go towards the desired target
     * @param game current game state
     * @return move of either up, down, left, right, neutral
     */
    public Constants.MOVE makeDecision(Game game){
        // get the target based on the target and heurstic given in the constructor
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        // move towards the target
        return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }
}
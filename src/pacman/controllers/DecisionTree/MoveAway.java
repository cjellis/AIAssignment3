package pacman.controllers.DecisionTree;

import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Move Away Primitive
 */
public class MoveAway implements Node {
    private Constants.DM heuristic;
    private String target;

    /**
     * Constructor for Move away primitive
     * @param heuristic heuristic to use for determining distance
     * @param target target to move away from
     */
    protected MoveAway(String heuristic, String target) {
        this.heuristic = Constants.DM.valueOf(heuristic);
        this.target = target;
    }

    /**
     * Determines move based on the game state
     * @param game current game state
     * @return move of either up, down, left, right, neutral
     */
    public Constants.MOVE makeDecision(Game game){
        // get the local target based on the target and heuristics set in the constructor
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        // move away from that target
        return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }

}
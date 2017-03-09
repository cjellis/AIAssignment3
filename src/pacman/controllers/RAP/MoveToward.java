package pacman.controllers.RAP;


import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class MoveToward extends Primitive {
    /**
     * Constructor
     * @param target target for the action
     * @param heuristic heuristic to use for distance calculation
     * @param post post condition
     * @param preconditions pre conditions
     */
    MoveToward(String target, String heuristic, String post, ArrayList<String> preconditions) {
        this.target = target;
        this.heuristic = Constants.DM.valueOf(heuristic);
        this.post = post;
        this.preconditions = preconditions;
    }

    /**
     * Determines the next move based on the game state
     * @param game current game state
     * @return move of either up, down, left, right, neutral
     */
    public Constants.MOVE execute(Game game) {
        // get the target based on the target and heuristic used in the constructor
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        // get the move away from the target
        return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }
}
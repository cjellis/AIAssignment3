package pacman.controllers.DecisionTree;


import pacman.game.Constants;
import pacman.game.Game;

/**
 * Node for making a decision on if a non edible ghost is within a certain distance
 */
public class NonEdibleGhostWithinDistance implements Node{
    private Node success;
    private Node failure;
    private int max;

    /**
     * Constructor
     * @param success success node
     * @param failure failure node
     * @param max distance to check for non edible ghosts
     */
    protected NonEdibleGhostWithinDistance(Node success, Node failure, int max) {
        this.success = success;
        this.failure = failure;
        this.max = max;
    }

    /**
     * Makes a move decision based on the current game state
     * @param game the current game state
     * @return move of either up, down, left, right, neutral
     */
    public Constants.MOVE makeDecision(Game game){
        // for all ghsots, if ghost is not edible and not in the lair and it is within the distance,
        // call success node
        for(Constants.GHOST ghost : Constants.GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                        game.getGhostCurrentNodeIndex(ghost)) < this.max) {
                    return success.makeDecision(game);
                }
            }
        }
        // if no ghosts satisfy the criteria, call failure node
        return failure.makeDecision(game);
    }
}
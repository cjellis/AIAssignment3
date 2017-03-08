package pacman.controllers.DecisionTree;


import pacman.game.Constants;
import pacman.game.Game;

/**
 * Node for making a decision on whether an edible ghost is within a certain distance
 */
public class EdibleGhostWithinDistance implements Node{
    private Node success;
    private Node failure;
    private int max;

    /**
     * Constructor for the node
     * @param success success node
     * @param failure failure node
     * @param max value to check max distance for a ghost
     */
    protected EdibleGhostWithinDistance(Node success, Node failure, int max) {
        this.success = success;
        this.failure = failure;
        this.max = max;
    }

    /**
     * Decides if a ghost is within the distance and passes call onto success or failure node
     * @param game current game state
     * @return move of either up, down, left, right, neutral
     */
    public Constants.MOVE makeDecision(Game game){
        // for each ghost, if edible, and within distance then call success node
        for(Constants.GHOST ghost : Constants.GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) > 0) {
                if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                        game.getGhostCurrentNodeIndex(ghost)) < this.max) {
                    return success.makeDecision(game);
                }
            }
        }
        // if no ghost within the distance, call the failure node
        return failure.makeDecision(game);
    }
}
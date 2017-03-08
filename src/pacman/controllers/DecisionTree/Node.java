package pacman.controllers.DecisionTree;


import pacman.game.Constants;
import pacman.game.Game;

/**
 * Interface for all nodes in the tree
 */
public interface Node {
    // only need to implement one method that can be called down the tree until a primitive is hit
    Constants.MOVE makeDecision(Game game);
}
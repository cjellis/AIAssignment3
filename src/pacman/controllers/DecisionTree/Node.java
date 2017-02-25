package pacman.controllers.DecisionTree;


import pacman.game.Constants;
import pacman.game.Game;

public interface Node {
    Constants.MOVE makeDecision(Game game);
}

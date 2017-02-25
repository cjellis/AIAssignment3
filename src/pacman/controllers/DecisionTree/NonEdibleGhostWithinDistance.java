package pacman.controllers.DecisionTree;


import pacman.game.Constants;
import pacman.game.Game;

public class NonEdibleGhostWithinDistance implements Node{
    Node success;
    Node failure;
    int max;

    protected NonEdibleGhostWithinDistance(Node success, Node failure, int max) {
        this.success = success;
        this.failure = failure;
        this.max = max;
    }

    public Constants.MOVE makeDecision(Game game){
        for(Constants.GHOST ghost : Constants.GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                        game.getGhostCurrentNodeIndex(ghost)) < this.max) {
                    return success.makeDecision(game);
                }
            }
        }
        return failure.makeDecision(game);
    }
}

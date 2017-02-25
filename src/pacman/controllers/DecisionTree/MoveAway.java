package pacman.controllers.DecisionTree;

import pacman.game.Constants;
import pacman.game.Game;


public class MoveAway implements Node {
    Constants.DM heuristic;
    String target;

    public MoveAway(String heuristic, String target) {
        this.heuristic = Constants.DM.valueOf(heuristic);
        this.target = target;
    }

    public Constants.MOVE makeDecision(Game game){
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }

}

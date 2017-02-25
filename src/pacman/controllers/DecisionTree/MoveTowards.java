package pacman.controllers.DecisionTree;

import pacman.game.Constants;
import pacman.game.Game;

public class MoveTowards implements Node {
    Constants.DM heuristic;
    String target;

    public MoveTowards(String heuristic, String target) {
        this.heuristic = Constants.DM.valueOf(heuristic);
        this.target = target;
    }

    public Constants.MOVE makeDecision(Game game){
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }
}

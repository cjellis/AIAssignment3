package pacman.controllers.RAP;


import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class MoveToward implements Primitive {
    String target;
    Constants.DM heuristic;

    MoveToward(String target, String heuristic) {
        this.target = target;
        this.heuristic = Constants.DM.valueOf(heuristic);
    }

    public boolean isPrimitive(){
        return true;
    }

    @Override
    public boolean isSuccessful(Game game) {
        return false;
    }

    @Override
    public boolean isValid(Game game) {
        return false;
    }

    @Override
    public ArrayList<String> getActions() {
        return null;
    }

    public Constants.MOVE execute(Game game) {
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }
}

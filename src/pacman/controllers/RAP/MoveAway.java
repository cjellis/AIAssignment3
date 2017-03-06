package pacman.controllers.RAP;


import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class MoveAway implements Primitive {
    String target;
    Constants.DM heuristic;
    String post;
    ArrayList<String> preconditions;
    String parent;

    MoveAway(String target, String heuristic, String post, ArrayList<String> preconditions) {
        this.target = target;
        this.heuristic = Constants.DM.valueOf(heuristic);
        this.post = post;
        this.preconditions = preconditions;
    }

    public boolean isPrimitive(){
        return true;
    }

    @Override
    public String getPostCondition() {
        return this.post;
    }

    public String getId() {
        return "";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getGoal() {
        return "";
    }

    @Override
    public void setParent(String id) {
        this.parent = id;
    }

    @Override
    public String getParent() {
        return this.parent;
    }


    public boolean isValid(Game game) {
        for(String test : preconditions) {
            if(!Util.checkValidityOfTest(test, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> getActions() {
        return null;
    }

    public Constants.MOVE execute(Game game) {
        int local_target = Util.getLocalTarget(game, this.target, this.heuristic);
        return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), local_target, this.heuristic);
    }
}

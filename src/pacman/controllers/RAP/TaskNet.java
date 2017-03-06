package pacman.controllers.RAP;

import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public class TaskNet implements RAPInstance {
    ArrayList<String> actions;
    String postcondition;
    ArrayList<String> preconditions;
    String id;
    String parent = "";
    int priority;
    String goal;

    public TaskNet(String id, int priority, String goal, ArrayList<String> actions, String postcondition, ArrayList<String> preconditions){
        this.actions = actions;
        this.postcondition = postcondition;
        this.preconditions = preconditions;
        this.id = id;
        this.priority = priority;
        this.goal =  goal;
    }

    public boolean isPrimitive(){
        return false;
    }

    public String getPostCondition() {
        return postcondition;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public String getGoal() {
        return this.goal;
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
        return this.actions;
    }

    @Override
    public Constants.MOVE execute(Game game) {
        return null;
    }

}

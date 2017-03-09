package pacman.controllers.RAP;

import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

/**
 * Non primitive RAP
 */
public class TaskNet implements RAPInstance {
    // list of actions to complete this RAP
    private ArrayList<String> actions;
    // post condition - goal to set if wanted
    private String postcondition;
    // preconditions before this RAP can run
    private ArrayList<String> preconditions;
    // id of this RAP
    private String id;
    // id of the parent RAP
    private String parent = "";
    // priority of this RAP
    private int priority;
    // goal of this RAP
    private String goal;

    /**
     * Constructor
     * @param id id for the RAP
     * @param priority priority for the RAP
     * @param goal goal for the RAP
     * @param actions list of actions for the RAP
     * @param postcondition goal to set after this RAP runs
     * @param preconditions preconditions before this RAP can run
     */
    public TaskNet(String id, int priority, String goal, ArrayList<String> actions, String postcondition, ArrayList<String> preconditions){
        this.actions = actions;
        this.postcondition = postcondition;
        this.preconditions = preconditions;
        this.id = id;
        this.priority = priority;
        this.goal =  goal;
    }

    /**
     * Not a primitive
     * @return false
     */
    public boolean isPrimitive(){
        return false;
    }

    /**
     * Get the postcondition for the RAP
     * @return post condition - goal to set
     */
    public String getPostCondition() {
        return postcondition;
    }

    /**
     * Get the Id for the RAP
     * @return id for the rap
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the priority of the RAP
     * @return priority for the RAP
     */
    @Override
    public int getPriority() {
        return this.priority;
    }

    /**
     * Get the goal for the RAP
     * @return goal for the RAP
     */
    @Override
    public String getGoal() {
        return this.goal;
    }

    /**
     * Set the parent for this RAP
     * @param id parent ID for this RAP
     */
    @Override
    public void setParent(String id) {
        this.parent = id;
    }

    /**
     * Get the parent for this RAP
     * @return parent id for this RAP
     */
    @Override
    public String getParent() {
        return this.parent;
    }

    /**
     * Check if the RAP is valid
     * @param game current game state
     * @return if the RAP is valid to run
     */
    public boolean isValid(Game game) {
        for(String test : preconditions) {
            if(!Util.checkValidityOfTest(test, game)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the actions for this RAP
     * @return list of actions to run for this RAP
     */
    @Override
    public ArrayList<String> getActions() {
        return this.actions;
    }

    /**
     * Run execute - not for non primitives
     * @param game current game state
     * @return null
     */
    @Override
    public Constants.MOVE execute(Game game) {
        return null;
    }

}
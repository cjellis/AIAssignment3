package pacman.controllers.RAP;

import pacman.controllers.Util;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

/**
 * Primitive class, implementes RAPInstance, but abstract
 * Subclasses will implement execute
 */
public abstract class Primitive implements RAPInstance {
    // target for the primitive
    protected String target;
    // heuristic to use to find the distance
    protected Constants.DM heuristic;
    // post condition
    protected String post;
    // pre conditions
    protected ArrayList<String> preconditions;
    // parent RAP
    protected String parent;

    /**
     * Is a primitive
     * @return true
     */
    public boolean isPrimitive(){
        return true;
    }

    /**
     * Get the post condition
     * @return the post condition
     */
    @Override
    public String getPostCondition() {
        return this.post;
    }

    /**
     * Primitive does not have an ID
     * @return empty string
     */
    public String getId() {
        return "";
    }

    /**
     * Primitive does not have a priority, always 0
     * @return 0
     */
    @Override
    public int getPriority() {
        return 0;
    }

    /**
     * Primitive does not have a goal
     * @return empty string
     */
    @Override
    public String getGoal() {
        return "";
    }

    /**
     * Set the ID of the parent RAP
     * @param id id of the parent RAP
     */
    @Override
    public void setParent(String id) {
        this.parent = id;
    }

    /**
     * Get the ID of the parent RAP
     * @return id of the parent RAP
     */
    @Override
    public String getParent() {
        return this.parent;
    }

    /**
     * Check if the primitive can be run
     * @param game current game state
     * @return true if can run, false if cannot
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
     * Primitives do not have actions
     * @return null
     */
    @Override
    public ArrayList<String> getActions() {
        return null;
    }

    public abstract Constants.MOVE execute(Game game);
}
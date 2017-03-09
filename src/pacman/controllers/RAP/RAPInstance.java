package pacman.controllers.RAP;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

/**
 * Interface for RAP instances
 */
public interface RAPInstance {
    boolean isPrimitive();
    String getPostCondition();
    boolean isValid(Game game);
    String getId();
    int getPriority();
    String getGoal();
    void setParent(String id);
    String getParent();
    ArrayList<String> getActions();
    Constants.MOVE execute(Game game);
}

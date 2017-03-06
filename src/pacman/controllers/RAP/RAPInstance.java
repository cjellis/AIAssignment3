package pacman.controllers.RAP;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public interface RAPInstance {
    public boolean isPrimitive();
    public String getPostCondition();
    public boolean isValid(Game game);
    public String getId();
    public int getPriority();
    public String getGoal();
    public void setParent(String id);
    public String getParent();
    public ArrayList<String> getActions();
    public Constants.MOVE execute(Game game);
}

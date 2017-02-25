package pacman.controllers.RAP;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

public interface RAPInstance {
    public boolean isPrimitive();
    public boolean isSuccessful(Game game);
    public boolean isValid(Game game);
    public ArrayList<String> getActions();
    public Constants.MOVE execute(Game game);
}

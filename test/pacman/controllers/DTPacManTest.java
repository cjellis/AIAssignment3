package pacman.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static pacman.game.Constants.DELAY;

public class DTPacManTest {
    private DTPacMan pacman;

    @BeforeEach
    public void setup(){
        pacman = new DTPacMan();
    }

    @Test
    public void getMoveTest() {
        Game game = new Game(82347523453L, 1);
        assertEquals(Constants.MOVE.LEFT, pacman.getMove(game, DELAY));
    }
}

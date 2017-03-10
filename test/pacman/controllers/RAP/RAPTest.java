package pacman.controllers.RAP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class RAPTest {
    private String file;
    private RAP rap;

    @BeforeEach
    public void setup() {
        file = "data/rap/rap3";
        rap = new RAP(file);
    }

    @Test
    public void parseFileAndBuildRapsTest(){
        assertEquals(file, rap.file);
        assertEquals(8, rap.raps.size());
        assertEquals(4, rap.nonPrimitives.size());
        assertEquals("", rap.goal);
        assertEquals(3, rap.goals.size());
        assertEquals(3, rap.queuePerGoal.size());
    }

    @Test
    public void executeTest() {
        Game game = new Game(82347523453L, 1);
        assertEquals(Constants.MOVE.LEFT, rap.execute(game));

        rap = new RAP("data/rap/rap4");
        assertEquals(Constants.MOVE.LEFT, rap.execute(game));

        rap = new RAP("data/rap/rap5");
        assertEquals(Constants.MOVE.LEFT, rap.execute(game));
    }
}

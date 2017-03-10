package pacman.controllers.DecisionTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Game;

import static org.junit.jupiter.api.Assertions.*;

public class DecisionTreeTest {
    private Game game;
    private DecisionTree dt;
    private String file;

    @BeforeEach
    public void setup(){
        game = new Game(82347523453L, 1);
        file = "data/decision/dt1";
        dt = new DecisionTree(file);
    }

    @Test
    public void parseFileAndBuildTreeTest(){
        assertEquals(file, dt.file);
        assertNotNull(dt.root);
    }

    @Test
    public void makeDecisionTest(){
        assertEquals(dt.makeDecision(game), dt.root.makeDecision(game));
    }
}

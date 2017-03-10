package pacman.controllers.DecisionTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import static org.junit.jupiter.api.Assertions.*;
public class MoveAwayTest {
    private Game game;
    private MoveAway moveAway;

    @BeforeEach
    public void setup(){
        game = new Game(82347523453L, 1);
        moveAway = new MoveAway(Constants.DM.PATH.toString(), "ClosestAnyPill");
    }

    @Test
    public void makeDecisionTest(){
        assertEquals(Constants.MOVE.RIGHT, moveAway.makeDecision(game));
    }
}

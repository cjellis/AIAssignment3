package pacman.controllers.DecisionTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import static org.junit.jupiter.api.Assertions.*;
public class MoveTowardsTest {
    private Game game;
    private MoveTowards moveTowards;

    @BeforeEach
    public void setup(){
        game = new Game(82347523453L, 1);
        moveTowards = new MoveTowards(Constants.DM.PATH.toString(), "ClosestAnyPill");
    }

    @Test
    public void makeDecisionTest(){
        assertEquals(Constants.MOVE.LEFT, moveTowards.makeDecision(game));
    }
}
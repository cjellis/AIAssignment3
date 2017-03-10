package pacman.controllers.RAP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class MoveAwayTest {
    private MoveAway moveAway;

    @BeforeEach
    public void setup(){
        ArrayList<String> pre = new ArrayList<String>();
        pre.add("powerPillWithinDistance,100");
        moveAway = new MoveAway("ClosestAnyPill", Constants.DM.PATH.toString(), "StayAlive", pre);
    }

    @Test
    public void isPrimitiveTest(){
        assertTrue(moveAway.isPrimitive());
    }

    @Test
    public void getPostConditionTest(){
        assertEquals("StayAlive", moveAway.getPostCondition());
    }

    @Test
    public void getIdTest(){
        assertEquals("", moveAway.getId());
    }

    @Test
    public void getPriorityTest(){
        assertEquals(0, moveAway.getPriority());
    }

    @Test
    public void getGoalTest(){
        assertEquals("", moveAway.getGoal());
    }

    @Test
    public void setAndGetParentTest(){
        moveAway.setParent("3");
        assertEquals("3", moveAway.getParent());
    }

    @Test
    public void isValidTest(){
        Game game = new Game(82347523453L, 1);
        assertTrue(moveAway.isValid(game));

        ArrayList<String> pre = new ArrayList<String>();
        pre.add("!powerPillWithinDistance,100");
        pre.add("!edibleGhostWithinDistance,10");
        moveAway = new MoveAway("ClosestAnyPill", Constants.DM.PATH.toString(), "StayAlive", pre);
        assertFalse(moveAway.isValid(game));
    }

    @Test
    public void getActionsTest(){
        assertNull(moveAway.getActions());
    }

    @Test
    public void executeTest(){
        Game game = new Game(82347523453L, 1);
        assertEquals(Constants.MOVE.RIGHT, moveAway.execute(game));
    }
}

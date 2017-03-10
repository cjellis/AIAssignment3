package pacman.controllers.RAP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class MoveTowardTest {
    private MoveToward moveToward;

    @BeforeEach
    public void setup(){
        ArrayList<String> pre = new ArrayList<String>();
        pre.add("powerPillWithinDistance,100");
        moveToward = new MoveToward("ClosestAnyPill", Constants.DM.PATH.toString(), "StayAlive", pre);
    }

    @Test
    public void isPrimitiveTest(){
        assertTrue(moveToward.isPrimitive());
    }

    @Test
    public void getPostConditionTest(){
        assertEquals("StayAlive", moveToward.getPostCondition());
    }

    @Test
    public void getIdTest(){
        assertEquals("", moveToward.getId());
    }

    @Test
    public void getPriorityTest(){
        assertEquals(0, moveToward.getPriority());
    }

    @Test
    public void getGoalTest(){
        assertEquals("", moveToward.getGoal());
    }

    @Test
    public void setAndGetParentTest(){
        moveToward.setParent("3");
        assertEquals("3", moveToward.getParent());
    }

    @Test
    public void isValidTest(){
        Game game = new Game(82347523453L, 1);
        assertTrue(moveToward.isValid(game));

        ArrayList<String> pre = new ArrayList<String>();
        pre.add("!powerPillWithinDistance,100");
        pre.add("!edibleGhostWithinDistance,10");
        moveToward = new MoveToward("ClosestAnyPill", Constants.DM.PATH.toString(), "StayAlive", pre);
        assertFalse(moveToward.isValid(game));
    }

    @Test
    public void getActionsTest(){
        assertNull(moveToward.getActions());
    }

    @Test
    public void executeTest(){
        Game game = new Game(82347523453L, 1);
        assertEquals(Constants.MOVE.LEFT, moveToward.execute(game));
    }
}

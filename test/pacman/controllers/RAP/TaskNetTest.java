package pacman.controllers.RAP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class TaskNetTest {
    private TaskNet taskNet;

    @BeforeEach
    public void setup(){
        ArrayList<String> actions = new ArrayList<String>();
        actions.add("2");
        ArrayList<String> pre = new ArrayList<String>();
        pre.add("edibleGhostWithinDistance,20");
        taskNet = new TaskNet("1", 1, "StayAlive", actions, "StayAlive", pre);
    }

    @Test
    public void isPrimitiveTest(){
        assertFalse(taskNet.isPrimitive());
    }

    @Test
    public void getPostConditionTest(){
        assertEquals("StayAlive", taskNet.getPostCondition());
    }

    @Test
    public void getIdTest(){
        assertEquals("1", taskNet.getId());
    }

    @Test
    public void getPriorityTest(){
        assertEquals(1, taskNet.getPriority());
    }

    @Test
    public void getGoalTest(){
        assertEquals("StayAlive", taskNet.getGoal());
    }

    @Test
    public void setAndGetParentTest(){
        taskNet.setParent("3");
        assertEquals("3", taskNet.getParent());
    }

    @Test
    public void isValidTest(){
        Game game = new Game(82347523453L, 1);
        assertFalse(taskNet.isValid(game));

        ArrayList<String> actions = new ArrayList<String>();
        actions.add("2");
        ArrayList<String> pre = new ArrayList<String>();
        pre.add("powerPillWithinDistance,100");
        pre.add("!edibleGhostWithinDistance,10");
        taskNet = new TaskNet("1", 1, "StayAlive", actions, "StayAlive", pre);
        assertTrue(taskNet.isValid(game));
    }

    @Test
    public void getActionsTest(){
        assertEquals(1, taskNet.getActions().size());
        assertEquals("2", taskNet.getActions().get(0));
    }

    @Test
    public void executeTest(){
        Game game = new Game(82347523453L, 1);
        assertNull(taskNet.execute(game));
    }

}

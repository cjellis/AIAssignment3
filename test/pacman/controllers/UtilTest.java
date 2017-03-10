package pacman.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @BeforeEach
    public void setup(){

    }

    @Test
    public void getLocalTargetTest(){
        Game game = new Game(82347523453L, 1);
        int target = Util.getLocalTarget(game, "ClosestNonEdibleGhost", Constants.DM.PATH);
        assertEquals(-1, target);
        target = Util.getLocalTarget(game, "ClosestEdibleGhost", Constants.DM.PATH);
        assertEquals(-1, target);
        target = Util.getLocalTarget(game, "ClosestAnyPill", Constants.DM.PATH);
        assertEquals(963, target);
        target = Util.getLocalTarget(game, "ClosestPowerPill", Constants.DM.PATH);
        assertEquals(1083, target);

    }

    @Test
    public void checkValidityOfTestTest(){
        Game game = new Game(82347523453L, 1);
        boolean valid = Util.checkValidityOfTest("nonEdibleGhostWithinDistance,20", game);
        assertFalse(valid);
        valid = Util.checkValidityOfTest("!nonEdibleGhostWithinDistance,20", game);
        assertTrue(valid);
        valid = Util.checkValidityOfTest("edibleGhostWithinDistance,20", game);
        assertFalse(valid);
        valid = Util.checkValidityOfTest("!edibleGhostWithinDistance,20", game);
        assertTrue(valid);
        valid = Util.checkValidityOfTest("powerPillWithinDistance,100", game);
        assertTrue(valid);
        valid = Util.checkValidityOfTest("!powerPillWithinDistance,100", game);
        assertFalse(valid);
        valid = Util.checkValidityOfTest("", game);
        assertFalse(valid);
    }
}

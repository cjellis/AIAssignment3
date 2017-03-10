package pacman.controllers.DecisionTree;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pacman.game.Constants.DELAY;

public class NonEdibleGhostWithinDistanceTest {
    private NonEdibleGhostWithinDistance dec;
    private int val;
    private Game game;

    @BeforeEach
    public void setup(){
        game = new Game(82347523453L, 1);
        Node success = mock(Node.class);
        when(success.makeDecision(game)).thenReturn(Constants.MOVE.DOWN);
        Node failure = mock(Node.class);
        when(failure.makeDecision(game)).thenReturn(Constants.MOVE.UP);
        val = 100;
        dec = new NonEdibleGhostWithinDistance(success,failure,val);
    }

    @Test
    public void makeDecisionTest(){
        assertEquals(Constants.MOVE.UP, dec.makeDecision(game));
        Controller<EnumMap<Constants.GHOST,Constants.MOVE>> ghostController = new StarterGhosts();

        new Thread(ghostController).start();

        while(!game.gameOver()) {
            if(!ghostWithinDistance()) {
                ghostController.update(game.copy(), System.currentTimeMillis() + DELAY);
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                game.advanceGame(Constants.MOVE.NEUTRAL, ghostController.getMove());
            } else {
                assertEquals(Constants.MOVE.DOWN, dec.makeDecision(game));
                break;
            }
        }
        ghostController.terminate();
    }

    private boolean ghostWithinDistance() {
        for(Constants.GHOST ghost : Constants.GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost)==0) {
                if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                        game.getGhostCurrentNodeIndex(ghost)) < this.val) {
                    return true;
                }
            }
        }
        return false;
    }
}

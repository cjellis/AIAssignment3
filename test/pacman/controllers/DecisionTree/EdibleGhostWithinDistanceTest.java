package pacman.controllers.DecisionTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.EnumMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static pacman.game.Constants.DELAY;

public class EdibleGhostWithinDistanceTest {
    private EdibleGhostWithinDistance dec;
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
        dec = new EdibleGhostWithinDistance(success,failure,val);
    }

    @Test
    public void makeDecisionTest(){
        assertEquals(Constants.MOVE.UP, dec.makeDecision(game));
        Controller<EnumMap<Constants.GHOST,Constants.MOVE>> ghostController = new StarterGhosts();
        Controller<Constants.MOVE> pacmanController = new PacmanContr();

        new Thread(pacmanController).start();
        new Thread(ghostController).start();

        while(!game.gameOver()) {
            if(!ghostWithinDistance()) {
                ghostController.update(game.copy(), System.currentTimeMillis() + DELAY);
                pacmanController.update(game.copy(), System.currentTimeMillis() + DELAY);
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                game.advanceGame(pacmanController.getMove(), ghostController.getMove());
            } else {
                assertEquals(Constants.MOVE.DOWN, dec.makeDecision(game));
                break;
            }
        }
        ghostController.terminate();
        pacmanController.terminate();
    }

    private boolean ghostWithinDistance() {
        for(Constants.GHOST ghost : Constants.GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) > 0) {
                if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                        game.getGhostCurrentNodeIndex(ghost)) < this.val) {
                    return true;
                }
            }
        }
        return false;
    }

    public class PacmanContr extends Controller<Constants.MOVE> {

        @Override
        public Constants.MOVE getMove(Game game, long timeDue) {
            for(Constants.GHOST ghost : Constants.GHOST.values()) {
                if (game.getGhostEdibleTime(ghost) > 0) {
                    if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                            game.getGhostCurrentNodeIndex(ghost)) < 120) {
                       return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
                               game.getGhostCurrentNodeIndex(ghost), Constants.DM.PATH);
                    }
                }
            }

            int[] powerPills = game.getPowerPillIndices();

            ArrayList<Integer> targets = new ArrayList<Integer>();

            for (int i = 0; i < powerPills.length; i++)
                if (game.isPowerPillStillAvailable(i))
                    targets.add(powerPills[i]);

            int[] targetsArray = new int[targets.size()];

            for (int i = 0; i < targetsArray.length; i++)
                targetsArray[i] = targets.get(i);

            return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
                    game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(),
                            targetsArray, Constants.DM.PATH), Constants.DM.PATH);

        }
    }
}

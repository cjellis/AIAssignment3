package pacman.controllers;

import pacman.controllers.DecisionTree.DecisionTree;
import pacman.game.Game;

import static pacman.game.Constants.MOVE;

/**
 * Decision Tree based PacMan
 */
public class DTPacMan extends Controller<MOVE>
{	
	private DecisionTree dt;

	public DTPacMan() {
		this.dt = new DecisionTree("data/decision/dt4");
	}
	
	public MOVE getMove(Game game,long timeDue)
	{			
		return this.dt.makeDecision(game);
	}
}
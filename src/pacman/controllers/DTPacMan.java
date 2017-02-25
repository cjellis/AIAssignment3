package pacman.controllers;

import pacman.controllers.DecisionTree.DecisionTree;
import pacman.game.Game;

import java.io.IOException;
import java.util.ArrayList;

import static pacman.game.Constants.DM;
import static pacman.game.Constants.GHOST;
import static pacman.game.Constants.MOVE;

/*
 * Pac-Man controller as part of the starter package - simply upload this file as a zip called
 * MyPacMan.zip and you will be entered into the rankings - as simple as that! Feel free to modify 
 * it or to start from scratch, using the classes supplied with the original software. Best of luck!
 * 
 * This controller utilises 3 tactics, in order of importance:
 * 1. Get away from any non-edible ghost that is in close proximity
 * 2. Go after the nearest edible ghost
 * 3. Go to the nearest pill/power pill
 */
public class DTPacMan extends Controller<MOVE>
{	
	DecisionTree dt;

	public DTPacMan() {
		this.dt = new DecisionTree("data/decision/dt1");
	}
	
	public MOVE getMove(Game game,long timeDue)
	{			
		return this.dt.makeDecision(game);
	}
}
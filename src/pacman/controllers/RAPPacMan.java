package pacman.controllers;

import pacman.controllers.RAP.RAP;
import pacman.game.Game;

import static pacman.game.Constants.MOVE;

/**
 *  RAP based PacMan
 */
public class RAPPacMan extends Controller<MOVE>
{
	private RAP rap;

	public RAPPacMan() {
		this.rap = new RAP("data/rap/rap2");
	}
	
	public MOVE getMove(Game game,long timeDue)
	{
		return this.rap.execute(game);
	}
}
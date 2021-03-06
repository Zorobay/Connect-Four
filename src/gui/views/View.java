package gui.views;

import gui.board.GameBoard;
import javafx.scene.layout.Pane;
import player.PlayerList;

/**
 * An abstract class specifying a view
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public abstract class View{
	
	protected PlayerList playerList;
	protected GameBoard gameBoard;
	
	public View(PlayerList pList, GameBoard gameBoard){
		playerList = pList;
		this.gameBoard = gameBoard;
	}
	/**
	 * Returns the root pane.
	 * @return The pane that contains all UI elements for this view.
	 */
	public abstract Pane getUI();
	
	/**
	 * Adds content to the view. Is to be called right before switching views, by the ViewSwitcher.
	 */
	public abstract void setUp();
}

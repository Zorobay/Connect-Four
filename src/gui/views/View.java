package gui.views;

import gui.board.GameBoard;
import javafx.scene.layout.Pane;
import player.PlayerList;

public abstract class View{
	
	protected PlayerList playerList;
	protected GameBoard gameBoard;
	
	public View(PlayerList pList, GameBoard gameBoard){
		playerList = pList;
		this.gameBoard = gameBoard;
	}
	/**
	 * @return The pane that contains all UI elements for this view.
	 */
	public abstract Pane getUI();
	
	/**
	 * Adds content to the view. Is to be called right before switching views, by the ViewSwitcher.
	 */
	public abstract void setUp();
}

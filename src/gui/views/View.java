package gui.views;

import javafx.scene.layout.Pane;
import player.PlayerList;

public abstract class View{

	protected PlayerList playerList;
	protected ViewSwitcher viewSwitcher;
	
	public View(PlayerList pList, ViewSwitcher switcher){
		this.viewSwitcher = switcher;
		this.playerList = pList;
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

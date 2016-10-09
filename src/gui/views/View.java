package gui.views;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.PlayerList;

public abstract class View{

	protected PlayerList playerList;
	protected ViewSwitcher viewSwitcher;
	
	public View(PlayerList pList, ViewSwitcher switcher){
		this.viewSwitcher = switcher;
		this.playerList = pList;
	}
	
	public abstract Pane getUI();
}

package gui.views;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.PlayerList;

public class GOView extends View{
	
	private Pane pane;
	
	public GOView(PlayerList pList, ViewSwitcher viewSwitcher){
		super(pList, viewSwitcher);
	}

	@Override
	public Pane getUI() {
		return pane;
	}

	@Override
	public void setUp() {
		
		
	}

	
}

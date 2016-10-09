package gui.views;

import gui.board.GameBoard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import player.PlayerList;

public class GameView extends View{

	private GameBoard board;
	private Label currentPlayer;
	private BorderPane pane;
	
	public GameView(PlayerList pList, ViewSwitcher switcher){
		super(pList, switcher);
		
		pane = new BorderPane();
		board = new GameBoard(pList, 7,7);
		
		if(pList.getNumPlayers() > 0)
			currentPlayer = new Label("Current player: " + pList.getActivePlayer().getName());
		else
			currentPlayer = new Label("No players created!");
		
		BorderPane.setAlignment(currentPlayer, Pos.CENTER);
		
		setUp();
	}
	
	private void setUp(){
		currentPlayer.setFont(Font.font(24));
		pane.setCenter(board);
		pane.setTop(currentPlayer);
	}

	@Override
	public Pane getUI() {
		return pane;
	}
}

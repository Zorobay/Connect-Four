package gui.views;

import java.util.Observable;
import java.util.Observer;

import gui.board.GameBoard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import player.PlayerList;

public class GameView extends View implements Observer{

	private GameBoard board;
	private Label currentPlayer;
	private BorderPane pane;
	
	public GameView(PlayerList pList, ViewSwitcher switcher){
		super(pList, switcher);
		
		pane = new BorderPane();
		board = new GameBoard(pList, 7,7);
		board.addObserver(this);
	}
	
	public void setUp(){
		
		if(playerList.getNumPlayers() > 0)
			currentPlayer = new Label("Current player: " + playerList.getActivePlayer().getName());
		else
			currentPlayer = new Label("No players created!");
		
		BorderPane.setAlignment(currentPlayer, Pos.CENTER);
		
		currentPlayer.setFont(Font.font(24));
		pane.setCenter(board.getUI());
		pane.setTop(currentPlayer);
	}

	@Override
	public Pane getUI() {
		return pane;
	}

	@Override
	public void update(Observable board, Object obj) {
		
		if(board == this.board){
			if(((GameBoard) board).gameOver()){
				//end game
			}else{
				playerList.nextPlayer();
			}
		}
		
	}
}

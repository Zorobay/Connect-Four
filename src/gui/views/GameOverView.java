package gui.views;

import gui.board.GameBoard;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import player.PlayerList;

public class GameOverView extends View{
	
	private final BorderPane pane = new BorderPane();
	private final Button rematchButton = new Button("Rematch");
	private final Button newGameButton = new Button("New Game");
	
	public GameOverView(PlayerList pList, GameBoard gBoard){
		super(pList, gBoard);
	}

	@Override
	public Pane getUI() {
		return pane;
	}

	@Override
	public void setUp() {
		HighScoreTable goTable = new HighScoreTable();
		pane.setCenter(goTable.getUI());
		
		//Setup buttons
		rematchButton.setOnMouseClicked(rematchClicked);
		newGameButton.setOnMouseClicked(newGameClicked);
		HBox buttons = new HBox();
		buttons.getChildren().addAll(newGameButton, rematchButton);
		pane.setBottom(buttons);
	}

	private final EventHandler<MouseEvent> newGameClicked = event -> {
		gameBoard.nuke();
		ViewSwitcher.setWelcomeView();
	};
	
	private final EventHandler<MouseEvent> rematchClicked = event -> {
		ViewSwitcher.setGameView();
	};
	
}

package gui.views;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import gui.board.GameBoard;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import logging.HighScoreLogger;
import logging.Logger;
import player.PlayerList;

public class GameView extends View implements Observer{

	private Label currentPlayer;
	private final BorderPane pane = new BorderPane();
	private Alert gameOverDialog;
	private enum GameOverType{WIN, DRAW};
	
	public GameView(PlayerList pList, GameBoard gBoard){
		super(pList, gBoard);
		
		gameBoard = gBoard;
		gameBoard.addObserver(this);
	}
	
	public void setUp(){
		
		if(playerList.getNumPlayers() > 0)
			currentPlayer = new Label(playerList.getActivePlayer().getName());
		else
			currentPlayer = new Label("No players created!");
		
		currentPlayer.setStyle("-fx-text-fill: " + playerList.getActivePlayer().getColorCSS());
		BorderPane.setAlignment(currentPlayer, Pos.CENTER);
		
		pane.setCenter(gameBoard.getUI());
		BorderPane.setAlignment(gameBoard.getUI(), Pos.CENTER);
		pane.setTop(currentPlayer);
	}

	private void showGameOverDialog(GameOverType type, String message){
		gameOverDialog = new Alert(AlertType.CONFIRMATION);
		gameOverDialog.setContentText(null);
		ImageView imgWin = new ImageView(new Image("file:img/win.gif"));
		imgWin.autosize();
		imgWin.setScaleX(0.4);
		imgWin.setScaleY(0.4);
		imgWin.prefHeight(60);
		imgWin.prefWidth(60);
		
		ImageView imgDraw = new ImageView(new Image("file:img/draw.gif"));
		
		if(type == GameOverType.DRAW){
			gameOverDialog.setTitle("Draw!");
			gameOverDialog.setHeaderText(message);
			gameOverDialog.setGraphic(imgDraw);
		}else{
			gameOverDialog.setTitle("Win!");
			gameOverDialog.setHeaderText(message);
			gameOverDialog.setGraphic(imgWin);
		}
		
		ButtonType rematchButton = new ButtonType("Rematch", ButtonData.FINISH);
		ButtonType newGameButton = new ButtonType("New Game");
		ButtonType highScoreButton = new ButtonType("High Score");
		ButtonType quitButton = new ButtonType("Quit");
		gameOverDialog.getButtonTypes().setAll(rematchButton, newGameButton, highScoreButton, quitButton);
		
		DialogPane dialog = gameOverDialog.getDialogPane();
		dialog.getStylesheets().add("file:style/dialog.css");
		dialog.getStyleClass().add("myDialog");
				
		Optional<ButtonType> result = gameOverDialog.showAndWait();
		
		if(result.get() == rematchButton){
			register(type);			
			clearAndRematch();
		}else if(result.get() == newGameButton){
			register(type);	
			clearAndGoToWelcomeView();
		}else if(result.get() == highScoreButton){
			register(type);
			clearAndGoToHighScore();
		}else{ //user pressed quitButton
			register(type);
			Logger.closeLogger();
			System.exit(0);
		}
	}
	
	private void register(GameOverType type){
		if(type == GameOverType.WIN)
			HighScoreLogger.registerWin(gameBoard.getPlayerList());
	}
	
	private void clearAndRematch(){
		gameBoard.resetRematch();
		playerList.newGame();
	}
	
	private void clearAndGoToWelcomeView(){
		gameBoard.nuke();
		playerList.nuke();
		ViewSwitcher.setWelcomeView();
	}
	
	private void clearAndGoToHighScore(){
		gameBoard.resetRematch();
		playerList.newGame();
		ViewSwitcher.setGameOverView();
	}
	
	@Override
	public Pane getUI() {
		return pane;
	}

	@Override
	public void update(Observable board, Object obj) {

		if (board == this.gameBoard) {
			
			if (((GameBoard) board).isGameOver()) {
				String winner = ((GameBoard) board).getPlayerList().getWinner().getName();
				showGameOverDialog(GameOverType.WIN, winner + " won the game!");
			} else if (((GameBoard) board).isDraw()) {
				showGameOverDialog(GameOverType.DRAW, "The game ended in a draw!");
			} else {
				playerList.nextPlayer();
				//Change title text
				currentPlayer.setText(playerList.getActivePlayer().getName());
				currentPlayer.setStyle("-fx-text-fill: " + playerList.getActivePlayer().getColorCSS());
			}
		}

	}
}

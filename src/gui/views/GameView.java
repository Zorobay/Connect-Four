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
import logging.HighScoreLogger;
import logging.Logger;
import player.PlayerList;

/**
 * The view that holds all components during game time.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class GameView extends View implements Observer{

	private Label currentPlayer;
	private final BorderPane pane = new BorderPane();
	private Alert gameOverDialog;
	private enum GameOverType{WIN, DRAW};
	
	/**
	 * Constructor.
	 * @param pList the list of playing Player objects.
	 * @param gBoard the GameBoard to be displayed. 
	 */
	public GameView(PlayerList pList, GameBoard gBoard){
		super(pList, gBoard);
		
		gameBoard = gBoard;
		gameBoard.addObserver(this);
	}
	
	/**
	 * Populates the view with components. 
	 * To be called when PlayerList contains all players.
	 */
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

	/**
	 * Creates and shows a dialog.
	 * The message and icon depends on if a win was registered, or a draw.
	 * @param type what type of message to display e.g. win or draw.
	 * @param message the message text of the dialog.
	 */
	private void showGameOverDialog(GameOverType type, String message){
		gameOverDialog = new Alert(AlertType.CONFIRMATION);
		gameOverDialog.setContentText(null);
		ImageView imgWin = new ImageView(new Image("file:img/win.gif"));
		imgWin.setScaleX(0.4);
		imgWin.setScaleY(0.4);
		
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
	
	/**
	 * If type == GameOverType.WIN then log a win.
	 * @param type type of register
	 */
	private void register(GameOverType type){
		if(type == GameOverType.WIN)
			HighScoreLogger.registerWin(gameBoard.getPlayerList());
	}
	
	/**
	 * Prepares view for a rematch.
	 */
	private void clearAndRematch(){
		gameBoard.resetRematch();
		playerList.newGame();
	}
	
	/**
	 * Nukes internal GameBoard, the PlayerList and switches view to WelcomeView.
	 */
	private void clearAndGoToWelcomeView(){
		gameBoard.nuke();
		playerList.nuke();
		ViewSwitcher.setWelcomeView();
	}
	
	/**
	 * Resets the internal GameBoard and PlayerList.
	 * Switches view to GameOverView.
	 */
	private void clearAndGoToHighScore(){
		gameBoard.resetRematch();
		playerList.newGame();
		ViewSwitcher.setGameOverView();
	}
	
	/**
	 * Returns the pane containing all components for this View.
	 */
	@Override
	public Pane getUI() {
		return pane;
	}

	/**
	 * As a listener to the GameBoard, this method responds to a user move on the game board.
	 * If game over or draw, switches to appropriate View.
	 * If not, sets next player.
	 */
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

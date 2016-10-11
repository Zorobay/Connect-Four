package gui;

import gui.board.GameBoard;
import gui.views.GameOverView;
import gui.views.GameView;
import gui.views.ViewSwitcher;
import gui.views.WelcomeView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logging.Logger;
import player.PlayerList;

/**
 * The main class of connect four.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class Connect4Interface extends Application {

	private Stage primaryStage;
	
	public static void main(String[] args) {

		launch(args);
	}

	/**
	 * Initiates the application and starts it.
	 */
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		initGame();
	}
	
	/**
	 * Creates all views and sets attributes of program.
	 */
	private void initGame(){
		primaryStage.setTitle("Connect Four");
		primaryStage.setOnCloseRequest(beforeExit);
		primaryStage.getIcons().add(new Image("file:img/icon.png"));

		PlayerList pList = new PlayerList();
		GameBoard gBoard = new GameBoard(pList);
		
		//Create all views and add to ViewSwitcher
		WelcomeView wView = new WelcomeView(pList, gBoard);
		GameView gView = new GameView(pList, gBoard);
		GameOverView goView = new GameOverView(pList, gBoard);
		ViewSwitcher.initiateViewSwitcher(primaryStage, wView, gView, goView);
		
		//Set first view
		ViewSwitcher.setWelcomeView();
		
		//Initialize logger
		Logger.initLogger();
		
		primaryStage.show();
	}
	
	/**
	 * Ensures that the Logger reader is closed before exit.
	 */
	private final EventHandler<WindowEvent> beforeExit = event -> {
		Logger.closeLogger();
	};

}
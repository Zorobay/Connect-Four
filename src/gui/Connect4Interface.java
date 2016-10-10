package gui;

import gui.board.GameBoard;
import gui.views.GOView;
import gui.views.GameView;
import gui.views.ViewSwitcher;
import gui.views.WelcomeView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logging.Logger;
import player.PlayerList;

public class Connect4Interface extends Application {

	private Stage primaryStage;
	
	public static void main(String[] args) {

		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		resetGame();
	}
	
	private void resetGame(){
		primaryStage.setTitle("Connect Four");
		primaryStage.setOnCloseRequest(beforeExit);

		PlayerList pList = new PlayerList();
		GameBoard gBoard = new GameBoard(pList);
		
		//Create all views and add to ViewSwitcher
		WelcomeView wView = new WelcomeView(pList, gBoard);
		GameView gView = new GameView(pList, gBoard);
		GOView goView = new GOView(pList, gBoard);
		ViewSwitcher.initiateViewSwitcher(primaryStage, wView, gView, goView);
		
		//Set first view
		ViewSwitcher.setWelcomeView();
		
		//Initialize logger
		Logger.initLogger();
		
		primaryStage.show();
	}
	
	private final EventHandler<WindowEvent> beforeExit = event -> {
		Logger.closeLogger();
	};

}
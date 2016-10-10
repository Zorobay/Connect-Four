package gui;

import gui.board.GameBoard;
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

	public static void main(String[] args) {

		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Test Interface");
		primaryStage.setOnCloseRequest(beforeExit);

		ViewSwitcher switcher = new ViewSwitcher(primaryStage);
		PlayerList pList = new PlayerList();
		GameBoard gBoard = new GameBoard(pList);
		
		//Create all views and add to ViewSwitcher
		WelcomeView wView = new WelcomeView(pList, switcher, gBoard);
		GameView gView = new GameView(pList, switcher, gBoard);
		switcher.setViewOrder(wView, gView);
		
		//Set first view
		switcher.setView(0);
		
		//Initialize logger
		Logger.initLogger();
		
		primaryStage.show();
	}
	
	private final EventHandler<WindowEvent> beforeExit = event -> {
		Logger.closeLogger();
	};

}
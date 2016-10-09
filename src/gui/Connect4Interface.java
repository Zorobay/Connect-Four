package gui;

import gui.views.GameView;
import gui.views.View;
import gui.views.ViewSwitcher;
import gui.views.WelcomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.PlayerList;

public class Connect4Interface extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Test Interface");

		ViewSwitcher switcher = new ViewSwitcher(primaryStage);
		PlayerList pList = new PlayerList();
		
		//Create all views and add to ViewSwitcher
		WelcomeView wView = new WelcomeView(pList, switcher);
		GameView gView = new GameView(pList, switcher);
		switcher.setViewOrder(wView, gView);
		
		//Set first view
		switcher.setView(0);
		
		primaryStage.show();
	}

}
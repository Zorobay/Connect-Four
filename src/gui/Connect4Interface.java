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
		
		WelcomeView wView = new WelcomeView(pList, switcher);
		GameView gView = new GameView(pList, switcher);
		primaryStage.setScene(new Scene(wView.getUI()));
		
		switcher.setViewOrder(wView, gView);
		
		// Create list of scenes

		// Create a scene switcher

		primaryStage.show();
	}

}
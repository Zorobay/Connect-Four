package gui.views;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewSwitcher {

	private static Stage primaryStage;
	private static View welcomeView, gameView, gameOverView;
	private static Scene welcomeScene, gameScene, gameOverScene;

	public static void initiateViewSwitcher(Stage stage, WelcomeView wView, GameView gView, GOView goView) {
		primaryStage = stage;
		ViewSwitcher.welcomeView = wView;
		ViewSwitcher.gameView = gView;
		ViewSwitcher.gameOverView = goView;
	}

	public static void setWelcomeView(){
		welcomeView.setUp();
		
		if(welcomeScene == null)
			welcomeScene = new Scene(welcomeView.getUI());
		welcomeScene.setCursor(Cursor.DEFAULT);
		welcomeScene.getStylesheets().add("file:style/welcomeviewstyle.css");
		primaryStage.setScene(welcomeScene);
	}
	
	public static void setGameView(){
		gameView.setUp();
		
		if(gameScene == null)
			gameScene = new Scene(gameView.getUI());
		gameScene.setCursor(Cursor.NONE); //hide the cursor so we can display players shape
		gameScene.getStylesheets().add("file:style/gameviewstyle.css");
		primaryStage.setScene(gameScene);
	}
	
	public static void setGameOverView(){
		gameOverView.setUp();
		
		if(gameOverScene == null)
			gameOverScene = new Scene(gameOverView.getUI());
		welcomeScene.setCursor(Cursor.DEFAULT);
		gameOverScene.getStylesheets().add("file:style/gameoverviewstyle.css");
		primaryStage.setScene(gameOverScene);
	}
}

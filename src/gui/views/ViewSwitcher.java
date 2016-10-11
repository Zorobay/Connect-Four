package gui.views;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A static class used to switch views
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class ViewSwitcher {

	private static Stage primaryStage;
	private static View welcomeView, gameView, gameOverView;
	private static Scene welcomeScene, gameScene, gameOverScene;

	/**
	 * Specifies the views contained in this view switcher.
	 * @param stage the root note for the application.
	 * @param wView the welcome view, to be shown first
	 * @param gView the game view
	 * @param goView the high score view
	 */
	public static void initiateViewSwitcher(Stage stage, WelcomeView wView, GameView gView, GameOverView goView) {
		primaryStage = stage;
		ViewSwitcher.welcomeView = wView;
		ViewSwitcher.gameView = gView;
		ViewSwitcher.gameOverView = goView;
	}

	/**
	 * Displays welcome view.
	 */
	public static void setWelcomeView(){
		welcomeView.setUp();
		
		if(welcomeScene == null)
			welcomeScene = new Scene(welcomeView.getUI());
		welcomeScene.setCursor(Cursor.DEFAULT);
		welcomeScene.getStylesheets().add("file:style/welcomeviewstyle.css");
		primaryStage.setScene(welcomeScene);
	}
	
	/**
	 * Displays game view.
	 */
	public static void setGameView(){
		gameView.setUp();
		
		if(gameScene == null)
			gameScene = new Scene(gameView.getUI());
		gameScene.setCursor(Cursor.NONE); //hide the cursor so we can display players shape
		gameScene.getStylesheets().add("file:style/gameviewstyle.css");
		primaryStage.setScene(gameScene);
	}
	
	/**
	 * Displays high score view.
	 */
	public static void setGameOverView(){
		gameOverView.setUp();
		
		if(gameOverScene == null)
			gameOverScene = new Scene(gameOverView.getUI());
		welcomeScene.setCursor(Cursor.DEFAULT);
		gameOverScene.getStylesheets().add("file:style/gameoverviewstyle.css");
		primaryStage.setScene(gameOverScene);
	}
}

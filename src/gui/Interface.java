package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logging.Logger;

public class Interface extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Test Interface");

		// set up grid pane
		GameBoard board = new GameBoard(7, 7);

		Scene scene = new Scene(board);
		primaryStage.setScene(scene);
		Logger.registerScene(scene);
		primaryStage.show();
	}

	// Event handler for round brush button. When clicked, select the round
	// brush as active tool
	// private final EventHandler<MouseEvent> roundBrushButtonClicked = event ->
	// {
	// Toolbar.setActiveTool(0);
	// };
}
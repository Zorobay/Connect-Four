package gui;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NewGameScene extends BorderPane{
	
	private TextField tFieldPlayer1;
	private TextField tFieldPlayer2;

	public NewGameScene(){
		super();
		setUp();
	}
	
	private void setUp(){
		VBox tFields = new VBox();
		
		tFieldPlayer1 = new TextField("Enter name of player 1");
		tFieldPlayer2 = new TextField("Enter name of player 2");
		tFields.getChildren().addAll(tFieldPlayer1, tFieldPlayer2);
		
		setTop(tFields);
	}
	
	
	
}

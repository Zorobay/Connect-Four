package gui.board;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import player.PlayerList;
import player.Player;

public class GameBoard extends GridPane{
	
	private GameCell[][] boardGrid;
	private int xSize, ySize;
	
	public GameBoard(PlayerList pList, int xNum, int yNum){
		super();
		boardGrid = new GameCell[xNum][yNum];
		xSize = xNum;
		ySize = yNum;
		
		setUp();
		fillBoard();
	}
	
	private void setUp(){
		setHgap(3);
		setVgap(3);
		setAlignment(Pos.TOP_LEFT);
		setGridLinesVisible(true);

		setPadding(new Insets(25, 25, 25, 25));
	}
	
	private void fillBoard(){
		for(int x = 0; x < xSize; x++){
			for(int y = 1; y < ySize; y++){
				GameCell butt = new GameCell();
				butt.setPrefSize(80,80);
				butt.setOnMouseClicked(gameButtonClicked);
				boardGrid[x][y] = butt;
				add(butt, x, y);
			}
		}
	}
	
	private final EventHandler<MouseEvent> gameButtonClicked = event -> {
		
		((GameCell) event.getSource()).setStyle("-fx-background-color: red");
	};
	
}

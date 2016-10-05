package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import logging.Logger;

public class GameBoard extends GridPane{
	
	private GameButton[][] boardGrid;
	private int xSize, ySize;
	
	public GameBoard(int xNum, int yNum){
		super();
		boardGrid = new GameButton[xNum][yNum];
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
			for(int y = 0; y < ySize; y++){
				GameButton butt = new GameButton();
				boardGrid[x][y] = butt;
				add(butt, x, y);
			}
		}
	}
	
}

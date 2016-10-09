package gui.board;

import java.util.Observable;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import player.PlayerList;

public class GameBoard extends Observable{
	
	private GameCell[][] boardGrid;
	private GridPane gridPane;
	private int rows, columns;
	private PlayerList pList;
	
	public GameBoard(PlayerList pList, int xNum, int yNum){
		super();
		gridPane = new GridPane();
		this.pList = pList;
		rows = xNum;
		columns = yNum;
		
		setUp();
		fillBoard();
	}
	
	
	public boolean gameOver(){
		return gameOver(0,0);
	}
	
	private boolean gameOver(int x, int y, GameCell cell, int amount){
		
		for(int col = y; col >= 0; col++){
			
		}
		if(y + 1 > columns || !boardGrid[x][y + 1].getOwner().equals(cell)) //check vertically up
			return false;
		
		return boardGrid[x + 1][y].getOwner().equals(obj)
	}
	private void setUp(){
		boardGrid = new GameCell[rows][columns];
		
		gridPane.setHgap(3);
		gridPane.setVgap(3);
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setGridLinesVisible(true);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
	}
	
	public Pane getUI(){
		return gridPane;
	}
	
	private void fillBoard(){
		for(int x = 0; x < rows; x++){
			for(int y = 1; y < columns; y++){
				GameCell butt = new GameCell(x,y);
				butt.setPrefSize(80,80);
				butt.setOnMouseClicked(gameCellClicked);
				boardGrid[x][y] = butt;
				gridPane.add(butt, x, y);
			}
		}
	}
	
	/**
	 * 
	 * @param x the horizontal position of the column that you want to drop a coin into
	 * @return the vertical position where the coin should land, or -1 if the column is full
	 */
	private int findFreeSpot(int x){
		for(int y = 0; y < columns; y++){
			if(!boardGrid[y][x].isOwned()){ //find first free spot vertically
				return y;
			}
		}
		//no free spot was found. Column is full
		return -1;
	}
	private final EventHandler<MouseEvent> gameCellClicked = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int y = findFreeSpot(gCell.getX());
		
		if(y != -1){
			gCell.setBackground(new Background(new BackgroundFill(pList.getActivePlayer().getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
			gCell.setOwned(pList.getActivePlayer());
			setChanged();
			notifyObservers();
		}
	};
	
}

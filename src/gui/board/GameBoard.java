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
import logging.Logger;
import player.Player;
import player.PlayerList;

public class GameBoard extends Observable {

	private GameCell[][] boardGrid;
	private GridPane gridPane;
	private int rows, columns;
	private PlayerList playerList;
	private boolean isGameOver = false;

	public GameBoard(PlayerList pList) {
		super();
		gridPane = new GridPane();
		playerList = pList;
	}
	
	public void setRowsAndCols(int rows, int cols){
		this.rows = rows;
		columns = cols;
	

		setUp();
		fillBoard();
	}
	
	private void setUp() {
		boardGrid = new GameCell[rows][columns];

		gridPane.setHgap(3);
		gridPane.setVgap(3);
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setGridLinesVisible(true);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
	}

	public Pane getUI() {
		return gridPane;
	}

	private void fillBoard() {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				GameCell butt = new GameCell(x, y);
				butt.setPrefSize(80, 80);
				butt.setOnMouseClicked(gameCellClicked);
				boardGrid[x][y] = butt;
				gridPane.add(butt, x, rows - y);
			}
		}
	}
	
	public boolean isGameOver(){
		return isGameOver;
	}

	private boolean investigateWinner(Player player, int x, int y) {
		/*
		 * Walk right as far as the owner is player and we're not outside the
		 * board. Then walk left towards the coin. If we can walk 4 steps
		 * without the owner shifting, then player is winner
		 */

		return investigateRightLeft(player, x, y) 
				|| investigateUppDown(player, x, y)
				|| investigateDiagRight(player, x, y)
				|| investigateDiagLeft(player, x, y);

	}

	private boolean investigateRightLeft(Player player, int x, int y) {

		// Find right-most cell that is still owned by player
		int rightLimit = x;
		while (rightLimit + 1 < columns 
				&& boardGrid[rightLimit + 1][y].isOwned() 
				&& boardGrid[rightLimit + 1][y].getOwner().equals(player)) {
			rightLimit++;
		}

		// Walk left and see if we can find 4 cells owned by the same player
		int cellsInARow = 0;
		int pos = rightLimit;
		while (pos >= 0 
				&& boardGrid[pos][y].isOwned()
				&& boardGrid[pos][y].getOwner().equals(player)) {
			cellsInARow++;
			pos--;
		}

		return cellsInARow >= 4;
	}

	private boolean investigateUppDown(Player player, int x, int y) {
		// Find upper-most cell that is still owned by player
		int upperLimit = y;
		while (upperLimit + 1 < rows 
				&& boardGrid[x][upperLimit + 1].isOwned()
				&& boardGrid[x][upperLimit + 1].getOwner().equals(player)) {
			upperLimit++;
		}

		// Walk down and see if we can find 4 cells owned by the same player in a row
		int cellsInARow = 0;
		int pos = upperLimit;
		while (pos >= 0 
				&& boardGrid[x][pos].isOwned()
				&& boardGrid[x][pos].getOwner().equals(player)) {
			cellsInARow++;
			pos--;
		}

		return cellsInARow >= 4;
	}

	private boolean investigateDiagRight(Player player, int x, int y){
		
		//Find right,upper-most cell that is owned by player
		int upperX = x;
		int upperY = y;
		while(upperX + 1< columns 
				&& upperY + 1 < rows 
				&& boardGrid[upperX + 1][upperY + 1].isOwned()
				&& boardGrid[upperX + 1][upperY + 1].getOwner().equals(player)){
			upperX++;
			upperY++;
		}
		
		//Traverse diagonally down (left) and see if we can find 4 owned by player in a row
		int cellsInARow = 0;
		int posx = upperX;
		int posy = upperY;
		while(posx >= 0 
				&& posy >= 0
				&& boardGrid[posx][posy].isOwned()
				&& boardGrid[posx][posy].getOwner().equals(player)){
			cellsInARow++;
			posx--;
			posy--;
		}
		
		return cellsInARow >= 4;
				
	}
	
	private boolean investigateDiagLeft(Player player, int x, int y){
		
		//Find left,upper-most cell that is owned by player
		int upperX = x;
		int upperY = y;
		while(upperX - 1 >= 0 
				&& upperY + 1 < rows 
				&& boardGrid[upperX - 1][upperY + 1].isOwned()
				&& boardGrid[upperX - 1][upperY + 1].getOwner().equals(player)){
			upperX--;
			upperY++;
		}
		
		//Traverse diagonally down (right) and see if we can find 4 owned by player in a row
		int cellsInARow = 0;
		int posx = upperX;
		int posy = upperY;
		while(posx < columns 
				&& posy >= 0
				&& boardGrid[posx][posy].isOwned()
				&& boardGrid[posx][posy].getOwner().equals(player)){
			cellsInARow++;
			posx++;
			posy--;
		}
		
		return cellsInARow >= 4;
				
	}


	/**
	 * Find the first free vertical spot in column x.
	 * @param x the horizontal position of the column that you want to drop a coin into
	 * @return the vertical position where the coin should land, or -1 if the column is full
	 */
	private int findFreeSpot(int x) {
		for (int y = 0; y < columns; y++) {
			if (!boardGrid[x][y].isOwned()) { // find first free spot vertically
				return y;
			}
		}
		// no free spot was found. Column is full
		return -1;
	}

	private final EventHandler<MouseEvent> gameCellClicked = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int y = findFreeSpot(gCell.getX());
		Logger.logUserClick(playerList.getActivePlayer(), gCell.getX(), y);

		if (y != -1) {
			boardGrid[gCell.getX()][y].setBackground(new Background(
					new BackgroundFill(playerList.getActivePlayer().getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
			boardGrid[gCell.getX()][y].setOwned(playerList.getActivePlayer());
			
			//Check win conditions
			if(investigateWinner(playerList.getActivePlayer(), gCell.getX(), y)){
				isGameOver = true;
				Logger.logWin(playerList.getActivePlayer());
			}
			
			setChanged();
			notifyObservers();
		}
	};

}

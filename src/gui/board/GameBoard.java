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
	private boolean isDraw = false;

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
		gridPane.setGridLinesVisible(false);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
	}

	public Pane getUI() {
		return gridPane;
	}
	
	public PlayerList getPlayerList(){
		return playerList;
	}

	private void fillBoard() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				GameCell butt = new GameCell(r, c);
				butt.setPrefSize(80, 80);
				butt.setOnMouseClicked(gameCellClicked);
				butt.setOnMouseEntered(gameCellMouseOver);
				butt.setOnMouseExited(gameCellMouseLeft);
				boardGrid[r][c] = butt;
				gridPane.add(butt, c, r);
			}
		}
	}
	
	public void nuke(){
		isGameOver = false;
		isDraw = false;
		boardGrid = null;
		gridPane.getChildren().clear();
	}
	
	public void resetRematch(){
		isGameOver = false;
		isDraw = false;
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				boardGrid[r][c].reset();
			}
		}
	}
	
	public boolean isGameOver(){
		return isGameOver;
	}
	
	public boolean isDraw(){
		return isDraw;
	}

	private boolean investigateWinner(Player player, int row, int col) {
		/*
		 * Walk right as far as the owner is player and we're not outside the
		 * board. Then walk left towards the coin. If we can walk 4 steps
		 * without the owner shifting, then player is winner
		 */

		return investigateRow(player, row, col)
				|| investigateColumn(player, row, col)
				|| investigateDiagRight(player, row, col)
				|| investigateDiagLeft(player, row, col);
	}

	private boolean investigateRow(Player player, int row, int col) {

		// Find right-most cell that is still owned by player
		int colLimit = col;
		while (colLimit + 1 < columns 
				&& boardGrid[row][colLimit + 1].isOwned() 
				&& boardGrid[row][colLimit + 1].getOwner().equals(player)) {
			colLimit++;
		}

		// Walk left and see if we can find 4 cells owned by the same player
		int cellsInARow = 0;
		int pos = colLimit;
		while (pos >= 0 
				&& boardGrid[row][pos].isOwned()
				&& boardGrid[row][pos].getOwner().equals(player)) {
			cellsInARow++;
			pos--;
		}

		return cellsInARow >= 4;
	}

	private boolean investigateColumn(Player player, int row, int col) {
		// For columns, the upper limit will be the row index
		// Traverse down and see if we can find 4 cells owned by the same player in a row
		int cellsInARow = 0;
		int pos = row;
		while (pos < rows 
				&& boardGrid[pos][col].isOwned()
				&& boardGrid[pos][col].getOwner().equals(player)) {
			cellsInARow++;
			pos++;
		}

		return cellsInARow >= 4;
	}

	private boolean investigateDiagRight(Player player, int row, int col){
		
		//Find right,upper-most cell that is owned by player
		int upperRow = row;
		int upperCol = col;
		while(upperRow + 1 < rows 
				&& upperCol + 1 < columns 
				&& boardGrid[upperRow + 1][upperCol + 1].isOwned()
				&& boardGrid[upperRow + 1][upperCol + 1].getOwner().equals(player)){
			upperRow++;
			upperCol++;
		}
		
		//Traverse diagonally down (left) and see if we can find 4 owned by player in a row
		int cellsInARow = 0;
		int posRow = upperRow;
		int posCol = upperCol;
		while(posRow < rows 
				&& posCol >= 0
				&& boardGrid[posRow][posCol].isOwned()
				&& boardGrid[posRow][posCol].getOwner().equals(player)){
			cellsInARow++;
			posRow++;
			posCol--;
		}
		
		return cellsInARow >= 4;
				
	}
	
	private boolean investigateDiagLeft(Player player, int row, int col){
		
		//Find left,upper-most cell that is owned by player
		int upperRow = row;
		int upperCol = col;
		while(upperRow - 1 >= 0 
				&& upperCol - 1 >= 0 
				&& boardGrid[upperRow - 1][upperCol - 1].isOwned()
				&& boardGrid[upperRow - 1][upperCol - 1].getOwner().equals(player)){
			upperRow--;
			upperCol--;
		}
		
		//Traverse diagonally down (right) and see if we can find 4 owned by player in a row
		int cellsInARow = 0;
		int posRow = upperRow;
		int posCol = upperCol;
		while(posRow < rows 
				&& posCol < columns
				&& boardGrid[posRow][posCol].isOwned()
				&& boardGrid[posRow][posCol].getOwner().equals(player)){
			cellsInARow++;
			posRow++;
			posCol++;
		}
		
		return cellsInARow >= 4;
				
	}

	/**
	 * Checks to see if the entire board is full
	 * @return true if the entire board is owned by a player, else false
	 */
	private boolean investigateDraw(){
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < columns; col++){
				if(!boardGrid[row][col].isOwned())
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Find the first free vertical spot in column col.
	 * @param col the horizontal position of the column that you want to drop a coin into
	 * @return the vertical position where the coin should land, or -1 if the column is full
	 */
	private int findFreeRow(int col) {
		int freeRow = -1;
		while (freeRow + 1 < rows && !boardGrid[freeRow + 1][col].isOwned()) { //Traverse down while there is a free spot
			freeRow++;
		}
		// return freeRow (-1 if column is full)
		return freeRow;
	}

	private final EventHandler<MouseEvent> gameCellClicked = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int row = findFreeRow(gCell.getCol());
		Logger.logUserClick(playerList.getActivePlayer(), row, gCell.getCol());

		if (row != -1) {
			boardGrid[row][gCell.getCol()].setOwned(playerList.getActivePlayer());
			
			//Check win conditions
			if(investigateWinner(playerList.getActivePlayer(), row, gCell.getCol())){
				System.out.println("WINNER: " + playerList.getActivePlayer());
				isGameOver = true;
				playerList.setWinner(playerList.getActivePlayer());
				Logger.logWin(playerList.getWinner());
			}else if(investigateDraw()){
				System.out.println("We have a draw!");
				isDraw = true;
				Logger.logDraw(playerList);
			}
			
			setChanged();
			notifyObservers();
		}
	};

	private final EventHandler<MouseEvent> gameCellMouseOver = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int col = gCell.getCol();
		
		for(int r = 0; r < rows; r++){
			boardGrid[r][col].highlight();
		}
	};
	
	private final EventHandler<MouseEvent> gameCellMouseLeft = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int col = gCell.getCol();
		
		for(int r = 0; r < rows; r++){
			boardGrid[r][col].lowlight();
		}
	};
}

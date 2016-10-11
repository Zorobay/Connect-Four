package gui.board;

import java.util.Observable;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logging.Logger;
import player.Player;
import player.PlayerList;


/**
 * The class that represents the game board component, used in {@link gui.views.GameView}
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 * @see gui.views.GameView
 */

public class GameBoard extends Observable {

	private GameCell[][] boardGrid;
	private GridPane gridPane;
	private int rows, columns;
	private PlayerList playerList;
	private boolean isGameOver = false;
	private boolean isDraw = false;

	/**
	 * Constructor
	 * @param pList A PlayerList object used to get information about who played what move
	 */
	public GameBoard(PlayerList pList) {
		super();
		gridPane = new GridPane();
		playerList = pList;
	}

	/**
	 * Sets the size of the GameBoard and fills it with GameCell objects. 
	 * Also initiates the internal Pane.
	 * @param rows Number of rows
	 * @param cols Number of columns
	 */
	public void setRowsAndCols(int rows, int cols) {
		this.rows = rows;
		columns = cols;

		setUp();
		fillBoard();
	}

	/**
	 * Initiates and sets up the BoardGrid
	 */
	private void setUp() {
		boardGrid = new GameCell[rows][columns];

		gridPane.setHgap(3);
		gridPane.setVgap(3);
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setGridLinesVisible(false);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
	}
	
	/**
	 * Fills the internal matrix with GameCell objects
	 */
	private void fillBoard() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				GameCell butt = new GameCell(r, c);
				butt.setPrefSize(70, 70);
				butt.setOnMouseClicked(gameCellClicked);
				butt.setOnMouseEntered(gameCellMouseOver);
				butt.setOnMouseExited(gameCellMouseLeft);
				boardGrid[r][c] = butt;
				gridPane.add(butt, c, r);
			}
		}
	}

	/**
	 * Gets the internal pane which contains all components to be displayed
	 * @return the internal GridPane
	 */
	public Pane getUI() {
		return gridPane;
	}

	/**
	 * Gets the PlayerList object passed to this object's constructor
	 * @return the internal PlayerList object
	 */
	public PlayerList getPlayerList() {
		return playerList;
	}

	/**
	 * Completely resets the game board, including its size and GameCell objects.
	 * {@link #setRowsAndCols(int, int)} has to be called again to restore board.
	 */
	public void nuke() {
		isGameOver = false;
		isDraw = false;
		boardGrid = null;
		gridPane.getChildren().clear();
	}

	/**
	 * Resets all internal GameCell objects, but keeps its size.
	 */
	public void resetRematch() {
		isGameOver = false;
		isDraw = false;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				boardGrid[r][c].reset();
			}
		}
	}

	/**
	 * Checks if a player has won
	 * @return true if a player has won the game
	 */
	public boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * Checks if the board is full and there is no winner
	 * @return true if the board is full, but there is no winner
	 */
	public boolean isDraw() {
		return isDraw;
	}

	/**
	 * Checks the board to see if a player owns 4 GameCells in a row
	 * @param player the player that last made a move
	 * @param row the row where the token was placed
	 * @param col the column where the token was placed
	 * @return return true if player is a winner
	 */
	private boolean investigateWinner(Player player, int row, int col) {

		return investigateRow(player, row, col) || investigateColumn(player, row, col)
				|| investigateDiagRight(player, row, col) || investigateDiagLeft(player, row, col);
	}

	private boolean investigateRow(Player player, int row, int col) {

		// Find right-most cell that is still owned by player
		int colLimit = col;
		while (colLimit + 1 < columns && boardGrid[row][colLimit + 1].isOwned()
				&& boardGrid[row][colLimit + 1].getOwner().equals(player)) {
			colLimit++;
		}

		// Walk left and see if we can find 4 cells owned by the same player
		int cellsInARow = 0;
		int pos = colLimit;
		while (pos >= 0 && boardGrid[row][pos].isOwned() && boardGrid[row][pos].getOwner().equals(player)) {
			cellsInARow++;
			pos--;
		}

		return cellsInARow >= 4;
	}

	private boolean investigateColumn(Player player, int row, int col) {
		// For columns, the upper limit will be the row index
		// Traverse down and see if we can find 4 cells owned by the same player
		// in a row
		int cellsInARow = 0;
		int pos = row;
		while (pos < rows && boardGrid[pos][col].isOwned() && boardGrid[pos][col].getOwner().equals(player)) {
			cellsInARow++;
			pos++;
		}

		return cellsInARow >= 4;
	}

	private boolean investigateDiagRight(Player player, int row, int col) {

		// Find right,upper-most cell that is owned by player
		int upperRow = row;
		int upperCol = col;
		while (upperRow + 1 < rows && upperCol + 1 < columns && boardGrid[upperRow + 1][upperCol + 1].isOwned()
				&& boardGrid[upperRow + 1][upperCol + 1].getOwner().equals(player)) {
			upperRow++;
			upperCol++;
		}

		// Traverse diagonally down (left) and see if we can find 4 owned by
		// player in a row
		int cellsInARow = 0;
		int posRow = upperRow;
		int posCol = upperCol;
		while (posRow < rows && posCol >= 0 && boardGrid[posRow][posCol].isOwned()
				&& boardGrid[posRow][posCol].getOwner().equals(player)) {
			cellsInARow++;
			posRow++;
			posCol--;
		}

		return cellsInARow >= 4;

	}

	private boolean investigateDiagLeft(Player player, int row, int col) {

		// Find left,upper-most cell that is owned by player
		int upperRow = row;
		int upperCol = col;
		while (upperRow - 1 >= 0 && upperCol - 1 >= 0 && boardGrid[upperRow - 1][upperCol - 1].isOwned()
				&& boardGrid[upperRow - 1][upperCol - 1].getOwner().equals(player)) {
			upperRow--;
			upperCol--;
		}

		// Traverse diagonally down (right) and see if we can find 4 owned by
		// player in a row
		int cellsInARow = 0;
		int posRow = upperRow;
		int posCol = upperCol;
		while (posRow < rows && posCol < columns && boardGrid[posRow][posCol].isOwned()
				&& boardGrid[posRow][posCol].getOwner().equals(player)) {
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
	private boolean investigateDraw() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				if (!boardGrid[row][col].isOwned())
					return false;
			}
		}
		return true;
	}

	/**
	 * Find the first free vertical spot in column col.
	 * 
	 * @param col the column where a token tries to be placed
	 * @return the first free row, or -1 if the column is full
	 */
	private int findFreeRow(int col) {
		int freeRow = -1;
		//Traverse down while there is a free spot
		while (freeRow + 1 < rows && !boardGrid[freeRow + 1][col].isOwned()) { 
			freeRow++;
		}
		// return freeRow (-1 if column is full)
		return freeRow;
	}

	/**
	 * If a game cell is clicked, place token and check if it is a winning move, or a draw.
	 * Switch view if appropriate
	 */
	private final EventHandler<MouseEvent> gameCellClicked = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int row = findFreeRow(gCell.getCol());
		Player active = playerList.getActivePlayer();
		Logger.logUserClick(active, row, gCell.getCol());

		if (row != -1) {
			boardGrid[row][gCell.getCol()].setOwned(active);
			// Check win conditions
			if (investigateWinner(active, row, gCell.getCol())) {
				isGameOver = true;
				playerList.setWinner(active);
				Logger.logWin(active);
			} //Check draw conditions 
			else if (investigateDraw()) {
				isDraw = true;
				Logger.logDraw(playerList);
			}

			setChanged();
			notifyObservers();
		}
	};

	/**
	 * Highlights a column when moused over
	 */
	private final EventHandler<MouseEvent> gameCellMouseOver = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int col = gCell.getCol();

		for (int r = 0; r < rows; r++) {
			boardGrid[r][col].highlight();
		}
	};

	/**
	 * Stops highlighting a column when mouse leaves column
	 */
	private final EventHandler<MouseEvent> gameCellMouseLeft = event -> {
		GameCell gCell = (GameCell) event.getSource();
		int col = gCell.getCol();

		for (int r = 0; r < rows; r++) {
			boardGrid[r][col].lowlight();
		}
	};
}

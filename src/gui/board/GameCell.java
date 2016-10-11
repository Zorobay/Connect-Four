package gui.board;

import javafx.scene.control.Button;
import player.Player;

/**
 * The class that represents a cell in the game board, where a user can play a token.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class GameCell extends Button {
	
	private int row,col;
	private Player owner;
	private boolean owned = false;
	private final String defaultStyle = "-fx-background-color: RED";
	
	/**
	 * Creates a new game cell.
	 * @param row the row where the game cell is placed.
	 * @param col the column where the game cell is placed.
	 */
	public GameCell(int row, int col){
		super();
		setStyle(defaultStyle);
		this.setOpacity(0.4);
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Sets the opacity to 100%.
	 */
	public void highlight(){
		setOpacity(1);
	}
	
	/**
	 * If the cell is not owned, sets the opacity to 40%.
	 */
	public void lowlight(){
		if(!owned)
		setOpacity(0.4);
	}
	
	/**
	 * Checks to see if the cell is owned.
	 * @return true if the cell is owned.
	 */
	public boolean isOwned(){
		return owned;
	}
	
	/**
	 * Sets this cell as owned by player.
	 * @param owner this cells owning Player.
	 */
	public void setOwned(Player owner){
		setOpacity(1);
		setStyle("-fx-background-color: " + owner.getColorCSS());
		owned = true;
		this.owner = owner;
	}
	
	/**
	 * Resets this cells ownership, opacity and style
	 */
	public void reset(){
		setOpacity(0.4);
		owner = null;
		owned = false;
		this.setStyle(defaultStyle);
	}
	
	/**
	 * Gets the player that owns this cell.
	 * @return Player that owns this cell.
	 */
	public Player getOwner(){
		return owner;
	}
	
	/**
	 * Gets the row where this cell is placed according to the constructor parameters.
	 * @return the row where this cell is placed.
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * Gets the column where this cell is placed according to the constructor parameters.
	 * @return the column where this cell is placed.
	 */
	public int getCol(){
		return col;
	}
	
	@Override
	public String toString(){
		return "GameButton(" + "owner: " + owner + ")";
	}
	
}

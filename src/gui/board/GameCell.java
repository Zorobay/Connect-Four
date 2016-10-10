package gui.board;

import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import player.Player;

public class GameCell extends Button {
	
	private int row,col;
	private Player owner;
	private boolean owned = false;
	private final String defaultStyle = "-fx-background-color: RED";
	
	public GameCell(int row, int col){
		super();
		setStyle(defaultStyle);
		this.setOpacity(0.4);
		this.row = row;
		this.col = col;
	}
	
	public void highlight(){
		setOpacity(1);
	}
	
	public void lowlight(){
		if(!owned)
		setOpacity(0.4);
	}
	public boolean isOwned(){
		return owned;
	}
	
	public void setOwned(Player owner){
		setOpacity(1);
		setStyle("-fx-background-color: " + owner.getColorCSS());
		owned = true;
		this.owner = owner;
	}
	
	public void reset(){
		setOpacity(0.4);
		owner = null;
		owned = false;
		this.setStyle(defaultStyle);
	}
	public Player getOwner(){
		return owner;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	@Override
	public String toString(){
		return "GameButton(" + "owner: " + owner + ")";
	}
	
}

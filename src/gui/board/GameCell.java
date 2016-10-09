package gui.board;

import javafx.scene.control.Button;
import player.Player;

//Package private class
class GameCell extends Button {
	
	private int x,y;
	private Player owner;
	private boolean owned;
	
	public GameCell(int x, int y){
		super();
		this.x = x;
		this.y = y;
	}
	
	public boolean isOwned(){
		return owned;
	}
	
	public void setOwned(Player owner){
		owned = true;
		this.owner = owner;
	}
	
	public Player getOwner(){
		return owner;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public String toString(){
		return "GameButton(" + "owner: " + owner + ")";
	}
	
}

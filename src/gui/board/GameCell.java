package gui.board;

import javafx.scene.control.Button;
import player.Player;

public class GameCell extends Button {
	
	private Player owner;
	
	public GameCell(String s){
		super(s);
	}
	
	public GameCell(){
		super();
	}
	
	@Override
	public String toString(){
		return "GameButton(" + "owner: " + owner + ")";
	}
}

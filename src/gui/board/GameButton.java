package gui.board;

import javafx.scene.control.Button;
import player.Player;

public class GameButton extends Button {
	
	private Player owner;
	
	public GameButton(String s){
		super(s);
	}
	
	public GameButton(){
		super();
	}
	
	@Override
	public String toString(){
		return "GameButton(" + "owner: " + owner + ")";
	}
}

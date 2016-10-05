package gui;

import javafx.scene.control.Button;

public class GameButton extends Button {
	
	public GameButton(String s){
		super(s);
	}
	
	public GameButton(){
		super();
	}
	
	@Override
	public String toString(){
		return "GameButton(" + "content" + ")";
	}
}

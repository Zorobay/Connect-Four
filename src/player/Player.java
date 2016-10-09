package player;

import javafx.scene.paint.Color;

public class Player {

	private String name;
	private Color color;
	
	public Player(String name, Color color){
		this.name = name;
		this.color = color;
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Player){
			return ((Player) obj).name.equals(name);
		}else{
			return false;
		}
	}
}

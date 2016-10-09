package player;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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
	
	public Shape getShape(){
		return null;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Player){
			return ((Player) obj).name.equals(name);
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		return "Player(name: " + name + ", color: " + color + ", shape: null)";
	}
}

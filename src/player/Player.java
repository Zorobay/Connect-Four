package player;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * Class representing a player.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class Player {

	private String name;
	private Color color;
	
	/**
	 * Constructor.
	 * @param name name of player.
	 * @param color the color that will be used to identify a players owned cells.
	 */
	public Player(String name, Color color){
		this.name = name;
		this.color = color;
	}
	
	/**
	 * Gets the name of player.
	 * @return the name of player.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the color of player.
	 * @return the Color object of this player.
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * Gets the CSS rgb string of this players color.
	 * 
	 * @return a String representation of this players color in rgb form.
	 */
	public String getColorCSS(){
		int red = (int) (color.getRed()*255);
		int green = (int) (color.getGreen()*255);
		int blue = (int) (color.getBlue()*255);
		return "rgb(" + red + ", " + green + ", " + blue + ")";
	}
	
	@Deprecated
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
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
}

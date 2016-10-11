package gui.guielements;

import javafx.scene.control.TextField;

/**
 * A custom text field that removes its content when the mouse if pressed inside it.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class TextInput extends TextField{

	/**
	 * Constructor.
	 * @param s text to be displayed by default in text field.
	 */
	public TextInput(String s){
		super(s);
		this.setOnMousePressed(handler -> {
			this.clear();
		});
	}
	
	/**
	 * Constructor. Creates a new TextField objet with no text by default.
	 */
	public TextInput(){
		super();
		this.setOnMousePressed(handler -> {
			this.clear();
		});
	}
}

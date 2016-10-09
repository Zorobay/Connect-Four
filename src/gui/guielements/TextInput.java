package gui.guielements;

import javafx.scene.control.TextField;

public class TextInput extends TextField{

	public TextInput(String s){
		super(s);
		this.setOnMousePressed(handler -> {
			this.clear();
		});
	}
	
	public TextInput(){
		super();
		this.setOnMousePressed(handler -> {
			this.clear();
		});
	}
}

package logging;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;

public class Logger {

	private final static List<Control> registeredControls = new ArrayList<Control>();
	private static Scene scene;
	
	public static void registerScene(Scene c){
		scene = c;
		listenToScene();
	}
	
	private static void listenToScene(){
		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent ev){
				System.out.println("This event occured: " + ev.toString());
			}
		});
	}
}

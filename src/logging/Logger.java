package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class Logger {

	private static BufferedWriter fileWriter;
	private static final File logFile = new File("log.txt");
	private static Scene scene;
	
	public static void registerScene(Scene c){
		scene = c;
		listenToScene();
		
		try {
			fileWriter = new BufferedWriter(new FileWriter(logFile, true));
		} catch (IOException e) {
			System.out.println("file log.txt does not exist. Creating new...");
			try {
				fileWriter = new BufferedWriter(new FileWriter(logFile));
			} catch (IOException e1) {
				System.out.println("Could not create new file :(");
			}
		}
	}
	
	private static void listenToScene(){
		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseHandler);
	}
	
	private static EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent ev){
			try {
				System.out.println(ev.toString());
				fileWriter.write(ev.toString());
			} catch (IOException e) {
				System.out.println("Could not write log to file!");
			}
		}
	};
}

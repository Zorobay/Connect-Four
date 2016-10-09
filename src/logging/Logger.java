package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import gui.board.GameCell;
import player.Player;

public class Logger {

	private static BufferedWriter writer;
	private static File logFile;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private static final DateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
	private static Calendar cal;
	
	public static void initLogger(){
		
		cal = Calendar.getInstance()
;		String logName = "log_" + dateFormat.format(cal.getTime());
		logFile = new File(logName);
		
		try {
			writer = new BufferedWriter(new FileWriter(logFile, true));
		} catch (IOException e) {
			System.out.println("Failed to write to " + logName);
		}
	}
	
	public static void logUserClick(Player trigger, int x, int y){
		cal = Calendar.getInstance();
		String logString = dateAndTimeFormat.format(cal.getTime()) + "......" + trigger + " played shape @ " + "x: " + x + ", y: " + y;
		
		print(logString);
	}
	
	public static void logWin(Player winner){
		cal = Calendar.getInstance();
		String logString = dateAndTimeFormat.format(cal.getTime()) + "......" + winner + " Won the game!";

		print(logString);
	}
	
	private static void print(String s){
		try {
			writer.write(s + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeLogger(){
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to close Buffered Writer!");
			e.printStackTrace();
		}
	}
}

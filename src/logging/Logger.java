package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import player.Player;
import player.PlayerList;

/**
 * Static utility class to log user moves.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class Logger {

	private static BufferedWriter writer;
	private static File logFile;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private static final DateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
	private static Calendar cal;
	
	/**
	 * Initializes logger and opens file for writing.
	 */
	public static void initLogger(){
		
		cal = Calendar.getInstance();
		String logName = "log_" + dateFormat.format(cal.getTime());
		logFile = new File(logName);
		
		try {
			writer = new BufferedWriter(new FileWriter(logFile, true));
		} catch (IOException e) {
			System.out.println("Failed to open or create " + logName);
		}
	}
	
	/**
	 * Logs a users placing of a token.
	 * @param trigger Player that placed token.
	 * @param row row where user played token.
	 * @param col column where user played token.
	 */
	public static void logUserClick(Player trigger, int row, int col){
		cal = Calendar.getInstance();
		String logString = dateAndTimeFormat.format(cal.getTime()) + "......" + trigger + " played token @ " + "row: " + row + ", column: " + col;
		
		log(logString);
	}
	
	/**
	 * Logs a win for specified Player.
	 * @param winner Player who won the game.
	 */
	public static void logWin(Player winner){
		cal = Calendar.getInstance();
		String logString = dateAndTimeFormat.format(cal.getTime()) + "......" + winner + " Won the game!";

		log(logString);
	}
	
	/**
	 * Logs a draw.
	 * @param playerList contains players.
	 */
	public static void logDraw(PlayerList playerList){
		cal = Calendar.getInstance();
		String logString = dateAndTimeFormat.format(cal.getTime()) + "......" + "Draw between ";
		
		for(Player p : playerList.getPlayerList())
			logString += p + " and ";
		
		log(logString);
	}
	
	/**
	 * Writes String to file.
	 * @param s String to write to file.
	 */
	private static void log(String s){
		try {
			writer.write(s);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes writer.
	 */
	public static void closeLogger(){
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to close Buffered Writer!");
			e.printStackTrace();
		}
	}
}

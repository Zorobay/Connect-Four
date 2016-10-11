	package logging;

	import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import player.Player;
import player.PlayerList;

/**
 * A static utility class to log high scores.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class HighScoreLogger {

		private static BufferedWriter writer;
		public static final File HIGH_SCORE_FILE = new File("highscore.csv");
		private static HighScore highScore;
		
		/**
		 * Initiates the logger by opening the logger file, and gets a new instance of HighScore.
		 */
		private static void initLogger(){
			
			highScore = HighScore.getInstance(); //read high scores and convert to HighScore
			
			try {
				writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE, false));
			} catch (IOException e) {
				System.out.println("Failed to open or create " + HIGH_SCORE_FILE.getName());
			}
		}
		
		/**
		 * Writes a win to the high score file.
		 * @param playerList contains the winner and looser.
		 */
		public static void registerWin(PlayerList playerList){
			initLogger();
			
			highScore.addWin(playerList.getWinner());
			highScore.addLoss(playerList.getLoser());
			
			log(highScore);
		}
		
		/**
		 * Writes entire HighScore object to file.
		 * @param hs HighScore to be written to high score file.
		 */
		private static void log(HighScore hs){
			try {
				Set<Player> players = hs.getPlayers();
				for(Player p : players){
					writer.write(p.getName() + ", " + hs.getWins(p) + ", " + hs.getLosses(p));
					writer.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			closeLogger();
		}
		
		/**
		 * Closes the writer.
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

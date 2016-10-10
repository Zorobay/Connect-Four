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

public class HighScoreLogger {

		private static BufferedWriter writer;
		public static final File HIGH_SCORE_FILE = new File("highscore.csv");
		private static HighScore highScore;
		private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		private static final DateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
		private static Calendar cal;
		private static boolean hasBeenInitiated = false;
		
		
		private static void initLogger(){
			
			highScore = HighScore.getInstance(); //read high scores and convert to HighScore
			
			try {
				writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE, false));
			} catch (IOException e) {
				System.out.println("Failed to open or create " + HIGH_SCORE_FILE.getName());
			}
			
			hasBeenInitiated = true;
		}
		
		public static void registerWin(PlayerList playerList){
			initLogger();
			
			highScore.addWin(playerList.getWinner());
			highScore.addLoss(playerList.getLoser());
			
			log(highScore);
		}
		
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
		
		public static void closeLogger(){
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("Failed to close Buffered Writer!");
				e.printStackTrace();
			}
		}
	}

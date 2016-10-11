package logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import player.Player;

/**
 * Reads and manages high score from file.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class HighScore {

	private Map<Player, WinLoss> highScore;

	/**
	 * Constructor.
	 * Reads lines from csv file and adds to HashMap.
	 * @param file the file to be read, containing high score.
	 */
	private HighScore(File file) {
		highScore = new HashMap<Player, WinLoss>();

		if (file.exists()) { //if files exists, then read. Else keep highScore empty
			try {
				Files.lines(file.toPath()).forEach(line -> {
					if (!line.isEmpty())
						highScore.put(getPlayer(line), getWinLoss(line));
				});
			} catch (IOException e) {
				System.out.println("Could not read HighScore file:  " + file.getName());
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Returns a new instance of HighScore, with an updated representation of the HighScores.
	 * @return new instance of HighScore.
	 */
	public static HighScore getInstance(){
		return new HighScore(HighScoreLogger.HIGH_SCORE_FILE);
	}
	
	/**
	 * Gets amount of wins for Player.
	 * @param p player whose wins are of interest.
	 * @return amount of total wins for that player.
	 */
	public int getWins(Player p){
		return highScore.get(p).wins;
	}
	
	/**
	 * Gets amount of losses for Player.
	 * @param p player whose losses are of interest.
	 * @return amount of total losses for that player.
	 */
	public int getLosses(Player p){
		return highScore.get(p).losses;
	}
	
	/**
	 * Returns a set of all players found.
	 * @return a Set containing all players found.
	 */
	public Set<Player> getPlayers(){
		return highScore.keySet();
	}
	
	/**
	 * Adds a win to the total amount of wins for a Player.
	 * @param player Player whose wins should be incremented by 1.
	 */
	public void addWin(Player player){
		WinLoss winloss = new WinLoss(0,0);
		
		if(highScore.containsKey(player))
			winloss = highScore.get(player);
			
		//will update wins if player exists, or otherwise create a new mapping for player with 1 win
		winloss.wins += 1;
		highScore.put(player, winloss);
	}
	
	/**
	 * Adds a win to the total amount of losses for a Player.
	 * @param player Player whose losses should be incremented by 1.
	 */
	public void addLoss(Player player){
		WinLoss winloss = new WinLoss(0, 0);

		if (highScore.containsKey(player))
			winloss = highScore.get(player);

		// will update wins if player exists, or otherwise create a new mapping for player with 1 loss
		winloss.losses += 1;
		highScore.put(player, winloss);
	}
	
	/**
	 * Fins the name of player first in a String, and returns a new Player object based on that name.
	 * @param line String containing player name.
	 * @return a new Player object that has the name found in line.
	 */
	private Player getPlayer(String line){
		return new Player(line.replaceAll(" ", "").split(",")[0], null);
	}
	
	/**
	 * Gets the WinLoss object in a String.
	 * @param line String containing the WinLoss data.
	 * @return a new WinLoss object containing the amount of wins and losses found in line.
	 */
	private WinLoss getWinLoss(String line){
		String[] split = line.replaceAll(" ", "").split(",");
		return new WinLoss(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
	}
	
	/**
	 * Class that holds information about total amount of wins and losses for a Player.
	 * @author Sebastian
	 *
	 */
	private class WinLoss{
		
		public int wins, losses;
		public WinLoss(int wins, int losses){
			this.wins = wins;
			this.losses = losses;
		}
		
	}
}

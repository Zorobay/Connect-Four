package logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import player.Player;

public class HighScore {

	private Map<Player, WinLoss> highScore;

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
	
	public static HighScore getInstance(){
		return new HighScore(HighScoreLogger.HIGH_SCORE_FILE);
	}
	public int getWins(Player p){
		return highScore.get(p).wins;
	}
	
	public int getLosses(Player p){
		return highScore.get(p).losses;
	}
	
	public Set<Player> getPlayers(){
		return highScore.keySet();
	}
	
	public void addWin(Player player){
		WinLoss winloss = new WinLoss(0,0);
		
		if(highScore.containsKey(player))
			winloss = highScore.get(player);
			
		//will update wins if player exists, or otherwise create a new mapping for player with 1 win
		winloss.wins += 1;
		highScore.put(player, winloss);
	}
	
	public void addLoss(Player player){
		WinLoss winloss = new WinLoss(0, 0);

		if (highScore.containsKey(player))
			winloss = highScore.get(player);

		// will update wins if player exists, or otherwise create a new mapping for player with 1 loss
		winloss.losses += 1;
		highScore.put(player, winloss);
	}
	
	private Player getPlayer(String line){
		return new Player(line.replaceAll(" ", "").split(",")[0], null);
	}
	
	private WinLoss getWinLoss(String line){
		String[] split = line.replaceAll(" ", "").split(",");
		return new WinLoss(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
	}
	
	private class WinLoss{
		
		public int wins, losses;
		public WinLoss(int wins, int losses){
			this.wins = wins;
			this.losses = losses;
		}
		
	}
}

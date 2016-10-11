package player;

/**
 * Container of Players. Keeps track of who is the winner, loser and next player.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class PlayerList {
	
	private Player activePlayer = null;
	private Player winner, loser = null;
	private Player[] players = {};
	private int counter = 0;
	
	/**
	 * Prepares for a new game.
	 */
	public void newGame(){
		if(counter == 1)
			counter = 0;
		else
			counter = 1;
		
		winner = null;
		loser = null;
		activePlayer = players[counter];
	}
	
	/**
	 * Completely reset this object.
	 */
	public void nuke(){
		counter = 0;
		winner = null;
		loser = null;
		players = null;
		activePlayer = null;
	}
	
	/**
	 * Add Players to this PlayerList.
	 * @param playerList Array of players to be added.
	 */
	public void setPlayerList(Player[] playerList){
		players = playerList;
	}
	
	/**
	 * Gets the list of Players contained in this object.
	 * @return an Array of Player objects contained in this object 
	 * or empty Array if no players have been set.
	 */
	public Player[] getPlayerList(){
		return players;
	}
	
	/**
	 * Sets which player is currently active.
	 * @param player currently active Player.
	 */
	public void setActive(Player player){
		activePlayer = player;
	}
	
	/**
	 * Gets the currently active Player.
	 * @return currently active Player or null if player list has not been set.
	 */
	public Player getActivePlayer(){
		return activePlayer;
	}
	
	/**
	 * Sets which Player is winner. Also sets the loser.
	 * @param winner Player who is winner.
	 */
	public void setWinner(Player winner){
		this.winner = winner;
		if(players[0].equals(winner))
			setLoser(players[1]);
		else
			setLoser(players[0]);
				
	}
	
	/**
	 * Returns the player who is set as winner.
	 * @return winner, or null if no winner has been set.
	 */
	public Player getWinner(){
		return winner;
	}
	
	/**
	 * Sets which player is loser.
	 * @param loser Player who is loser
	 */
	public void setLoser(Player loser){
		this.loser = loser;
	}
	
	/**
	 * Gets loser.
	 * @return loser or null if no winner or loser have been set.
	 */
	public Player getLoser(){
		return loser;
	}
	
	/**
	 * Gets number of players registered.
	 * @return numer of players registered.
	 */
	public int getNumPlayers(){
		return players.length;
	}
	
	/**
	 * Sets next player to next Player in specified Array of Players.
	 */
	public void nextPlayer(){
		if(counter >= getNumPlayers() - 1){
			counter = 0;
		}else{
			counter++;
		}

		setActive(players[counter]);
	}
	

}

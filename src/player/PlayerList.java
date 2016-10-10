package player;

public class PlayerList {
	
	private Player activePlayer = null;
	private Player winner, loser = null;
	private Player[] players = {};
	private int counter = 0;
	
	public void newGame(){
		if(counter == 1)
			counter = 0;
		else
			counter = 1;
		
		winner = null;
		loser = null;
		activePlayer = players[counter];
	}
	
	public void nuke(){
		counter = 0;
		winner = null;
		loser = null;
		players = null;
		activePlayer = null;
	}
	
	public void setPlayerList(Player[] playerList){
		players = playerList;
	}
	
	public Player[] getPlayerList(){
		return players;
	}
	
	public void setActive(Player player){
		activePlayer = player;
	}
	
	public Player getActivePlayer(){
		return activePlayer;
	}
	
	public void setWinner(Player winner){
		this.winner = winner;
		if(players[0].equals(winner))
			setLoser(players[1]);
		else
			setLoser(players[0]);
				
	}
	
	public Player getWinner(){
		return winner;
	}
	
	public void setLoser(Player loser){
		this.loser = loser;
	}
	
	public Player getLoser(){
		return loser;
	}
	
	public int getNumPlayers(){
		return players.length;
	}
	
	public void nextPlayer(){
		if(counter >= getNumPlayers() - 1){
			counter = 0;
		}else{
			counter++;
		}

		setActive(players[counter]);
	}
	

}

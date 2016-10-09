package player;

public class PlayerList {
	
	private Player activePlayer = null;
	private Player[] players = {};
	private int counter = 0;
	
	public void setPlayerList(Player[] playerList){
		players = playerList;
	}
	
	public void setActive(Player player){
		activePlayer = player;
	}
	
	public Player getActivePlayer(){
		return activePlayer;
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

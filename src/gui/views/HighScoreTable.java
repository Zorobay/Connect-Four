package gui.views;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logging.HighScore;
import player.Player;

/**
 * A components that represents a table showing the high score.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class HighScoreTable {

	private TableView<Entry> table = new TableView<Entry>();
	private TableColumn<Entry, String> playerNameCol;
	private TableColumn<Entry, Integer> winCol, loseCol;
	private HighScore highScore;
	
	private final ObservableList<Entry> data = FXCollections.observableArrayList();
	
	/**
	 * Constructor. 
	 * Creates a new table and populates it with columns and fills it with data. 
	 */
	public HighScoreTable() {
		table.setEditable(false);
		highScore = HighScore.getInstance();

		playerNameCol = new TableColumn<Entry, String>("Player Name");
		playerNameCol.setPrefWidth(240);
		winCol = new TableColumn<Entry, Integer>("Wins");
		winCol.setPrefWidth(100);
		loseCol = new TableColumn<Entry, Integer>("Loses");
		loseCol.setPrefWidth(100);
		
		playerNameCol.setCellValueFactory(
			    new PropertyValueFactory<Entry,String>("name"));
		winCol.setCellValueFactory(
			    new PropertyValueFactory<Entry,Integer>("wins"));
		loseCol.setCellValueFactory(
			    new PropertyValueFactory<Entry,Integer>("losses"));
		
		getData();
		table.setItems(data);
		table.getColumns().addAll(playerNameCol, winCol, loseCol);
		//sort based on wins by default
		winCol.setSortType(SortType.DESCENDING);
		table.getSortOrder().add(winCol);
	}
	
	/**
	 * Gets data for each player.
	 */
	private void getData(){
		for(Player p : highScore.getPlayers()){
			data.add(new Entry(p.getName(), highScore.getWins(p), highScore.getLosses(p)));
		}
	}
	
	/**
	 * Gets the javaFX table.
	 * @return the table
	 */
	public TableView<Entry> getUI(){
		return table;
	}
	
	/**
	 * An inner class representing an high score entry. 
	 * Contains name of player, wins and losses.
	 * @author Sebastian Hegardt
	 */
	public static class Entry{
		
		private final SimpleStringProperty name;
		private final SimpleIntegerProperty wins;
		private final SimpleIntegerProperty losses;
		
		private Entry(String name, int wins, int losses){
			this.name = new SimpleStringProperty(name);
			this.wins = new SimpleIntegerProperty(wins);
			this.losses = new SimpleIntegerProperty(losses);
		}
		
		public String getName(){
			return name.get();
		}
		
		public int getWins(){
			return wins.get();
		}
		
		public int getLosses(){
			return losses.get();
		}
	}

}

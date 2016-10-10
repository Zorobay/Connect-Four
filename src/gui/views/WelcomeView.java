package gui.views;

import gui.board.GameBoard;
import gui.guielements.TextInput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import player.Player;
import player.PlayerList;

public class WelcomeView extends View {

	private final BorderPane pane = new BorderPane();
	private TextInput tFieldPlayer1;
	private TextInput tFieldPlayer2;
	private Label title;
	private Button goButton;
	private ListView<Integer> rowList, colList;
	private GameBoard gBoard;

	public WelcomeView(PlayerList pList, ViewSwitcher switcher, GameBoard gBoard) {
		super(pList, switcher);
		this.gBoard = gBoard;
	}

	public void setUp() {
		pane.setPadding(new Insets(10,10,10,10));
		
		VBox centralControls = new VBox(10); // VBox for central controls
		HBox rowSizeControls = new HBox(10); //HBox for controls to choose row size
		rowSizeControls.setAlignment(Pos.CENTER);

		tFieldPlayer1 = new TextInput("Enter name of player 1");
		tFieldPlayer2 = new TextInput("Enter name of player 2");
		title = new Label("Connect 4 - New Game");
		title.setFont(Font.font("Cambria", 32));
		goButton = new Button("GO!");
		goButton.setOnMouseClicked(goButtonClicked);
		
		//Setup function to choose game board size
		rowList = new ListView<Integer>();
		rowList.setPrefSize(80, 120);
		colList = new ListView<Integer>();
		colList.setPrefSize(80, 120);
		ObservableList<Integer> choices = FXCollections.observableArrayList(4,5,6,7,8,9,10);
		rowList.setItems(choices);
		rowList.getSelectionModel().select(3);
		colList.setItems(choices);
		colList.getSelectionModel().select(3);
		rowSizeControls.getChildren().addAll(new VBox(new Label("rows"), rowList), new VBox(new Label("columns"), colList));

		centralControls.getChildren().addAll(tFieldPlayer1, tFieldPlayer2, rowSizeControls);
		centralControls.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setAlignment(goButton, Pos.BOTTOM_RIGHT);
		pane.setTop(title);
		pane.setCenter(centralControls);
		pane.setBottom(goButton);
	}

	@Override
	public Pane getUI() {
		return pane;
	}

	private final EventHandler<MouseEvent> goButtonClicked = event -> {
		Player[] players = { new Player(tFieldPlayer1.getText(), Color.ALICEBLUE),
				new Player(tFieldPlayer2.getText(), Color.RED) };
		playerList.setPlayerList(players);
		playerList.setActive(players[0]);
		gBoard.setRowsAndCols(rowList.getSelectionModel().getSelectedItem(), colList.getSelectionModel().getSelectedItem());
		viewSwitcher.nextView();
	};
	
	@Override
	public String toString(){return "Welcome View";}

}

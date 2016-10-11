package gui.views;

import gui.board.GameBoard;
import gui.guielements.TextInput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import player.Player;
import player.PlayerList;

/**
 * The first view to be displayed for the user.
 *
 * @author Sebastian Hegardt
 * @version 1.0
 * @since 2016-10-11
 */
public class WelcomeView extends View {

	private final BorderPane pane = new BorderPane();
	private TextInput tFieldPlayer1;
	private TextInput tFieldPlayer2;
	private Label title;
	private Button goButton, highScoreButton;
	private ListView<Integer> rowList, colList;
	private ColorPicker colorPicker1, colorPicker2;

	public WelcomeView(PlayerList playerList, GameBoard gameBoard){
		super(playerList, gameBoard);
	}
	
	/**
	 * Adds content to the view. Is to be called right before switching views, by the ViewSwitcher.
	 */
	@Override
	public void setUp() {

		pane.setPadding(new Insets(10, 10, 10, 10));

		VBox centralControls = new VBox(10); // VBox for central controls
		HBox rowSizeControls = new HBox(10); // HBox for controls to choose row size
		rowSizeControls.setAlignment(Pos.CENTER);

		title = new Label("Connect Four - New Game");
		title.setStyle("-fx-font-size: 24pt");

		setUpColorPickers();
		
		// Setup player 1 input
		tFieldPlayer1 = new TextInput("Enter name of player 1");
		tFieldPlayer1.setPrefColumnCount(25);
		HBox player1Box = new HBox();
		player1Box.getChildren().addAll(tFieldPlayer1, colorPicker1);
		player1Box.setAlignment(Pos.CENTER);

		// Setup player2 input
		tFieldPlayer2 = new TextInput("Enter name of player 2");
		tFieldPlayer2.setPrefColumnCount(25);
		HBox player2Box = new HBox();
		player2Box.getChildren().addAll(tFieldPlayer2, colorPicker2);
		player2Box.setAlignment(Pos.CENTER);

		setUpBoardSizeControls(rowSizeControls);

		//Setup buttons
		goButton = new Button("GO!");
		goButton.setOnMouseClicked(goButtonClicked);
		highScoreButton = new Button("High Score");
		highScoreButton.setOnAction(highScoreButtonClicked);
		HBox buttons = new HBox();
		buttons.getChildren().addAll(highScoreButton, goButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);

		centralControls.getChildren().addAll(player1Box, player2Box, rowSizeControls);
		centralControls.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(title, Pos.CENTER);
		pane.setTop(title);
		pane.setCenter(centralControls);
		pane.setBottom(buttons);
	}
	
	/**
	 * Sets up the two color pickers.
	 */
	private void setUpColorPickers() {
		colorPicker1 = new ColorPicker(Color.GREEN);
		colorPicker1.setPrefWidth(60);
		colorPicker2 = new ColorPicker(Color.BLUE);
		colorPicker2.setPrefWidth(60);
	}
	
	/**
	 * Sets up the two ListView objects that control the board size.
	 * @param rowSizeControls the HBox which the controls will be added to.
	 */
	private void setUpBoardSizeControls(HBox rowSizeControls) {
		// Setup function to choose game board size
		rowList = new ListView<Integer>();
		rowList.setPrefSize(100, 200);
		colList = new ListView<Integer>();
		colList.setPrefSize(100, 200);
		ObservableList<Integer> choices = FXCollections.observableArrayList(4, 5, 6, 7, 8, 9, 10);
		rowList.setItems(choices);
		rowList.getSelectionModel().select(3);
		colList.setItems(choices);
		colList.getSelectionModel().select(3);
		rowSizeControls.getChildren().addAll(new VBox(new Label("Rows"), rowList),
				new VBox(new Label("Columns"), colList));
	}

	/**
	 * Returns the root pane.
	 * @return The pane that contains all UI elements for this view.
	 */
	@Override
	public Pane getUI() {
		return pane;
	}

	/**
	 * This event handler is fired when the go button is clicked.
	 */
	private final EventHandler<MouseEvent> goButtonClicked = event -> {
		Player[] players = { new Player(tFieldPlayer1.getText(), colorPicker1.getValue()),
				new Player(tFieldPlayer2.getText(), colorPicker2.getValue()) };
		playerList.setPlayerList(players);
		playerList.setActive(players[0]);
		gameBoard.setRowsAndCols(rowList.getSelectionModel().getSelectedItem(),
				colList.getSelectionModel().getSelectedItem());
		ViewSwitcher.setGameView();
	};
	
	/**
	 * This event handler is fired when the high score button is clicked.
	 */
	private final EventHandler<ActionEvent> highScoreButtonClicked = event -> {
		ViewSwitcher.setGameOverView();
	};
}

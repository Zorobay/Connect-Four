package gui.views;

import gui.guielements.TextInput;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

	public WelcomeView(PlayerList pList, ViewSwitcher switcher) {
		super(pList, switcher);
	}

	public void setUp() {
		VBox tFields = new VBox(10); // create and set spacing for VBox

		tFieldPlayer1 = new TextInput("Enter name of player 1");
		tFieldPlayer2 = new TextInput("Enter name of player 2");
		title = new Label("Connect 4 - New Game");
		title.setFont(Font.font("Cambria", 32));
		goButton = new Button("GO!");
		goButton.setOnMouseClicked(goButtonClicked);

		tFields.getChildren().addAll(tFieldPlayer1, tFieldPlayer2, goButton);
		tFields.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(title, Pos.CENTER);
		pane.setTop(title);
		pane.setCenter(tFields);
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
		viewSwitcher.nextView();
	};
	
	@Override
	public String toString(){return "Welcome View";}

}

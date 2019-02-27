package poker.version_graphics.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import poker.version_graphics.PokerGame;
import poker.version_graphics.controller.PokerGameController;
import poker.version_graphics.model.PokerGameModel;

public class PokerGameView {
	private HBox players;
	private ControlArea controls;
	
	private PokerGameModel model;
	private MenuBar menu;
	
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		
		// Create all of the player panes we need, and put them into an HBox
		players = new HBox();
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			PlayerPane pp = new PlayerPane();
			pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
			players.getChildren().add(pp);
		}
		
		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic

		// Create MenuItems and MenuBar
		menu = new MenuBar();
		Menu menu1 = new Menu("Choose Color");
		MenuItem red = new MenuItem("Red");
		MenuItem blue = new MenuItem("Blue");
		MenuItem black = new MenuItem("Black");
		MenuItem purple = new MenuItem("Purple");

		menu1.getItems().add(blue);
		menu1.getItems().add(black);
		menu1.getItems().add(purple);
		menu1.getItems().add(red);
		menu.getMenus().add(menu1);
		
		// Put players and controls into a BorderPane
		BorderPane root = new BorderPane();
		root.setCenter(players);
		root.setBottom(controls);
		root.setTop(menu);
		
		// Disallow resizing - which is difficult to get right with images
		stage.setResizable(false);

        // Create the scene using our layout; then display it
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("poker.css").toExternalForm());
        stage.setTitle("Poker Miniproject");

        //create entry scene
		Button enter = new Button("Start Game");
		enter.setOnAction(event -> {
			stage.setScene(scene);
		});
		Label enterNames = new Label("Enter Player Names!");
		HBox div = new HBox();

		div.getChildren().add(enter);
		div.getChildren().add(enterNames);
		Scene entry = new Scene(div);

		entry.getStylesheets().add(
				getClass().getResource("poker.css").toExternalForm());




        stage.setScene(entry);
        stage.show();		
	}
	
	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) players.getChildren().get(i);
	}
	
	public Button getShuffleButton() {
		return controls.btnShuffle;
	}
	
	public Button getDealButton() {
		return controls.btnDeal;
	}
	public MenuItem getColorChoice(int i){
		return menu.getMenus().get(0).getItems().get(i);
	}
}

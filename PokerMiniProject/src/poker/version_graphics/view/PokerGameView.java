package poker.version_graphics.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.PokerGameModel;

public class PokerGameView {
	private GridPane players;
	private ControlArea controls;
	
	private PokerGameModel model;
	private MenuBar menu;
	private Stage stage;
	
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		this.stage = stage;
		
		// Create all of the player panes we need, and put them into an Gridpane
		players = new GridPane();

		for (int i = 0; i < PokerGame.numPlayers; i++) {
			PlayerPane pp = new PlayerPane();
			pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
			if(PokerGame.numPlayers<4) players.add(pp,0,i);
			else {
				if(i<2){
				players.add(pp,0,i);
				} else players.add(pp,1,i-2);
			}


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

		Menu menu2 = new Menu("Choose Player Number");
		MenuItem twoPlayer = new MenuItem("2 Players");
		MenuItem threePlayer = new MenuItem("3 Players");
		MenuItem fourPlayer = new MenuItem("4 Players");


		menu1.getItems().addAll(blue, black, purple, red);
		menu2.getItems().addAll(twoPlayer,threePlayer,fourPlayer);
		menu.getMenus().addAll(menu1, menu2);
		
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

        stage.setScene(scene);
        stage.show();		
	}

	public void stop(){
		stage.hide();
	}
	
	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) players.getChildren().get(i);
	}
	
	public Button getShuffleButton() {
		return controls.btnShuffle;
	}
	public Button getWinnerButton(){ return controls.btnShowWinner; }
	public Button getDealButton() {
		return controls.btnDeal;
	}
	public MenuItem getColorChoice(int i){
		return menu.getMenus().get(0).getItems().get(i);
	}
	public MenuItem getPlayerNum(int i){ return menu.getMenus().get(1).getItems().get(i);}
}

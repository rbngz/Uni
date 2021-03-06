package poker.version_graphics.view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.DeckOfCards;

public class ControlArea extends HBox{
    private DeckLabel lblDeck = new DeckLabel();
    private Region spacer = new Region(); // Empty spacer
    Button btnShuffle = new Button("Shuffle");
    Button btnDeal = new Button("Deal");
    Button btnShowWinner = new Button("Show Winner");

    public ControlArea() {
    	super(); // Always call super-constructor first !!
    	this.getChildren().addAll(lblDeck, spacer, btnShuffle, btnDeal,btnShowWinner);

        HBox.setHgrow(spacer, Priority.ALWAYS); // Use region to absorb resizing
        this.setId("controlArea"); // Unique ID in the CSS
    }
    
    public void linkDeck(DeckOfCards deck) {
    	lblDeck.setDeck(deck);
    	deck.getCardsRemainingProperty().addListener(
                ((observable, oldValue, newValue) -> {if(PokerGame.numPlayers*5>(int)newValue){
                btnDeal.setDisable(true);} else btnDeal.setDisable(false);
                })
        );
    }
}

package poker.version_graphics.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Card;
import poker.version_graphics.model.DeckOfCards;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.PokerGameModel;
import poker.version_graphics.view.CardLabel;
import poker.version_graphics.view.PlayerPane;
import poker.version_graphics.view.PokerGameView;

public class PokerGameController {
	private PokerGameModel model;
	private PokerGameView view;
	
	public PokerGameController(PokerGameModel model, PokerGameView view) {
		this.model = model;
		this.view = view;
		
		view.getShuffleButton().setOnAction( e -> shuffle() );
		view.getDealButton().setOnAction( e -> deal() );
		view.getWinnerButton().setOnAction(event -> showWinner());
		//assign action for color selection
		for (int i = 0; i< 4;i++) {
			int index = i;
			view.getColorChoice(i).setOnAction(event -> {
				CardLabel.setColor("card_back_" + view.getColorChoice(index).getText().toLowerCase() + ".png");
				shuffle();
			});
		}
		//assign action for player number selection
		for (int i = 0;i<3;i++) {
			int index = i;
			view.getPlayerNum(i).setOnAction(event -> {
				PokerGame.numPlayers = Integer.parseInt(view.getPlayerNum(index).getText());
				this.view.stop();
				PokerGameModel newModel = new PokerGameModel();
				PokerGameView newView = new PokerGameView(new Stage(), newModel);
				new PokerGameController(newModel, newView);
			});
		}


	}



    /**
     * Remove all cards from players hands, and shuffle the deck
     */
    private void shuffle() {
    	for (int i = 0; i < PokerGame.numPlayers; i++) {
    		Player p = model.getPlayer(i);
    		p.discardHand();
    		PlayerPane pp = view.getPlayerPane(i);
    		pp.updatePlayerDisplay();
    	}

    	model.getDeck().shuffle();
    }
    
    /**
     * Deal each player five cards, then evaluate the two hands
     */
    private void deal() {
    	int cardsRequired = PokerGame.numPlayers * Player.HAND_SIZE;
    	DeckOfCards deck = model.getDeck();
    	if (cardsRequired <= deck.getCardsRemaining()) {
        	for (int i = 0; i < PokerGame.numPlayers; i++) {
        		Player p = model.getPlayer(i);
        		p.discardHand();
        		for (int j = 0; j < Player.HAND_SIZE; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        		}
        		p.evaluateHand();

        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
		} else {
            Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
            alert.showAndWait();
    	}
    }
    private void showWinner(){
		//Comparing hands and evaluating the best Hand
		Player winner = model.getPlayer(0);
		PlayerPane winnerPane = null;
		for(int i = 1; i<PokerGame.numPlayers;i++){
			if(winner.compareTo(model.getPlayer(i))<0){
				winner = model.getPlayer(i);
				winnerPane = view.getPlayerPane(i);
				i = 1;
			}
		}

		if(winnerPane !=null) {
			winnerPane.setWinner();

			//See if anyone else has the same Hand
			for (int i = 0; i < PokerGame.numPlayers; i++) {
				if (winner.evaluateHand().equals(model.getPlayer(i).evaluateHand())) {
					view.getPlayerPane(i).setWinner();
				}
			}
		}

	}

}

package poker.version_graphics.view;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import poker.version_graphics.model.Card;
import poker.version_graphics.model.HandType;
import poker.version_graphics.model.Player;

import java.sql.Time;

public class PlayerPane extends VBox {
    private Label lblName = new Label();
    private HBox hboxCards = new HBox();
    public Label lblEvaluation = new Label("--");

    Timeline timeline = new Timeline();


    // Link to player object
    private Player player;
    
    public PlayerPane() {
        super(); // Always call super-constructor first !!
        this.getStyleClass().add("player"); // CSS style class
        
        // Add child nodes
        this.getChildren().addAll(lblName, hboxCards, lblEvaluation);
        lblName.getStyleClass().add("on-grass");
        lblEvaluation.getStyleClass().add("on-grass");

        // Add CardLabels for the cards
        for (int i = 0; i < 5; i++) {
            Label lblCard = new CardLabel();
            hboxCards.getChildren().add(lblCard);
        }
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    	updatePlayerDisplay(); // Immediately display the player information
    }
    public Player getPlayer(){
        return player;
    }
    
    public void updatePlayerDisplay() {
        lblName.setText(player.getPlayerName());
        lblName.getStyleClass().removeAll("winner");
    	for (int i = 0; i < Player.HAND_SIZE; i++) {
    		Card card = null;
    		if (player.getCards().size() > i) card = player.getCards().get(i);
    		CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
    		cl.setCard(card);
    		HandType evaluation = player.evaluateHand();
    		if (evaluation != null){
                timeline.setCycleCount(2);
                timeline.setAutoReverse(true);
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(700)));
                timeline.setOnFinished(event -> {
                    lblEvaluation.setText(evaluation.toString());
                });
                timeline.play();
            }
    		else
    			lblEvaluation.setText("--");
    	}

    }
    public void setWinner() {
        lblName.getStyleClass().add("winner");
    }
}

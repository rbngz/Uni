package poker.version_graphics.model;

import javafx.animation.RotateTransition;
import javafx.util.Duration;
import poker.version_graphics.view.PlayerPane;

import javax.net.ssl.SNIHostName;
import java.util.ArrayList;

public class Player implements Comparable<Player> {
    public static final int HAND_SIZE = 5;
    
    private String playerName; // This is the ID
    private final ArrayList<Card> cards = new ArrayList<>();
    private HandType handType;
    public boolean winner = false;
    
    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void addCard(Card card) {

        if (cards.size() < HAND_SIZE) cards.add(card);
    }
    
    public void discardHand() {
        cards.clear();
        handType = null;
        winner = false;
    }
    
    public int getNumCards() {
        return cards.size();
    }

    public void isWinner(){
        winner = true;

    }

    /**
     * If the hand has not been evaluated, but does have all cards, 
     * then evaluate it.
     */
    public HandType evaluateHand() {
        if (handType == null && cards.size() == HAND_SIZE) {
            handType = HandType.evaluateHand(cards);
        }
        return handType;
    }

    /**
     * Hands are compared, based on the evaluation they have.
     */
    @Override
    public int compareTo(Player o) {
        return handType.compareTo(o.handType);
    }
}

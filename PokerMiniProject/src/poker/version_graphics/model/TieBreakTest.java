package poker.version_graphics.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TieBreakTest {

    private static String[][] twoPairs = {
            { "2S", "2C", "7H", "5D", "7H" },
            { "2S", "AC", "5H", "5D", "AH" },
            { "3S", "2C", "3H", "2D", "QH" },
            { "9S", "2C", "2H", "5D", "5H" }
    };

    ArrayList<ArrayList<Card>> twoPairHands;
    ArrayList<Player> twoPairPlayers;

    @Before
    public void makeHands() {
        twoPairHands = makeHands(twoPairs);
    }



    @Test
    public void testTieTwoPair() {

        for (ArrayList<Card> hand : twoPairHands) {
            assertTrue(HandType.isTwoPair(hand));
        }
    }

    private ArrayList<ArrayList<Card>> makeHands(String[][] handsIn) {
        ArrayList<ArrayList<Card>> handsOut = new ArrayList<>();
        for (String[] hand : handsIn) {
            handsOut.add(makeHand(hand));
        }
        return handsOut;
    }


    private ArrayList<Card> makeHand(String[] inStrings) {
        ArrayList<Card> hand = new ArrayList<>();
        for (String in : inStrings) {
            hand.add(makeCard(in));
        }
        return hand;
    }

    private Card makeCard(String in) {
        char r = in.charAt(0);
        Card.Rank rank = null;
        if (r <= '9') rank = Card.Rank.values()[r-'0' - 2];
        else if (r == 'T') rank = Card.Rank.Ten;
        else if (r == 'J') rank = Card.Rank.Jack;
        else if (r == 'Q') rank = Card.Rank.Queen;
        else if (r == 'K') rank = Card.Rank.King;
        else if (r == 'A') rank = Card.Rank.Ace;

        char s = in.charAt(1);
        Card.Suit suit = null;
        if (s == 'C') suit = Card.Suit.Clubs;
        if (s == 'D') suit = Card.Suit.Diamonds;
        if (s == 'H') suit = Card.Suit.Hearts;
        if (s == 'S') suit = Card.Suit.Spades;

        return new Card(suit, rank);
    }


}

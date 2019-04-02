package poker.version_graphics.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TieBreakTest {

    private static String[][] twoPairs = {
            { "2S", "2C", "7H", "4D", "7H" },
            { "2S", "7C", "2H", "5D", "7H" },
    };
    private static String[][] onePairs = {
            { "5S", "5C", "3H", "8D", "4H" },
            { "5S", "2C", "6H", "5D", "AH" },
    };
    private static String[][] highCards ={
            { "8S", "2C", "6H", "4D", "KH" },
            { "KS", "6C", "8H", "2D", "4H" },
    };
    ArrayList<Player> onePairPlayers;
    ArrayList<Player> twoPairPlayers;
    ArrayList<Player> highCardPlayers;


    @Before
    public void makePlayers() {
        onePairPlayers = makePlayers(onePairs);
        twoPairPlayers = makePlayers(twoPairs);
        highCardPlayers = makePlayers(highCards);
    }


    @Test
    public void testHighCard(){
        assertTrue(TieBreak.tieHighCard(highCardPlayers).size()==2);
    }
    @Test
    public void testTieTwoPair() {
        assertEquals(twoPairPlayers.get(1),(TieBreak.tieTwoPair(twoPairPlayers).get(0)));
    }
    @Test
    public void testOnePair(){
        assertEquals(onePairPlayers.get(1),TieBreak.tieOnePair(onePairPlayers).get(0));
    }
    private ArrayList<Player> makePlayers(String[][]twoPairs){
        ArrayList<Player> players = new ArrayList<>();
        for(String[] hands: twoPairs){
            Player p = new Player("Player");
            for(String s : hands) {
                p.addCard(makeCard(s));
            }
            players.add(p);
        }
        return players;
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

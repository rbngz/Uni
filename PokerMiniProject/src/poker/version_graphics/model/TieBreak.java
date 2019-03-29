package poker.version_graphics.model;

import java.util.ArrayList;
import java.util.Collections;

public class TieBreak {
    public static ArrayList<Player> getTieWinner(ArrayList<Player> allWinners){
        ArrayList<Player> definiteWinners = new ArrayList<>();
        HandType bestHand = allWinners.get(0).evaluateHand();
        switch (bestHand){
            case HighCard: definiteWinners = tieHighCard(allWinners);
            break;
            case OnePair: definiteWinners = tieOnePair(allWinners);
            break;
            case TwoPair: definiteWinners = tieTwoPair(allWinners);
            break;
            case ThreeOfAKind: definiteWinners = tieThreeOfAKind(allWinners);
            break;
            case FourOfAKind: definiteWinners = tieFourOfAKind(allWinners);
            break;
            case Straight: definiteWinners = tieStraight(allWinners);
            break;
            case FullHouse: definiteWinners = tieFullHouse(allWinners);
            break;
            case Flush: definiteWinners = tieFlush(allWinners);
            break;
            case StraightFlush:  definiteWinners = tieStraightFlush(allWinners);
            break;
        }
        return definiteWinners;

    }
    private static ArrayList<Player> tieHighCard(ArrayList<Player> allWinners){
        ArrayList<Player> definiteWinners = new ArrayList<>();
        ArrayList<Integer> cardValues = new ArrayList<>();
        for (int i = 0; i<allWinners.size();i++){
            for (int j = 0; j<Player.HAND_SIZE;j++){
                //Store all card values in ArrayList to evaluate highest card
                cardValues.add(allWinners.get(i).getCards().get(j).getRank().ordinal());
            }
        }
        Collections.sort(cardValues);
        int highestCard = cardValues.get(cardValues.size()-1);
        //find highest card in player Hands and add them to list of definite winners
        for (int i = 0; i<allWinners.size();i++) {
            for (int j = 0; j < Player.HAND_SIZE; j++) {
                if(highestCard==allWinners.get(i).getCards().get(j).getRank().ordinal()){
                    definiteWinners.add(allWinners.get(i));
                }
            }
        }

                return definiteWinners;
    }
    private static ArrayList<Player> tieOnePair(ArrayList<Player> allWinners){

        return allWinners;
    }
    private static ArrayList<Player> tieTwoPair(ArrayList<Player> allWinners){

        return allWinners;
    }
    private static ArrayList<Player> tieThreeOfAKind(ArrayList<Player> allWinners){

        return allWinners;
    }
    private static ArrayList<Player> tieFourOfAKind(ArrayList<Player> allWinners){

        return allWinners;
    }
    private static ArrayList<Player> tieStraight(ArrayList<Player> allWinners){

        return allWinners;
    }

    private static ArrayList<Player> tieFullHouse(ArrayList<Player> allWinners){

        return allWinners;

    }
    private static ArrayList<Player> tieFlush(ArrayList<Player> allWinners){

        return allWinners;
    }
    private static ArrayList<Player> tieStraightFlush(ArrayList<Player> allWinners){

        return allWinners;

    }

}

package poker.version_graphics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum HandType {
    HighCard, OnePair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush;
    
    /**
     * Determine the value of this hand. Note that this does not
     * account for any tie-breaking
     */
    public static HandType evaluateHand(ArrayList<Card> cards) {
        HandType currentEval = HighCard;
        
        if (isOnePair(cards)) currentEval = OnePair;
        if (isTwoPair(cards)) currentEval = TwoPair;
        if (isThreeOfAKind(cards)) currentEval = ThreeOfAKind;
        if (isStraight(cards)) currentEval = Straight;
        if (isFlush(cards)) currentEval = Flush;
        if (isFullHouse(cards)) currentEval = FullHouse;
        if (isFourOfAKind(cards)) currentEval = FourOfAKind;
        if (isStraightFlush(cards)) currentEval = StraightFlush;
        
        return currentEval;
    }
    
    public static boolean isOnePair(ArrayList<Card> cards) {
        boolean found = false;
        for (int i = 0; i < cards.size() - 1 && !found; i++) {
            for (int j = i+1; j < cards.size() && !found; j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank()) found = true;
            }
        }
        return found;
    }
    
    public static boolean isTwoPair(ArrayList<Card> cards) {
        // Clone the cards, because we will be altering the list
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

        // Find the first pair; if found, remove the cards from the list
        boolean firstPairFound = false;
        for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
            for (int j = i+1; j < clonedCards.size() && !firstPairFound; j++) {
                if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
                    firstPairFound = true;
                    clonedCards.remove(j);  // Remove the later card
                    clonedCards.remove(i);  // Before the earlier one
                }
            }
        }
        // If a first pair was found, see if there is a second pair
        return firstPairFound && isOnePair(clonedCards);
    }
    
    public static boolean isThreeOfAKind(ArrayList<Card> cards) {
        ArrayList<Integer> cardValue = new ArrayList<>();

        for (int i = 0; i<cards.size();i++){
            cardValue.add(cards.get(i).getRank().ordinal());
        }
        Collections.sort(cardValue);
        if(cardValue.get(0).equals(cardValue.get(1))&& cardValue.get(1).equals(cardValue.get(2))) return true;
        if(cardValue.get(cardValue.size()-1).equals(cardValue.get(cardValue.size()-2))&& cardValue.get(cardValue.size()-2).equals(cardValue.get(cardValue.size()-3))) return true;
        else return false;

    }
    
    public static boolean isStraight(ArrayList<Card> cards) {
        // TODO
        //First find lowest card
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

        int lowestRankValue = 13;
        for (int i = 0; i< clonedCards.size();i++){
            if(clonedCards.get(i).getRank().ordinal()<lowestRankValue){
                lowestRankValue = clonedCards.get(i).getRank().ordinal();
            }
        }
        int i =0;
        while (i<clonedCards.size()){
            if (clonedCards.get(i).getRank().ordinal()==lowestRankValue){
                clonedCards.remove(i);
                lowestRankValue++;
                i=0;
            } else i++;
        }
        return (clonedCards.isEmpty());
    }
    
    public static boolean isFlush(ArrayList<Card> cards) {
        // TODO should work!
        boolean allEqual = true;
        for(int i =0; i<cards.size()-1;i++){
            if (cards.get(i).getSuit()!=cards.get(i+1).getSuit()){
                allEqual = false;
                break;
            }
        }
        return allEqual;
    }
    
    public static boolean isFullHouse(ArrayList<Card> cards) {
        // TODO edit code!!! does not work in every case!

        if (isThreeOfAKind(cards)&&isTwoPair(cards)){
            return true;
        } else return false;
    }
    
    public static boolean isFourOfAKind(ArrayList<Card> cards) {
        // TODO
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();
        //check if the first three cards are the same
        clonedCards.remove(4);
        clonedCards.remove(3);
        if(isThreeOfAKind(clonedCards)){
            if(cards.get(4).getRank()==cards.get(0).getRank()||cards.get(3).getRank()==cards.get(0).getRank()){
                return true;
            } else return false;

        } else return false;
    }
    
    public static boolean isStraightFlush(ArrayList<Card> cards) {
        // TODO

        return (isStraight(cards)&&isFlush(cards));

    }
}

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
        //TODO look for next highest card also
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
        ArrayList<Player> definiteWinners = new ArrayList<>();
        ArrayList<Integer> pairs = new ArrayList<>();
        //go through each player hand and store the value of the pair in a Arraylist
        for (Player x : allWinners) {
            boolean found = false;
            for (int i = 0; i < x.getCards().size() - 1 && !found; i++) {
                for (int j = i + 1; j < x.getCards().size() && !found; j++) {
                    if (x.getCards().get(i).getRank() == x.getCards().get(j).getRank()) {
                        pairs.add(x.getCards().get(i).getRank().ordinal());
                        found = true;
                    }

                }
            }
        }
        //find highest pair value
        int highestValue = 0;
        for(int i : pairs){
            if(i>highestValue) highestValue = i;
        }
        //Find highest values in Arraylist and add player at the same index in definiteWinners
        for(int i=0; i< pairs.size(); i++){
            if(pairs.get(i)==highestValue){
                definiteWinners.add(allWinners.get(i));
            }
        }

        return definiteWinners;
    }
    private static ArrayList<Player> tieTwoPair(ArrayList<Player> allWinners){
        ArrayList<Player> definiteWinners = new ArrayList<>();
        ArrayList<Integer> pairs = new ArrayList<>();
        ArrayList<Integer> remainingCards = new ArrayList<>();
        //store all pair values in Arraylist
        for(Player x : allWinners) {
            ArrayList<Card> clonedCards = (ArrayList<Card>) x.getCards().clone();
            int pairsFound = 0;
            for (int i = 0; i < clonedCards.size() - 1 && pairsFound < 2; i++) {
                for (int j = i + 1; j < clonedCards.size() && pairsFound < 2; j++) {
                    if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
                        pairs.add(clonedCards.get(i).getRank().ordinal());
                        clonedCards.remove(j);
                        clonedCards.remove(i);
                        pairsFound++;
                        i=0;
                    }
                }
            }
            remainingCards.add(clonedCards.get(0).getRank().ordinal());
        }
        //store highest pair and lowest pair values of each hand in a new Arraylist
        ArrayList<Integer> highestPairs = new ArrayList<>();
        for(int i = 0; i<pairs.size();i+=2){
            if(pairs.get(i)>pairs.get(i+1)){
                highestPairs.add(pairs.get(i));
            }else{
                highestPairs.add(pairs.get(i+1));
            }
        }
        // find highest pair in all hands
        int bestPair = 0;
        for(int i : highestPairs){
            if(i>bestPair) bestPair = i;
        }
        ArrayList<Player> tempWinners = new ArrayList<>();
        //stor each hand with this highest pair as winner
        for(int i =0; i<highestPairs.size();i++){
            if(highestPairs.get(i) == bestPair){
                tempWinners.add(allWinners.get(i));
            }
        }
        //If there is only one winner return the winner, else find best second pair
        if (tempWinners.size()==1) definiteWinners = tempWinners;
        else{
            ArrayList<Integer> tempLowestPairs = new ArrayList<>();
            ArrayList<Integer> lowestPairs = new ArrayList<>();
            ArrayList<Player> tempWinner2 = new ArrayList<>();

            for(int i = 0; i<pairs.size();i+=2){
                if(pairs.get(i)>pairs.get(i+1)){
                    tempLowestPairs.add(pairs.get(i+1));
                }else{
                    tempLowestPairs.add(pairs.get(i));
                }
            }
            //add the lowest pairs to final lowest pair list from players that are in temp Winners
            for(int i = 0; i<tempWinners.size();i++){
                for(int j = 0; j<allWinners.size();j++){
                    if(tempWinners.get(i).equals(allWinners.get(j))){
                        lowestPairs.add(tempLowestPairs.get(j));
                    }
                }
            }
            // find best pair of the lower ones and store each players of the tempWinners as definite winners
            int bestLowestPair = 0;
            for (int i : lowestPairs){
                if(i>bestLowestPair) bestLowestPair = i;
            }
            for (int i =0;i<lowestPairs.size();i++){
                if(lowestPairs.get(i).equals(bestLowestPair)){
                    tempWinner2.add(tempWinners.get(i));
                }
            }
            // again if there is only one winner store as definite winner, else compare remaining card
            if(tempWinner2.size()==1) definiteWinners = tempWinner2;
            else{
                ArrayList<Integer> lastCards = new ArrayList<>();
                for (Player x : tempWinner2){
                    lastCards.add(remainingCards.get(allWinners.indexOf(x)));
                }
                //remove player with lower last card of if it is the same then both players win
                if(lastCards.get(0)>lastCards.get(1)) tempWinner2.remove(1);
                else if (lastCards.get(0)<lastCards.get(1)) tempWinner2.remove(0);
                definiteWinners = tempWinner2;
            }
        }
        return definiteWinners;
    }
    private static ArrayList<Player> tieThreeOfAKind(ArrayList<Player> allWinners) {
        //store all hand in Arraylist and clone them;
        ArrayList<ArrayList<Card>> hands = new ArrayList<>();
        ArrayList<Player> winner = new ArrayList<>(); //there can only be one Winner
        for (Player p : allWinners){
            hands.add(p.getCards());
        }
        ArrayList<ArrayList<Card>> clonedHands = (ArrayList<ArrayList<Card>>) hands.clone();
        //search for one pair and store the values of the hand in Array
        int[] threeOfAKindValues = new int[allWinners.size()];
        for(int i = 0;i<clonedHands.size();i++){
            boolean found = false;
            for (int j = 0;j<Player.HAND_SIZE-1&&!found;j++){
                for (int x = j+1;x<Player.HAND_SIZE&&!found;x++){
                    if(clonedHands.get(i).get(j).equals(clonedHands.get(i).get(x))){
                        found = true;
                        threeOfAKindValues[i] = clonedHands.get(i).get(j).getRank().ordinal();
                    }
                }
            }
        }
        //find highest value in all hands
        int highestValue=0;
        for(int i = 0; i<threeOfAKindValues.length;i++){
            if(threeOfAKindValues[i]>highestValue) highestValue = threeOfAKindValues[i];
        }
        for (int i = 0; i<threeOfAKindValues.length;i++){
            if (threeOfAKindValues[i]==highestValue){
                winner.add(allWinners.get(i));
            }
        }



        return allWinners;
    }
    private static ArrayList<Player> tieFourOfAKind(ArrayList<Player> allWinners){

        return allWinners;
    }
    private static ArrayList<Player> tieStraight(ArrayList<Player> allWinners){
        //Evaluate players with highest card
        return tieHighCard(allWinners);
    }

    private static ArrayList<Player> tieFullHouse(ArrayList<Player> allWinners){

        return allWinners;

    }
    private static ArrayList<Player> tieFlush(ArrayList<Player> allWinners){
        //Highest card in Flush wins
        return tieHighCard(allWinners);
    }
    private static ArrayList<Player> tieStraightFlush(ArrayList<Player> allWinners){
        //Evaluate players with highest card
        return tieHighCard(allWinners);
    }

}

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
    public static ArrayList<Player> tieHighCard(ArrayList<Player> allWinners){
        ArrayList<ArrayList<Integer>> hands = new ArrayList<>();
        ArrayList<Player> definiteWinners;
        ArrayList<Player> tempWinners = new ArrayList<>();
        for (int i = 0; i<allWinners.size();i++){
            hands.add(new ArrayList<>());
            for(int j = 0; j< allWinners.get(i).getCards().size();j++){
                hands.get(i).add(allWinners.get(i).getCards().get(j).getRank().ordinal());
            }
        }
        //determine highest card of each hand
        for (ArrayList<Integer> hand : hands){
            Collections.sort(hand, Collections.reverseOrder());
        }

        int highestCard = 0;
        for(int i = 0; i< hands.size();i++){
            if(hands.get(i).get(0)>highestCard) highestCard = hands.get(i).get(0);
        }
        for(int i = 0; i< hands.size();i++){
            if(highestCard == hands.get(i).get(0)) tempWinners.add(allWinners.get(i));
            hands.get(i).remove(0);
        }
        int cardsRemaining=4*tempWinners.size();
        //while there is still more than one winner and there are still cards left to compare
        while (tempWinners.size()>1&&cardsRemaining>0){
            highestCard = 0;
            ArrayList<ArrayList<Integer>> tempHands = new ArrayList<>();
            for (int i = 0;i<tempWinners.size();i++){
                tempHands.add(hands.get(allWinners.indexOf(tempWinners.get(i))));
            }
            for (int i = 0; i<tempHands.size();i++) {
                if (tempHands.get(i).get(0) > highestCard) highestCard = tempHands.get(i).get(0);
            }
            for (int i = 0; i<tempHands.size(); i++){
                if (tempHands.get(i).get(0)<highestCard){
                    tempWinners.remove(i);
                }
                tempHands.get(i).remove(0);
                cardsRemaining--;
            }
        }
        definiteWinners = tempWinners;
        return definiteWinners;
    }
    public static ArrayList<Player> tieOnePair(ArrayList<Player> allWinners){
        // remove pair and send remaining cards to tieHighCard
        ArrayList<Player> definiteWinners = new ArrayList<>();
        ArrayList<Integer> pairs = new ArrayList<>();
        ArrayList<ArrayList<Card>> hands = new ArrayList<>();
        for(int i = 0; i< allWinners.size();i++){
            hands.add(allWinners.get(i).getCards());
        }
        ArrayList<ArrayList<Card>> clonedHands = (ArrayList<ArrayList<Card>>) hands.clone();
        //go through each player hand and store the value of the pair in a Arraylist
        for (ArrayList<Card> hand : clonedHands) {
            boolean found = false;
            for (int i = 0; i < hand.size() - 1 && !found; i++) {
                for (int j = i + 1; j < hand.size() && !found; j++) {
                    if (hand.get(i).getRank().equals(hand.get(j).getRank())) {
                        pairs.add(hand.get(i).getRank().ordinal());
                        found = true;
                        hand.remove(j);
                        hand.remove(i);
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
        // if more than one player have the same pair then evaluate the remaining cards
        if (definiteWinners.size()>1){
            return tieHighCard(definiteWinners);
        } else return definiteWinners;
    }
    public static ArrayList<Player> tieTwoPair(ArrayList<Player> allWinners){
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
                        j=0;
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
        //store each hand with this highest pair as winner
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
        //search for one pair and store the values of the hand in Array
        int[] threeOfAKindValues = new int[allWinners.size()];
        for(int i = 0;i<hands.size();i++){
            boolean found = false;
            for (int j = 0;j<Player.HAND_SIZE-1&&!found;j++){
                for (int x = j+1;x<Player.HAND_SIZE&&!found;x++){
                    if(hands.get(i).get(j).equals(hands.get(i).get(x))){
                        found = true;
                        threeOfAKindValues[i] = hands.get(i).get(j).getRank().ordinal();
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
        return winner;
    }
    private static ArrayList<Player> tieFourOfAKind(ArrayList<Player> allWinners){

        return tieThreeOfAKind(allWinners); // works also for four of a kind
    }
    private static ArrayList<Player> tieStraight(ArrayList<Player> allWinners){
        //Evaluate players with highest card
        return tieHighCard(allWinners);
    }

    private static ArrayList<Player> tieFullHouse(ArrayList<Player> allWinners){
        int[] valuesThreeOfAKind = new int[allWinners.size()];
        ArrayList<Player> winner = new ArrayList<>();
        ArrayList<ArrayList<Card>> hands = new ArrayList<>();
        for (Player p : allWinners){
            hands.add(p.getCards());
        }
        // find value of the three of a kind
        for (int i = 0;i<hands.size();i++){
            Card currentCard = hands.get(i).get(0);
            int count = 0;
            for (int j=0;j<hands.get(i).size();j++){
                if(currentCard.getRank().equals(hands.get(i).get(j).getRank())) count++;
            }
            if(count == 3){
                valuesThreeOfAKind[i] = currentCard.getRank().ordinal();
            } else {
                for (Card card : hands.get(i)){
                    if(!card.getRank().equals(currentCard.getRank())){
                        valuesThreeOfAKind[i] = card.getRank().ordinal();
                    }
                }
            }
        }
        int highestValue = 0;
        for(int value : valuesThreeOfAKind){
            if(value>highestValue) highestValue = value;
        }
        for (int i = 0;i<valuesThreeOfAKind.length;i++){
            if(valuesThreeOfAKind[i]==highestValue){
                winner.add(allWinners.get(i));
            }
        }

        return winner;

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

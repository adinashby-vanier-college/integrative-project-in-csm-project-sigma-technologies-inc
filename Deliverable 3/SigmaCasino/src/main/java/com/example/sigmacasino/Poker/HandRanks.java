package com.example.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import io.lyuda.jcards.game.Player;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public enum HandRanks{

    //Rank enum values
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10);

    private final int value;

    HandRanks(int value) {
        this.value = value;
    }

    int getValue(){
        return this.value;
    }

    //Used to figure out the rank of an individual players hand
    static int getIndividualHandRank(Player player){
        ArrayList<Rank> cardNames = new ArrayList<>(Arrays.asList(
                Rank.ACE, Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN,
                Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE,
                Rank.FOUR, Rank.THREE, Rank.TWO
        ));
        int[] subRankValues = {13,12,11,10,9,8,7,6,5,4,3,2,1};
        Card[] hand = {player.getHand().getCards().get(0), player.getHand().getCards().get(1)};
        Arrays.sort(hand);

        //Places Ace at the end of the array (for sorting purposes)
        if(hand[0].getRank().equals(Rank.ACE)){
            Card temp = hand[0];
            hand[0] = hand[1];
            hand[1] = temp;
        }
        //System.out.println(Arrays.toString(hand));
        //System.out.println((subRankValues[cardNames.indexOf(hand[0].getRank())]) + (100*subRankValues[cardNames.indexOf(hand[1].getRank())]));
        return (subRankValues[cardNames.indexOf(hand[0].getRank())]) + (100*subRankValues[cardNames.indexOf(hand[1].getRank())]);
    }

    //Using alpha-beta pruning, the rank of the best possible hand is calculated
    static float bestHand(ArrayList<Card> allCards) {
        Card[] hand = new Card[5];
        float bestRank = HandRanks.HIGH_CARD.getValue();

        // Length of total Cards (7)
        int n = allCards.size();

        // Alpha-Beta-like Pruning in this context
        // Alpha: Best rank found so far
        // Beta: We would only proceed if we find a better hand than the current best
        float alpha = bestRank;

        // All possible hands P(allCards.size(),5)
        for (int i = 0; i < n - 4; i++) {

            // We can prune this branch if we already have a better hand
            if (bestRank > alpha) break;

            for (int j = i + 1; j < n - 3; j++) {
                if (bestRank > alpha) break;

                for (int k = j + 1; k < n - 2; k++) {
                    if (bestRank > alpha) break;

                    for (int l = k + 1; l < n - 1; l++) {
                        if (bestRank > alpha) break;

                        for (int m = l + 1; m < n; m++) {
                            hand[0] = allCards.get(i);
                            hand[1] = allCards.get(j);
                            hand[2] = allCards.get(k);
                            hand[3] = allCards.get(l);
                            hand[4] = allCards.get(m);

                            // Evaluate the hand rank
                            float calRank = calculateRank(hand);

                            // Prune if the current hand is not better
                            if (calRank > bestRank) {
                                bestRank = calRank;
                                alpha = bestRank;  // Update alpha to the new best rank
                            }
                        }
                    }
                }
            }
        }
        return bestRank;
    }

    //Returns an array of the best possible cards to have out of all usable cards
    static Card[] getBestHand(ArrayList<Card> allCards) {
        Card[] hand = new Card[5];
        Card[] bestHand = new Card[5];
        float bestRank = HandRanks.HIGH_CARD.getValue();

        // Length of total Cards (7)
        int n = allCards.size();

        // Alpha-Beta-like Pruning in this context
        // Alpha: Best rank found so far
        // Beta: We would only proceed if we find a better hand than the current best
        float alpha = bestRank;

        // All possible hands P(allCards.size(),5)
        for (int i = 0; i < n - 4; i++) {

            // We can prune this branch if we already have a better hand
            if (bestRank > alpha) break;

            for (int j = i + 1; j < n - 3; j++) {
                if (bestRank > alpha) break;

                for (int k = j + 1; k < n - 2; k++) {
                    if (bestRank > alpha) break;

                    for (int l = k + 1; l < n - 1; l++) {
                        if (bestRank > alpha) break;

                        for (int m = l + 1; m < n; m++) {
                            hand[0] = allCards.get(i);
                            hand[1] = allCards.get(j);
                            hand[2] = allCards.get(k);
                            hand[3] = allCards.get(l);
                            hand[4] = allCards.get(m);

                            // Evaluate the hand rank
                            float calRank = calculateRank(hand);

                            // Prune if the current hand is not better
                            if (calRank > bestRank) {
                                bestRank = calRank;
                                alpha = bestRank;  // Update alpha to the new best rank
                                System.arraycopy(hand, 0, bestHand, 0, bestHand.length); //Retrieving best hand
                            }
                        }
                    }
                }
            }
        }
        return bestHand;
    }

    //Converts the best possible cards into images
    static Image[] getBestHandImages(ArrayList<Card> allCards){
          Image[] images = new Image[5];
          Card[] cards = getBestHand(allCards);
          System.out.println("Cards (Inside Second): "+ Arrays.toString(cards));
          for(int i=0;i<images.length;i++)
          {
              File file = new File("src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/"+cards[i].getRank().toString().toLowerCase()+"_of_"+cards[i].getSuit().toString().toLowerCase()+".png");
              images[i]= new Image(file.toURI().toString());
          }
          return images;
    }

    //Calculates the rank/sub rank of a given 5 card hand
    private static float calculateRank(Card[] hand){
        ArrayList<Rank> cardNames = new ArrayList<>(Arrays.asList(
                Rank.ACE, Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN,
                Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE,
                Rank.FOUR, Rank.THREE, Rank.TWO
        ));
        int[] cardNamesCount = new int[13];
        float rank = HIGH_CARD.getValue();
        int pairs =0;
        int threeOfAKind = 0;
        ArrayList<Rank> pairedCards = new ArrayList<>();
        ArrayList<Integer> pairedCardValue = new ArrayList<>();
        Arrays.sort(hand);

        Suit suit = hand[0].getSuit();
        if(isRoyalFlush(hand,suit))
        {
            return ROYAL_FLUSH.getValue();
        }

        if(isStraight(cardNames,hand) && isFlush(hand, suit))
        {
            return STRAIGHT_FLUSH.getValue() + getHighestCard(cardNames, hand);
        }

        if(isStraight(cardNames,hand))
        {
            return STRAIGHT.getValue() + getHighestCard(cardNames,hand);
        }

        if(isFlush(hand,suit))
        {
            return FLUSH.getValue() + getHighestCard(cardNames, hand);
        }

        //Sorts Cards
        for (Card card : hand) {
            cardNamesCount[cardNames.indexOf(card.getRank())]++;
        }
        for (int i=0;i<cardNamesCount.length;i++) {
            switch (cardNamesCount[i]) {
                case 2:
                    pairedCards.add(cardNames.get(i));
                    pairedCardValue.add(2);
                    pairs++;
                    break;
                case 3:
                    threeOfAKind++;
                    pairedCards.add(cardNames.get(i));
                    pairedCardValue.add(3);
                    break;
                case 4:
                    pairedCards.add(cardNames.get(i));
                    pairedCardValue.add(4);
                    rank = FOUR_OF_A_KIND.getValue() + subCardRanking(pairedCards, pairedCardValue, 4);
                    break;
            }
        }
        if (pairs==1 && threeOfAKind==1) { //Full house
            rank = (FULL_HOUSE.getValue() + subCardRanking(pairedCards,pairedCardValue,3) + (0.01f * subCardRanking(pairedCards,pairedCardValue,2)));
        } else if(pairs==1){ //One pair
            rank = ONE_PAIR.getValue() + subCardRanking(pairedCards,pairedCardValue,2);
        } else if(pairs==2){ //Two pair
            rank = TWO_PAIR.getValue()+ subCardRanking(pairedCards,pairedCardValue,2);
        } else if (threeOfAKind==1) { //Three of a kind
            rank = THREE_OF_A_KIND.getValue()+ subCardRanking(pairedCards,pairedCardValue,3);
        } else if(rank==HIGH_CARD.getValue()) { //High card
            rank = rank + getHighestCard(cardNames,hand);
        }
        return rank;
    }

    //Calculates the sub ranking of pairs, three of a kind, ect.
    private static float subCardRanking(ArrayList<Rank> pairedCards, ArrayList<Integer> pairedCardValue, int lookFor){
        float multiplier = 1.0f;
        float bonusValue = 0.0f;
        for(int i=0;i<pairedCards.size();i++){
            if(pairedCardValue.get(i)==lookFor) {
                switch (pairedCards.get(i)) {
                    case TWO -> {
                        bonusValue += (0.01f * multiplier);
                        break;
                    }
                    case THREE -> {
                        bonusValue += (0.02f * multiplier);
                        break;
                    }
                    case FOUR -> {
                        bonusValue += (0.03f * multiplier);
                        break;
                    }
                    case FIVE -> {
                        bonusValue += (0.04f * multiplier);
                        break;
                    }
                    case SIX -> {
                        bonusValue += (0.05f * multiplier);
                        break;
                    }
                    case SEVEN -> {
                        bonusValue += (0.06f * multiplier);
                        break;
                    }
                    case EIGHT -> {
                        bonusValue += (0.07f * multiplier);
                        break;
                    }
                    case NINE -> {
                        bonusValue += (0.08f * multiplier);
                        break;
                    }
                    case TEN -> {
                        bonusValue += (0.09f * multiplier);
                        break;
                    }
                    case JACK -> {
                        bonusValue += (0.10f * multiplier);
                        break;
                    }
                    case QUEEN -> {
                        bonusValue += (0.11f * multiplier);
                        break;
                    }
                    case KING -> {
                        bonusValue += (0.12f * multiplier);
                        break;
                    }
                    case ACE -> {
                        bonusValue += (0.13f * multiplier);
                        break;
                    }
                }
                multiplier *= 0.01f;
            }
        }
        return bonusValue;
    }

    //Royal Flush
    private static boolean isRoyalFlush(Card[] hand,Suit suit){
        Card[] royalFlushHand = {new Card(Rank.ACE, suit), new Card(Rank.KING, suit), new Card(Rank.QUEEN, suit), new Card(Rank.JACK, suit), new Card(Rank.TEN, suit)};
        Arrays.sort(royalFlushHand);
        return Arrays.equals(hand, royalFlushHand);
    }

    //Retrieves the highest card from a hand
    private static float getHighestCard(ArrayList<Rank> cardNames, Card[] hand){
        int[] subRankValues = {13,12,11,10,9,8,7,6,5,4,3,2,1};
        int max=subRankValues[cardNames.indexOf(hand[0].getRank())];
        for(int i=0;i<hand.length;i++)
        {
            if(max<subRankValues[cardNames.indexOf(hand[i].getRank())])
            {
                max=subRankValues[cardNames.indexOf(hand[i].getRank())];
            }
        }
        return (max*0.01f);
    }

    //Straight
    private static boolean isStraight(ArrayList<Rank> cardNames, Card[] hand){
        int[] subRankValues = {13,12,11,10,9,8,7,6,5,4,3,2,1};
        ArrayList<Integer> cardRanks = new ArrayList<>();
        for(int i=0;i<hand.length;i++)
        {
            cardRanks.add(subRankValues[cardNames.indexOf(hand[i].getRank())]);
        }
        cardRanks.sort(Integer::compareTo);
        for(int i=0;i<cardRanks.size()-1;i++)
        {
            if(cardRanks.get(i)!=cardRanks.get(i+1)-1)
            {
                return false;
            }
        }
        return true;
    }

    //Flush
    private static boolean isFlush(Card[] hand, Suit suit){
        for(int i=0;i<hand.length;i++)
        {
            if(hand[i].getSuit()!=suit)
            {
                return false;
            }
        }
        return true;
    }

}

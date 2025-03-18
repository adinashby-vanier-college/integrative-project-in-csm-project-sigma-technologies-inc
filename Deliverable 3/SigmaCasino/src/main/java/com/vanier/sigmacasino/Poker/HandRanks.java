package java.com.vanier.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;

import java.util.ArrayList;
import java.util.Arrays;

public enum HandRanks{
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

    public int getValue(){
        return this.value;
    }

    public static float calculateRank(Card[] hand){
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
        if (pairs==1 && threeOfAKind==1) {
            rank = (FULL_HOUSE.getValue() + subCardRanking(pairedCards,pairedCardValue,3) + (0.01f * subCardRanking(pairedCards,pairedCardValue,2)));
        } else if(pairs==1){
            rank = ONE_PAIR.getValue() + subCardRanking(pairedCards,pairedCardValue,2);
        } else if(pairs==2){
            rank = TWO_PAIR.getValue()+ subCardRanking(pairedCards,pairedCardValue,2);
        } else if (threeOfAKind==1) {
            rank = THREE_OF_A_KIND.getValue()+ subCardRanking(pairedCards,pairedCardValue,3);
        } else if(rank==HIGH_CARD.getValue())
        {
            rank = rank + getHighestCard(cardNames,hand);
        }
        return rank;
    }

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

    private static boolean isRoyalFlush(Card[] hand,Suit suit){
        Card[] royalFlushHand = {new Card(Rank.ACE, suit), new Card(Rank.KING, suit), new Card(Rank.QUEEN, suit), new Card(Rank.JACK, suit), new Card(Rank.TEN, suit)};
        Arrays.sort(royalFlushHand);
        return Arrays.equals(hand, royalFlushHand);
    }

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

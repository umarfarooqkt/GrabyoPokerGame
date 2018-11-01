import java.util.Arrays;
import java.util.HashMap;

public class SimplePokerHand {

    // {H->0, D->1, S->2, C->3}
    private int[] suits = new int[4];
    private String[] suitArray = new String[15];
    // {A->1, 2->2, 3->3, 4->4, 5->5, 6->6, 7->7, 8->8,
    //  9->9, 10->10, J->11, Q->12, K->13, A->14
    private int[] ranks = new int[15];
    private int[] kickers = new int[15];
    private int result;
    private int highestCardValue = -1; // used for the highest 1st card
    private HashMap<String,Integer> suitMap = new HashMap<String, Integer>();
    private HashMap<String,Integer> rankMap = new HashMap<String, Integer>();

    /**
     * Constructor
     * @param hand
     */
    public SimplePokerHand(String hand) {
        setMaps();
        setArrays(hand);
//        System.out.println("ranks \n"+ Arrays.toString(ranks));
//        System.out.println("ranks \n"+Arrays.toString(suitArray));
    }

    /////Setters and getters////

    /**
     * Setting default values to be used by the map
     * Map was mainly used due to clarity and quickly getting values
     */
    private void setMaps() {
        //set the suit map
        suitMap.put("H",0);
        suitMap.put("D",1);
        suitMap.put("S",2);
        suitMap.put("C",3);

        //set the rank map
        rankMap.put("A",14);
        rankMap.put("K",13);
        rankMap.put("Q",12);
        rankMap.put("J",11);
    }

    public void setKickers(int[] kickers) {
        this.kickers = kickers;
    }

    public int[] getKickers() {
        return kickers;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    // Evaluate value of hand//

    public int compareWith(SimplePokerHand otherPokerHand) {
        this.calculateResult(otherPokerHand);
        return this.result;
    }

    /**
     * This method sets the result by comparing this hand with the parameter hand
     * @param otherHand
     * @throws Exception If the hands were not evaluated
     */
    public void calculateResult(SimplePokerHand otherHand) {
        int thisRankValue = this.whatsMyRank();
        int otherRankValue = otherHand.whatsMyRank();
        if (thisRankValue > otherRankValue) {
            result = 1;
        } else if (thisRankValue < otherRankValue) {
            result = 2;
        } else {
            if (this.highestCardValue > otherHand.highestCardValue) {
                result = 1;
            } else if (this.highestCardValue < otherHand.highestCardValue) {
                result = 2;
            } else {
                result = compareKickers(otherHand);
            }
        }
    }

    /**
     * This method finds out the hand values
     * 9 being a straight flush
     * @return [int] 2-->9
     */
    public int whatsMyRank() {
        if (isStraightFlush() != -1) {
            highestCardValue = isStraightFlush();
            return 9;
        }
        if (isFourOfKind() != -1) {
            highestCardValue = isFourOfKind();
            return 8;
        }
        if (isFullHouse()[0] != -1) {
            highestCardValue = isFullHouse()[0];
            return 7;
        }
        if (isFlush() != -1) {
            highestCardValue = isFlush();
            return 6;
        }
        if (isStraight() != -1) {
            highestCardValue = isStraight();
            return 5;
        }
        if (isThreeOfKind() != -1) {
            highestCardValue = isThreeOfKind();
            return 4;
        }
        if (isTwoOfKind() != -1) {
            highestCardValue = isTwoOfKind();
            return 3;
        }
        if (getLastArrayVar(ranks) != -1) {
            highestCardValue = getLastArrayVar(ranks);
            return 2;
        }
        return -1;
    }

    ///Determine hands methods /////

    /**
     * Checks if the hand is a Straight flush
     * @returns highest int of the flush or -1 if not found
     */
    public int isStraightFlush() {
        int count = 1;
        String previousSuit = "";
        for (int i = suitArray.length-1; i > 0; i--) {
            if(suitArray[i] != null) {
                if(suitArray[i].equals(previousSuit)){
                    count++;
                } else {
                    count = 1;
                }
                previousSuit = suitArray[i];
            } else {
                count = 1;
                previousSuit = "";
            }
            if(count == 5){
                return i+4;
            }
        }
        return -1;
    }

    /**
     * This method checks for a straight
     * @return the highest card of a straight if found
     * else it will return -1
     */
    public int isStraight() {
        int count = 0;
        for (int i = ranks.length-1; i > 0; i--) {
            if(ranks[i] > 0) {
                count++;
            } else {
                count = 0;
            }
            if(count == 5){
                return i +4;
            }
        }
        return -1;
    }

    public int isFlush() {
        String flushSuit;
        for (int i = 0; i < suits.length; i++) {
            if(suits[i] == 5) {
                flushSuit = getKey(suitMap, i);
                for (int j = suitArray.length-1; j > 1 ; j--) {
                    if (flushSuit.equals(suitArray[j]) && suitArray[j] != null) {
                        kickers = ranks;
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    public int isFourOfKind() {
        // end to 1 to avoid Ace
        for (int i = ranks.length-1; i > 1; i--) {
            if(ranks[i] == 4) {
                kickers = ranks.clone();
                kickers[i] = 0;
                return i;
            }
        }
        return -1;
    }

    public int[] isFullHouse() {
        // first index is for 3, second for the double
        int[] highCard = new int[2];
        highCard[0] = isThreeOfKind();
        highCard[1] = isTwoOfKind();
        kickers = ranks.clone();
        return highCard;
    }

    public int isThreeOfKind() {
        // end to 1 to avoid Ace
        for (int i = ranks.length-1; i > 1; i--) {
            if(ranks[i] == 3) {
                kickers = ranks.clone();
                kickers[i] = 0;
                return i;
            }
        }
        return -1;
    }

    public int isTwoOfKind() {
        // end to 1 to avoid Ace
        for (int i = ranks.length -1; i > 1; i--) {
            if(ranks[i] == 2) {
                kickers = ranks.clone();
                kickers[i] = 0;
                return i;
            }
        }
        return -1;
    }

    /**
     *  gets the last index of non zero value in the frequency arrays
     *  primarily useful for highest card
     * @param array
     * @return last index
     */
    public int getLastArrayVar(int[] array) {
        for (int i = array.length-1; i < 0; i--) {
            if(array[i] > 0) {
                kickers = ranks.clone();
                return i;
            }
        }
        return -1;
    }

    /**
     * compares the kicker arrays from both hands
     * @return
     */
    public int compareKickers(SimplePokerHand otherHand) {
        for (int i = kickers.length-1; i > 1; i--) {
            if(kickers[i] > otherHand.kickers[i]) {
               return 1;
            }
            if(kickers[i] < otherHand.kickers[i]) {
                return 2;
            }
        }
        return 3;
    }

    /////Utility Methods//////

    /**
     * This method sorts hands to suits and ranks arrays using bucket sorting
     * @param hand
     */
    public void setArrays(String hand) {
        String[] cards = hand.split(" ");
        String suitIndex, rank;
        for (String card: cards) {
            suitIndex = card.substring(card.length()-1);
            rank = card.substring(0,card.length()-1);
            suits[suitMap.get(suitIndex)] = suits[suitMap.get(suitIndex)]+1;
            if(isInteger(rank)){
                ranks[Integer.valueOf(rank)] = ranks[Integer.valueOf(rank)] + 1;
                addSuitArray(Integer.valueOf(rank), suitIndex);
            } else {
                ranks[rankMap.get(rank)] = ranks[rankMap.get(rank)] + 1;
                addSuitArray(rankMap.get(rank), suitIndex);
                if(rank.equals("A")){
                    ranks[1] = ranks[1] + 1;
                    addSuitArray(1, suitIndex);
                }
            }
        }
    }

    /**
     * Utility add method for the Suit Array
     * @param index
     * @param suit
     */
    public void addSuitArray(int index, String suit) {
        if(suitArray[index] == null){
            suitArray[index] = suit;
        } else {
            suitArray[index] = suitArray[index] + "," + suit;
        }
    }

    /**
     * Helper method to determine if a rank is an integer
     * @param value
     * @return boolean confirming if value is true || false
     */
    public boolean isInteger(String value) {
        for (char c : value.toCharArray()) {
            if(c < '0' || c > '9'){
                return false;
            }
        }
        return true;
    }

    /***
     * This method gets and returns the key for the parameter value
     * @param hashMap
     * @param value
     * @return will return null if value doesn't exist
     */
    public String getKey(HashMap<String, Integer> hashMap, Integer value) {
        int valueGetter;
        for (Object key: hashMap.keySet().toArray()) {
            valueGetter = hashMap.get(key);
            if(valueGetter == value) {
                return (String) key;
            }
        }
        return null;
    }
}

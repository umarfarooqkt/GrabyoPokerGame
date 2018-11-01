import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        SimplePokerHand hand1 = new SimplePokerHand("AC 4S 5S 8C AH"); // two of a kind As
        SimplePokerHand hand2 = new SimplePokerHand("5C 3C 2C 3C AC"); // flush
        SimplePokerHand hand3 = new SimplePokerHand("5C 3C 2C 4C AC"); // Straight flush
        SimplePokerHand hand4 = new SimplePokerHand("QS JS 10S KS AS"); // Royal flush
        SimplePokerHand hand5 = new SimplePokerHand("3S 3C 3H AS AC"); // full house
        SimplePokerHand hand6 = new SimplePokerHand("3D 3C 3H 3S AC"); // four of a kind
        SimplePokerHand hand7 = new SimplePokerHand("5C 3C 2C 3C 4C"); // flush with high card 5
        SimplePokerHand hand8 = new SimplePokerHand("AH 3C 2C 6C 7C"); // high card A
        SimplePokerHand hand9 = new SimplePokerHand("3S 3C 3H 2S 2C"); /// full house lower
        SimplePokerHand hand10 = new SimplePokerHand("QH JH 10H KH AH"); // royal flush
        SimplePokerHand hand11 = new SimplePokerHand("3D 3C 3H 3S 2C"); // four of a kind with 2 kicker

        if (hand1.compareWith(hand2) == 2) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand2.compareWith(hand1) == 1) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand4.compareWith(hand10) == 3) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand5.compareWith(hand9) == 1) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand7.compareWith(hand2) == 2) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand6.compareWith(hand11) == 1) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand1.compareWith(hand8) == 1) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        if (hand3.compareWith(hand3) == 3) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }
    }
}

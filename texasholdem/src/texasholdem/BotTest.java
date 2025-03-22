package texasholdem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BotTest {

    // Hands array for reference
    public static final String[] hands = {
        "Royal Flush", "Straight Flush", "Four of a Kind", "Full House",
        "Flush", "Straight", "Three of a Kind", "Two Pair", "Pair", "High Card"
    };

    @Test
    public void testRoyalFlush() {
        Card[] royalFlush = {
            new Card("hearts", 14), // Ace
            new Card("hearts", 13), // King
            new Card("hearts", 12), // Queen
            new Card("hearts", 11), // Jack
            new Card("hearts", 10)  // Ten
        };
        int[] result = Bot.findHand(royalFlush);
        assertEquals(0, result[0]); // Royal Flush is at index 0
    }

    @Test
    public void testStraightFlush() {
        Card[] straightFlush = {
            new Card("spades", 9),
            new Card("spades", 8),
            new Card("spades", 7),
            new Card("spades", 6),
            new Card("spades", 5)
        };
        int[] result = Bot.findHand(straightFlush);
        assertEquals(1, result[0]); // Straight Flush is at index 1
    }

    @Test
    public void testFourOfAKind() {
        Card[] fourOfAKind = {
            new Card("diamonds", 7),
            new Card("hearts", 7),
            new Card("clubs", 7),
            new Card("spades", 7),
            new Card("hearts", 2)
        };
        int[] result = Bot.findHand(fourOfAKind);
        assertEquals(2, result[0]); // Four of a Kind is at index 2
    }

    @Test
    public void testFullHouse() {
        Card[] fullHouse = {
            new Card("diamonds", 10),
            new Card("hearts", 10),
            new Card("clubs", 10),
            new Card("spades", 5),
            new Card("hearts", 5)
        };
        int[] result = Bot.findHand(fullHouse);
        assertEquals(3, result[0]); // Full House is at index 3
    }

    @Test
    public void testFlush() {
        Card[] flush = {
            new Card("clubs", 2),
            new Card("clubs", 4),
            new Card("clubs", 6),
            new Card("clubs", 8),
            new Card("clubs", 10)
        };
        int[] result = Bot.findHand(flush);
        
        assertEquals(4, result[0]); // Flush is at index 4
    }

    @Test
    public void testStraight() {
        Card[] straight = {
            new Card("hearts", 9),
            new Card("diamonds", 8),
            new Card("clubs", 7),
            new Card("spades", 6),
            new Card("hearts", 5)
        };
        int[] result = Bot.findHand(straight);
        assertEquals(5, result[0]); // Straight is at index 5
    }

    @Test
    public void testThreeOfAKind() {
        Card[] threeOfAKind = {
            new Card("diamonds", 9),
            new Card("hearts", 9),
            new Card("clubs", 9),
            new Card("spades", 4),
            new Card("hearts", 2)
        };
        int[] result = Bot.findHand(threeOfAKind);
        assertEquals(6, result[0]); // Three of a Kind is at index 6
    }

    @Test
    public void testTwoPair() {
        Card[] twoPair = {
            new Card("diamonds", 8),
            new Card("hearts", 8),
            new Card("clubs", 5),
            new Card("spades", 5),
            new Card("hearts", 2)
        };
        int[] result = Bot.findHand(twoPair);
        assertEquals(7, result[0]); // Two Pair is at index 7
    }

    @Test
    public void testPair() {
        Card[] pair = {
            new Card("diamonds", 7),
            new Card("hearts", 7),
            new Card("clubs", 4),
            new Card("spades", 3),
            new Card("hearts", 2)
        };
        int[] result = Bot.findHand(pair);
        assertEquals(8, result[0]); // Pair is at index 8
    }

    @Test
    public void testHighCard() {
        Card[] highCard = {
            new Card("diamonds", 14), // Ace
            new Card("hearts", 10),
            new Card("clubs", 8),
            new Card("spades", 6),
            new Card("hearts", 4)
        };
        int[] result = Bot.findHand(highCard);
        assertEquals(9, result[0]); // High Card is at index 9
    }
    
    public void testRoyalFlush2() {
        Card[] sevenCardHand = {
            new Card("Hearts", 14), // Ace
            new Card("Hearts", 13), // King
            new Card("Hearts", 12), // Queen
            new Card("Hearts", 11), // Jack
            new Card("Hearts", 10), // Ten
            new Card("Diamonds", 7), // Extra card
            new Card("Clubs", 2)    // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(0, result[0]); // Royal Flush is at index 0
    }

    @Test
    public void testStraightFlush2() {
        Card[] sevenCardHand = {
            new Card("Spades", 9),
            new Card("Spades", 8),
            new Card("Spades", 7),
            new Card("Spades", 6),
            new Card("Spades", 5),
            new Card("Hearts", 3), // Extra card
            new Card("Diamonds", 2) // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(1, result[0]); // Straight Flush is at index 1
    }

    @Test
    public void testFourOfAKind2() {
        Card[] sevenCardHand = {
            new Card("Diamonds", 7),
            new Card("Hearts", 7),
            new Card("Clubs", 7),
            new Card("Spades", 7),
            new Card("Hearts", 2),
            new Card("Diamonds", 3), // Extra card
            new Card("Clubs", 4)     // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(2, result[0]); // Four of a Kind is at index 2
    }

    @Test
    public void testFullHouse2() {
        Card[] sevenCardHand = {
            new Card("Diamonds", 10),
            new Card("Hearts", 10),
            new Card("Clubs", 10),
            new Card("Spades", 5),
            new Card("Hearts", 5),
            new Card("Diamonds", 2), // Extra card
            new Card("Clubs", 3)     // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(3, result[0]); // Full House is at index 3
    }

    @Test
    public void testFlush2() {
        Card[] sevenCardHand = {
            new Card("Clubs", 2),
            new Card("Clubs", 4),
            new Card("Clubs", 6),
            new Card("Clubs", 8),
            new Card("Clubs", 10),
            new Card("Hearts", 3), // Extra card
            new Card("Diamonds", 5) // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(4, result[0]); // Flush is at index 4
    }

    @Test
    public void testStraight2() {
        Card[] sevenCardHand = {
            new Card("Hearts", 9),
            new Card("Diamonds", 8),
            new Card("Clubs", 7),
            new Card("Spades", 6),
            new Card("Hearts", 5),
            new Card("Diamonds", 2), // Extra card
            new Card("Clubs", 3)    // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(5, result[0]); // Straight is at index 5
    }

    @Test
    public void testThreeOfAKind2() {
        Card[] sevenCardHand = {
            new Card("Diamonds", 9),
            new Card("Hearts", 9),
            new Card("Clubs", 9),
            new Card("Spades", 4),
            new Card("Hearts", 2),
            new Card("Diamonds", 3), // Extra card
            new Card("Clubs", 5)     // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(6, result[0]); // Three of a Kind is at index 6
    }

    @Test
    public void testTwoPair2() {
        Card[] sevenCardHand = {
            new Card("Diamonds", 8),
            new Card("Hearts", 8),
            new Card("Clubs", 5),
            new Card("Spades", 5),
            new Card("Hearts", 2),
            new Card("Diamonds", 3), // Extra card
            new Card("Clubs", 4)     // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(7, result[0]); // Two Pair is at index 7
    }

    @Test
    public void testPair2() {
        Card[] sevenCardHand = {
            new Card("Diamonds", 7),
            new Card("Hearts", 7),
            new Card("Clubs", 4),
            new Card("Spades", 3),
            new Card("Hearts", 2),
            new Card("Diamonds", 5), // Extra card
            new Card("Clubs", 6)     // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(8, result[0]); // Pair is at index 8
    }

    @Test
    public void testHighCard2() {
        Card[] sevenCardHand = {
            new Card("Diamonds", 14), // Ace
            new Card("Hearts", 10),
            new Card("Clubs", 8),
            new Card("Spades", 6),
            new Card("Hearts", 4),
            new Card("Diamonds", 2), // Extra card
            new Card("Clubs", 3)     // Extra card
        };
        Card[] bestHand = Bot.findBest(sevenCardHand);
        int[] result = Bot.findHand(bestHand);
        assertEquals(9, result[0]); // High Card is at index 9
    }
}

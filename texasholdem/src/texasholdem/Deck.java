package texasholdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	public static String[] suits={"Hearts", "Clubs", "Spades", "Diamonds"};
	public static int[] values={2,3,4,5,6,7,8,9,10,11,12,13,14};
	public static Card[] deck= new Card[52];
	private static final Card dealedCard = new Card("",0);

	Deck() {
		buildDeck();
	}
	
	public static void buildDeck() {
		int c=0;
		for (int i=0; i<4; i++) {
			for (int j=0; j<13;j++) {
				deck[c]=new Card(suits[i], values[j]);
				c++;
			}
		}
	}
	
	public static void shuffleDeck() {
		Random random= new Random();
		int n=52;
		for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }
	public static void printDeck() {
		for (int i=0; i<52;i++) {
			if (i==51) {
				System.out.printf(deck[i].toString());
			} else {
			System.out.printf(deck[i].toString()+", ");
			}
		}
		System.out.println();
	}
	
	
	public static Card[] deal(int numCards) {
		int c=0;
		while (deck[c].equals(dealedCard)) {
			c++;
		}
		
		Card[] deal = new Card[numCards];
		for (int i=0; i<numCards; i++) {
			deal[i]=new Card(deck[c].getSuit(), deck[c].getValue());
			c++;
		}
		return deal;
	}
}
	
	
	
	
	
	
		

	

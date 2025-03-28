 package texasholdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	public static String[] suits={"Hearts", "Clubs", "Spades", "Diamonds"};
	public static int[] values={2,3,4,5,6,7,8,9,10,11,12,13,14};
	public static Card[] deck= new Card[52];
	private boolean[] dealtCards = new boolean[52];

	Deck() {
		buildDeck();
		shuffleDeck();
	}
	
	public void buildDeck() {
		int c=0;
		for (int i=0; i<4; i++) {
			for (int j=0; j<13;j++) {
				deck[c]=new Card(suits[i], values[j]);
				c++;
			}
		}
	}
	
	public void shuffleDeck() {
		Random random= new Random();
		int n=52;
		for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }
	public void printDeck() {
		for (int i=0; i<52;i++) {
			if (i==51) {
				System.out.printf(deck[i].toString());
			} else {
			System.out.printf(deck[i].toString()+", ");
			}
		}
		System.out.println();
	}
	
	
	public Card[] deal(int numCards) {
		System.out.print(numCards);
		int c=0;
	
		Card[] deal = new Card[numCards];
		
		for (int i=0; i<52; i++) {
			if (!dealtCards[i] && c<numCards) {
				deal[c]= deck[i];
				dealtCards[i]= true;
				c++;
			}
		}
		
		for (int i=0; i<deal.length;i++) {
			if (deal[i]==null) {
				System.err.println("Error: Card at index"+i+"is null");
			}
		}
		return deal;
	}
}
	
	
	
	
	
	
		

	

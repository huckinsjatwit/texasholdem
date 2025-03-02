package texasholdem;

public class Hand {
	
	Hand() {
		
	}
	public static Card[] makeHand() {
	
	Card[] handed = Deck.deal(2);
	Card[] hand = new Card[2];
		
		for(int i = 0; i < handed.length; i++) {
			hand[i] = handed[i];
		}
		return hand;
	}
	
	
}

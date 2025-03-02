package texasholdem;

public class Hand {
	public static Card[] hand;
	
	
	Hand() {
		makeHand();
	}
	
	public static Card[] makeHand() {
	hand = Deck.deal(2);
	return hand;
	}

	
	public String toString() {
		String hand=this.hand[0].toString()+", "+this.hand[1].toString();
		return hand;
	}
}
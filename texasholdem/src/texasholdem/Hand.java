package texasholdem;

public class Hand {
	public Card[] hand;
	
	
	Hand() {
		makeHand();
	}
	
	public Card[] makeHand() {
	hand = Deck.deal(2);
	return hand;
	}
	
	public Card getCard(int index) {
		return hand[index];
	}

	
	public String toString() {
		String hand=this.hand[0].toString()+", "+this.hand[1].toString();
		return hand;
	}
}
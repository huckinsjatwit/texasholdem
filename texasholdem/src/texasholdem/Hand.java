package texasholdem;

public class Hand {
	public Card[] hand;
	public static final String[] hands= {"Royal Flush","Straight Flush", "Four of A Kind","Full House","Flush","Straight","Three of a Kind"
,"Two Pair","Pair","High Card"};
	public Card[] combinedHand;
	private Game game;
	
	Hand(Game game) {
		this.game=game;
		makeHand();
		
	}
	
	public Card[] makeHand() {
		this.hand = game.deck.deal(2);
		if (hand[0]==null || hand[1]==null) System.err.println("Error: improper deal.");
		return hand;
	}
	
	public Card getCard(int index) {
		return hand[index];
	}
	
	public void combineHand() {
		
		if (this.hand==null || this.hand.length<2) throw new IllegalStateException("Bot's hand is not properly intialized");
		if (game.river==null || game.river.river==null) throw new IllegalStateException("River is not properly intialized");
		if (game.river.river.size()<3) throw new IllegalStateException("River <3 cards.");
		
		int riverSize= game.river.river.size();
		Card[] allCards= new Card[2+riverSize];
		allCards[0]=this.hand[0];
		allCards[1]=hand[1];
		for (int i=2; i<riverSize+2; i++) {
			allCards[i]=game.river.river.get(i-2);
		}
		this.combinedHand=allCards;
	}
	/*
	 * Prints object of hand (2 card, what each player holds)
	 */
	public String toString() {
		String hand=this.hand[0].toString()+", "+this.hand[1].toString();
		return hand;
	}
	
	/*
	 * Can print any amount of cards, mostly for testing, might be useful in the future, though
	 */
	public static String toString(Card[] hand) {
		String handString="";
		for (int i=0; i<hand.length;i++) {
			if (i==hand.length-1) {
				handString+=hand[i].toString();
			} else {
				handString+=hand[i].toString()+", ";
			}
		}
		return handString;
	}
	
	
	public static void main (String[] args) {
	
		
	}
}
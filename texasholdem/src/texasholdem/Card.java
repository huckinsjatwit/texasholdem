package texasholdem;

public class Card {
	private final String suit;
	private int value;
	
	
	Card(String suit, int value) {
		this.suit=suit;
		this.value=value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String toString() {
		if (this.value<11) return (this.value+" of "+this.suit);
		if (this.value==11) return ("Jack of "+this.suit);
		if (this.value==12) return ("Queen of "+this.suit);
		if (this.value==13) return ("King of "+this.suit);
		if (this.value==14) return ("Ace of "+this.suit);
		else return "";
	}
}

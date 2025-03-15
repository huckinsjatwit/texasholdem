package texasholdem;

import javafx.scene.image.Image;

public class Card {
	private final String suit;
	private int value;
	private Image image;
	
	
	Card(String suit, int value) {
		this.suit=suit;
		this.value=value;
		this.image=createImage();
	}
	
	public int getValue() {
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public Image getImage() {
		return image;
	}
	
	public String toString() {
		if (this.value<11) return (this.value+" of "+this.suit);
		if (this.value==11) return ("Jack of "+this.suit);
		if (this.value==12) return ("Queen of "+this.suit);
		if (this.value==13) return ("King of "+this.suit);
		if (this.value==14) return ("Ace of "+this.suit);
		else return "";
	}
	
	public Image createImage() {
		String path= "/sources/cards/"+imageName();
		return new Image(getClass().getResourceAsStream(path));
	}
	
	public String imageName() {
		String valueName;
		
		switch(this.value) {
		
		case (11): valueName="Jack";
		case (12): valueName="Queen";
		case (13): valueName="King";
		case (14): valueName="Ace";
		default: valueName= String.valueOf(this.value);
		}
		return (valueName+"_of_"+this.suit);
	}
}

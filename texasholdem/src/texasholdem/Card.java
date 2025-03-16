package texasholdem;

import javafx.scene.image.Image;
import java.io.InputStream;

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
		String path= "/"+imageName();
		try {
			InputStream stream= getClass().getResourceAsStream(path);
			if (stream == null) {
				System.err.print("Couldn't find image");
				return new Image("/card_placeholder.png");
			}
			return new Image(stream);
		} catch (Exception e){
			return new Image("/card_placeholder.png");
		}
	}
	
	public String imageName() {
		String valueName;
		
		switch(this.value) {
		
		case (11): 
			valueName="jack";
			break;
		case (12): 
			valueName="queen";
			break;
		case (13): 
			valueName="king";
			break;
		case (14): 
			valueName="ace";
			break;
		default: valueName= String.valueOf(this.value);
		}
		return (valueName+"_of_"+this.suit+".png");
	}
}

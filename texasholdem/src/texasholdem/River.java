package texasholdem;
import java.util.ArrayList;
import java.util.Collections;


public class River {
	public ArrayList<Card> river;
	private Game game;
	
	
	River(Game game) {
		this.game=game;
		this.river = new ArrayList<>();
	}
	
	public void riverCreate() {
		ArrayList<Card> river = new ArrayList<>();
		Card[] temp = game.deck.deal(3);
		Collections.addAll(river, temp);		
		this.river=river;
	}
	
	public void riverAdd() {
		Card[] temp = game.deck.deal(1);
		Collections.addAll(this.river, temp);		
	}
	
	public String toString() {
		String river="";
		for (int i=0; i<this.river.size(); i++) {
			if (i==this.river.size()-1) {
				river=river+this.river.get(i).toString();
			} else {
				river=river+this.river.get(i).toString()+", ";
			}
		}
		return river;
	}
	
}


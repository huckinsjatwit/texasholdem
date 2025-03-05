package texasholdem;
import java.util.ArrayList;
import java.util.Collections;


public class River {
	public ArrayList<Card> river;
	
	River() {
		riverCreate();
	}
	
	public void riverCreate() {
		ArrayList<Card> river = new ArrayList<>();
		Card[] temp = Game.deck.deal(3);
		Collections.addAll(river, temp);		
		this.river=river;
	}
	
	public void riverAdd() {
		Card[] temp = Game.deck.deal(1);
		Collections.addAll(this.river, temp);		
	}
	
	public String toString() {
		String river="";
		for (int i=0; i<this.river.size(); i++) {
			if (i==this.river.size()-1) {
				river=river+this.river.get(i);
			} else {
				river=this.river.get(i).toString()+", ";
			}
		}
		return river;
	}
	
}


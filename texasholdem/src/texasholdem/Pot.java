package texasholdem;
import java.util.ArrayList;

public class Pot {
	public static int currentPot = 0;
	public static ArrayList<Integer> bets = new ArrayList<>();
	
	Pot() {
	}
	
	// adds bets to ArrayList and currentPot
	
	public static void addBet(int bet) {
		bets.add(bet);
		currentPot += bet;
		
	}
	
	//Pays out from currentPot then resets the bets Arraylist
	
	public static int payOut() {
		int pay = currentPot;
		currentPot = 0;
		bets.clear();
		
		return pay;
	}
	
	public String toString() {
		String s="The current pot is: " + currentPot;
		return s;
	}

}
	


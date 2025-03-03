package texasholdem;
import java.util.ArrayList;

public class Pot {
	static int currentPot = 0;
	static Balance bet = new Balance();
	public static ArrayList<Integer> bets = new ArrayList<>();
	
	Pot() {
	}
	
	// adds bets to ArrayList and currentPot
	
	public static void addBet(int bet) {
		bets.add(bet);
		currentPot += bet;
		
	}
	
	//Pays out from currentPot then resets the bets Arratlist
	
	public static int payOut() {
		int pay = currentPot;
		currentPot = 0;
		bets.clear();
		
		return pay;
	}
	
}

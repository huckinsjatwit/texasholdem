package texasholdem;

public class Pot {
	static int currentPot = 0;
	static Balance bet = new Balance();
	
	Pot() {
	}
	
	public static void addBet(int bet) {
		currentPot += bet;
	}
	
	public static int payOut() {
		int pay = currentPot;
		currentPot = 0;
		
		return pay;
	}
}

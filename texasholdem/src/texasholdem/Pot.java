package texasholdem;

public class Pot {
	static int money=0;
	static Balance bet = new Balance();
	
	Pot() {
	}
	
	public static void addBet(int bet) {
		money+=bet;
	}
	
	public static int payOut() {
		int pay=money;
		money=0;
		
		return pay;
	}
}

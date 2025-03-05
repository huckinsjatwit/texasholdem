package texasholdem;

public class Balance {
	static int balance=1000;
	
	Balance() {
		
	}
	
	static int currentBalance(int setBal) {
		int balance = setBal;
		return balance;
	}
	
	public String toString() {
		String s="Your current balance is: " + balance;
		return s;
	}

}

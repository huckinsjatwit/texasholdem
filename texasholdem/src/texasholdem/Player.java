package texasholdem;
import java.util.Scanner;

public class Player {
	
	static boolean fold = false;
	static Balance bet = new Balance();
	static Pot balls = new Pot();
	public static int Bal = 1000;
	
	Player(){
	}
	public static void standHand(boolean stand) {
		fold = true;
	}
	public static void foldHand(boolean fold) {
		if(fold == false) {
			//end round code
		}
	}
	
	public static void enterBet() {
		Scanner input = new Scanner(System.in);
		int betAmount;
		
		System.out.print("Enter amount to bet:");
		betAmount = input.nextInt();
		
		if(betAmount > Bal) {
			System.out.println("Cannot bet more than you have");
		}else {
			Bal = Bal - betAmount;
		}
		
	}
	public static int makeBet(int bet) {
		int currentBal = Balance.balance;
		Pot.addBet(currentBal);
		return currentBal;
		
	}
	public static void setBalance(Object Balance) {
		
	}
}

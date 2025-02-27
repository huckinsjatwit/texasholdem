package texasholdem;
import java.util.Scanner;

public class Player {
	
	static boolean fold = false;
	static Pot balls = new Pot();
	private static int Bal = 1000;
	
	Player(){
	}
	public static void standHand(boolean stand) {
		fold = true;
	}
	public static void foldHand(boolean fold) {
		if(fold == false) {
			//skip over player and let bots play code
		}
	}
	
	public static void makeBet() {
		Scanner input = new Scanner(System.in);
		int betAmount;
		
		do {
		System.out.print("Enter amount to bet:");
		betAmount = input.nextInt();
		}while(betAmount > Bal); {
			System.out.println("Bet amount cannot exceed balance.");
		}
			Bal = Bal - betAmount;
			Pot.addBet(betAmount);
		}

}

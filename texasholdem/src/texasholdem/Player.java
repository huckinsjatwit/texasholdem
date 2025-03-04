package texasholdem;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {
	
	public static boolean fold = false;
	static Pot Money = new Pot();
	public static int Bal = 1000;
	public static Hand playerHand;
	
	
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
		System.out.print("Enter amount to bet: ");
		betAmount = input.nextInt();
		}while(betAmount > Bal); {
			System.out.println("Bet amount cannot exceed balance.");
		}
			Bal = Bal - betAmount;
			Pot.addBet(betAmount);
		}
	
	public void makeHand() {
		Hand playerHand= new Hand();
		this.playerHand=playerHand;
	}
	
	public int call(ArrayList<Integer> bet) {
		
		return 0;
	}
		
	public static void main(String[] args) {
		
	}
}


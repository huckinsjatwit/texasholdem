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
	
	//Will run all methods required for the player to play the round
	public static void play() {
		//Add code so that everytime a round starts, the player just checks
		//also nice for code that could count and display rounds
		makeBet();
	}
	
	public static void makeBet() {
		Scanner input = new Scanner(System.in);
		int betAmount;
		
		do {
			System.out.print("Enter amount to bet: ");
			betAmount = input.nextInt();
			if (betAmount>Bal) System.out.println("Bet amount cannot exceed balance.");
		} while(betAmount > Bal);
			
		Bal = Bal - betAmount;
		Pot.addBet(betAmount);
		}
	
	public void makeHand() {
		Hand playerHand= new Hand();
		this.playerHand=playerHand;
	}
	
	public static int buyIn() {

		Scanner input = new Scanner(System.in);
		int ans;
		do {
		System.out.print("Would you like to buy in?(1 for yes 0 for no): ");
		
		ans = input.nextInt();
		
		if(ans > 1 || ans < 0) System.out.println("Please enter a valid input");
		}
		while(ans > 1 || ans < 0);
		
		if(ans == 1) return 20;
		if(ans == 0) return 0; 
		return 0;
	}
	
	
	public static boolean check() {
		
		Scanner input = new Scanner(System.in);
		int checker;
		do {
		System.out.print("Do you check?(1 for YES/0 for NO): ");
		checker = input.nextInt();
		
		if(checker > 1 || checker < 0) System.out.println("Please enter '1' for YES or '0' for NO");
		}while(checker > 1 || checker < 0);
		
		if(checker == 1) return true;
		if(checker == 0) return false;
		return false;
	}
		
	public static void main(String[] args) {
		
	}
}


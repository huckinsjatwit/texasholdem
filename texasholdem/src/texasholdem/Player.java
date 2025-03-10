package texasholdem;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {
	
	public static boolean fold = false;
	static Pot Money = new Pot();
	public static int Bal = 1000;
	public static Hand playerHand;
	public static int prevBet;
	
	
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
		//Add code so that everytime a round starts, other than the start of the round, the player has an option to just checks
		/*if(Game.miniRound == 1) {
			if(buyIn() == 0) {
				Game.remove = true;
			}
		}else {
			if(wantToContinue() == true) {
			Game.remove = true;
			
			code is able to have it so buyIn only occurs in first mini round and every other round will ask if the player wants to fold
			
			ABOVE CODE WORKS BUT CONFLICTS WITH CURRENT GAME
			WILL MAKE IT BOTS AFTER CANNOT BET/PLAY THE GAME
			*/
		
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
		
		betAmount = prevBet;
		Bal = Bal - betAmount;
		Pot.addBet(betAmount);
		prevBet = betAmount;
		}
	
	public void makeHand() {
		Hand playerHand= new Hand();
		this.playerHand=playerHand;
	}
	
	public static int buyIn() {

		Scanner input = new Scanner(System.in);
		int ans;
		do {
		System.out.print("Would you like to buy in?(1 for YES 0 for NO): ");
		ans = input.nextInt();
		
		if(ans > 1 || ans < 0) System.out.println("Please enter a valid input");
		}
		while(ans > 1 || ans < 0);
		
		if(ans == 1) {
			Bal = Bal - 20;
			Pot.addBet(20);
			prevBet = 20;
			System.out.println("Player buys in for 20 chips.");
			return 1;
		}
		if(ans == 0) {
			System.out.println("Player folds");
			return 0;
		}
		return 0;
	}
	
	
	public static boolean check() {
		
		Scanner input = new Scanner(System.in);
		int checker;
		do {
			System.out.print("Do you check?(1 for YES/0 for NO): ");
			checker = input.nextInt();
		
		if(checker > 1 || checker < 0) System.out.println("Please enter '1' for YES or '0' for NO.");
		}while(checker > 1 || checker < 0);
		
		if(checker == 1) return true;
		if(checker == 0) return false;
		return true;
	}
	
	public static boolean wantToContinue() {
		
		Scanner input = new Scanner(System.in);
		int con;
		do {
			System.out.print("Would you like to fold? (1 for YES/0 for NO): ");
			con = input.nextInt();
			
			if(con > 1 || con < 0) System.out.println("Please enter '1' for YES or '0' for NO.");
		}while(con > 1 || con < 0);
		
		if(con == 1) return true;
		if(con == 0) return false;
		return true;
	}
	
	//
	
	public static int call() {
		int high = Pot.highestBet((Pot.currentBets(Game.currentPlayerCount))); //idk why this doesnt work
		
		if(prevBet == high) {
			return 0;
		}else {
			int ans = askCall();
			
			if(ans == 0) return -1;
			if(ans == 1) {
				int diff = high - prevBet;
				Pot.currentPot += diff;
				System.out.println("Player calls!");
				return diff;
			}
		}
		return 0;
		
	}
	
	//Asks player if they want to call
	
	public static int askCall() {
		Scanner input = new Scanner(System.in);
		int ans;
		
		do {
		System.out.print("Would you like to call? (1 for YES/0 for NO): ");
		ans = input.nextInt();
		
		if(ans > 1 || ans < 0) System.out.println("Please enter '1' for YES and '0' for NO");
		}while(ans > 1 || ans < 0);
		
		if(ans == 1) return 1;
		if(ans == 0) return 0;
		return 0;
	}
		
	public static void main(String[] args) {
		
	}
}


package texasholdem;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Player  {
	
	public boolean fold = false;
	//static Pot Money = new Pot();
	public static int Bal = 1000;
	public Hand playerHand;
	public static int prevBet;
	public int[] currentBest;
	private Game game;
	
	
	Player(Game game){
		this.game=game;
	}
	
	public void standHand() {
		fold = true;
	}
	public void foldHand(boolean fold) {
		
	}
	
	//Will run all methods required for the player to play the round
	
	public String play(int n) {
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
				if (game.miniRound>1) {
					
					playerHand.combineHand();
					setCurrentBest();
				}
				return makeBet(n);
			}
	

	private void setCurrentBest() {
		this.currentBest=Bot.findHand(Bot.findBest(playerHand.combinedHand));

	}
	
	public String makeBet(int betAmount) {
		Bal = Bal - betAmount;
		game.pot.addBet(betAmount);
		prevBet = betAmount;
		
		return ("You bet "+betAmount+" chips!\n");
	}
	
	public void makeHand() {
		Hand playerHand= new Hand(game);
		this.playerHand=playerHand;
	}
	
	public int buyIn() {

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
			game.pot.addBet(20);
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
	
	public int call() {
		int high = Pot.highestBet((game.pot.currentBets())); //idk why this doesnt work
		
		if(prevBet == high) {
			return 0;
		}else {
			int ans = askCall();
			
			if(ans == 0) return -1;
			if(ans == 1) {
				int diff = high - prevBet;
				game.pot.currentPot += diff;
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


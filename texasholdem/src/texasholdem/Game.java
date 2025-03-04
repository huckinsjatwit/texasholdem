package texasholdem;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class Game {
	public static int round = 1;
	public static Bot[] bots;

	
	Game(){
	}		
	
	public static void currentRound() {
			System.out.println("Round " + round + "!");
	}
	public static void setRound() {
		round++;
	}
	public static void exitGame() {
		
		String sure;
		Scanner input = new Scanner(System.in);
		System.out.println("Are you sure you want to exit? (Y/N): ");
		sure = input.toString();
		
		if(sure == "Y" || sure == "y") {
			System.out.println("Goodbye!");
			System.exit(0);
		}else if(sure == "N" || sure == "n") {
			System.out.print("ok");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						
		}else {
			System.out.println("Please enter 'Y' for yes or 'N' for no");
		}
		
	}
	
	//just something to print after every round to remind the player what is happening
	
		public static void display() {
			System.out.println("Your current balance is: " + Player.Bal);
			System.out.println("The current pot is: " + Pot.currentPot);
			System.out.println("Your hand is: " + Player.playerHand);
		}
		
		public static void setBots() {
			int botCount=0;
			Scanner input= new Scanner(System.in);
			System.out.printf("How many bots (1-3)?: ");
			botCount=input.nextInt();
			
			while (botCount<1 || botCount>3) {
				System.out.println("Error! Number of bots needs to be between 1-3. Try again.");
				System.out.printf("How many bots (1-3)?: ");
				botCount=input.nextInt();
			}
			
			bots= new Bot[botCount];
			for (int i=0; i<botCount; i++) {
				bots[i]= new Bot(i);
			}
			
			if (botCount==1) System.out.printf("Created bot ");
			else System.out.printf("Created bots: ");
			for (int i=0; i<bots.length; i++) {
				if (i==bots.length-1) {
					System.out.printf(bots[i].name+".%n");
				} else System.out.printf(bots[i].name+", ");
			}
			System.out.println("");
		}
		
	public static void main(String[] args) {
		Deck deck = new Deck();
		Player player = new Player();
		setBots();
	
		player.makeHand();
		
		
		display();
	}

}

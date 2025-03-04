package texasholdem;
import java.util.Scanner;

public class Game {
public static int round = 1;
	
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
	public static void main(String[] args) {
		Deck deck = new Deck();
		Player player = new Player();
		player.makeHand();
		
		display();
	}

}

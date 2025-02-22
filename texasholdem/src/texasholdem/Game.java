package texasholdem;
import java.util.Scanner;

public class Game {
static int round = 1;
	
	Game(){
	}		
	
	public static void currentRound() {
			System.out.println("Round " + round + "!");
	}
	public static void setRound() {
		round = round + 1;
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

		}
		
	}
}

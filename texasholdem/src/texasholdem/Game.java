package texasholdem;

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
		System.exit(0);
	}
}

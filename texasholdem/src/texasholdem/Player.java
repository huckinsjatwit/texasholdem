package texasholdem;

public class Player {
	
	static boolean fold = false;
	Object Balance;
	
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
	public static void makeBet(Object Balance, int bet) {
	
	}
	public static void setBalance(Object Balance) {

	}
}

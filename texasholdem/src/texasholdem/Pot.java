package texasholdem;
import java.util.ArrayList;

public class Pot {
	public static int currentPot = 0;
	public static ArrayList<Integer> bets = new ArrayList<>();
	
	Pot() {
	}
	
	// adds bets to ArrayList and currentPot
	
	public static void addBet(int bet) {
		bets.add(bet);
		currentPot += bet;
		
	}
	
	//Pays out from currentPot then resets the bets Arraylist
	
	public static int payOut() {
		int pay = currentPot;
		currentPot = 0;
		bets.clear();
		
		return pay;
	}
	public static boolean allBetsSame(ArrayList numberOfBots) {
		//create an array that refreshes every round with size of players
		int index=0;
		int highest = 0;
		boolean isSame = true;
		if(numberOfBots.size()<bets.size()){
			index = bets.size()-(numberOfBots.size()); 
		}else {
			index = 0;
			}
		int [] currentBets = new int [numberOfBots.size()];		//uses the bets array to grab the least x amount of bets where x is the number of players still in the round.
		for(int i = 0; i<currentBets.length;i++) {
			currentBets[i] = bets.get(index);		
			index++;
		}
	/* 
		System.out.println("CurrentBets: ");
		for(int j = 0; j<currentBets.length; j++) {
		System.out.print(currentBets[j]+" ");
		}
	*/
		for(int k =0; k < currentBets.length; k++) {
			if(highest<currentBets[k]) {
				highest = currentBets[k];
			}
		}
		//System.out.println("Highest: " + highest);
		for(int l = 0; l<currentBets.length;l++) {
			if(currentBets[l]!=highest) {
				isSame = false;
			}
		}
		return isSame;
	}
	public String toString() {
		String s="The current pot is: " + currentPot;
		return s;
	}
	
	//We'll need to reset the bets ArrayList after each mini-round since we don't want to carry over the lost mini-round's bets
	
	public static void resetBets() {
		bets.clear();
	}

}
	


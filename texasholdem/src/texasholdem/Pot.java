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
	public static boolean allBetsSame(ArrayList currentPlayers) {
		//create an array that refreshes every round with size of players
		boolean isSame=true;
		
		//System.out.println("Highest: " + highest);
		int [] currentBets = currentBets(currentPlayers);				
		int highest = highestBet(currentBets);
		
		for(int l = 0; l<currentBets.length;l++) {
			if(currentBets[l]!=highest) {
				isSame = false;
			}
		}
		
		return isSame;
	}
	
	/*
	 * creates an array of the current round bets using the arraylist of currentPlayers(botCopy) to get the number of current players. 
	 */
	public static int[] currentBets(ArrayList currentPlayers) {
		int index=0;
		
		if(currentPlayers.size()<bets.size()){ 			//uses the bets array to grab the least x amount of bets where x is the number of players still in the round.
			index = bets.size()-(currentPlayers.size()); 		//if the number of current players is greater than or equal to the number of bets in the round, the index would be zero. 
		}else {													// As long as the botCopy arraylist updates immediately whenever a player or bot folds, it should work fine
			index = 0;
			}
		int [] currentBets = new int [currentPlayers.size()];  
		for(int i = 0; i<currentBets.length;i++) {
			currentBets[i] = bets.get(index);		
			index++;
		}
		return currentBets;
	}

	/*
	 * pairs with the currentBets method to return the highest bet of the round. Used for checking that all the bets match the highest. Useful for a player call method to make sure the player can't call lower than the highest bet
	 */
	public static int highestBet(int[] currentBets){
		int highest = 0;	

		for(int i = 0; i < currentBets.length; i++) {
			if(highest<currentBets[i]) {
				highest = currentBets[i];
			}

		}
		/* 
		System.out.println("CurrentBets: ");					// for testing
		for(int j = 0; j<currentBets.length; j++) {
		System.out.print(currentBets[j]+" ");
		}
	*/
		//System.out.println("Highest: " + highest);
		return highest;
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
	


package texasholdem;
import java.util.ArrayList;

public class Bot {
	
	private int Balance=1000;
	private int Confidence=0;
	private Card[] hand = new Card[2];
	//When balance reaches 0 remove bot
	
	Bot() {
		Card[] handed = Deck.deal(2);
		
		for(int i = 0; i < handed.length; i++) {
			hand[i] = handed[i];
		}
	}
	private static int analzyeHand1(ArrayList<Card> river) {
		
		
		return 0;
	}
	
	private static int analyzeBets(ArrayList<Card> river) {
		return 0;
	}
	
	private static int analyzeHand2(ArrayList<Card> river) {
		return 0;
	}
	
	private static int analyzeHand3(ArrayList<Card> river) {
		return 0;
	}
	
	private static int analyzeHand4(ArrayList<Card> river) {
		return 0;
	}
}
	
	//private static int analyzeHand1(//river) { first round
		//750 points for hand
	//}
	
	//private static int analyzeBets(//otherbets[];
		//250 for other players bets
//}
	
//private static int analyzeHand2(//river) { second round
//private static int analyzeHand3(//river) { third round
//private static int analyzeHand4(//river) { fourth round
		//save final hand for comparison
		//decides who wins the pot
		
//Methods for each hand type: Each will return int of confidence
					
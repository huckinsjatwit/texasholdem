package texasholdem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Bot {
	
	private int Balance=1000;
	public int Confidence=0;
	public Hand botHand;
	public boolean Stand = true;
	public String name;
	public static List<String> possibleNames= Arrays.asList("Jeremy", "Thomas", "Jack", "Kristian", "Jayvon", "Haley", "Sam", "Ava", "Todd", "Nicole",
			"Rick"); //Add any names you like
	public static int prevBet;
	
	Bot(int n) {
		setName(n);
	}
	
	public void setName(int n) {
		name= possibleNames.get(n);
	}
	
	public void makeHand() {
		Hand botHand= new Hand();
		this.botHand=botHand;
	}
	
	//Will run all methods required for the bot to play the round
	public String play(int subRound) {
		int bet=0;
		if (subRound==0) {
			Confidence=analyzeStartingHand();
			Confidence+=analyzeBets();
			return buyIn();
		}
		if (subRound==1) {
			Confidence=analyzeHand2(Game.river);
			Confidence+=analyzeBets();
			bet=betAmount();
			return bet(bet);
		}
		if (subRound==2) {
			Confidence=analyzeHand3(Game.river);
			Confidence+=analyzeBets();
			bet=betAmount();
			return bet(bet);
		}
		if (subRound==3) {
			Confidence=analyzeHand4(Game.river);
			Confidence+=analyzeBets();
			bet=betAmount();
			return bet(bet);
		}
		return "";
	}

	//uses checkCardValueStart to find card's values than add Confidence
	
	private int analyzeStartingHand() {
		
		int value= checkCardValueStart(botHand.hand);
		int confidence=0;
		
		for(int i = 0; i < 2; i++) {
			if(value == 20) {
				confidence += 100;
			}else if(value > 15) {
				confidence += 50;
			}else if(value > 10) {
				confidence += 30;
			}else if(value > 5) {
				confidence += 10;
			}else {
				confidence += 1;
			}
		}
		return confidence;
	}
	
	//looks at card values and use of Chen formula to figure out hand value (CHANGE CONFIDENCE VALUES IF NEEDED)
	
	private static int checkCardValueStart(Card[] hand) {
	
		int allConfidence = 0;
		
		for(int i = 0; i < 2; i++) {
			if(hand[i].getValue() == 12) {
				allConfidence += 10;
			}else if(hand[i].getValue() >= 9) {
				allConfidence += 8;
			}else if(hand[i].getValue() >= 5) {
				allConfidence += 6;
			}else if(hand[i].getValue() >= 2) {
				allConfidence += 4;
			}else {
				allConfidence += 2;
			}
		}
			return allConfidence;
	}
		
		//adds confidence to the bot based on what other players bet (CHANGE CONFIDENCE AMOUNT IF NEEDED)
	
	private int analyzeBets() {
		
		for(int i = 0; i < Pot.bets.size(); i++) {
			if(Pot.bets.get(i) < 25) {
				Confidence += 20;
			}else if(Pot.bets.get(i) < 50){
				Confidence += 15;
			}else if(Pot.bets.get(i) < 75) {
				Confidence += 10;
			}else if(Pot.bets.get(i) < 100) {
				Confidence += 5;
			}else {
				return Confidence;
			}
		}
		
		return Confidence;
	}
	
	//decides amount to bet based on confidence (if we decide that the person that starts always changes)
	//Might have to change based on raising rather than saying how much 
	private int betAmount() {
		
		Random random = new Random();
		int bet;
			
		if (Confidence >= 1000) return random.nextInt((int)(Balance - (Balance * 0.9))) + (int)(Balance * 0.9);
		if (Confidence >= 900) return random.nextInt((int)(Balance * .9 - (Balance* 0.8))) + (int)(Balance / 2 * 0.8);
		if (Confidence >= 800) return random.nextInt((int)(Balance * .8 - (Balance * 0.7))) + (int)(Balance / 3 * 0.7);
		if (Confidence >= 700) return random.nextInt((int)(Balance * .7 - (Balance * 0.6))) + (int)(Balance / 4 * 0.6);
		if (Confidence >= 600) return random.nextInt((int)(Balance * .6 - (Balance * 0.5))) + (int)(Balance / 5 * 0.5);
		if (Confidence >= 500) return random.nextInt((int)(Balance * .5 - (Balance * 0.4))) + (int)(Balance / 6 * 0.4);
		if (Confidence >= 400) return random.nextInt((int)(Balance * .4 - (Balance * 0.3))) + (int)(Balance / 7 * 0.3);
		if (Confidence >= 300) return random.nextInt((int)(Balance * .3 - (Balance * 0.2))) + (int)(Balance / 8 * 0.2);
		if (Confidence >= 200) return random.nextInt((int)(Balance * .2 - (Balance * 0.1))) + (int)(Balance / 9 * 0.1);
		if (Confidence >= 100) return random.nextInt((int)(Balance * .1 - (Balance * 0.0))) + (int)(Balance / 10 * 0.0);
		return 0;
	}
	
	private String buyIn() {
		String noBuy=this.name+" doesn't buy in.";
		
		if(Confidence >= 10) {
			return bet(20);
		}else {
			Stand = false;
		}
		return noBuy;
	}
	
	private String bet(int betAmount) {
		Game.pot.addBet(betAmount);
		Balance=Balance-betAmount;
		String bet=this.name + " bets " + betAmount + " chips.";
		return bet;
	}
	
	private int call() {
		int high = Pot.highestBet(Game.currentPlayerCount);
		int minConfidence = 0;
		
		if(prevBet == high) {
			return 0;
		}else {
			if(Game.miniRound == 2) minConfidence += 100;
			if(Game.miniRound == 3) minConfidence += 200;
			if(Game.miniRound == 4) minConfidence += 300;
			if(Confidence > minConfidence) {
				int diff = high - prevBet;
				Pot.currentPot += diff;
				System.out.println(name + "calls!");
				return diff;
			}else {
				return -1;
			}
		}
	}
	
	private boolean check(ArrayList bets) {
		
		
		
		return false;
	}
	
	/*
	 * Cards in bot hand + the 3 cards in river
	 */
	private int analyzeHand2(River River) {
		Card[] bigHand= new Card[5];
		bigHand[0]=botHand.getCard(0);
		bigHand[1]=botHand.getCard(1);
		bigHand[2]=River.river.get(0);
		bigHand[3]=River.river.get(1);
		bigHand[4]=River.river.get(2);
		
		int[] readHand=findHand(bigHand);
		return getConfidence(readHand);
	}
	
	
	/*
	 * Cards in bot hand + 4 cards in river, will test all card combo possibilities (NEEDS ALGORITHM TO FIND ALL POSSIBILITIES)
	 */
	private int analyzeHand3(River River) {
		Card[] bigHand= new Card[6];
		bigHand[0]=botHand.getCard(0);
		bigHand[1]=botHand.getCard(1);
		bigHand[2]=River.river.get(0);
		bigHand[3]=River.river.get(1);
		bigHand[4]=River.river.get(2);
		bigHand[5]=River.river.get(3);
		
		int[] bestHand= findBest(bigHand);

		return getConfidence(bestHand);
	}
		
	
	/*
	 * Cards in bot hand + 5 cards in river, will test all card combo possibilities (NEEDS ALGORITHM TO FIND ALL POSSIBILITIES)
	 */
	private int analyzeHand4(River River) {
		Card[] bigHand= new Card[7];
		bigHand[0]=botHand.getCard(0);
		bigHand[1]=botHand.getCard(1);
		bigHand[2]=River.river.get(0);
		bigHand[3]=River.river.get(1);
		bigHand[4]=River.river.get(2);
		bigHand[5]=River.river.get(3);
		bigHand[6]=River.river.get(4);
		
		int[] bestHand= findBest(bigHand);
		return getConfidence(bestHand);
	}
	

	private static int[] findBest(Card[] bigHand) {

		List<Card[]> possibleHands=findHandCombos(bigHand);
		ArrayList<int[]> readHands = new ArrayList<>();
		
		int lowest=10;
		int indexOfBest=0;
		for (int i=0; i<possibleHands.size(); i++) {
			readHands.add(findHand(possibleHands.get(i)));
			if (readHands.get(i)[0]<lowest) {
				lowest=readHands.get(i)[0];
				indexOfBest=i;
			}
			if (readHands.get(i)[0]==lowest) {
				if (readHands.get(i)[1]>readHands.get(indexOfBest)[1]) indexOfBest=i;
			}
		}

		return readHands.get(indexOfBest);
	}
	
	
	/*
	 * Takes array from findHand and returns a confidence value
	 */
	private int getConfidence(int[] readHand) {
		if (readHand[0]==0) return 900;
		if (readHand[0]==1) return 800+readHand[1];
		if (readHand[0]==2) return 700+readHand[1];
		if (readHand[0]==3) return 600+readHand[1];
		if (readHand[0]==4) return 500+readHand[1];
		if (readHand[0]==5) return 400+readHand[1];
		if (readHand[0]==6) return 300+readHand[1];
		if (readHand[0]==7) return 200+readHand[1];
		if (readHand[0]==8) return 100+readHand[1];
		if (readHand[0]==9) return 50+readHand[1];
		return 0;
	}
	
	/*
	 * Finds the hand from 5 card array, returns array where first element is representation of what hand (indexes in hands variable in Hand class)
	 * Second variable is how much the cards are worth
	 */
	public static int[] findHand(Card[] hand) {
		int[] countS=countSuits(hand);
		int[] countV=countValues(hand);
		int[] readHand= new int[2]; 
		if (checkRoyalFlush(countS,countV)) {
			readHand[0]=0;
			readHand[1]=0;
			return readHand;
		}
		int SF=(checkStraightFlush(countS,countV));
		if (SF>0) {
			readHand[0]=1;
			readHand[1]=SF;
			return readHand;
		}
		int FOAK=(checkFourOfAKind(countS,countV));
		if (FOAK>0) {
			readHand[0]=2;
			readHand[1]=FOAK;
			return readHand;
		}
		int FH=(checkFullHouse(countS,countV));
		if (FH>0) {
			readHand[0]=3;
			readHand[1]=FH;
			return readHand;
		}
		int F=(checkFlush(countS,countV));
		if (F>0) {
			readHand[0]=4;
			readHand[1]=F;
			return readHand;
		}
		int S=(checkStraight(countS,countV));
		if (S>0) {
			readHand[0]=5;
			readHand[1]=S;
			return readHand;
		}
		int TOAK=(checkThreeOfAKind(countS,countV));
		if (TOAK>0) {
			readHand[0]=6;
			readHand[1]=TOAK;
			return readHand;
		}
		int TP=(checkTwoPair(countS,countV));
		if (TP>0) {
			readHand[0]=7;
			readHand[1]=TP;
			return readHand;
		}
		int P=(checkPair(countS,countV));
		if (P>0) {
			readHand[0]=8;
			readHand[1]=P;
			return readHand;
		}
		int HC=(checkHighCard(countS,countV));
		if (HC>0) {
			readHand[0]=9;
			readHand[1]=HC;
			return readHand;
		}
		return readHand;
	}
	
	/*
	 * Prints what hand it is, and "+ CARD_VALUE", for example "Pair, +18")
	 */
	public static String findHandToString(Card[] hand) {
		int[] readHand=findHand(hand);
		int handNum=readHand[0];
		String handAdd=String.valueOf(readHand[1]);
		String bestHand=(Hand.hands[handNum]+", +"+handAdd);
		return bestHand;
	}
	
	/*
	 * Counts how many of each suit
	 */
	public static int[] countSuits(Card[] bigHand) {
		int[] suitCount= new int[4];
		for (int i=0; i<5; i++) {
			for (int j=0; j<4; j++) {
				if (bigHand[i].getSuit()==Deck.suits[j]) suitCount[j]++;
			}
		}
		return suitCount;
	}
	
	/*
	 * Will find and return a matrix of all possible combinations of 5 from any amount of cards
	 */

	private static List<Card[]> findHandCombos(Card[] allCards) {

		int k = 5;                             // sequence length   

		List<Card[]> subsets = new ArrayList<>();
		int[] s = new int[k];                  

		if (k <= allCards.length) {
		    for (int i = 0; (s[i] = i) < k - 1; i++) {

		    	addIfUnique(allCards, s, subsets);

		    }
		    for(;;) {
		        int i;
		 
		        for (i = k - 1; i >= 0 && s[i] == allCards.length - k + i; i--); 
		        if (i < 0) {
		            break;
		        }
		        s[i]++;                   
		        for (++i; i < k; i++) {    
		            s[i] = s[i - 1] + 1; 
		        }

		        addIfUnique(allCards, s,subsets);

		    }
		}
		return subsets;
	}

	
	private static void addIfUnique(Card[] allCards, int[] subset, List<Card[]> subsets) {
	    Set<Card> cardSet = new HashSet<>();
	    for (int i : subset) {
	        if (!cardSet.add(allCards[i])) {  // check if adding the card is a duplicate
	            return;  // if duplicate found, do not add this combination
	        }
	    }
	    subsets.add(getSubset(allCards, subset));  // Add only if unique
	}

		
	static Card[] getSubset(Card[] input, int[] subset) {

		Card[] result = new Card[subset.length]; 
		for (int i = 0; i < subset.length; i++) {
		    result[i] = input[subset[i]];
		}
	    return result;
	}
	
	
	/*
	 * Counts how many of each value
	 */
	public static int[] countValues(Card[] bigHand) {
		int[] valueCount= new int[13];
		for (int i=0; i<5; i++) {
			for (int j=0; j<13; j++) {
				if (bigHand[i].getValue()==Deck.values[j]) valueCount[j]++;
			}
		}
		return valueCount;
	}
	
	
	
	public static boolean checkRoyalFlush(int[] suitCount, int[] valueCount) {
		for (int i=0; i<4; i++) {
			if (suitCount[i]!=0 && suitCount[i]!=5) return false;
		}
		for (int i=12; i>7; i--) {
			if (valueCount[i]!=1) return false;
		}
		return true;
	}
	
	public static int checkStraightFlush(int[] suitCount, int[] valueCount) {
		for (int i = 0; i < 4; i++) {
	        if (suitCount[i] != 0 && suitCount[i] != 5) return 0;
	    }
	    
	    int c = 0;
	    int value = 0;
	    while (c < 13 && valueCount[c] != 1) {
	        c++;
	    }
	    
	    // Ensure we don't exceed the array bounds (i < 13)
	    for (int i = c; i < c + 5 && i < 13; i++) {
	        if (valueCount[i] != 1) return 0;
	        value += Deck.values[i];
	    }
	    return value;
	}
	

	public static int checkFlush(int[] suitCount, int[] valueCount) {
		int value=0;
		for (int i=0; i<4; i++) {
			if (suitCount[i]!=0 && suitCount[i]!=5) return 0;
		}
		for (int i=0; i<13; i++) {
			if (valueCount[i]==1) value+= Deck.values[i];
		}
		return value;
	}
	
	public static int checkFullHouse(int[] suitCount, int[] valueCount) {
		int value=0;
		for (int i=0; i<13; i++) {
			if (valueCount[i]!=3 && valueCount[i]!=2 && valueCount[i]!=0) return 0;
			if (valueCount[i]==3) value+=Deck.values[i]*3;
			if (valueCount[i]==2) value+=Deck.values[i]*2;
		}
		return value;
	}
	
	public static int  checkFourOfAKind(int[] suitCount, int[] valueCount) {
		for (int i=0; i<13; i++) {
			if (valueCount[i]==4) return Deck.values[i]*4;
		}
		return 0;
	}
	
	public static int checkStraight(int[] suitCount, int[] valueCount) {
		int c=0;
		int value=0;
		for (int i=0; i<13;i++) {
			if (valueCount[i]>1) return 0;
		}
		while (valueCount[c]!=1) {
			c++;
		}
		for (int i=c; i<c+5; i++) {
			if (valueCount[i]!=1) return 0;
			if (valueCount[i]==1) value+=Deck.values[i];
		}
		return value;
	}
	
	public static int checkThreeOfAKind(int[] suitCount, int[] valueCount) {
		for (int i=0; i<13; i++) {
			if (valueCount[i]==3) return Deck.values[i]*3;
		}
		return 0;
	}
	
	public static int checkTwoPair(int[] suitCount, int[] valueCount) {
		int pairCount=0;
		int value=0;
		for (int i=0; i<13; i++) {
			if (valueCount[i]==2) { 
				pairCount++;
				value+=Deck.values[i]*2;
			}
		}
		if (pairCount==2) return value;
		return 0;
	}
	
	public static int checkPair(int[] suitCount, int[] valueCount) {
		for (int i=0; i<13; i++) {
			if (valueCount[i]==2) return Deck.values[i]*2;
		}
		return 0;
	}
	
	public static int checkHighCard(int[] suitCount, int[] valueCount) {
		for (int i=12; i>-1; i--) {
			if (valueCount[i]>0) return Deck.values[i];
		}
		return 0;
	}
	
	public static void main(String[] args) {
		
	}
	
}
					
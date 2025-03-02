package texasholdem;
import java.util.ArrayList;
import java.util.Collections;

public class Bot {
	
	private int Balance=1000;
	private int Confidence=0;
	public  Hand botHand;
	
	Bot() {
	}
	
	public void makeHand() {
		Hand botHand= new Hand();
		this.botHand=botHand;
	}

	private int analzyeHand1() {
		
		return 0;
	}
	
	private int analyzeBets(ArrayList<Card> river) {
		return 0;
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
		Card[] bigHand= new Card[5];
		bigHand[0]=botHand.getCard(0);
		bigHand[1]=botHand.getCard(1);
		bigHand[2]=River.river.get(0);
		bigHand[3]=River.river.get(1);
		bigHand[4]=River.river.get(2);
		bigHand[5]=River.river.get(3);
	}
	
	/*
	 * Cards in bot hand + 5 cards in river, will test all card combo possibilities (NEEDS ALGORITHM TO FIND ALL POSSIBILITIES)
	 */
	private int analyzeHand4(River River) {
		Card[] bigHand= new Card[5];
		bigHand[0]=botHand.getCard(0);
		bigHand[1]=botHand.getCard(1);
		bigHand[2]=River.river.get(0);
		bigHand[3]=River.river.get(1);
		bigHand[4]=River.river.get(2);
		bigHand[5]=River.river.get(3);
		bigHand[6]=River.river.get(4);
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
	private Card[][] findHandCombos(Card[] allCards) {
		
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
		for (int i=0; i<4; i++) {
			if (suitCount[i]!=0 && suitCount[i]!=5) return 0;
		}
		int c=0;
		int value=0;
		while (valueCount[c]!=1) {
			c++;
		}
		for (int i=c; i<c+5; i++) {
			if (valueCount[c]!=1) return 0;
			if (valueCount[i]==1) value+=Deck.values[i];
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
}
					
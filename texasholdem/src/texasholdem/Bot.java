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
	
	private int analyzeHand2(River River) {
		Card[] bigHand= new Card[5];
		bigHand[0]=botHand.getCard(0);
		bigHand[1]=botHand.getCard(1);
		bigHand[2]=River.river.get(0);
		bigHand[3]=River.river.get(1);
		bigHand[4]=River.river.get(2);
		
		int[] suitCount=countSuits(bigHand);
		int[] valueCount=countValues(bigHand);
		
		if (checkRoyalFlush(suitCount, valueCount)) return 750;
		
		int SF=checkStraightFlush(suitCount, valueCount);
		
		
		if (SF>0) return 600+SF;
		return 0;
	}
	
	private int analyzeHand3(ArrayList<Card> river) {
		return 0;
	}
	
	private int analyzeHand4(ArrayList<Card> river) {
		return 0;
	}

	
	private int[] countSuits(Card[] bigHand) {
		int[] suitCount= new int[4];
		for (int i=0; i<5; i++) {
			for (int j=0; j<4; j++) {
				if (bigHand[i].getSuit()==Deck.suits[j]) suitCount[j]++;
			}
		}
		return suitCount;
	}
	
	private int[] countValues(Card[] bigHand) {
		int[] valueCount= new int[13];
		for (int i=0; i<5; i++) {
			for (int j=0; j<13; j++) {
				if (bigHand[i].getValue()==Deck.values[j]) valueCount[j]++;
			}
		}
		return valueCount;
	}
	
	
	
	private boolean checkRoyalFlush(int[] suitCount, int[] valueCount) {
		for (int i=0; i<4; i++) {
			if (suitCount[i]!=0 || suitCount[i]!=5) return false;
		}
		for (int i=12; i>7; i--) {
			if (valueCount[i]!=1) return false;
		}
		return true;
	}
	
	private int checkStraightFlush(int[] suitCount, int[] valueCount) {
		for (int i=0; i<4; i++) {
			if (suitCount[i]!=0 || suitCount[i]!=5) return 0;
		}
		int c=0;
		int value=0;
		while (valueCount[c]!=1) {
			c++;
		}
		for (int i=c; i<c+5; i++) {
			if (valueCount[c]!=1) return 0;
			if (valueCount[i]==1) value+=valueCount[i];
		}
		return value;
	}
	
	private int checkFlush(int[] suitCount, int[] valueCount) {
		int value=0;
		for (int i=0; i<4; i++) {
			if (suitCount[i]!=0 || suitCount[i]!=5) return 0;
		}
		for (int i=0; i<13; i++) {
			if (valueCount[i]==1) value+= valueCount[i];
		}
		return value;
	}
	
	private int checkFullHouse(int[] suitCount, int[] valueCount) {
		int value=0;
		for (int i=0; i<13; i++) {
			if (valueCount[i]!=3 || valueCount[i]!=2 ||valueCount[i]!=0) return 0;
			if (valueCount[i]==3) value+=valueCount[i]*3;
			if (valueCount[i]==2) value+=valueCount[i]*2;
		}
		return value;
	}
	
	private int  checkFourOfAKind(int[] suitCount, int[] valueCount) {
		for (int i=0; i<13; i++) {
			if (valueCount[i]==4) return valueCount[i]*4;
		}
		return 0;
	}
	
	private int checkStraight(int[] suitCount, int[] valueCount) {
		int c=0;
		int value=0;
		while (valueCount[c]!=1) {
			c++;
		}
		for (int i=c; i<c+5; i++) {
			if (valueCount[c]!=1) return 0;
			if (valueCount[i]==1) value+=valueCount[i];
		}
		return value;
	}
	
	private int checkThreeOfAKind(int[] suitCount, int[] valueCount) {
		for (int i=0; i<13; i++) {
			if (valueCount[i]==3) return valueCount[i]*3;
		}
		return 0;
	}
	
	private int checkTwoPair(int[] suitCount, int[] valueCount) {
		int pairCount=0;
		int value=0;
		for (int i=0; i<13; i++) {
			if (valueCount[i]==2) { 
				pairCount++;
				value=valueCount[i]*2;
			}
		}
		if (pairCount==2) return value;
		return 0;
	}
	
	private boolean checkPair(int[] suitCount, int[] valueCount) {
		for (int i=0; i<13; i++) {
			if (valueCount[i]==2) return true;
		}
		return true;
	}
	
	private int checkHighCard(int[] suitCount, int[] valueCount) {
		for (int i=12; i>-1; i--) {
			if (valueCount[i]>0) return valueCount[i];
		}
		return 0;
	}
}
					
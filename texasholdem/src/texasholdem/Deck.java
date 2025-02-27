package texasholdem;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Deck {
	static int[] deck=new int[52];
	static File cardDeck = new File("texasholden/texasholdem/scr/texasholdem/deck");
	
	Deck() {
		for (int i=0; i<52; i++) {
			deck[i]=i+1;
		}
	}
	
	public static void shuffleDeck() {
		Random random= new Random();
		int n=52;
		for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }
	public static void printDeck() {
		for (int i=0; i<52;i++) {
			if (i==51) {
				System.out.printf("%d",deck[i]);
			} else {
			System.out.printf("%d, ",deck[i]);
			}
		}
		System.out.println();
	}
}
	
	//public static String getCard() {
		//cardDeck
	//}
//}

	//public static String getSuit() {
		
	//}
	
		

	

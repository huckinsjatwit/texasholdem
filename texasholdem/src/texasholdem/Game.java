package texasholdem;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.InputMismatchException;

public class Game {
	public static int round = 1;
	public static ArrayList<Bot> bots;
	public static Deck deck;

	
	Game(){
		
	}		
	
	public static void currentRound() {
			System.out.println("Round " + round + "!");
	}
	public static void setRound() {
		round++;
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
			System.out.print("ok");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						
		}else {
			System.out.println("Please enter 'Y' for yes or 'N' for no");
		}
		input.close();
	}
	
	//just something to print after every round to remind the player what is happening
	
	public static void display() {
		System.out.println("Your current balance is: " + Player.Bal);
		System.out.println("The current pot is: " + Pot.currentPot);
		System.out.println("Your hand is: " + Player.playerHand);
	}
		/*
		 * Allows user to pick number of bots, also creates a bot with the name player, this bot will be detected and start the players turn.
		 */
	public static void setBots() {
		bots=new ArrayList<>();
		Collections.shuffle(Bot.possibleNames);
		int botCount=0;
		Scanner input= new Scanner(System.in);
		boolean success= false;
		while (!success) {
			try {
				System.out.printf("How many bots (1-3)?: ");
				botCount=input.nextInt();
				success=true;
				if (botCount>=1 && botCount<=3) {
					success=true;
				} else {
					System.out.println("Error! Number of bots needs to be between 1-3. Try again.");
					success= false;
					System.out.printf("How many bots (1-3)?: ");
					botCount=input.nextInt();
				}
			} catch (InputMismatchException ex) {
				System.out.println("Error! Number has to be an integer. Try again.");
				input.nextLine();
			}
		}
	
			
		for (int i=0; i<botCount+1; i++) {
			bots.add(new Bot(i));
		}
		bots.get(botCount).name="Player";
			
		if (botCount==1) System.out.printf("Created bot ");
		else System.out.printf("Created bots: ");
		for (int i=0; i<bots.size()-1; i++) {
			if (i==bots.size()-2) {
				System.out.printf(bots.get(i).name+".%n");
			} else System.out.printf(bots.get(i).name+", ");
		}
		System.out.println();
	}
	
	
	
	//shifts one for every round besides the first
	public static void shiftLeft(ArrayList<Bot> array) {
        Bot firstElement = array.get(0);
        
        for (int i=0; i<array.size()-1; i++) {
            array.set(i, array.get(i+1));
        }
        
        array.set(array.size()-1, firstElement);
    }
		
	public static void main(String[] args) {
		deck = new Deck();
		Player player = new Player();
	
		setBots();
		Collections.shuffle(bots); //Shuffles order for first round
		
		player.makeHand();
		
		//When it hits bot named player, it will trigger the players play method. Otherwise bot's
		for (int i=0; i<bots.size(); i++) {
			if (bots.get(i).name=="Player") {
				player.play();
			} else bots.get(i).play();
		
		}
		
		display();
		
	}

}

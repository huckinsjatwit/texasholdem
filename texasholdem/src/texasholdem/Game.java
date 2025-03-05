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
	public static Pot pot;
	public static River river;

	
	Game(){
		
	}		
	
	public static void setRound() {
		round++;
	}
	public static void exitGame() {
		
		int sure;
		Scanner input = new Scanner(System.in);
		do {
		System.out.printf("Are you sure you want to exit? (1 for YES, 0 for NO): ");
		sure = input.nextInt();
		
		if(sure > 1 || sure < 0) System.out.println("Please enter '1' for YES or '0' for NO");
		}
		while(sure > 1 || sure < 0);
		
		if(sure == 1) {
			System.out.println("Goodbye!");
			System.exit(0);
		}else if(sure == 0) {
			System.out.print("ok :D");
		}
		input.close();
	}
	
	//just something to print after every round to remind the player what is happening
	
	public static void display() {
		System.out.println("");
		System.out.println("Your current balance is: " + Player.Bal);
		try {
			Thread.sleep(1000);
			System.out.println(pot.toString());
			Thread.sleep(1000);
			System.out.println("Your hand is: " + Player.playerHand);
			Thread.sleep(1000);
			System.out.println("Current Round: " + round);
			Thread.sleep(1000);
			if (river.river==null) System.out.println("River is empty.");
			else System.out.println("The river is: "+river.toString());
			//Thread.sleep(1000);
			System.out.println("");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
				System.out.printf("How many bots (1-7)?: ");
				botCount=input.nextInt();
				success=true;
				if (botCount>=1 && botCount<=7) {
					success=true;
				} else {
					System.out.println("Error! Number of bots needs to be between 1-7. Try again.");
					success= false;
					System.out.printf("How many bots (1-7)?: ");
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
				System.out.printf(bots.get(i).name+".");
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
		Scanner input = new Scanner(System.in);
		Player player = new Player();
	
		setBots();
		Collections.shuffle(bots); //Shuffles order for first round
		ArrayList<Bot> botsCopy= new ArrayList<>(bots); //Allows us to remove players without affecting the original list.
		
		int c=1; //allows to loop infinitely until player quits.
		while (c==1) {
			
			//Makes deck, pot, and river reset after every round
			deck = new Deck();
			pot = new Pot();
			river=new River();
			
			//Deals to bots and players in their order.
			for (int i=0; i<botsCopy.size(); i++) {
				if (botsCopy.get(i).name=="Player") {
					player.makeHand();
				} else botsCopy.get(i).makeHand();
			}
			
		
			//When it hits bot named player, it will trigger the players play method. Otherwise bot's
			for (int i=0; i<4; i++) {
				if (i==1) river.riverCreate();
				if (i>1) river.riverAdd();
				
				display();
				
				for (int j=0; j<botsCopy.size(); j++) {
					if (botsCopy.get(j).name=="Player") {
						System.out.println();
						System.out.println(pot.toString());
						player.play();
					} else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(botsCopy.get(j).botHand.toString());
						System.out.println(botsCopy.get(j).play(i));
						
					}
				
				}
				shiftLeft(botsCopy);
			}
			
			
			display();
			System.out.printf("Type 1 to continue to next round, 0 to quit. ");
			if (input.nextInt()==0) {
				exitGame();
			} else {
				System.out.println("");
				continue;
			}
			
		}
	}

}

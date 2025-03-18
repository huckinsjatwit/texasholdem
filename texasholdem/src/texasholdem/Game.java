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
	public static int miniRound = 1;
	public static boolean remove = false;
	public static int currentPlayerCount;
	
	Game(){
		
	}		
	
	public static void setRound() {
		round++;
	}
	public static void setCurrentPlayerCount(ArrayList currentPlayers) {
		currentPlayerCount=currentPlayers.size();
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
	
	public static Bot findWinner(ArrayList<Bot> array) {
		int[] maxHand= {10,0}; 
		Bot currentWinner=array.get(0);
		
		for (int i=0; i<array.size(); i++) {
			if (array.get(i).name=="Player") { //Checks if the bot is a player to check the players hand rather than placeholder bot
				if (Player.bestHand(Player.playerHand, Game.river)[0]<maxHand[0]) {
					maxHand=Player.bestHand(Player.playerHand, Game.river);
					currentWinner=array.get(i);
				} else if (Player.bestHand(Player.playerHand, Game.river)[0]==maxHand[0]) {
					if (Player.bestHand(Player.playerHand, Game.river)[1]>maxHand[1]) {
						maxHand=Player.bestHand(Player.playerHand, Game.river);
						currentWinner=array.get(i);
					}
				}
				
			} else if (Bot.findBest(Bot.combineHand(array.get(i).botHand, Game.river))[0]<maxHand[0]) {
				maxHand=Bot.findHand(array.get(i).botHand.hand);
				currentWinner=array.get(i);
			} else if (Bot.findHand(array.get(i).botHand.hand)[0]==maxHand[0]) {
				if (Bot.findHand(array.get(i).botHand.hand)[1]>maxHand[1]) {
					maxHand=Bot.findHand(array.get(i).botHand.hand);
					currentWinner=array.get(i);
				}
			}
		}
		if (currentWinner.name=="Player") {
			Player.Bal+=Pot.payOut();
		} else currentWinner.Balance+=Pot.payOut();
		return currentWinner;
	}
	
	public static void endOfRoundDisplay(ArrayList<Bot> bots) {
		Bot winner=findWinner(bots);
		
		if (winner.name=="Player") {
			String hand=Bot.findHandToString(Player.bestHand(Player.playerHand, Game.river));
			System.out.println("You won this round!");
			System.out.printf("Your hand was %s!%n",hand);
			System.out.printf("You win the pot of %d!%n",Pot.currentPot);
			System.out.printf("Your new balance is %d!%n",Player.Bal);
		} else {
			String hand=Bot.findHandToString(Player.bestHand(winner.botHand,Game.river));
			System.out.printf("Bot %s won this round!%n",winner.name);
			System.out.printf("Their hand was %s!%n", hand);
			System.out.printf("They win the pot of %d!%n",Pot.currentPot);
		}
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
				if (botsCopy.get(i).name == "Player") {
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
						//if(remove == true) botsCopy.remove(j);
					}else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(botsCopy.get(j).botHand.toString());
						System.out.println(botsCopy.get(j).play(i));
						
					}
;				
				}
				miniRound++;
				System.out.println(miniRound);
				//Pot.resetBets();
				setCurrentPlayerCount(botsCopy);
				System.out.println(Pot.allBetsSame()); //have to pass on an array of the currentBets not the playerCount
				shiftLeft(botsCopy);
				

			}
			
			
			endOfRoundDisplay(bots);
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

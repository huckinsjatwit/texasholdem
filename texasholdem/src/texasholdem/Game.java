package texasholdem;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.InputMismatchException;
import java.util.Optional;



public class Game {
	public int round = 1;
	public ArrayList<Bot> bots;
	public Deck deck;
	public Pot pot;
	public River river;
	public int miniRound = 1;
	public boolean remove = false;
	public int currentPlayerCount;
	public Player player;
	private runGame gameRun;
	public boolean reset;
	
	
	public Game(runGame gameRun) {
		this.gameRun=gameRun;
		this.deck = new Deck();
		this.pot= new Pot(this);
		this.river = new River(this);
		this.player= new Player(this);
	}
	
	public void setRound() {
		round++;
	}
	
	public void setCurrentPlayerCount(ArrayList currentPlayers) {
		currentPlayerCount=currentPlayers.size();
	}
	
	
		/*
		 * Allows user to pick number of bots, also creates a bot with the name player, this bot will be detected and start the players turn.
		 */
	public String setBots(int n) {
		if (reset) return "";
		String sendBack ="";
		bots=new ArrayList<>();
		Collections.shuffle(Bot.possibleNames);
			for (int i=0; i<n+1; i++) {
				bots.add(new Bot(i,this));
			}
			bots.get(n).name="Player";
						
			for (int i=0; i<bots.size()-1; i++) {
				if (i==bots.size()-2) {
					sendBack+=(bots.get(i).name+".");
				} else sendBack+=(bots.get(i).name+", ");
			}
		if (n==1) return ("Created bot: "+sendBack+"\n");
		else return ("Created bots: "+sendBack+"\n");
	}
	
	
	
	//shifts one for every round besides the first
	public static void shiftLeft(ArrayList<Bot> array) {
        Bot firstElement = array.get(0);
        
        for (int i=0; i<array.size()-1; i++) {
            array.set(i, array.get(i+1));
        }
        
        array.set(array.size()-1, firstElement);
    }
	

	public Bot findWinner(ArrayList<Bot> botsCopy) {
		int[] maxHand= {10,0}; 
		Bot currentWinner=botsCopy.get(0);
		
		for (Bot bot : botsCopy) {
			if (bot.name=="Player") { //Checks if the bot is a player to check the players hand rather than placeholder bot
				if (player.currentBest[0]<maxHand[0] || (player.currentBest[0] == maxHand[0] && player.currentBest[1]>maxHand[1])) {
					maxHand=player.currentBest;
					currentWinner=bot;
				}
			} else if (Bot.findHand(bot.currentBest)[0]<maxHand[0] || (Bot.findHand(bot.currentBest)[0]==maxHand[0] && Bot.findHand(bot.currentBest)[1]>maxHand[1])) {
					maxHand=Bot.findHand(bot.currentBest);
					currentWinner=bot;
			}
		}
		return currentWinner;
	}
		
		
	
	public String endOfRoundDisplay() {
		ArrayList<Bot> botsCopy = new ArrayList<>();
		for (Bot bot: bots) {
			if (bot.name=="Player" && !player.fold) botsCopy.add(bot);
			else if (bot.name!="Player" && !bot.isOut) botsCopy.add(bot);
		}
		if (botsCopy.size()==0) return "No winner, all players fold.";
		Bot winner=findWinner(botsCopy);
		String win="";
		
		if (winner.name=="Player") {
			String hand=Bot.findHandToString(player.currentBest);
			win+=("You won this round!\n");
			win+=("Your hand was "+hand+"!\n");
			win+=("You win the pot of "+pot.currentPot+"!\n");
			Player.Bal+=pot.payOut();
			win+=("Your new balance is "+player.Bal+"!\n\n");
		} else {
			String hand=Bot.findHandToString(Bot.findHand(winner.currentBest));
			win+=("Bot "+winner.name+" won this round!\n");
			win+=("Their hand was "+hand+"!\n");
			win+=("They win the pot of "+pot.currentPot+"!\n\n");
			winner.Balance+=pot.payOut();
		}
		return win;
	}
	
	public void deal() {
		for (Bot bot : bots) {
			if (bot.name.equals("Player")) {
				player.makeHand();
			} else bot.makeHand();
		}
	}
	
	public void riverUpdates(int i) {
		if (i==1) river.riverCreate();
		if (i>1) river.riverAdd();
			
	}
	

}
	


	

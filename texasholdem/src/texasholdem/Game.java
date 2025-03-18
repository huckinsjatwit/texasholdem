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
	
	private runGame runGame;
	
	public Game(runGame runGame){
		this.runGame=runGame;
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
		String sendBack ="";
		bots=new ArrayList<>();
		Collections.shuffle(Bot.possibleNames);
			for (int i=0; i<n+1; i++) {
				bots.add(new Bot(i,runGame.game));
			}
			bots.get(n).name="Player";
						
			for (int i=0; i<bots.size()-1; i++) {
				if (i==bots.size()-2) {
					sendBack+=(bots.get(i).name+".");
				} else sendBack+=(bots.get(i).name+", ");
			}
		if (n==1) return ("Created bot: "+sendBack+"\n");
		else return ("Created bots "+sendBack+"\n");
	}
	
	
	
	//shifts one for every round besides the first
	public static void shiftLeft(ArrayList<Bot> array) {
        Bot firstElement = array.get(0);
        
        for (int i=0; i<array.size()-1; i++) {
            array.set(i, array.get(i+1));
        }
        
        array.set(array.size()-1, firstElement);
    }
	

	public Bot findWinner(ArrayList<Bot> array) {
		int[] maxHand= {10,0}; 
		Bot currentWinner=array.get(0);
		
		for (int i=0; i<array.size(); i++) {
			if (array.get(i).name=="Player") { //Checks if the bot is a player to check the players hand rather than placeholder bot
				if (player.currentBest[0]<maxHand[0]) {
					maxHand=player.currentBest;
					currentWinner=array.get(i);
				} else if (player.currentBest[0]==maxHand[0]) {
					if (player.currentBest[1]>maxHand[1]) {
						maxHand=player.currentBest;
						currentWinner=array.get(i);
					}
				}
				
			} else if (Bot.findHand(array.get(i).currentBest)[0]<maxHand[0]) {
				maxHand=Bot.findHand(array.get(i).currentBest);
				currentWinner=array.get(i);
			} else if (Bot.findHand(array.get(i).currentBest)[0]==maxHand[0]) {
				if (Bot.findHand(array.get(i).currentBest)[1]>maxHand[1]) {
					maxHand=Bot.findHand(array.get(i).currentBest);
					currentWinner=array.get(i);
				}
			}
		}
		if (currentWinner.name=="Player") {
			Player.Bal+=pot.payOut();
		} else currentWinner.Balance+=pot.payOut();
		return currentWinner;
	}
	
	public void endOfRoundDisplay(ArrayList<Bot> bots) {
		Bot winner=findWinner(bots);
		
		if (winner.name=="Player") {
			String hand=Bot.findHandToString(player.currentBest);
			System.out.println("You won this round!");
			System.out.printf("Your hand was %s!%n",hand);
			System.out.printf("You win the pot of %d!%n",pot.currentPot);
			System.out.printf("Your new balance is %d!%n",player.Bal);
		} else {
			String hand=Bot.findHandToString(Bot.findHand(winner.currentBest));
			System.out.printf("Bot %s won this round!%n",winner.name);
			System.out.printf("Their hand was %s!%n", hand);
			System.out.printf("They win the pot of %d!%n",pot.currentPot);
		}
	}
}
	
	//public static void main(String[] args) {

	

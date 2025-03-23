package texasholdem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.util.Collections;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class runGame {
	
	private TextArea outputArea;
	public Game game;
	private Stage mainStage;
	private VBox RList;
	private gameView view;
	
	public runGame(gameView view) {
		this.view=view;
		this.game= new Game(this);
	}
	

	public void startGame(int botCount) {
		Thread gamePlay = new Thread(() -> {
			view.updateRiver(null);
			view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
			view.updateOutput(game.setBots(botCount));
				
			Collections.shuffle(game.bots); //Shuffles order for first round
			view.updateOutput("Game started!\n\n");
				
			game.deal();
			view.showYourHand(game.player.playerHand.getCard(0).getImage(), game.player.playerHand.getCard(1).getImage());
				
			for (int i=0; i<4; i++) {
				game.riverUpdates(i);
				view.updateRiver(game.river.river);
					
				for (Bot bot: game.bots) {
					if (bot.name.equals("Player")&& !game.player.fold) {
						view.getBetDialog().initializeFuture();
						view.betMenuEnable();
						if (game.pot.bets.isEmpty()) view.checkPossible();
						else view.checkImpossible();
						waitForPlayerBet();
						view.updateRightDisplay(game.player.Bal,game.pot.currentPot);
						view.betMenuDisable();
					} else if (bot.name!="Player") {
						String output = bot.play(i);
						try { 
							Thread.sleep(1000);
						} catch (InterruptedException x) {
							System.err.print("Sleep intersrupted");
						}
						view.updateOutput(output);
						view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
					}
				}
				view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
				view.updateOutput("\n");
				game.miniRound++;
				game.shiftLeft(game.bots);
				game.pot.resetBets();
				view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
			}
			view.updateOutput(game.endOfRoundDisplay());
			view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
			try { 
				Thread.sleep(1000);
			} catch (InterruptedException x) {
				System.err.print("Sleep intersrupted");
			}
			view.askToContinue();
			
		});
			gamePlay.start();
	}
	
	public void reset() {
		game.reset=true;
		game.deck=new Deck();
		game.river=new River(game);
		game.player.makeHand();
		game.miniRound=0;
		
		view.updateRiver(null);
		for (Bot bot: game.bots) {
			if (bot.name.equals("Player")) {
				continue;
			} else if (bot.Balance>0) {
				bot.isOut=false;
				bot.makeHand();
			}
		}
	}
	
	public void waitForPlayerBet() {
		view.getBetDialog().playerWait(() -> {
			view.betMenuDisable();
		});
	}
	
	
	public void fold() {
		game.player.standHand();
	}
	
	public void check() {
		view.updateOutput(game.player.makeBet(0));
		
	}
	
	public void call() {
		view.updateOutput(game.player.call());
	}
	
		
		
	
}
	
	


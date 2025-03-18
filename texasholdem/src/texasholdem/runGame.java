package texasholdem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.io.InputStream;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

public class runGame extends Application {
	
	private TextArea outputArea;
	public Game game;
	private Stage mainStage;
	private VBox RList;
	private gameView view;
	
	public runGame() {
		
	}
	
	public void start(Stage mainStage) {
		this.game=new Game(this);
		this.mainStage = mainStage;
		this.view=new gameView(mainStage);
		view.mainMenu(this);
	}
	
	public void startGame(int botCount) {
		view.updateOutput("Game started!");
		view.updateOutput(game.setBots(botCount));
		Collections.shuffle(game.bots); //Shuffles order for first round
		
		int c=1; //allows to loop infinitely until player quits.
		while (c==1) {
				//Deals to bots and players in their order.
			for (Bot bot : game.bots) {
				if (bot.name.equals("Player")) {
					game.player.makeHand();
					view.showYourHand(game.player.playerHand.getCard(0).getImage(), game.player.playerHand.getCard(1).getImage());
				} else bot.makeHand();
			}
				
				//When it hits bot named player, it will trigger the players play method. Otherwise bot's
			for (int i=0; i<4; i++) {
				if (i==1) {
					game.river.riverCreate();
					view.updateRiver(game.river.river);
				}
				if (i>1) {
					game.river.riverAdd();
					view.updateRiver(game.river.river);
				}
				
					
				for (Bot bot: game.bots) {
					if (bot.name=="Player") {
						view.updateOutput("");
	
						bet();
						view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
						//if(remove == true) botsCopy.remove(j);
					}else {
						view.updateOutput(bot.play(i));
						view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
					}			
				}
				game.miniRound++;
				//System.out.println(miniRound);
				//Pot.resetBets();
				game.shiftLeft(game.bots);
				System.out.println(Pot.highestBet(game.pot.currentBets())); //have to pass on an array of the currentBets not the playerCount
	
			}
	
		}
	
	}
	
	public void bet() {
		TextInputDialog amt= new TextInputDialog();
		
		amt.setTitle("Make a bet!");
		amt.setHeaderText("How much would you like to bet?");
		amt.setContentText("Bet");
		
		UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            } else {
                return null;
            }
        };
        
        TextFormatter form= new TextFormatter<>(filter);
        
        amt.getEditor().setTextFormatter(form);
        
        Optional<String> result= amt.showAndWait();
        
        result.ifPresent(value-> {
        	game.player.play(Integer.valueOf(value));
        });
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

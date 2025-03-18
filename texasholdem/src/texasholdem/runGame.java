package texasholdem;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;


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
		view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
		view.updateOutput(game.setBots(botCount));
		Collections.shuffle(game.bots); //Shuffles order for first round
		view.updateOutput("Game started!\n");
		
		
		
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
						bet(mainStage);
						view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
						
					}else {
						view.updateOutput(bot.play(i));
						view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
					}
					
				}
				
				view.updateOutput("\n");
				game.miniRound++;
				game.shiftLeft(game.bots);
				
	
			}
			view.updateOutput(game.endOfRoundDisplay(game.bots));
		}
	
	}
	
	public void bet(Stage stage) {
		//can add buttons for check, call, etc
		TextInputDialog amt= new TextInputDialog();
		
		amt.setTitle("Make a bet!");
		amt.setHeaderText("How much would you like to bet?");
		amt.setContentText("Bet");
		
		amt.initOwner(stage);
		amt.setX(stage.getX()+300);
		amt.setY(stage.getY()+425);
		amt.setResizable(false);
		
		
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
        
        Button okay= (Button) amt.getDialogPane().lookupButton(ButtonType.OK);
        okay.setDisable(true);
        
        
        
        amt.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        amt.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
        	Platform.exit();
        	System.exit(0);
        });
        
        //Button close= (Button) amt.getDialogPane().lookupButton(ButtonType.CLOSE);
        //close.setOnAction(event-> {
			//Platform.exit();
		//});
        
        amt.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
        	okay.setDisable(newValue.trim().isEmpty());
        	if (!newValue.trim().isEmpty()) {
        		Long val= Long.parseLong(newValue.trim());
            	if (val>game.player.Bal) {
            		okay.setDisable(true);
            	} else {
            		okay.setDisable(false);
            	}
        	}
        });
        
        Optional<String> result= amt.showAndWait();
    
        result.ifPresent(value-> {
        	if (Integer.valueOf(value)>game.player.Bal) {
        		
        	}
        	amt.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        	game.player.play(Integer.valueOf(value));
        });
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

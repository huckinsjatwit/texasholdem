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
			Timeline deal= new Timeline();
				//Deals to bots and players in their order.
			for (Bot bot : game.bots) {
				if (bot.name.equals("Player")) {
					game.player.makeHand();
					deal.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> view.showYourHand(game.player.playerHand.getCard(0).getImage(), game.player.playerHand.getCard(1).getImage())));
				} else bot.makeHand();
			}
			deal.play();
			
			
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
					Timeline tl= new Timeline();
					if (bot.name=="Player") {
						tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
							view.updateOutput("");
							bet(mainStage, () -> {
								view.updateRightDisplay(game.player.Bal, game.pot.currentPot);
								proceedToNextAction();
							}); 
						})); 
						
	
					}else {
						view.updateOutput(bot.play(i));
						tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> view.updateRightDisplay(game.player.Bal, game.pot.currentPot)));
					}
					tl.play();
				}
				
				
				view.updateOutput("\n");
				game.miniRound++;
				game.shiftLeft(game.bots);
				
	
			}
			view.updateOutput(game.endOfRoundDisplay(game.bots));
		}
	
	}
	
	public void bet(Stage stage, Runnable complete) {
		//can add buttons for check, call, etc
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("How would you like to bet?");
		alert.setHeaderText("Choose your bet type.");

		ButtonType check = new ButtonType("Check");
		ButtonType call = new ButtonType("Call");
		ButtonType custom = new ButtonType("Custom Bet");
		
		alert.initOwner(stage);
		alert.setX(stage.getX()+300);
		alert.setY(stage.getY()+430);
		alert.setResizable(false);
		alert.getButtonTypes().setAll(check, call, custom);
		alert.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
        	Platform.exit();
        	System.exit(0);
        });

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == check){
		    game.player.check();
		} else if (result.get() == call) {
		    game.player.call();
		} else if (result.get() == custom) {
			TextInputDialog amt= new TextInputDialog();
			
			amt.setTitle("Make a bet!");
			amt.setHeaderText("How much would you like to bet?");
			amt.setContentText("Bet");
			
			amt.initOwner(stage);
			amt.setX(stage.getX()+300);
			amt.setY(stage.getY()+430);
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
	        
	        Optional<String> result2= amt.showAndWait();
	    
	        result2.ifPresent(value-> {
	        	if (Integer.valueOf(value)>game.player.Bal) {
	    
	        	}
	        	amt.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
	        	game.player.play(Integer.valueOf(value));
	        	complete.run();
	        });
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

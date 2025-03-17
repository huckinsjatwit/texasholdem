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

public class runGame extends Application {
	
	private TextArea outputArea;
	public Game game;
	private Stage mainStage;
	private VBox RList;
	
	
	public void start(Stage stage) {
		this.mainStage=stage;
		game = new Game(this);
		stage.setTitle("Texas Hold'em: Main Menu");
	
		BorderPane root= new BorderPane();
		root.setStyle("-fx-background-color: green");
		
		Button startButton = new Button("Start");
		startButton.setPrefSize(435,50);
		startButton.setOnAction(event -> startGame());
		
		Alert exitAlert= new Alert(AlertType.CONFIRMATION, "Are you sure you want to quit?", ButtonType.YES, ButtonType.NO);
		exitAlert.setTitle("Confirmation");
		exitAlert.setHeaderText(null);
		
		Button exitButton= new Button("Quit");
		exitButton.setPrefSize(435,50);
		exitButton.setOnAction(event-> {
			Optional<ButtonType> result= exitAlert.showAndWait();
			if (result.get()==ButtonType.YES) Platform.exit();
		});

		root.setBottom(createMenu(startButton, exitButton));
		
		Image logo;
		
		try {
			InputStream inputStream = getClass().getResourceAsStream("/logo.png");
			logo = new Image(inputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		ImageView logoView= new ImageView(logo);
		root.setCenter(logoView);
		stage.setScene(new Scene (root, 900, 600));
		stage.show();
	}
	
	public HBox createMenu(Button startButton, Button exitButton) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #FF1000;");
		
		Stage gameStage = new Stage();
		
		
		hbox.getChildren().add(startButton);
		hbox.getChildren().add(exitButton);
		
		return hbox;
	}
	
	
	public void startGame(int botCount) {
		
		updateOutput(game.setBots(botCount));
		Collections.shuffle(game.bots); //Shuffles order for first round
		ArrayList<Bot> botsCopy= new ArrayList<>(game.bots); //Allows us to remove players without affecting the original list.
		
		int c=1; //allows to loop infinitely until player quits.
		while (c==1) {
				//Deals to bots and players in their order.
			for (int i=0; i<botsCopy.size(); i++) {
				if (botsCopy.get(i).name == "Player") {
					game.player.makeHand();
					root.setBottom(yourHand());
				} else botsCopy.get(i).makeHand();
			}
				
			
				//When it hits bot named player, it will trigger the players play method. Otherwise bot's
			for (int i=0; i<4; i++) {
				if (i==1) game.river.riverCreate();
				if (i>1) game.river.riverAdd();
					
				//display();
					
				for (int j=0; j<botsCopy.size(); j++) {
					if (botsCopy.get(j).name=="Player") {
						updateOutput("");
						
						game.player.play();
						//if(remove == true) botsCopy.remove(j);
					}else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(botsCopy.get(j).botHand.toString());
						updateOutput(botsCopy.get(j).play(i));
						
					}			
				}
				game.miniRound++;
				//System.out.println(miniRound);
				//Pot.resetBets();
				game.shiftLeft(botsCopy);
				System.out.println(Pot.highestBet(game.pot.currentBets())); //have to pass on an array of the currentBets not the playerCount
	
			}
	
		}
	
		//howManyBots();
		
		//root.setBottom(yourHand());
	}
	
	public HBox yourHand() {
		HBox hand= new HBox();
		hand.setSpacing(20);
		hand.setPadding(new Insets(10));
		
		ImageView card1 = new ImageView(game.player.playerHand.getCard(0).getImage());
		ImageView card2 = new ImageView(game.player.playerHand.getCard(1).getImage());
		
		hand.getChildren().add(card1);
		hand.getChildren().add(card2);
		
		return hand;
	}
	
	public void setRightDisplay() {
		Integer balance= game.player.Bal;
		Integer pot= game.pot.currentPot;
		RList = new VBox();
		
		Label currentBalance = new Label("Balance: "+balance.toString());
		Label currentPot= new Label("Pot: "+pot.toString());
		
		RList.getChildren().addAll(currentBalance, currentPot);
	}
	
	public void updateRightDisplay() {
		Integer balance= game.player.Bal;
		Integer pot= game.pot.currentPot;
		RList.getChildren().clear();
		
		Label currentBalance = new Label("Balance: "+balance.toString());
		Label currentPot= new Label("Pot: "+pot.toString());
		
		RList.getChildren().addAll(currentBalance, currentPot);
	}
	
	public int howManyBots() {
		Stage dialog = new Stage();
		VBox oneThroughSeven = new VBox();
		Button[] buttons = new Button[7];
		
		
		for (int i=0; i<7; i++) {
			Integer index = i+1;
			buttons[i]= new Button(index.toString());
		}
		
		oneThroughSeven.getChildren().addAll(buttons);
		
		BorderPane temp = new BorderPane();
		temp.setCenter(oneThroughSeven);
		
		dialog.setTitle("How many bots would you like?");
		dialog.setScene(new Scene(temp,200,200));
		dialog.show();
		return 0;
	}
	
	public void howManyBotsAnswer(int n) {
		if (n==1) updateOutput("Created bot ");
		else updateOutput("Created bots: ");
	}
	
	public void updateOutput(String text) {
		outputArea.appendText(text);
	}
	
	//public void startGameLogic() {
			
		
	
	public static void main(String[] args) {
		launch(args);
	}
}

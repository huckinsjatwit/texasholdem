package texasholdem;

import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.io.InputStream;
import javafx.scene.control.Label;


public class gameView extends Application {
	
	private TextArea outputArea;
	private Stage mainStage;
	private VBox RList;
	private HBox River;
	private BorderPane root;
	private HBox yourHand;
	private betDialog betDialog;
	
	runGame gameRun;
	
	public void start(Stage mainStage) {
		this.mainStage = mainStage;
		this.gameRun=new runGame(this);
		
		mainStage.setResizable(false);
		this.root= new BorderPane();
		root.setStyle("-fx-background-color: #35654d;");
		
		Label emptyRiver= new Label("River is currently empty.");
		emptyRiver.setFont(Font.font("verdana", FontWeight.BOLD, 20));
		emptyRiver.setStyle("-fx-text-fill: black;");
		
		RList= new VBox();
		RList.setPadding(new Insets(5,3,5,3));
		RList.setSpacing(5);
		RList.setStyle("-fx-background-color: #1e2e26;");
		
		River= new HBox(emptyRiver);
		yourHand= new HBox();
		this.betDialog= new betDialog();
		mainMenu(gameRun);
	}
	
	public gameView() {
		
	}

	
	public void mainMenu(runGame gameRun) {
		this.gameRun=gameRun;
		outputArea= new TextArea();
		outputArea.setEditable(false);
		outputArea.setWrapText(true);
		outputArea.setStyle("-fx-control-inner-background: #1e2e26; -fx-background-color: #1e2e26;");
		
		Integer[] choices = {1,2,3,4,5,6,7};
		ChoiceDialog<Integer> oneThroughSeven = new ChoiceDialog(choices[0], Arrays.asList(choices));
		oneThroughSeven.setHeaderText("Bot Selection");
		oneThroughSeven.setContentText("How many bots would you like to play against?");
		
		oneThroughSeven.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
        	Platform.exit();
        	System.exit(0);
        });
		
		Button startButton = new Button("Start");
		startButton.setPrefSize(435,50);
		startButton.setOnAction(event ->  {
			oneThroughSeven.showAndWait();
			gameMenu(oneThroughSeven.getSelectedItem());
		});
		
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
		
		mainStage.setScene(new Scene(root, 900, 600));
		mainStage.setTitle("Texas Hold'em");
		mainStage.show();
	}
	
	public void gameMenu(int botCount) {
	
		root=new BorderPane();
		root.setStyle("-fx-background-color: #35654d;");
		
		outputArea.setMaxWidth(300);
		outputArea.setMaxHeight(400);
		root.setLeft(outputArea);

		yourHand.setMaxHeight(200);
		yourHand.setMaxWidth(400);
		root.setBottom(yourHand);
		
		RList.setMaxHeight(50);
		RList.setMaxWidth(200);
		root.setRight(RList);
		
		initializeBetMenu();
		betDialog.setMaxHeight(100);
		betDialog.setMaxWidth(300);
		root.setTop(betDialog);
		
		River.setMaxHeight(200);
		River.setMaxWidth(350);
		root.setCenter(River);
		
		mainStage.setScene(new Scene(root,900,600));
		gameRun.startGame(botCount);
	}
	
	public HBox createMenu(Button startButton, Button exitButton) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #1e2e26;");
		
		hbox.getChildren().addAll(startButton, exitButton);
		
		return hbox;
	}
	
	public void updateRiver(ArrayList<Card> river) {
		Platform.runLater(()-> {
			if (river==null) {
				return;
			}
			River.setSpacing(10);
			River.setPadding(new Insets(10));
			
			River.getChildren().clear();
			ArrayList<ImageView> images = new ArrayList<>();
			for (int i = 0; i < river.size(); i++) {
		        ImageView imageView = new ImageView(river.get(i).getImage());
		        imageView.setFitWidth(84); 
		        imageView.setFitHeight(126);
		        imageView.setPreserveRatio(true); 
		        images.add(imageView);
		    }
			River.getChildren().addAll(images);
		});
	}
	
	public void updateRightDisplay(int balance, int pot) {
		System.out.println("Updating right display - Balance: " + balance + ", Pot: " + pot); // Debugging
		Platform.runLater(()-> {
			RList.getChildren().clear();
			
			Label currentBalance = new Label("Balance: "+balance);
			Label currentPot= new Label("Pot: "+pot);
			
			currentBalance.setFont(Font.font("verdana", FontWeight.BOLD, 15));
			currentBalance.setStyle("-fx-text-fill: white;");
			currentPot.setFont(Font.font("verdana", FontWeight.BOLD, 15));
			currentPot.setStyle("-fx-text-fill: white;");
			
			RList.getChildren().addAll(currentBalance, currentPot);
		});
	}
	
	public void updateOutput(String text) {
		Platform.runLater(()-> outputArea.appendText(text));
	}
	
	public void showYourHand(Image c1, Image c2) {
		Platform.runLater(()-> {
			yourHand.setSpacing(20);
			yourHand.setPadding(new Insets(10));
			
			ImageView card1 = new ImageView(c1);
			card1.setFitWidth(100); 
			card1.setFitHeight(150); 
			card1.setPreserveRatio(true);

			ImageView card2 = new ImageView(c2);
			card2.setFitWidth(100); 
			card2.setFitHeight(150); 
			card2.setPreserveRatio(true);
			
			
			
			yourHand.getChildren().addAll(card1,card2);
		});

	}
	
	public void initializeBetMenu() {
			betDialog.initializeFuture();
			
			betDialog.setFold(() -> {
				gameRun.fold();
				betDialog.completeFuture();
			});
				
			getBetDialog().setCheck(() -> {
				gameRun.check();
				betDialog.completeFuture();
			});
				
			betDialog.setCall(() -> {
				gameRun.call();
				betDialog.completeFuture();
			});
				
			betDialog.setRaise(()-> {
				raise(this.mainStage);
				
					
			});
				
			betMenuDisable();
			updateOutput("");
					
	}
	
	public void betMenuEnable() {
		Platform.runLater(() -> {
			this.betDialog.enableButtons();
		});
		
	}
	
	public void betMenuDisable() {
		Platform.runLater(() -> {
			this.betDialog.disableButtons();
		});
	}
	
	public betDialog getBetDialog() {
		return this.betDialog;
	}
	
	public void raise(Stage stage) {
		Platform.runLater(()-> {
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
	            	if (val>gameRun.game.player.Bal) {
	            		okay.setDisable(true);
	            	} else {
	            		okay.setDisable(false);
	            	}
	        	}
	        });
	        
	        Optional<String> result2= amt.showAndWait();
	    
	        result2.ifPresent(value-> {
	        	if (Integer.valueOf(value)>gameRun.game.player.Bal) {
	    
	        	}
	        	amt.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
	        	updateOutput(gameRun.game.player.play(Integer.valueOf(value)));
	        	betDialog.completeFuture();
	        });
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

package texasholdem;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.io.InputStream;
import javafx.scene.control.Label;

public class gameView {
	
	private TextArea outputArea;
	private Stage mainStage;
	private VBox RList;
	private HBox River;
	private BorderPane root;
	private HBox yourHand;
	runGame gameRun;
	
	public gameView(Stage mainStage) {
		this.mainStage=mainStage;
		this.root= new BorderPane();
		root.setStyle("-fx-background-color: green;");
		
		RList= new VBox();
		River= new HBox(new Label("River is empty."));
		yourHand= new HBox();
		
	}
	
	public void mainMenu(runGame gameRun) {
		this.gameRun=gameRun;
		outputArea= new TextArea();
		outputArea.setEditable(false);
		outputArea.setWrapText(true);
		
		Integer[] choices = {1,2,3,4,5,6,7};
		ChoiceDialog<Integer> oneThroughSeven = new ChoiceDialog(choices[0], Arrays.asList(choices));
		oneThroughSeven.setHeaderText("Bot Selection");
		oneThroughSeven.setContentText("How many bots would you like to play against?");
		
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
		
		gameRun.startGame(botCount);
		root=new BorderPane();
		root.setStyle("-fx-background-color: green");
		
		root.setLeft(outputArea);
		outputArea.prefWidth(100);
		outputArea.prefHeight(200);
		root.setBottom(yourHand);
		root.setRight(RList);
		root.setCenter(River);
		
		mainStage.setScene(new Scene(root,900,600));
	}
	
	public HBox createMenu(Button startButton, Button exitButton) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #FF1000;");
		
		hbox.getChildren().addAll(startButton, exitButton);
		
		return hbox;
	}
	
	public void updateRiver(ArrayList<Card> river) {
		River.getChildren().clear();
		ArrayList<ImageView> images = new ArrayList<>();
		for (int i=0; i<river.size(); i++) {
			images.add(new ImageView(river.get(i).getImage()));
		}
		River.getChildren().addAll(images);
	}
	
	public void updateRightDisplay(int balance, int pot) {
		RList.getChildren().clear();
		
		Label currentBalance = new Label("Balance: "+balance);
		Label currentPot= new Label("Pot: "+pot);
		
		RList.getChildren().addAll(currentBalance, currentPot);
	}
	
	public void updateOutput(String text) {
		outputArea.appendText(text);
	}
	
	public void showYourHand(Image c1, Image c2) {
		yourHand.setSpacing(20);
		yourHand.setPadding(new Insets(10));
		
		ImageView card1 = new ImageView(c1);
		ImageView card2 = new ImageView(c2);
		
		yourHand.getChildren().addAll(card1,card2);

	}
	
}

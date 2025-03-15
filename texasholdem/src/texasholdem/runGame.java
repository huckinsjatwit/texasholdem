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
import javafx.geometry.Insets;
import javafx.scene.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.io.InputStream;

public class runGame extends Application {
	
	private TextArea outputArea;
	public Game game;
	private Stage mainStage;
	
	
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
	
	
	public void startGame() {
		new Thread(()-> game.startGameLogic()).start();
		
		outputArea= new TextArea();
		outputArea.setEditable(false);
		outputArea.setWrapText(true);
		
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: green");
		root.setLeft(outputArea);
		mainStage.setTitle("Texas Hold'em: Game");
		
		mainStage.setScene(new Scene(root,900,600));
		
		
		//root.setBottom(yourHand());
		
	}
	
	//public HBox yourHand() {
		//HBox hand= new HBox();
		//hand.setSpacing(20);
		//hand.setPadding(new Insets(10));
//		
	//	ImageView card1 = new ImageView(game.player.playerHand.getCard(0).getImage());
//		ImageView card2 = new ImageView(game.player.playerHand.getCard(1).getImage());
		
//		hand.getChildren().add(card1);
	//	hand.getChildren().add(card2);
		
		//return hand;
//	}
	
	public void updateOutput(String text) {
		Platform.runLater(()-> outputArea.appendText(text));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

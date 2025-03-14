package texasholdem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class runGame extends Application {
	
	private TextArea outputArea;
	private Game game;
	
	
	public void start(Stage stage) {
		game = new Game(this);
		stage.setTitle("Texas Hold'em: Main Menu");
		
		outputArea= new TextArea();
		outputArea.setEditable(false);
		outputArea.setWrapText(true);
	
		BorderPane root= new BorderPane();
		root.setStyle("-fx-background-color: green");
		
		
		root.setBottom(createMenu());
		Image logo;
		try {
			FileInputStream inputStream = new FileInputStream("C:\\Users\\Jackd\\git\\replace\\texasholdem\\texasholdem\\sources\\105751.png");
			logo = new Image(inputStream);
		} catch (FileNotFoundException ex) {
			return;
		}
		ImageView logoView= new ImageView(logo);
		root.setCenter(logoView);
		stage.setScene(new Scene (root, 900, 600));
		stage.show();
	}
	
	public HBox createMenu() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #FF1000;");
		
		Button startButton = new Button("Start");
		startButton.setPrefSize(100,30);
		Stage gameStage = new Stage();
		startButton.setOnAction(event -> startGame(gameStage));
		
		hbox.getChildren().add(startButton);
		
		return hbox;
	}
	
	
	public void startGame(Stage gameStage) {
		new Thread(()-> game.startGameLogic());
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

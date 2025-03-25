package texasholdem;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.concurrent.CompletableFuture;

public class betDialog extends HBox {
	private Button fold;
	private Button check;
	private Button call; 
	private Button raise;
	private CompletableFuture<Void> future;
	
	public betDialog() {
		fold = new Button("Fold");
		check = new Button("Check");
		call = new Button("Call");
		raise = new Button("Raise");
		
		this.setPadding(new Insets(5,5,5,5));
		this.setSpacing(41);
		this.setStyle("-fx-background-color: #1e2e26;");
		this.getChildren().addAll(fold, check, call, raise);
		
		
	}
	
	public void disableButtons() {
		fold.setDisable(true);
		check.setDisable(true);
		call.setDisable(true);
		raise.setDisable(true);
	}
	
	public void enableButtons() {
		fold.setDisable(false);
		check.setDisable(false);
		call.setDisable(false);
		raise.setDisable(false);
	}
	
	public void checkPossible() {
		check.setDisable(false);
		call.setDisable(true);
	}
	
	public void checkImpossible() {
		check.setDisable(true);
		call.setDisable(false);
	}
	
	public void disableCall() {
		call.setDisable(true);
	}
	
	public void setFold(Runnable run) {
		fold.setOnAction(event -> run.run());
	
	}
	
	public void setCheck(Runnable run) {
		check.setOnAction(event -> run.run());
		
	}
	
	public void setCall(Runnable run) {
		call.setOnAction(event -> run.run());
		
	}
	
	public void setRaise(Runnable run) {
		raise.setOnAction(event -> run.run());
	
	}
	
	public void initializeFuture() {
		future=new CompletableFuture<>();
	}
	
	public void completeFuture() {
		if (future!=null) {
			future.complete(null);
		}
	}
	
	public void playerWait(Runnable complete) {
		if (future!=null) {
			try {
				future.get();
			} catch (Exception a) {
			a.printStackTrace();
			}
		}
	}		
}

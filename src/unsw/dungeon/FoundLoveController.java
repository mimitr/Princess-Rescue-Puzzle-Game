package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FoundLoveController {
	private Stage stage;
	
	@FXML
	private Button startAgainButton;
	
	public FoundLoveController(Stage primaryStage) {
		this.stage = primaryStage;
		
	}
	
	@FXML
	public void handleStartAgainButton(ActionEvent event) throws IOException {
		StartPageScene startPage = new StartPageScene(stage);
		startPage.start();

	}
}

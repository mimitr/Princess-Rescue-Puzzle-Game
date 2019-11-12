package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PlayerWinController {
	
	private Stage stage;
	
	@FXML
	private Button nextLevelButton;
	
	public PlayerWinController(Stage primaryStage) {
		this.stage = primaryStage;
	}
	
	@FXML
	public void handelNextLevelButton(ActionEvent event) throws IOException {
		String currLevel = stage.getTitle();
		String nextLevel = Integer.toString(Integer.parseInt(currLevel) + 1);
		System.out.println("Current level is " + currLevel);
		String filename = "dungeons/" + nextLevel + ".json";
		InstructionsScene nextLevelScene = new InstructionsScene(stage, filename);
		nextLevelScene.start();
	}
}

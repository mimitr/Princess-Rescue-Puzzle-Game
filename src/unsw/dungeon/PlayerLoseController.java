package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PlayerLoseController {
private Stage stage;
	
	@FXML
	private Button restartButton;
	
	public PlayerLoseController(Stage primaryStage) {
		this.stage = primaryStage;
	}
	
	@FXML
	public void handleRestartButton(ActionEvent event) throws IOException {
		System.out.println("I wanna restart the game");
		String filename = stage.getTitle().toLowerCase();
		filename = filename + ".json";
		System.out.println(filename);
		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(filename);	
        DungeonController controller = dungeonLoader.loadController();
        stage.setTitle("Advance");
        //controller.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller); 	
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
	}
}

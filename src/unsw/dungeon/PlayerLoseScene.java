package unsw.dungeon;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerLoseScene {
	private Stage stage;
	private PlayerLoseController controller;
	private Scene scene;
	public PlayerLoseScene(Stage stage) throws IOException {
		this.stage = stage;
		controller = new PlayerLoseController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerLoseView.fxml"));
	    loader.setController(controller);
	    Parent root = loader.load();
	    root.requestFocus();
	    scene = new Scene(root);
	}
	
	public void start() {
		 stage.setScene(scene);
	     stage.show();
	    
	}
}

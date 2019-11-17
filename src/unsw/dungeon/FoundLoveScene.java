package unsw.dungeon;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FoundLoveScene {
	private Stage stage;
	private FoundLoveController controller;
	private Scene scene;
	public FoundLoveScene(Stage stage) throws IOException {
		this.stage = stage;
		controller = new FoundLoveController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FoundLoveView.fxml"));
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

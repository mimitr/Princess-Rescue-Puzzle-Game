package unsw.dungeon;


import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class PlayerWinScene {
	private Stage stage;
	private PlayerWinController controller;
	private Scene scene;
	public PlayerWinScene(Stage stage) throws IOException {
		this.stage = stage;
		controller = new PlayerWinController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerWinView.fxml"));
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

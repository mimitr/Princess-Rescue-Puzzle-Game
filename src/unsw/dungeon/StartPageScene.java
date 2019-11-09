package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartPageScene {
	private Stage stage;
	private StartPageController controller;
	private Scene scene;
	public StartPageScene(Stage stage) throws IOException {
		this.stage = stage;
		stage.setTitle("Dungeon Game");
		controller = new StartPageController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartPageView.fxml"));
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

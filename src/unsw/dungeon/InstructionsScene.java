package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InstructionsScene {
	private Stage stage;
	private InstructionsController controller;
	private Scene scene;
	public InstructionsScene(Stage stage, ArrayList<String> instructions) throws IOException {
		this.stage = stage;
		stage.setTitle("INSTRUCTIONS");
		controller = new InstructionsController(stage, instructions);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructionsView.fxml"));
	    loader.setController(controller);
	    Parent root = loader.load();
	    root.requestFocus();
	    scene = new Scene(root);
	}
	
	public void start() {
		 stage.setScene(scene);
		 controller.addInstructions();
	     stage.show();
	     //controller.addInstructions();
	}
	
	public InstructionsController getController() {
		return controller;
	}
}

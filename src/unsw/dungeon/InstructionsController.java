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

public class InstructionsController {
	private Stage stage;
	private ArrayList<String> instructions;
	private String filename = "";
	@FXML
	private Pane instructionsPane;
	
	@FXML
	private Button finishInstructionButton;
	
	public InstructionsController(Stage primaryStage, ArrayList<String> instructions, String filename) {
		this.stage = primaryStage;
		this.instructions = instructions;
		this.filename = filename;
		//addInstructions();
	}
	
	@FXML
	public void addInstructions() {
		System.out.println(instructionsPane);
		String total = "";
		for(String in : instructions) {
			total = total + in + "\n";
		}
		Label txt = new Label(total);
		txt.setLayoutX(0);
		txt.setLayoutY(0);
		txt.setMinHeight(320);
		txt.setMinWidth(600);
		instructionsPane.getChildren().add(txt);
		
	}
	
	@FXML
	public void handleFinishInstructionButton(ActionEvent event) throws IOException {
		// load the game
		// show instructions of this level
		String[] array = filename.split("/");
		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(array[1]);	
        DungeonController controller = dungeonLoader.loadController();
        System.out.println(array[1]);
        String[] array1 = array[1].split("\\.");
        stage.setTitle(array1[0]);
        controller.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller); 	
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();

	}
}

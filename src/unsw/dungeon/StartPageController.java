package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartPageController {
	private Stage stage;
	
	@FXML
	private Button startGameButton;
	
	public StartPageController(Stage primaryStage) {
		this.stage = primaryStage;
	}
	
	@FXML
	public void handleStartGameButton(ActionEvent event) throws IOException {
		
		String filename = "dungeons/1.json";
    	InstructionsScene scene = new InstructionsScene(stage, filename);
    	scene.start();


	}
	
	private ArrayList<String> getInstruction(JSONObject json) {
    	ArrayList<String> instructions = new ArrayList<>();

    	String goal = json.getString("goal");
    	
    	if(goal.equals("AND")) {
    		
    		JSONArray array = json.getJSONArray("subgoals");
    		instructions.add("Must complete all of the following goals:");
    		ArrayList<String> andInstructions = new ArrayList<>();
    		for(int i = 0 ; i < array.length(); i++) {
    			andInstructions = getInstruction(array.getJSONObject(i));
    			instructions.addAll(andInstructions);
    		}
    		return instructions;
    	} else if(goal.equals("OR")) {
    		ArrayList<String> orInstructions = new ArrayList<>();
    		instructions.add("Must complete one of the follwing goals:");
    		JSONArray array = json.getJSONArray("subgoals");
    		for(int i = 0 ; i < array.length(); i++) {
    			orInstructions = getInstruction(array.getJSONObject(i));
    			instructions.addAll(orInstructions);
    		}
    		return instructions;
    	} else if(goal.equals("enemies")) {
    		instructions.add("Kill all the enemies in the dungeon");
    		return instructions;
    	} else if(goal.equals("boulders")) {
    		instructions.add("Push all the boulders onto floor switches");
    		return instructions;
    	} else if(goal.equals("exit")) {
    		instructions.add("Find your way to the exit");
    		return instructions;
    	} else if(goal.equals("treasure")) {
    		instructions.add("Collect all the treasures");
    		return instructions;
    	}
    	
    	return instructions;
    }

}

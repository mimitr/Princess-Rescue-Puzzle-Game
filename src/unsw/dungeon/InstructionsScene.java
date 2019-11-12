package unsw.dungeon;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InstructionsScene {
	private Stage stage;
	private InstructionsController controller;
	private Scene scene;
	public InstructionsScene(Stage stage, String filename) throws IOException {
		
		JSONObject json = new JSONObject(new JSONTokener(new FileReader(filename)));    
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("INSTRUCTIONS");
    	instructions.add("Use array key to move player around");
    	instructions.add("Pickable entities will be automatically picked up when player is standing on the same square as the entity");
    	instructions.add("Use SPACE to drop the entity");
    	instructions.add("Goals for this level:");
    	instructions.addAll(getInstruction(json.getJSONObject("goal-condition")));
    	instructions.add("GOOD LUCK");
		
		this.stage = stage;
		stage.setTitle("INSTRUCTIONS");
		controller = new InstructionsController(stage, instructions, filename);
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
	
	private ArrayList<String> getInstruction(JSONObject json) {
    	ArrayList<String> instructions = new ArrayList<>();
    	/*
    	instructions.add("INSTRUCTIONS");
    	instructions.add("Use array key to move player around");
    	instructions.add("Pickable entities will be automatically picked up when player is standing on the same square as the entity");
    	instructions.add("Use SPACE to drop the entity");
    	instructions.add("Goals for this level:");
    	*/
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

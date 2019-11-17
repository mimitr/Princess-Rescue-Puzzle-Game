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
        instructions.add("     INSTRUCTIONS");
    	instructions.add("        - use array key to move player around");
    	instructions.add("        - pickable entities will be automatically picked up when player is standing"+"\n"+" on the same square as the entity");
    	instructions.add("        - use SPACE to drop the entity");
    	instructions.addAll(getInstruction(json.getJSONObject("goal-condition")));
    	instructions.add("\n\n\n"+"     GOOD LUCK!");
		
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
	}
	
	public InstructionsController getController() {
		return controller;
	}
	
	public ArrayList<String> getInstruction(JSONObject json) {
    	ArrayList<String> instructions = new ArrayList<>();
    	
    	String goal = json.getString("goal");
    	
    	if(goal.equals("AND")) {
    		
    		JSONArray array = json.getJSONArray("subgoals");
    		instructions.add("\n"+"     You must complete all of the following goals:");
    		ArrayList<String> andInstructions = new ArrayList<>();
    		for(int i = 0 ; i < array.length(); i++) {
    			andInstructions = getInstruction(array.getJSONObject(i));
    			instructions.addAll(andInstructions);
    		}
    		return instructions;
    	} else if(goal.equals("OR")) {
    		ArrayList<String> orInstructions = new ArrayList<>();
    		instructions.add("   You must complete one of the follwing goals:");
    		JSONArray array = json.getJSONArray("subgoals");
    		for(int i = 0 ; i < array.length(); i++) {
    			orInstructions = getInstruction(array.getJSONObject(i));
    			instructions.addAll(orInstructions);
    		}
    		return instructions;
    	} else if(goal.equals("enemies")) {
    		instructions.add("         <> kill all the enemies in the dungeon");
    		return instructions;
    	} else if(goal.equals("boulders")) {
    		instructions.add("         <>  push all the boulders onto floor switches");
    		return instructions;
    	} else if(goal.equals("exit")) {
    		instructions.add("         <> find your way to the exit");
    		return instructions;
    	} else if(goal.equals("treasure")) {
    		instructions.add("         <> collect all the treasures");
    		return instructions;
    	} else if(goal.equals("princess"))  {
    		instructions.add("         <> save the princess");
    	}
    	
    	return instructions;
    }
	
}
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
		// load the game
		// show instructions of this level
		//DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("advanced.json");
		//DungeonController controller = dungeonLoader.loadController();
        //controller.setStage(stage);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        //loader.setController(controller);
		
		String filename = "dungeons/1.json";
		/*
		JSONObject json = new JSONObject(new JSONTokener(new FileReader("dungeons/1.json")));    
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("INSTRUCTIONS");
    	instructions.add("Use array key to move player around");
    	instructions.add("Pickable entities will be automatically picked up when player is standing on the same square as the entity");
    	instructions.add("Use SPACE to drop the entity");
    	instructions.add("Goals for this level:");
    	instructions.addAll(getInstruction(json.getJSONObject("goal-condition")));
    	instructions.add("GOOD LUCK");
    	*/
    	InstructionsScene scene = new InstructionsScene(stage, filename);
    	scene.start();
		//InstructionsController inController = new InstructionsController(stage, instructions);
		//FXMLLoader inLoader = new FXMLLoader(getClass().getResource("InstructionsView.fxml"));
		//inLoader.setController(inController);
		//inController.addInstructions();
		/*
		
        DungeonController controller = dungeonLoader.loadController();
        //controller.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("INSTRUCTIONS");
    	instructions.add("Use array key to move player around");
    	instructions.add("Pickable entities will be automatically picked up when player is standing on the same square as the entity");
    	instructions.add("Use SPACE to drop the entity");
    	instructions.add("Goals for this level:");
    	instructions.addAll(dungeonLoader.getInstruction());
    	instructions.add("GOOD LUCK");
    	
    	*/
    	/*
        Parent root = inLoader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
        */

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

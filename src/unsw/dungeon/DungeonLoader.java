package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.scene.control.Label;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public JSONObject getJSON() {
    	return json;
    }
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        GoalComponent goal = getGoal(dungeon, json.getJSONObject("goal-condition"));
        dungeon.getPlayer().setGoal(goal);
        return dungeon;
    }
    public ArrayList<String> getInstruction(JSONObject json) {
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
    private GoalComponent getGoal(Dungeon dungeon, JSONObject json) {
    	String goal = json.getString("goal");
    	if(goal.equals("AND")) {
    		
    		GoalComponent compositeGoal = new CompositeGoal(true);
    		JSONArray array = json.getJSONArray("subgoals");
    		
    		for(int i = 0 ; i < array.length(); i++) {
    			GoalComponent subGoal = getGoal(dungeon, array.getJSONObject(i));
    			if(Objects.nonNull(subGoal)) {
    				compositeGoal.addSubGoal(subGoal);
    			}
    		}
    		
    		return compositeGoal;
    	} else if(goal.equals("OR")) {
    		GoalComponent compositeGoal = new CompositeGoal(false);
    		JSONArray array = json.getJSONArray("subgoals");
    		for(int i = 0 ; i < array.length(); i++) {
    			GoalComponent subGoal = getGoal(dungeon, array.getJSONObject(i));
    			compositeGoal.addSubGoal(subGoal);
    		}
    		return compositeGoal;
    	} else if(goal.equals("enemies")) {
    		GoalComponent singleGoal = new EnemyGoal(dungeon.getAllEnemies());
    		return singleGoal;
    	} else if(goal.equals("boulders")) {
    		GoalComponent singleGoal = new BoulderGoal(dungeon.getAllSwitches());
    		return singleGoal;
    	} else if(goal.equals("exit")) {
    		GoalComponent singleGoal = new ExitGoal(dungeon.getExit());
    		return singleGoal;
    	} else if(goal.equals("treasure")) {
    		GoalComponent singleGoal = new TreasureGoal(dungeon.getAllTreasures());
    		return singleGoal;
    	}
   
    	return null;
    } 
    
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;
        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            dungeon.addWall(wall);
            break;
        case "door":
        	id = json.getInt("id");
        	Door door = new Door(x, y, id);
        	onLoad(door);
        	entity = door;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x, y);
        	onLoad(boulder);
        	entity = boulder;
        	dungeon.addBoulder(boulder);
        	break;
        case "key":
        	id = json.getInt("id");
        	Key key = new Key(x, y, id);
        	key.functional().addListener((observable, oldValue, newValue) -> {
        		dungeon.deleteEntity(key);
        	});
        	onLoad(key);
        	entity = key;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x, y);
        	treasure.isPickedUp().addListener((observable, oldValue, newValue) -> {
        		dungeon.deleteEntity(treasure);
        	});
        	onLoad(treasure);
        	entity = treasure;
        	dungeon.addTreasure(treasure);
        	break;
        case "sword":
        	Sword sword = new Sword(x, y);
        	sword.functional().addListener((observable, oldValue, newValue) -> {
        		dungeon.deleteEntity(sword);
        	});
        	onLoad(sword);
        	entity = sword;
        	break;
        case "portal":
        	id = json.getInt("id");
        	Portal portal = new Portal(x, y, id);
        	onLoad(portal);
        	entity = portal;
        	dungeon.addPortal(portal);
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(dungeon, x, y);
        	enemy.stillAlive().addListener((observable, oldValue, newValue) -> {
            	dungeon.deleteEntity(enemy);
            });
        	onLoad(enemy);
        	entity = enemy;
        	dungeon.addEnemy(enemy);
        	break;
        case "invincibility":
        	Potion potion = new Potion(dungeon, x, y);
        	potion.functional().addListener((observable, oldValue, newValue) -> {
        		dungeon.deleteEntity(potion);
        	});
        	onLoad(potion);
        	entity = potion;
        	break;
        case "switch":
        	FloorSwitch floorSwitch = new FloorSwitch(x, y);
        	onLoad(floorSwitch);
        	entity = floorSwitch;
        	dungeon.addSwitch(floorSwitch);
        	break;
        case "exit":
        	Exit exit = new Exit(x, y);
        	onLoad(exit);
        	entity = exit;
        	dungeon.addExit(exit);
        	break;
        case "grass":
        	Grass grass = new Grass(x, y);
        	onLoad(grass);
        	entity = grass;
        	break;
        } 
        
        if(entity != null) {
        	dungeon.addEntity(entity);
        }
        
        
    }

    public abstract void onLoad(Entity player);
    
    //public abstract void onLoad(Princess princess);
    public abstract void onLoad(Grass grass);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad(Potion potion);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Portal portal);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(FloorSwitch floorSwitch);
    
    public abstract void onLoad(Exit exit);
}

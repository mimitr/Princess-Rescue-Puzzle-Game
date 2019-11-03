package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
    private GoalComponent getGoal(Dungeon dungeon, JSONObject json) {
    	String goal = json.getString("goal");
    	if(goal.equals("AND")) {
    		GoalComponent compositeGoal = new CompositeGoal(true);
    		JSONArray array = json.getJSONArray("subgoals");
    		//System.out.println(array.length());
    		for(int i = 0 ; i < array.length(); i++) {
    			//System.out.println("Index i is " + i);
    			GoalComponent subGoal = getGoal(dungeon, array.getJSONObject(i));
    			//System.out.println("-------------");
    			//System.out.println(subGoal);
    			//System.out.println("-------------");
    			if(Objects.nonNull(subGoal)) {
    				//System.out.println(subGoal);
    				compositeGoal.addSubGoal(subGoal);
    			}
    		}
    		//System.out.println("-------------");
    		//System.out.println(compositeGoal);
    		//System.out.println("-------------");
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
    		GoalComponent singleGoal = new EnemyGoal(dungeon);
    		return singleGoal;
    	} else if(goal.equals("boulders")) {
    		GoalComponent singleGoal = new BoulderGoal(dungeon);
    		return singleGoal;
    	} else if(goal.equals("exit")) {
    		//System.out.println("Exit goal");
    		GoalComponent singleGoal = new ExitGoal(dungeon);
    		return singleGoal;
    	} else if(goal.equals("treasure")) {
    		///System.out.println("Treasure goal");
    		GoalComponent singleGoal = new TreasureGoal(dungeon);
    		return singleGoal;
    	}
    	//System.out.println("###");
    	//System.out.println(goal);
    	return null;
    } 
    /*
    private GoalComponent parseGoal(Dungeon dungeon, JSONObject jb) {
    	if (jb.getString("goal").compareTo("AND") == 0) {
    		CompositeGoal goal = new CompositeGoal(dungeon, true);
    		JSONArray arr = jb.getJSONArray("subgoals");
    		for (int i = 0 ; i < arr.length(); i++) {
    			goal.addSubGoal(parseGoal(dungeon, arr.getJSONObject(i)));
    		}
    		return goal;
    	} else if (jb.getString("goal").compareTo("OR") == 0) {
    		CompositeGoal goal = new CompositeGoal(dungeon, false);
    		JSONArray arr = jb.getJSONArray("subgoals");
    		for (int i = 0 ; i < arr.length(); i++) {
    			goal.addSubGoal(parseGoal(dungeon, arr.getJSONObject(i)));
    		}
    		return goal;
    	} else if (jb.getString("goal").compareTo("boulders") == 0) {
    		return new BoulderGoal(dungeon);
    	} else if (jb.getString("goal").compareTo("enemies") == 0) {
    		return new EnemyGoal(dungeon);
    	} else if (jb.getString("goal").compareTo("treasure") == 0) {
    		return new TreasureGoal(dungeon);
    	} else if (jb.getString("goal").compareTo("exit") == 0) {
    		return new ExitGoal(dungeon);
    	}
    	return null;
    }
    */
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
        	//System.out.println("666666");
        	Door door = new Door(x, y, id);
        	//System.out.println("door door");
        	onLoad(door);
        	//System.out.println("succeed");
        	entity = door;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x, y);
        	onLoad(boulder);
        	entity = boulder;
        	dungeon.addBoulder(boulder);
        	break;
        case "key":
        	//System.out.println("key key key");
        	id = json.getInt("id");
        	Key key = new Key(x, y, id);
        	//System.out.println(key);
        	onLoad(key);
        	//System.out.println("hahaha");
        	entity = key;
        	//System.out.println("successful");
        	break;
        // TODO Handle other possible entities
        case "treasure":
        	Treasure treasure = new Treasure(x, y);
        	onLoad(treasure);
        	entity = treasure;
        	dungeon.addTreasure(treasure);
        	break;
        case "sword":
        	Sword sword = new Sword(x, y);
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
        	onLoad(enemy);
        	entity = enemy;
        	dungeon.addEnemy(enemy);
        	break;
        case "invincibility":
        	Potion potion = new Potion(dungeon, x, y);
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
<<<<<<< HEAD
 
=======
        	Exit exit = new Exit(x, y);
        	onLoad(exit);
        	entity = exit;
        	dungeon.addExit(exit);
        	break;
>>>>>>> 0e2eb668f895b93789111969a7476df55da3e491
        }
        
        if(entity != null) {
        	dungeon.addEntity(entity);
        }
        
        
    }

    public abstract void onLoad(Entity player);

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
    // TODO Create additional abstract methods for the other entities

}

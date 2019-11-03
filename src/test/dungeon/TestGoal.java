package test.dungeon;

import unsw.dungeon.Boulder;
import unsw.dungeon.BoulderGoal;
import unsw.dungeon.CompositeGoal;
import unsw.dungeon.Dungeon;

import unsw.dungeon.Player;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;
import unsw.dungeon.TreasureGoal;
import unsw.dungeon.Wall;
import unsw.dungeon.Enemy;
import unsw.dungeon.EnemyGoal;
import unsw.dungeon.ExitGoal;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.GoalComponent;
import unsw.dungeon.Sword;
import unsw.dungeon.GoalComponent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;


import org.junit.jupiter.api.Test;

class TestGoal {

	@Test
	void testSingleGoal() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 3, 2);
		dungeon.setPlayer(player);
			
		Sword sword = new Sword(4, 2);
		dungeon.addEntity(sword);
		
		Enemy enemy = new Enemy(dungeon, 7, 2);
		dungeon.addEnemy(enemy);
		dungeon.addEntity(enemy);
		
		String str = "{\"goal\": \"enemies\"}";
		JSONObject jb = new JSONObject(str);
		GoalComponent enemyGoal = getGoal(dungeon, jb);
		System.out.println(enemyGoal);
		assertEquals(enemyGoal.completed(), false);
	
		// create vertical walls
		int x, y;
		for (x = 0; x < 9; x++) {
			Wall wall1 = new Wall(x, 0);
			Wall wall2 = new Wall(x, 8);
			dungeon.addEntity(wall1);
			dungeon.addEntity(wall2);
		}
		
		// create horizontal walls
		for (y = 0; y < 8; y++) {
			Wall wall3 = new Wall(0, y);
			Wall wall4 = new Wall(8, y);
			dungeon.addEntity(wall3);
			dungeon.addEntity(wall4);
		}
		
		// create walls in the middle
		for (x = 3; x < 6; x++) {
			Wall wall5 = new Wall(x, 3);
			dungeon.addEntity(wall5);
		}
		
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 2);
		/**
		 * initially the enemyGoal is not completed
		 */
		assertEquals(enemyGoal.completed(), false);
		/**
		 * the player picks up the sword
		 */
		player.moveRight();
		/**
		 * the player kills the enemy
		 */
		player.moveRight();
		/**
		 * the single goal is accomplished!
		 */
		assertEquals(enemyGoal.completed(),true);
		
		
		
	}
	
	@Test
	void testCompositeGoal() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 3, 2);
		dungeon.setPlayer(player);
			
		Sword sword = new Sword(4, 2);
		dungeon.addEntity(sword);
		
		Enemy enemy = new Enemy(dungeon, 1, 2);
		dungeon.addEnemy(enemy);
		dungeon.addEntity(enemy);
		
		Treasure treasure = new Treasure(3, 3);
		dungeon.addTreasure(treasure);
		dungeon.addEntity(treasure);
		
		Boulder boulder = new Boulder(7, 6);
		dungeon.addBoulder(boulder);
		dungeon.addEntity(boulder);
		
		FloorSwitch floorSwitch = new FloorSwitch(7, 4);
		dungeon.addSwitch(floorSwitch);
		dungeon.addEntity(floorSwitch);
		
		String str = " { \"goal\": \"AND\", \"subgoals\": [{ \"goal\": \"enemies\"},{\"goal\": \"treasure\"}]}";
		JSONObject jb = new JSONObject(str);
		GoalComponent compositeGoal = getGoal(dungeon, jb);
		//System.out.println("_________________");
		//System.out.println(compositeGoal);
		//System.out.println("_________________");
		
		assertEquals(compositeGoal.completed(), false);
	
		// create vertical walls
		int x, y;
		for (x = 0; x < 9; x++) {
			Wall wall1 = new Wall(x, 0);
			Wall wall2 = new Wall(x, 8);
			dungeon.addEntity(wall1);
			dungeon.addEntity(wall2);
		}
		
		// create horizontal walls
		for (y = 0; y < 8; y++) {
			Wall wall3 = new Wall(0, y);
			Wall wall4 = new Wall(8, y);
			dungeon.addEntity(wall3);
			dungeon.addEntity(wall4);
		}
		
		// create walls in the middle
		for (x = 3; x < 6; x++) {
			Wall wall5 = new Wall(x, 3);
			dungeon.addEntity(wall5);
		}
		
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 2);
		/**
		 * initially the enemyGoal is not completed
		 */
		assertEquals(compositeGoal.completed(), false);
		
		/**
		 * player picks up the sword
		 */
		player.moveRight();
		
		/**
		 * player kills the enemy
		 */
		player.moveLeft();
		assertEquals(enemy.stillAlive(), false);
		
		/**
		 * the enemy goal is completed
		 * but the treasure subgoal is not
		 */
		assertEquals(compositeGoal.completed(), false);
		System.out.println("-----" + player.getCarriedEntity());
		
		/**
		 * player collects the treasure for the second goal
		 */
		player.moveDown();
		player.moveDown();
		System.out.println(player.getCarriedEntity());
		
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
	    		GoalComponent singleGoal = new EnemyGoal(dungeon);
	    		return singleGoal;
	    	} else if(goal.equals("boulders")) {
	    		GoalComponent singleGoal = new BoulderGoal(dungeon);
	    		return singleGoal;
	    	} else if(goal.equals("exit")) {
	    		GoalComponent singleGoal = new ExitGoal(dungeon);
	    		return singleGoal;
	    	} else if(goal.equals("treasure")) {
	    		GoalComponent singleGoal = new TreasureGoal(dungeon);
	    		return singleGoal;
	    	}
	   
	    	return null;
	    } 

}

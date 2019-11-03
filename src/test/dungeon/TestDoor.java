package test.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Key;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Door;

/*
 * Epic story 3
 * US 1
 * Testing picking up the key and using the key to move through the corresponding door
 */

public class TestDoor {
	
	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 1, 6);
		
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
		
		// create a door with the  ID = 111
		Door door = new Door(4, 5, 111);
		dungeon.addEntity(door);
		
		// create a key of the matching ID = 111
		Key key1 = new Key(2, 5, 111);
		dungeon.addEntity(key1);
				
		// create an not matching key
		Key key2 = new Key(1, 4, 222);
		dungeon.addEntity(key2);
		
		dungeon.setPlayer(player);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 6);
		
		/*
		 * the player picks up the not matching key
		 */
		player.moveUp();
		player.moveUp();
		
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 5);
		
		player.moveRight();
		player.moveRight();
		player.moveRight();
		/*
		 * the player cannot go through the door because he's having non matching key
		 */
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 5);
		
		player.moveDown();
		/*
		 * leave the key 
		 */
		player.putDown();
		player.moveLeft();
		
		/*
		 * picking up the matching key
		 */
		player.moveUp();
		player.moveRight();
		player.moveRight();
		/*
		 * player successfully went throught the door using the correct matching key
		 */
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 5);
		
		
		
	}
	
}

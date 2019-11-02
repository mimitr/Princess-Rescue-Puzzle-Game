package test.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Treasure;
import unsw.dungeon.Sword;
import unsw.dungeon.Potion;
import unsw.dungeon.Key;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 * Epic story 4
 * US 1
 */

class TestPickUp {

	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 1, 2);
		
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
		
		Treasure treasure1 = new Treasure (2, 2);
		Treasure treasure2 = new Treasure (2, 3);
		Key key = new Key(3, 4, 111);
		Potion potion = new Potion(6, 6);
		Sword sword = new Sword(5, 5);
		
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		dungeon.addEntity(key);
		dungeon.addEntity(potion);
		dungeon.addEntity(sword);
		
		dungeon.setPlayer(player);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 2);
		
		/*
		 * player picks up the first treasure
		 * initially, the treasure amount is 0
		 * then it get incremented to 1
		 */
		assertEquals(player.treasureAmount(), 0);
		player.moveRight();
		assertEquals(player.treasureAmount(), 1);
		
		/* 
		 * player picks up the second treasure
		 * treasure amount is now 2
		 */
		player.moveDown();
		assertEquals(player.treasureAmount(), 2);
		
		player.moveDown();
		
		/*
		 * player picks up the key
		 */
		player.moveRight();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 4);
		
		/*
		 * the key's position initially was (3, 4)
		 * as the player has picked the key and is moving along with it
		 * the key's position is now changed and is equal to the players position
		 */
		player.moveRight();
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 4);
		
		assertEquals(key.getX(), 4);
		assertEquals(key.getY(), 4);
		
		/*
		 * player drops the key so he can pick up other stuff like potion or sword
		 */
		player.putDown();
		
		player.moveDown();
		
		/*
		 * player picks up the sword
		 */
		player.moveRight();
		assertEquals(player.getX(), 5);
		assertEquals(player.getY(), 5);
		
		/*
		 * now, the sword moves along with the player
		 */
		player.moveDown();
		assertEquals(player.getX(), 5);
		assertEquals(player.getY(), 6);
		
		assertEquals(sword.getX(), 5);
		assertEquals(sword.getY(), 6);
		
		/*
		 * player drops the sword
		 */
		player.putDown();
		
		/*
		 * player picks up the potion
		 */
		player.moveRight();
		
		/*
		 * player moves with the potion
		 */
		player.moveDown();
		assertEquals(player.getX(), 6);
		assertEquals(player.getY(), 7);
		
		assertEquals(potion.getX(), 6);
		assertEquals(potion.getY(), 7);
	}
	

}

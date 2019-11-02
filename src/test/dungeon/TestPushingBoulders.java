package test.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Boulder;

/*
 * Epic story 4
 * US 2
 */

public class TestPushingBoulders {
	
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
		
		Boulder boulder1 = new Boulder(3, 2);
		Boulder boulder2 = new Boulder(4, 4);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		
		dungeon.setPlayer(player);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 2);
		
		player.moveRight();
		/*
		 * player(2,2) is standing next to the boulder1(3,2)
		 * now, when the player one step to the right 
		 * the boulder will also be pushed 1 step to the right
		 */
		player.moveRight();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 2);
		
		assertEquals(boulder1.getX(), 4);
		assertEquals(boulder1.getY(), 2);
		
		player.moveRight();
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 2);
		
		assertEquals(boulder1.getX(), 5);
		assertEquals(boulder1.getY(), 2);
		
		/*
		 * now player tries to go to another boulder to push to another direction
		 */
		player.moveDown();
		
		/*
		 * player(4, 3) is standing next to bouler2(4, 4)
		 * now, he puhses the boulder down
		 */
		player.moveDown();
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 4);
		
		assertEquals(boulder2.getX(), 4);
		assertEquals(boulder2.getY(), 5);
	}
		
}

package test.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/*
 * Epic story 3
 * US 2
 */

class TestPlayerMove {

	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 3, 2);
		
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
		
		dungeon.setPlayer(player);
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 2);
		
		// could not move because encountered the wall
		player.moveDown();
		assertEquals(player.getY(), 2);
		assertEquals(player.getX(), 3);
		
		player.moveLeft();
		player.moveLeft();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 2);
		
		// go around the wall
		player.moveDown();
		player.moveDown();
		player.moveDown();
		assertEquals(player.getY(), 5);
		assertEquals(player.getX(), 1);
		
		player.moveUp();
		assertEquals(player.getY(), 4);
		assertEquals(player.getX(), 1);
		
		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 4);
		
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveLeft();
		player.moveUp();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 4);
		
		//  cannot move through the wall
		player.moveUp();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 4);
		player.moveUp();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 4);
		
		player.moveLeft();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 4);
		
		player.moveLeft();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 4);
		
		// cannot move through the boarder wall
		player.moveLeft();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 4);
		
	}

}

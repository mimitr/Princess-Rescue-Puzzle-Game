package test.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Portal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 * Epic story 3
 * US 3
 */

class TestPortal {

	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 2, 6);
		
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
	
		
		/**
		 * Creating portals:
		 * portal 1 & 2 with id = 333
		 * portal 3 & 4 with id = 444
		 */
		Portal portal1 = new Portal(3, 6, 333);
		Portal portal2 = new Portal(5, 1, 333);
		dungeon.addEntity(portal1);
		dungeon.addEntity(portal2);
		
		Portal portal3 = new Portal(2, 7, 444);
		Portal portal4 = new Portal(7, 3, 444);
		dungeon.addEntity(portal3);
		dungeon.addEntity(portal4);
		
		dungeon.setPlayer(player);
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 6);
		
		/**
		 * appear on the other side of the portal of the same ID = 333
		 */
		player.moveRight();
		assertEquals(player.getX(), 6);
		assertEquals(player.getY(), 1);
		
		
		player.moveLeft();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 6);
		
		/**
		 * entering another portal with ID = 444
		 * going up -> down
		 */
		player.moveDown();
		assertEquals(player.getX(), 7);
		assertEquals(player.getY(), 4);
		
		/**
		 * go back to the other side
		 * entering from down -> up
		 */
		player.moveUp();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 6);
		
		player.moveLeft();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 6);
		
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 7);
		
		/**
		 * entering the portal from right to left
		 * but there is a wall on the other side of the portal
		 * so player stays at the same location
		 */
		player.moveRight();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 7);
	}

}

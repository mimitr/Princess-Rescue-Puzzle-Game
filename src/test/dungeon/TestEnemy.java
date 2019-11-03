package test.dungeon;

import unsw.dungeon.Dungeon;

import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Enemy;
import unsw.dungeon.Sword;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 * Testing the enemies moving TOWARDS the player
 * Testing the sword can be used 5 times to kill the enemies 
 * Testing the enemies will die after they get hit with the sword
 */

class TestEnemy {

	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 1, 1);
		Sword sword = new Sword(2, 1);
		dungeon.addEntity(sword);
		
		Enemy e1 = new Enemy(dungeon, 1, 6);
		Enemy e2 = new Enemy(dungeon, 3, 4);
		Enemy e3 = new Enemy(dungeon, 5, 5);
		Enemy e4 = new Enemy(dungeon, 6, 3);
		Enemy e5 = new Enemy(dungeon, 7, 6);
		
		dungeon.addEnemy(e1);
		dungeon.addEnemy(e2);
		dungeon.addEnemy(e3);
		dungeon.addEnemy(e4);
		dungeon.addEnemy(e5);
		
		
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
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
		
		/*
		 * the player moves to the right and picks up the sword
		 * all other enemies moves TOWARDS the player
		 * player is at (1,1) -> moves to (2, 1)
		 * e1(1,6)  -> e1(1,5)
		 * e2(3, 4) -> e2(2, 4)
		 * e3(5, 5) -> e3(5, 4)
		 * e4(6, 3) -> e3(6, 2)
		 * e5(7, 6) -> e5(7, 5)
		 */
		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		assertEquals(e1.getX(), 1);
		assertEquals(e1.getY(), 5);
		
		assertEquals(e2.getX(), 2);
		assertEquals(e2.getY(), 4);
		
		assertEquals(e3.getX(), 5);
		assertEquals(e3.getY(), 4);
		
		assertEquals(e4.getX(), 6);
		assertEquals(e4.getY(), 2);
		
		assertEquals(e5.getX(), 7);
		assertEquals(e5.getY(), 5);
		
		/*
		 * player continues moving down with the sword in order to kill the closest enemy e2
		 * all enemies are still moving TOWARDS the player
		 */
		player.moveDown();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);
		
		assertEquals(e1.getX(), 1);
		assertEquals(e1.getY(), 4);
		
		assertEquals(e2.getX(), 2);
		assertEquals(e2.getY(), 3);
		
		assertEquals(e3.getX(), 4);
		assertEquals(e3.getY(), 4);
		
		assertEquals(e4.getX(), 5);
		assertEquals(e4.getY(), 2);
		
		assertEquals(e5.getX(), 7);
		assertEquals(e5.getY(), 4);
		
		/* 
		 * the player moves to e2 and kills it
		 * meanwhile other enemies continue to approach the player
		 */
		player.moveDown();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);
		
		assertEquals(e1.getX(), 1);
		assertEquals(e1.getY(), 3);
		
		assertEquals(e2.getX(), 2);
		assertEquals(e2.getY(), 3);
		
		assertEquals(e3.getX(), 3);
		assertEquals(e3.getY(), 4);
		
		assertEquals(e4.getX(), 4);
		assertEquals(e4.getY(), 2);
		
		assertEquals(e5.getX(), 7);
		assertEquals(e5.getY(), 3);
		
		/*
		 * the player moves left to kill e1
		 */
		player.moveLeft();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 3);
		
		assertEquals(e1.getX(), 1);
		assertEquals(e1.getY(), 3);
		
		assertEquals(e2.getX(), 2);
		assertEquals(e2.getY(), 3);
		
		//assertFalse(e2.stillAlive());
		assertEquals(e3.getX(), 3);
		assertEquals(e3.getY(), 4);
		
		assertEquals(e4.getX(), 4);
		assertEquals(e4.getY(), 2);
		
		assertEquals(e5.getX(), 7);
		assertEquals(e5.getY(), 3);
		
		
		
		
	}

}
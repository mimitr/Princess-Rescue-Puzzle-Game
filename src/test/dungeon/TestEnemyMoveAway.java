package test.dungeon;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Potion;

/*
 * Epic Story 1
 * Testing the enemy moving AWAY from the player
 * Testing the potion lasts only for 20 steps
 */
class TestEnemyMoveAway {

	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 1, 1);
		
		Potion potion = new Potion(dungeon, 2, 1);
		dungeon.addEntity(potion);
		
		Enemy enemy = new Enemy(dungeon, 4, 2);
		dungeon.addEnemy(enemy);
		dungeon.addEntity(enemy);
		
		
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
		
		Wall wall6 = new Wall(7, 2);
		dungeon.addEntity(wall6);
		
		dungeon.setPlayer(player);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
		
		/*
		 * player picks up the potion
		 */
		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 4);
		assertEquals(enemy.getY(), 1);
		
		/*
		 * now, the player has the potion and the enemy will move AWAY from the player
		 */
		player.moveRight();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 1);
		
		/*
		 * the enemy is moving away from the player
		 * potion.stepCounter = 1
		 */
		player.moveRight();
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 6);
		assertEquals(enemy.getY(), 1);
		
		/*
		 * the enemy is moving away from the player
		 * player (4,1) -> player(5, 1)
		 * enemy(6,1) -> enemy(7, 1)
		 * * potion.stepCounter = 2
		 */
		player.moveRight();
		assertEquals(player.getX(), 5);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 7);
		assertEquals(enemy.getY(), 1);
		
		/*
		 * the enemy stays at the same place since he is blocked by another wall
		 * * potion.stepCounter = 3
		 */
		player.moveRight();
		assertEquals(player.getX(), 6);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 7);
		assertEquals(enemy.getY(), 1);
		
		/*
		 * the player catches the enemy while he's having the potion 
		 * so the enemy dies and does not move anymore
		 * * potion.stepCounter = 4
		 */
		player.moveRight();
		assertEquals(player.getX(), 7);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 7);
		assertEquals(enemy.getY(), 1);
		assertEquals(enemy.stillAlive(), false);
		
		/*
		 * potion.stepCounter = 5
		 */
		player.moveLeft();
		assertEquals(player.getX(), 6);
		assertEquals(player.getY(), 1);
		
		/*
		 * potion.stepCounter = 6
		 */
		player.moveLeft();
		assertEquals(player.getX(), 5);
		assertEquals(player.getY(), 1);
		
		/*
		 * potion.stepCounter = 7
		 */
		player.moveLeft();
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 1);
		
		/*
		 * potion.stepCounter = 8
		 */
		player.moveLeft();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		
		/*
		 * potion.stepCounter = 9
		 */
		player.moveLeft();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		/*
		 * potion.stepCounter = 10
		 */
		player.moveLeft();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
		//assertEquals(player.getCarriedEntity(), potion);
		assertEquals(potion.getX(), 2);
		assertEquals(potion.getY(), 1);
		
		/*
		 * after 10 steps with the potion, the potion stays at the same spot
		 * but the player continues to move without the potion
		 */
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 2);
		assertEquals(player.getCarriedEntity(), potion);

		
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 3);
		assertEquals(player.getCarriedEntity(), potion);
		
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 4);
		assertEquals(player.getCarriedEntity(), potion);
		
		
	}

}
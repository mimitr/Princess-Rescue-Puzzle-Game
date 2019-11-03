package test.dungeon;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Potion;

class TestEnemyMoveAway {

	@Test
	void test() {
		Dungeon dungeon = new Dungeon(9, 9);
		Player player = new Player(dungeon, 1, 1);
		
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
	}

}
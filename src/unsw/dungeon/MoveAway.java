package unsw.dungeon;

public class MoveAway implements EnemyMoveStrategy {
	public void move(Player player, Enemy enemy) {
		Boolean left;
		Boolean right;
		Boolean up;
		Boolean down;
		
		left = enemy.canEnemyMoveTo(enemy.getX() - 1, enemy.getY());
		right = enemy.canEnemyMoveTo(enemy.getX() + 1, enemy.getY());
		up = enemy.canEnemyMoveTo(enemy.getX(), enemy.getY() - 1);
		down = enemy.canEnemyMoveTo(enemy.getX(), enemy.getY() + 1);
		
		if(player.getY() == enemy.getY()) {
			if(player.getX() < enemy.getX()) {
				// move to the left
				/*
				if(enemy.canEnemyMoveTo(enemy.getX() - 1, enemy.getY())) {
					enemy.x().set(enemy.getX() - 1);
				}
				*/
				if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				}
				
			} else {
				// move to the right
				/*
				if(enemy.canEnemyMoveTo(enemy.getX() + 1, enemy.getY())) {
					enemy.x().set(enemy.getX() + 1);
				}
				*/
				if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				}
			}
		} 
		if(player.getY() < enemy.getY()) {
			// enemy should move upwards
			/*
			if(enemy.canEnemyMoveTo(enemy.getX(), enemy.getY() - 1)) {
				enemy.y().set(enemy.getY() - 1);
			}
			*/
			if(player.getX() < enemy.getX()) {
				if(down) {
					enemy.y().set(enemy.getY() + 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(up) {
					enemy.y().set(enemy.getY() - 1);
				}
			} else {
				if(down) {
					enemy.y().set(enemy.getY() + 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(down) {
					enemy.y().set(enemy.getY() - 1);
				}
			}
			
		} 
		if(player.getY() > enemy.getY()) {
			/*
			if(enemy.canEnemyMoveTo(enemy.getX(), enemy.getY() - 1)) {
				enemy.y().set(enemy.getY() - 1);
			}
			*/
			if(player.getX() < enemy.getX()) {
				if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				}
			} else {
				if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				}
			}
		}
	}
}

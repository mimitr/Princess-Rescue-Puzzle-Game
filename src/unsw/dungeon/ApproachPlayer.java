package unsw.dungeon;

public class ApproachPlayer implements EnemyMoveStrategy {
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

				if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				}
				
			} else if (player.getX() > enemy.getX()){

				if(right) {
					System.out.println("right");
					enemy.x().set(enemy.getX() + 1);
				} else if(up) {
					System.out.println("up");
					enemy.y().set(enemy.getY() - 1);
				} else if(down) {
					System.out.println("down");
					enemy.y().set(enemy.getY() + 1);
				} else if(left) {
					System.out.println("left");
					enemy.x().set(enemy.getX() - 1);
				}
			}
		} else if(player.getY() < enemy.getY()) {
			
			if(player.getX() < enemy.getX()) {
				
				if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				}
			} else {
				if(up) {
					enemy.y().set(enemy.getY() - 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(down) {
					enemy.y().set(enemy.getY() + 1);
				}
			}
			
		} else if(player.getY() > enemy.getY()) {
			
			if(player.getX() < enemy.getX()) {
				if(down) {
					enemy.y().set(enemy.getY() + 1);
				} else if(left) {
					enemy.x().set(enemy.getX() - 1);
				} else if(right) {
					enemy.x().set(enemy.getX() + 1);
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
				} else if(up) {
					enemy.y().set(enemy.getY() - 1);
				}
			}
		}
	}
}

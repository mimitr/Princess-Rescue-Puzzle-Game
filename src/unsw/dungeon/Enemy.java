package unsw.dungeon;

import java.util.Objects;

import java.util.List;

public class Enemy extends Entity implements EntityObserver{
	
	private Boolean alive;
	private EnemyMoveStrategy strategy;
	private Dungeon dungeon;
	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		alive = true;
		strategy = new ApproachPlayer();
		this.dungeon = dungeon;
	} 
	
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		System.out.println("update");
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(player.getX() == getX() && player.getY() == getY() && alive) {
			if(!player.killEnemy()) {
				player.setAlive(false);
				return false;
			} else {
				System.out.println("Enemy should die");
				alive = false;
			}
		}
		return true;
	}
	
	public Boolean stillAlive() {
		return alive;
	}

	public void move(Player player) {
		strategy.move(player, this);
	}
	
	public void setStrategy(EnemyMoveStrategy newStrategy) {
		strategy = newStrategy;
	}
	
	public Boolean canEnemyMoveTo(int x, int y) {
		Boolean canMove = true;
		if(alive) {
			List<Entity> entities = dungeon.getEntityOnSquare(x, y);
			if(!entities.isEmpty()) {
				for(Entity entity : entities) {
					if(!entity.canEnemyMove()) {
						canMove = false;
						break;
					}
				}
			}
		} else {
			canMove = false;
		}
		return canMove;
	}
	
	public Dungeon getDungeon() {
		return dungeon;
	}
	
	public Boolean canEnemyMove() {
		if(alive) {
			return false;
		}
		return true;
	}
}

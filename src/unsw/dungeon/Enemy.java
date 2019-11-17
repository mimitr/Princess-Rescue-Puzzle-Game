package unsw.dungeon;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;

public class Enemy extends Entity implements EntityObserver{
	
	private BooleanProperty alive;
	private EnemyMoveStrategy strategy;
	private Dungeon dungeon;
	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		alive = new SimpleBooleanProperty(true);
		strategy = new ApproachPlayer();
		this.dungeon = dungeon;
	} 
	
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		// depends on whether player has potion or not
		System.out.println("adf");
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(player.getX() == getX() && player.getY() == getY()) {
			if(!player.killEnemy()) {
				System.out.println("Player cannot kill enemy");
				player.setAlive(false);
				return false;
			} else {
				System.out.println("Enemy should die");
				alive.setValue(false);;
			}
		}
		return true;
	}
	
	public BooleanProperty stillAlive() {
		return alive;
	}
	// strategy pattern
	public void move(Player player) {
		strategy.move(player, this);
	}
	
	public void setStrategy(EnemyMoveStrategy newStrategy) {
		strategy = newStrategy;
	}
	
	public Boolean canEnemyMoveTo(int x, int y) {
		Boolean canMove = true;
		if(alive.getValue()) {
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
		return false;
	}
}

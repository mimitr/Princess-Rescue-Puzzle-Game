package unsw.dungeon;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty; 

public class Potion extends Entity implements EntityObserver{

	//If the player picks this up they become invincible to enemies. 
	// Colliding with an enemy should result in their immediate destruction. 
	//Because of this, all enemies will run away from the player when they are invincible. 
	//The effect of the potion only lasts a limited time.
	
	private IntegerProperty counter;
	private Dungeon dungeon;
	
	public Potion(Dungeon dungeon, int x, int y) {
		super(x, y);
		counter = new SimpleIntegerProperty(10);
		this.dungeon = dungeon;
	}

	@Override
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		// move along with the player
		if (counter.get() != 0) {
		x().setValue(((Player) player).getX());
		y().setValue(((Player) player).getY());
			//counter--;
			decreaseCount();
			System.out.println(counter);
		} else {
			((Player) player).setState(new NoWeapon((Player) player));
			dungeon.enemyMoveTowards();
		}
		// should make the potion disappear somehow	
		
	}
	public void decreaseCount() {
		int counterInt = counter.get();
		counterInt--;
		counter.set(counterInt);
	}
	public IntegerProperty getCounter() {
		return counter;
	}
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(Objects.nonNull(player.getCarriedEntity()) && player.getCarriedEntity() instanceof Boulder) {
			return false;
		}
		return true;
	}
	
	public Boolean canBePickedUp() {
		return true;
	}
	
	public Boolean canEnemyMove() {
		return false;
	}

	public void pickedUp(Player player) {
		player.attach((EntityObserver)this);
		player.setState(player.getHasPotionState());
		dungeon.enemyMoveAway();
		//
	}
}

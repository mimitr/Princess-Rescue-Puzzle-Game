package unsw.dungeon;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty; 

public class Potion extends Entity implements EntityObserver{

	//If the player picks this up they become invincible to enemies. 
	// Colliding with an enemy should result in their immediate destruction. 
	//Because of this, all enemies will run away from the player when they are invincible. 
	//The effect of the potion only lasts a limited time.
	
	private int counter;
	private Dungeon dungeon;
	private BooleanProperty functional;
	
	public Potion(Dungeon dungeon, int x, int y) {
		super(x, y);
		counter = 10;
		this.dungeon = dungeon;
		functional = new SimpleBooleanProperty(true);
	}

	@Override
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		// move along with the player
		if (counter != 0) {
		x().setValue(((Player) player).getX());
		y().setValue(((Player) player).getY());
			//counter--;
			decreaseCount();
			if(counter == 0) {
				Player p = (Player) player;
				p.setState(p.getNoWeaponState());
				p.detach();
				functional.setValue(false);
			}
			//System.out.println(counter);
		} else {
			((Player) player).setState(new NoWeapon((Player) player));
			dungeon.enemyMoveTowards();
			functional.setValue(false);
		}
		// should make the potion disappear somehow	
		
	}
	public void decreaseCount() {
		counter--;
	}
	public int getCounter() {
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
	
	public BooleanProperty functional() {
		return functional;
	}
}

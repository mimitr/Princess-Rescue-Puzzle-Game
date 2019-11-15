package unsw.dungeon;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Sword extends Entity implements EntityObserver {
	
	private int remainingHits;
	private BooleanProperty functional;

	public Sword(int x, int y) {
		super(x, y);
		remainingHits = 5;
		functional = new SimpleBooleanProperty(true);
	}
	
	public void setRemainingHits() {
		remainingHits--;
		if(remainingHits == 0) {
			functional.setValue(false);
		}
	}
	
	public int getRemainingHits() {
		return remainingHits;
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

	public void update(PlayerSubject player, int up, int down, int left, int right) {
		// move along with the player
		x().setValue(((Player) player).getX());
		y().setValue(((Player) player).getY());
	}
	
	public void pickedUp(Player player) {
		player.attach((EntityObserver)this);
		player.setState(player.getHasSwordState());
	}
	
	public BooleanProperty functional() {
		return functional;
	}
}

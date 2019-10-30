package unsw.dungeon;

import java.util.Objects;

public class Sword extends Entity implements EntityObserver {
	
	private int remainingHits;

	public Sword(int x, int y) {
		super(x, y);
		remainingHits = 5;
	}
	
	public void setRemainingHits() {
		remainingHits--;
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
	
}

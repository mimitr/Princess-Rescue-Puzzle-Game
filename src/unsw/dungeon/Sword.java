package unsw.dungeon;

import java.util.Objects;

public class Sword extends Entity implements EntityObserver {
	
	private int remainingHits;

	public Sword(int x, int y) {
		super(x, y);
		remainingHits = 5;
	}
	
	public int getRemainingHits() {
		return remainingHits;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		
	}
	
	public Boolean canBePickedUp() {
		
	}
	
}

package unsw.dungeon;

import java.util.Objects;

public class Treasure extends Entity implements EntityObserver {

	public Treasure(int x, int y) {
		super(x, y);
	}

	public void update(PlayerSubject player, int up, int down, int left, int right) {
		
	}
	
	public Boolean canBePickedUp() {
		return true;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(Objects.nonNull(player.getCarriedEntity()) && player.getCarriedEntity() instanceof Boulder) {
			return false;
		}
		return true;
	}
	
	public void pickedUp(Player player) {
		player.increaseTreasureAmount();
	}
	
}

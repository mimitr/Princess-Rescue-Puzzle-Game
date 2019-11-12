package unsw.dungeon;

import java.util.Objects;

public class Treasure extends Entity {

	private Boolean pickedUp;
	
	public Treasure(int x, int y) {
		super(x, y);
		pickedUp = false;
	}
	
	public Boolean canBePickedUp() {
		return true;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(Objects.nonNull(player.getCarriedEntity()) && player.getCarriedEntity() instanceof Boulder) {
			return false;
		}
		//pickedUp(player);
		return true;
	}
	
	public void pickedUp(Player player) {
		player.increaseTreasureAmount();
		pickedUp = true;
	}
	
	public String name() {
		return "treasure";
	}
	
	public Boolean isPickedUp() {
		return pickedUp;
	}
}

package unsw.dungeon;

import java.util.Objects;

public class HasPotion implements PlayerState {

	private Player player;
	
	public HasPotion(Player player) {
		this.player = player;
	}
	
	public void pickUp(Entity entity) {
		if(entity.name().equals("treasure")) {
			// player can pick it up
			// treasure will just disappear from the dungeon
			player.increaseTreasureAmount();
		}
	}
	
	public void putDown() {
		player.detach();
		player.setState(player.getNoWeaponState());
	}
	
	public Boolean activeWeapon() {
		return true;
	}
	
	public void killEnemy() {
		if(Objects.nonNull(player.getCarriedEntity())) {
			// the enemy should disappear from the dungeon
		}
	}
		
}

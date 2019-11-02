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
	
	public Boolean killEnemy() {
		Potion potion = (Potion)player.getCarriedEntity();
		if(potion.getCounter() > 0) {
			potion.decreaseCount();
			return true;
		} else {
			player.setState(player.getNoWeaponState());
			return false;
		}
		
	}
		
}

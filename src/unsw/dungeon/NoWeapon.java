package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public class NoWeapon implements PlayerState {
	
	private Player player;
	
	public NoWeapon(Player player) {
		this.player = player;
	}
	
	public void pickUp(Entity entity) {
		// can pick up anything
		entity.pickedUp(player);
	}
	
	public void putDown() {
		System.out.println("Player carries nothing");
	}
	
	public Boolean activeWeapon() {
		return false;
	}
	
	public Boolean killEnemy() {
		// Instead of killing the enemy, the player get killed
		// player loses the game
		// ask the player if she/he wants to restart again
		return false;
	}
}

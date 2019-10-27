package unsw.dungeon;

public class HasKey implements PlayerState {
	
	private Player player;
	
	public HasKey(Player player) {
		this.player = player;
	}
	
	public void pickUp(Entity entity) {
		if(entity.name().equals("treasure")) {
			// can pick up treasure only
			player.increaseTreasureAmount();
		}
	}
	
	public void putDown() {
		player.detach();
		player.setState(player.getNoWeaponState());
	}
	
	public Boolean activeWeapon() {
		return false;
	}
	
	public void killEnemy() {
		// player will die
	}
}

package unsw.dungeon;

public class HasSword implements PlayerState {
	
	private Player player;
	
	public HasSword(Player player) {
		this.player = player;
	}
	
	public void pickUp(Entity entity) {
		// check if the entity can be picked up
		// if the entity is anything other than treasure then the player cannot pick it up
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
		// if the sword still has remaining hits
		// make the enemy disappear from the dungeon
	}
}

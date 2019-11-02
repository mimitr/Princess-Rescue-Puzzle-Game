package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

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
		List<Entity> entities = new ArrayList<>();
		entities = player.getDungeon().getEntityOnSquare(player.getX(), player.getY());
		System.out.println(entities.size());
		if(entities.size() == 1) {
			player.detach();
			player.setState(player.getNoWeaponState());
		}
		//player.detach();
		//player.setState(player.getNoWeaponState());
	}
	
	public Boolean activeWeapon() {
		return true;
	}
	
	public Boolean killEnemy() {
		// if the sword still has remaining hits
		// make the enemy disappear from the dungeon
		Sword sword = (Sword)player.getCarriedEntity();
		if(sword.getRemainingHits() > 0) {
			System.out.println("Player can still kill an enemy");
			sword.setRemainingHits();
			return true;
		} else {
			player.setState(player.getNoWeaponState());
			return false;
		}
		//return true;
	}
}

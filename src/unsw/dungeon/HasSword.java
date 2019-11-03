package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class HasSword implements PlayerState {
	
	private Player player;
	
	public HasSword(Player player) {
		this.player = player;
	}
	
	public void pickUp(Entity entity) {
		if(entity.name().equals("treasure")) {
			//player.increaseTreasureAmount();
			entity.pickedUp(player);
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
	}
	
	public Boolean activeWeapon() {
		return true;
	}
	
	public Boolean killEnemy() {
		
		Sword sword = (Sword)player.getCarriedEntity();
		if(sword.getRemainingHits() > 0) {
			sword.setRemainingHits();
			return true;
		} else {
			player.setState(player.getNoWeaponState());
			player.detach();
			return false;
		}
	}
}

package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HasPotion implements PlayerState {

	private Player player;
	
	public HasPotion(Player player) {
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
		Potion potion = (Potion)player.getCarriedEntity();
		if(potion.getCounter() > 0) {
			return true;
		} else {
			player.setState(player.getNoWeaponState());
			player.detach();
			return false;
		}
		
	}
		
}

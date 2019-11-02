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
			// player can pick it up
			// treasure will just disappear from the dungeon
			player.increaseTreasureAmount();
		}
	}
	
	public void putDown() {
		/*
		List<Entity> entities = new ArrayList<>();
		entities = player.getDungeon().getEntityOnSquare(player.getX(), player.getY());
		if(entities.size() == 1) {
			Entity entity = entities.get(0);
			if(entity instanceof FloorSwitch) {
				player.detach();
				player.setState(player.getNoWeaponState());
			}
		}
		if(entities.size() == 0) {
			player.detach();
			player.setState(player.getNoWeaponState());
		}
		*/
		//player.detach();
		//player.setState(player.getNoWeaponState());
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
			potion.decreaseCount();
			return true;
		} else {
			player.setState(player.getNoWeaponState());
			return false;
		}
		
	}
		
}

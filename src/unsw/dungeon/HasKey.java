package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

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
		return false;
	}
	
	public Boolean killEnemy() {
		// player will die
		return false;
	}
}

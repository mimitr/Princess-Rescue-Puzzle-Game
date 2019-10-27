package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public class NoWeapon implements PlayerState {
	
	private Player player;
	
	public NoWeapon(Player player) {
		this.player = player;
	}
	
	public void pickUp(Entity entity) {
		// can pick up anything
		if(entity.name().equals("treasure")) {
			player.increaseTreasureAmount();
		} else {
			// the image of the entity should move as the player moves
			if(entity.name().equals("sword")) {
				player.attach((EntityObserver)entity);
				player.setState(player.getHasSwordState());
			}
			if(entity.name().equals("key")) {
				System.out.println("Entity encountered is key");
				player.attach((EntityObserver)entity);
				player.setState(player.getHasKeyState());
			}
			if(entity.name().equals("potion")) {
				player.attach((EntityObserver)entity);
				player.setState(player.getHasPotionState());
			}
			
		}
	}
	
	public void putDown() {
		System.out.println("Player carries nothing");
	}
	
	public Boolean activeWeapon() {
		return false;
	}
	
	public void killEnemy() {
		// Instead of killing the enemy, the player get killed
		// player loses the game
		// ask the player if she/he wants to restart again
	}
}

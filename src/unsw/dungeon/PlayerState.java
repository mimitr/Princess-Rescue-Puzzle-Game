package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface PlayerState {
	public void pickUp(Entity entity);
	public Boolean activeWeapon();
	public void killEnemy();
}

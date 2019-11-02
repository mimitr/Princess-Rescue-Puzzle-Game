package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface PlayerState {
	public void pickUp(Entity entity);
	public void putDown();
	public Boolean activeWeapon();
	public Boolean killEnemy();
}

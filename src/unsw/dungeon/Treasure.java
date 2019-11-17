package unsw.dungeon;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Treasure extends Entity {

	private BooleanProperty pickedUp;
	
	public Treasure(int x, int y) {
		super(x, y);
		pickedUp = new SimpleBooleanProperty(false);
	}
	
	public Boolean canBePickedUp() {
		return true;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(Objects.nonNull(player.getCarriedEntity()) && player.getCarriedEntity() instanceof Boulder) {
			return false;
		}
		return true;
	}
	
	public void pickedUp(Player player) {
		player.increaseTreasureAmount();
		pickedUp.setValue(true);;
	}
	
	public String name() {
		return "treasure";
	}
	
	public BooleanProperty isPickedUp() {
		return pickedUp;
	}
}

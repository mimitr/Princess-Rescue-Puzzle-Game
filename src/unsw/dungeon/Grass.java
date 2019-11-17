package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Grass extends Entity {
	
	private BooleanProperty appear;
	
	public Grass(int x, int y) {
		super(x, y);
		appear = new SimpleBooleanProperty(true);
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(player.getX() == getX() && player.getY() == getY() && appear.getValue()) {
			player.setAlive(false);
			return false;
		}
		return true;
	}
	
	public BooleanProperty shouldAppear() {
		return appear;
	}
	
	public void setAppear(Boolean b) {
		appear.setValue(b);
	}
}

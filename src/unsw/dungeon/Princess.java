package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Princess extends Entity {
	
	private BooleanProperty foundLove;
	
	public Princess(int x, int y) {
		super(x, y);
		foundLove = new SimpleBooleanProperty(false);
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(player.getX() == getX() && player.getY() == getY()) {
			player.setFoundLove(true);
			foundLove.setValue(true);
		}
		return true;
	}
	
	public BooleanProperty FoundLove() {
		return foundLove;
	}
}

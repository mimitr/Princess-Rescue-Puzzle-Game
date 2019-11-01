package unsw.dungeon;

import java.util.Objects;

public class Portal extends Entity {
	
	private int id;

	public Portal(int x, int y, int id) {
		super(x, y);
		this.id = id;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if((player.getX() + left + right) == getX() && player.getY() + up + down == getY()) {
			// send the player to the other corresponding portal
			Dungeon dungeon = player.getDungeon();
			Portal otherPortal = dungeon.getOtherPortal(this);
			if(Objects.nonNull(otherPortal)) {
				player.x().set(otherPortal.getX());
				player.y().set(otherPortal.getY() + 1);
				
			}
			return true;
		} else {
			return true;
		}
	}
	
	public int getID() {
		return id;
	}
}

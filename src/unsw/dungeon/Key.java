package unsw.dungeon;

import java.util.Objects;

public class Key extends Entity implements EntityObserver {
	
	private int id;
	
	public Key(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }
	
	public int keyID() {
		return id;
	}
	
	public String name() {
		return "key";
	}
	
	public Boolean canBePickedUp() {
		return true;
	}
	
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		
		// move along with the player
		//x().s
		x().setValue(((Player) player).getX());
		y().setValue(((Player) player).getY());
	}
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(Objects.nonNull(player.getCarriedEntity()) && player.getCarriedEntity() instanceof Boulder) {
			return false;
		}
		return true;
	}
	
	public void pickedUp(Player player) {
		player.attach((EntityObserver)this);
		player.setState(player.getHasKeyState());
	}
	
}

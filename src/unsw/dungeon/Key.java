package unsw.dungeon;

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
	
	public void update(PlayerSubject player) {
		
		// move along with the player
		//x().s
		x().setValue(((Player) player).getX());
		y().setValue(((Player) player).getY());
	}
}

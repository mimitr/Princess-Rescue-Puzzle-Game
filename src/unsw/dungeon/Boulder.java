package unsw.dungeon;

public class Boulder extends Entity implements EntityObserver {
	
	public Boulder(int x, int y) {
        super(x, y);
    }
	
	public void update(PlayerSubject player) {
		x().setValue(((Player) player).getX());
		y().setValue(((Player) player).getY());
	}
	
    public String name() {
    	return "boulder";
    }
}

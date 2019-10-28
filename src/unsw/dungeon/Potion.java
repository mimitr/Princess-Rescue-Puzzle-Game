package unsw.dungeon;

public class Potion extends Entity implements EntityObserver {

	public Potion(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public String name() {
		return "potion";
	}
	
	public void update(PlayerSubject player) {
		
		// make the enemy run away from the player
		
		
	}

}

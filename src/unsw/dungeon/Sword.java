package unsw.dungeon;

public class Sword extends Entity implements EntityObserver {

	public Sword(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public String name() {
		return "sword";
	}
	
	public void update(PlayerSubject player) {
		
		// This can be picked up the player and used to kill enemies. 
		//Only one sword can be carried at once. Each sword is only capable of 5 hits and disappears after that. 
		//One hit of the sword is sufficient to destroy any enemy.
		
		
	}
}

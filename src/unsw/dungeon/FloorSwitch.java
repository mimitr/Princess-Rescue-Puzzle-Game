package unsw.dungeon;

import java.util.List;

public class FloorSwitch extends Entity implements EntityObserver {
	
	// Switches behave like empty squares, so other entities can appear on top of them. 
	// When a boulder is pushed onto a floor switch, it is triggered. 
	//  Pushing a boulder off the floor switch untriggers it.
	private Boolean triggered;
	
	public FloorSwitch(int x, int y) {
		super(x, y);
		setTriggered(false);
		
	}

	public Boolean getTriggered() {
		return triggered;
	}
	
	public Boolean canPlayerMove() {
		return true;
	}

	public void setTriggered(Boolean triggered) {
		this.triggered = triggered;
	}
	

	public void update(PlayerSubject player, int up, int down, int left, int right) {
		
	}
	
	public void checkBoulderOnTop(List<Boulder> boulders) {
		for (Boulder boulder : boulders) {
			System.out.println(boulder.getX());
			System.out.println(boulder.getY());
			if (boulder.getX() == getX() && boulder.getY() == getY()) {
				System.out.println("HAHAHAHAHAHA");
				triggered = true;
				break;
			}
		}
	}

}

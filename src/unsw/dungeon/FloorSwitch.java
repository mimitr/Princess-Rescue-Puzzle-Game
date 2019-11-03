package unsw.dungeon;

import java.util.List;

public class FloorSwitch extends Entity implements EntityObserver {

	private Boolean triggered;
	
	public FloorSwitch(int x, int y) {
		super(x, y);
		setTriggered(false);
		
	}

	public Boolean getTriggered() {
		return triggered;
	}
	
	public Boolean canPlayerMove() {
		return false;
	}

	public void setTriggered(Boolean triggered) {
		this.triggered = triggered;
	}
	

	public void update(PlayerSubject player, int up, int down, int left, int right) {
		
	}
	
	public void checkBoulderOnTop(List<Boulder> boulders, int x, int y) {
		for (Boulder boulder : boulders) {
			if (boulder.onSquare(x, y)) {
				triggered = true;
			}
		}
		triggered = false;
	}

}

package unsw.dungeon;


import java.util.List;

public class BoulderGoal implements GoalComponent {

	private List<FloorSwitch> floorSwitches;
	
	public BoulderGoal(List<FloorSwitch> floorSwitches) {
		this.floorSwitches = floorSwitches;
	}
	
	public Boolean completed(int x, int y) {
		Boolean completed = true;
    	for(FloorSwitch s : floorSwitches) {
    		if(!s.getTriggered()) {
    			completed = false;
    			break;
    		}
    	}
    	return completed;
	}
	
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}

package unsw.dungeon;

import java.util.List;

public class TreasureGoal implements GoalComponent {
	private List<Treasure> treasures;
	
	public TreasureGoal(List<Treasure> treasures) {
		this.treasures = treasures;
	}
	
	public Boolean completed(int x, int y) {
		Boolean completed = true;
    	for(Treasure treasure : treasures) {
    		if(!treasure.isPickedUp().getValue()) {
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
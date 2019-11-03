package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class CompositeGoal implements GoalComponent {
	private List<GoalComponent> goals;
	private Boolean andCondition;
	private Boolean completed;
	
	public CompositeGoal(Boolean andCondition) {
		goals = new ArrayList<>();
		completed = false;
		this.andCondition = andCondition;
	}
	
	public Boolean completed() {
		Boolean completed;
		if(andCondition) {
			completed = true;
			for(GoalComponent goal : goals) {
				if(!goal.completed()) {
					completed = false;
					break;
				}
			}
			return completed;
		} else {
			completed = false;
			for(GoalComponent goal : goals) {
				if(goal.completed()) {
					completed = true;
					break;
				}
			}
			return completed;
		}
		//return completed;
	}
	
	public Boolean addSubGoal(GoalComponent goal) {
		goals.add(goal);
		return true;
	}
}
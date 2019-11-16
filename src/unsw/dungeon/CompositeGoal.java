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
	
	public Boolean completed(int x, int y) {
		Boolean completed;
		if(andCondition) {
			completed = true;
			for(GoalComponent goal : goals) {
				if(!goal.completed(x, y)) {
					completed = false;
					break;
				}
			}
			return completed;
		} else {
			completed = false;
			for(GoalComponent goal : goals) {
				if(goal.completed(x, y)) {
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
	
	/*public String completedGoals() {
		for (GoalComponent goal : goals) {
			if (goal.)
		}
	}*/
}
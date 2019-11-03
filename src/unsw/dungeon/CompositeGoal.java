package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class CompositeGoal implements GoalComponent {
	/**
	 * a list of subgoals
	 */
	private List<GoalComponent> goals;
	
	/**
	 * true if the "AND" condition exists
	 */
	private Boolean andCondition;
	
	public CompositeGoal(Boolean andCondition) {
		goals = new ArrayList<>();
		this.andCondition = andCondition;
	}
	
	/**
	 * check if the composite goal has been completed
	 * @return true/ false
	 */
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
	}
	
	/**
	 * add the subgoal to the goal list
	 * @param goal
	 * @return true if was added successfully
	 */
	public Boolean addSubGoal(GoalComponent goal) {
		goals.add(goal);
		return true;
	}
}
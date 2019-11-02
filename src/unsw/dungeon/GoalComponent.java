package unsw.dungeon;

public interface GoalComponent {
	public Boolean completed();
	public Boolean addSubGoal(GoalComponent goal);
}

package unsw.dungeon;

public interface GoalComponent {
	public Boolean completed(int x, int y);
	public Boolean addSubGoal(GoalComponent goal);
}

package unsw.dungeon;

public class ExitGoal implements GoalComponent {
	private Dungeon dungeon;
	
	public ExitGoal(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	public Boolean completed() {
		return dungeon.exitGoalCompleted();
	}
	
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}

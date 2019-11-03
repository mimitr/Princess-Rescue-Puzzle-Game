package unsw.dungeon;

public class BoulderGoal implements GoalComponent {

	private Dungeon dungeon;
	
	public BoulderGoal(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	public Boolean completed() {
		return dungeon.boulderGoalCompleted();
	}
	
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}

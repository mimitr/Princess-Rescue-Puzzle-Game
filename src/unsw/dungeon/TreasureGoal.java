package unsw.dungeon;

public class TreasureGoal implements GoalComponent {
	private Dungeon dungeon;
	
	public TreasureGoal(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	public Boolean completed() {
		return dungeon.treasureGoalCompleted();
	}
	
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}
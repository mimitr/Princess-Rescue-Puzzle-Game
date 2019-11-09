package unsw.dungeon;

public class EnemyGoal implements GoalComponent {
	private Boolean completed;
	private Dungeon dungeon;
	public EnemyGoal(Dungeon dungeon) {
		this.dungeon = dungeon;
		completed = true;
	}
	@Override
	public Boolean completed() {
		return dungeon.enemyGoalCompleted();
	}
	@Override
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}

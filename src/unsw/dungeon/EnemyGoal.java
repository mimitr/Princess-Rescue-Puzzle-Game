package unsw.dungeon;

<<<<<<< HEAD

=======
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
>>>>>>> 0e2eb668f895b93789111969a7476df55da3e491

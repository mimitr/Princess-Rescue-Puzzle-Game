package unsw.dungeon;

import java.util.List;

public class EnemyGoal implements GoalComponent {
	private Boolean completed;
	private List<Enemy> enemies;
	public EnemyGoal(List<Enemy> enemies) {
		this.enemies = enemies;
		completed = true;
	}
	@Override
	public Boolean completed(int x, int y) {
		Boolean completed = true;
    	for(Enemy enemy : enemies) {
    		if(enemy.stillAlive().getValue()) {
    			completed = false;
    			break;
    		}
    	}
    	return completed;
	}
	@Override
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}

package unsw.dungeon;

public class ExitGoal implements GoalComponent {
	private Exit exit;
	
	public ExitGoal(Exit exit) {
		this.exit = exit;
	}
	
	public Boolean completed(int x, int y) {
		Boolean completed = true;
    	if(!(exit.getX() == x) || !(exit.getY() == y)) {
    		completed = false;
    	}
    	return completed;
	}
	
	public Boolean addSubGoal(GoalComponent goal) {
		return false;
	}
}

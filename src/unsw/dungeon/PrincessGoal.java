package unsw.dungeon;

public class PrincessGoal implements GoalComponent {
	
	private Princess princess;
	
	public PrincessGoal(Princess princess) {
		this.princess = princess;
	}
	
	@Override
	public Boolean completed(int x, int y) {
		if (princess.FoundLove().getValue())
			return true;
		return false;
	}

	@Override
	public Boolean addSubGoal(GoalComponent goal) {
		return null;
	}


}

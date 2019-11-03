package unsw.dungeon;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask; 

public class Potion extends Entity implements EntityObserver{

	private int counter;
	private Dungeon dungeon;
	
	public Potion(Dungeon dungeon, int x, int y) {
		super(x, y);
		counter = 10;
		this.dungeon = dungeon;
	}

	@Override
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		// move along with the player
		if (counter != 0) {
			x().setValue(((Player) player).getX());
			y().setValue(((Player) player).getY());
			counter--;
		} else {
			((Player) player).setState(new NoWeapon((Player) player));
			dungeon.enemyMoveTowards();
		}		
	}
	
	public void decreaseCount() {
		counter--;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		if(Objects.nonNull(player.getCarriedEntity()) && player.getCarriedEntity() instanceof Boulder) {
			return false;
		}
		return true;
	}
	
	public Boolean canBePickedUp() {
		return true;
	}
	
	public Boolean canEnemyMove() {
		return false;
	}

	public void pickedUp(Player player) {
		player.attach((EntityObserver)this);
		player.setState(player.getHasPotionState());
		dungeon.enemyMoveAway();
	}
}

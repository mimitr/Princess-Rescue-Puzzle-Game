package unsw.dungeon;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask; 

public class Potion extends Entity implements EntityObserver{

	//If the player picks this up they become invincible to enemies. 
	// Colliding with an enemy should result in their immediate destruction. 
	//Because of this, all enemies will run away from the player when they are invincible. 
	//The effect of the potion only lasts a limited time.
	
	static Timer timer;
	static int interval;
	
	public Potion(int x, int y) {
		super(x, y);
		interval = 10;
		timer = new Timer();
	}

	@Override
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		// move along with the player
			x().setValue(((Player) player).getX());
			y().setValue(((Player) player).getY());
		
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

	public void pickedUp(Player player) {
		player.attach((EntityObserver)this);
		player.setState(player.getHasPotionState());
	}
}

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
		int delay = 1000;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				x().setValue(((Player) player).getX());
				y().setValue(((Player) player).getY());	
	            System.out.println(setInterval());
	        }
		}, delay, interval);
		
	}
	
	private static final int setInterval() {
	    if (interval == 1)
	        timer.cancel();
	    return --interval;
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


}

package unsw.dungeon;

import java.util.Objects;

public class Boulder extends Entity implements EntityObserver {
	
	private Boolean canMoveFurther = false;
	
	public Boulder(int x, int y) {
        super(x, y);
    }
	
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		x().setValue(getX() + left + right);
		y().setValue(getY() + up + down);
	}
	
	public Boolean canMoveFurther() {
		return canMoveFurther;
	}
	
    public String name() {
    	return "boulder";
    }
    
    public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
    	Boolean canMove = false;
    	if(Objects.isNull(player.getCarriedEntity())) {
    		player.attach((EntityObserver)this);
    		if(player.canMove(getX() + left + right, getY() + up + down, 0, 0, 0, 0)) {
    			//player.attach((EntityObserver)this);
    			canMove = true;
    			player.notifyEntity(up, down, left, right);
				player.getDungeon().notifySwitch();
    		}
    		player.detach();
    	}
    	return canMove;
    }
    
    public Boolean canEnemyMove() {
    	return false;
    }
}

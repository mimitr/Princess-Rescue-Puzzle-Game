package unsw.dungeon;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
	private int id;
	private Boolean open;
	public Door(int x, int y, int id) {
        super(x, y);
        this.id = id;
        open = false;
    }
	
	public int doorID() {
		return id;
	}
	
    public String name() {
    	return "door";
    }
    
    public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
    	// determine player state
    	Boolean canMove = true;
    	if(!open) {
	    	if(player.getCurrState().equals(player.getHasKeyState())) {
	    		Key key = (Key) player.getCarriedEntity();
	    		if(key.keyID() == id) {
	        		open = true;
	        		player.detach();
	        		player.setState(player.getNoWeaponState());
	        	} else {
	        		canMove = false;
	        	}
	    		
	    	} else {
	    		canMove = false;
	    	}
    	}
    	return canMove;
    }
    
    public Boolean isOpen() {
    	return open;
    }
}

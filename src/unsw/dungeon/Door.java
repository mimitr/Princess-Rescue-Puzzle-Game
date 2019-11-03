package unsw.dungeon;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
	/**
	 * each door has an ID
	 */
	private int id;
	
	/**
	 * true if the door is opened
	 * false otherwise
	 */
	private Boolean open;
	public Door(int x, int y, int id) {
        super(x, y);
        this.id = id;
        open = false;
    }
	
	/**
	 * get the door ID
	 * @return id
	 */
	public int doorID() {
		return id;
	}
	
    public String name() {
    	return "door";
    }
    
    /**
     * check if the player can more through the door
     * the player can only move through if he carries the key with the matching ID
     * @param player
     * @param up
     * @param down
     * @param left
     * @param right
     * 
     * @return true/ false
     * 
     */
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

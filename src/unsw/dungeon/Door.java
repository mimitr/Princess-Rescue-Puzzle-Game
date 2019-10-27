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
        //open.setValue(false);
    }
	
	/*
	public boolean keyMatch(int keyId) {
		if(keyId == id) {
			//open.setValue(true);
			return true;
			// change door states to open
		}
		return false;
	}
	*/
	public int doorID() {
		return id;
	}
	
    public String name() {
    	return "door";
    }
    
    public void meetPlayer(Player player) {
    	// determine player state
    	if(!open) {
	    	if(player.getCurrState().equals(player.getHasKeyState())) {
	    		Entity entity = (Entity) player.getCarriedEntity();
	    		if(entity.name().equals("key")) {
	    			Key key = (Key) entity;
		        	if(key.keyID() == id) {
		        		open = true;
		        		// detach the key from the player
		        		player.getCurrState().putDown();
		        		// change closed door image to open door image
		        		// How to change the image?
		        	}
	    		}
	    	}
    	}
    }
    
    public Boolean isOpen() {
    	return open;
    }
}

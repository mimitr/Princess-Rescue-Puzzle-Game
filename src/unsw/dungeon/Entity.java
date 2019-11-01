package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everestca
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
   
    public Boolean onSquare(int x, int y) {
    	//System.out.println("on square");
    	//System.out.println(name());
    	if(x == x().get() && y == y().get()) {
    		return true;
    	}
    	//System.out.println("return false");
    	return false;
    }
    
    public String name() {
    	return "";
    }
    
    public Boolean canBePickedUp() {
    	return false;
    }
    
    public void meetPlayer(Player player) {
    	System.out.println("Meet player");
    }
    
    public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
    	return true;
    }
    
    public void pickedUp(Player player) {
    	System.out.println("Picked up by player");
    }
}

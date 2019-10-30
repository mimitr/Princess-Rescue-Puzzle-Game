gpackage unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public Entity getEntityOnSquare(int x, int y) {
    	//System.out.println("inside get entity on square");
    	Entity e = null;
    	for(Entity entity : entities) {
    		//System.out.println(entity.name());
    		if(entity.onSquare(x, y) && !entity.equals(player)) {
    			System.out.println(entity.name());
    			e = entity;
    			break;
    		}
    	}
    	//System.out.println("entity is null");
    	return e;
    }
    
}

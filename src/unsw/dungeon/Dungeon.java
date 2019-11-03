package unsw.dungeon;

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
    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<Boulder> boulders;
    private List<Portal> portals;
    private List<FloorSwitch> floorSwitches;
    private List<Treasure> treasures;
    private Exit exit;
    private Player player;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.walls = new ArrayList<>();
        this.boulders = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.floorSwitches = new ArrayList<>();
        this.treasures = new ArrayList<>();
        this.exit = null;
    }

    /**
     * add an exit to the exit list
     * @param exit
     */
    public void addExit(Exit exit) {
    	this.exit = exit;
    }
    
    /**
     * add a treasure to the treasure list
     * @param treasure
     */
    public void addTreasure(Treasure treasure) {
    	treasures.add(treasure);
    }
    
    /**
     * add a wall to the wall list
     * @param wall
     */
    public void addWall(Wall wall) {
    	walls.add(wall);
    }
    
    /**
     * add a boulder to the boulder list
     * @param boulder
     */
    public void addBoulder(Boulder boulder) {
    	boulders.add(boulder);
    }
    
    /**
     * add an enemy to the enemy list
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
    	enemies.add(enemy);
    }
    
    /**
     * add a portal to the portal list
     * @param portal
     */
    public void addPortal(Portal portal) {
    	portals.add(portal);
    }
    
    /**
     * add a switch to the switch list
     * @param floorSwitch
     */
    public void addSwitch(FloorSwitch floorSwitch) {
    	floorSwitches.add(floorSwitch);
    }
    
    /**
     * 
     * @return the width of the dungeon
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @return the height of the dungeon
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * set the player
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

	/**
	 * add an entity into the entity list
	 * @param entity
	 */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return the list of entities in the specific square
     */
    public List<Entity> getEntityOnSquare(int x, int y) {

    	List<Entity> e = new ArrayList<>();
    	for(Entity entity : entities) {
    		if(entity.onSquare(x, y) && !entity.equals(player)) {
    			e.add(entity);
    		}
    	}
    	return e;
    }
    
    /**
     * get the other corresponding portal with the same ID
     * @param portal
     * @return the other portal
     */
    public Portal getOtherPortal(Portal portal) {
    	Portal otherPortal = null;
 
    	for(Portal other : portals) {
    		if(!other.equals(portal) && other.getID() == portal.getID()) {
    			otherPortal = other;
    			break;
    		}
    	}
    	return otherPortal;
    }
    
    public void notifySwitch() {
    	for(FloorSwitch floorSwitch : floorSwitches) {
    		floorSwitch.checkBoulderOnTop(boulders, floorSwitch.getX(), floorSwitch.getY());
    	}
    }
    
    public int getTotalTreasureAmount() {
    	int total = 0;
    	for(Entity entity : entities) {
    		if(entity instanceof Treasure) {
    			total = total + 1;
    		}
    	}
    	return total;
    }
    
    public void enemyMoveAway() {
    	for(Enemy enemy : enemies) {
    		EnemyMoveStrategy newStrategy = new MoveAway();
    		enemy.setStrategy(newStrategy);
    	}
    }
    
    public void enemyMoveTowards() {
    	for(Enemy enemy : enemies) {
    		EnemyMoveStrategy newStrategy = new ApproachPlayer();
    		enemy.setStrategy(newStrategy);
    	}
    }
    
    public void enemyMove() {
    	for(Enemy enemy : enemies) {
    		enemy.move(player);
    	}
    }
    
    public Boolean treasureGoalCompleted() {
    	Boolean completed = true;
    	for(Treasure treasure : treasures) {
    		if(!treasure.isPickedUp()) {
    			completed = false;
    			break;
    		}
    	}
    	return completed;
    }
    
    public Boolean exitGoalCompleted() {
    	Boolean completed = true;
    	if(!(exit.getX() == player.getX()) || !(exit.getY() == player.getY())) {
    		completed = false;
    	}
    	return completed;
    }
    
    public Boolean boulderGoalCompleted() {
    	Boolean completed = true;
    	for(FloorSwitch s : floorSwitches) {
    		if(!s.getTriggered()) {
    			completed = false;
    			break;
    		}
    	}
    	return completed;
    }
    
    public Boolean enemyGoalCompleted() {
    	Boolean completed = true;
    	for(Enemy enemy : enemies) {
    		if(enemy.stillAlive()) {
    			completed = false;
    			break;
    		}
    	}
    	return completed;
    }
}

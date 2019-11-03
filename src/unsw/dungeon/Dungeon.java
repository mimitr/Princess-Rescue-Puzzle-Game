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
    }

    public void addWall(Wall wall) {
    	walls.add(wall);
    }
    
    public void addBoulder(Boulder boulder) {
    	boulders.add(boulder);
    }
    
    public void addEnemy(Enemy enemy) {
    	enemies.add(enemy);
    }
    
    public void addPortal(Portal portal) {
    	portals.add(portal);
    }
    
    public void addSwitch(FloorSwitch floorSwitch) {
    	floorSwitches.add(floorSwitch);
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
    
    public List<Entity> getEntityOnSquare(int x, int y) {
    	//System.out.println("inside get entity on square");
    	List<Entity> e = new ArrayList<>();
    	for(Entity entity : entities) {
    		//System.out.println(entity.name());
    		if(entity.onSquare(x, y) && !entity.equals(player)) {
    			System.out.println(entity.name());
    			e.add(entity);
    			//break;
    		}
    	}
    	//System.out.println("entity is null");
    	return e;
    }
    
    public Portal getOtherPortal(Portal portal) {
    	Portal otherPortal = null;
    	/*
    	for(Entity entity : entities) {
    		if(entity.getClass().equals(portal.getClass()) && !portal.equals((Portal)entity) && portal.getID() == ((Portal)entity).getID()) {
    			otherPortal = (Portal) entity;
    			break;
    		}
    	}
    	*/
    	for(Portal other : portals) {
    		if(!other.equals(portal) && other.getID() == portal.getID()) {
    			System.out.println(other.getX());
    			System.out.println(other.getY());
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
    
    /*public Boolean getEntityOnSwitch(int x, int y)  {
    	for (FloorSwitch floorSwitch : floorSwitches) {
    		if ((floorSwitch.getX() == x && floorSwitch.getY() == y) {
    			
    		}
    	}
    }*/
    
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
}

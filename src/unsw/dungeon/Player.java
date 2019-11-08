package unsw.dungeon;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements PlayerSubject, PlayerState {

    private Dungeon dungeon;
    private EntityObserver carriedEntity;
    private PlayerState noWeaponState;
    private PlayerState hasSwordState;
    private PlayerState hasPotionState;
    private PlayerState hasKeyState;
    private PlayerState currState = noWeaponState;
    private int treasureAmount;
    private GoalComponent goal;
    private Boolean alive;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        carriedEntity = null;
        noWeaponState = new NoWeapon(this);
        hasSwordState = new HasSword(this);
        hasPotionState = new HasPotion(this);
        hasKeyState = new HasKey(this);
        currState = noWeaponState;
        treasureAmount = 0;
        alive = true;
    }
    
    public void setGoal(GoalComponent goal) {
    	this.goal = goal;
    }
    
    public Boolean goalCompleted() {
    	if(Objects.nonNull(goal)) {
    		return goal.completed();
    	}
    	return true;
    }
    public Boolean stillAlive() {
    	return alive;
    }
    
    public void setAlive(Boolean alive) {
    	this.alive = alive;
    }
    public PlayerState getCurrState() {
    	return currState;
    }
    
    public void setState(PlayerState state) {
    	this.currState = state;
    }

    public PlayerState getNoWeaponState() {
    	return noWeaponState;
    }
    
    public PlayerState getHasSwordState() {
    	return hasSwordState;
    }
    
    public PlayerState getHasPotionState() {
    	return hasPotionState;
    }
    
    public PlayerState getHasKeyState() {
    	return hasKeyState;
    }
    
    public void pickUp(Entity entity) {
    	System.out.println("555");
    	currState.pickUp(entity);
    }
    
    public void putDown() {
    	currState.putDown();
    }
    
    public Boolean activeWeapon() {
    	return currState.activeWeapon();
    }
    
    
    public Boolean killEnemy() {
    	return currState.killEnemy();
    }
    
    public void moveUp() {
    	if(alive) {
	    	if(getY() > 0 && canMove(x().get(), y().get() - 1, -1, 0, 0, 0)) {
	    		y().set(getY() - 1);
	    	}
	    	dungeon.enemyMove();
	    	checkSameSquare();
	    	if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(1,0,0,0);
	    	}
	    	if(goalCompleted()) {
	        	System.out.println("Goal completed :)");
	        } else {
	        	System.out.println("Goal not completed :(");
	        }
    	}
    }

    public void moveDown() {
    	if(alive) {
	        if ((getY() < dungeon.getHeight() - 1) && canMove(x().get(), y().get() + 1, 0, 1, 0, 0)) {
	            y().set(getY() + 1);
	        }
	        dungeon.enemyMove();
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,1,0,0);
	    	}
	        if(goalCompleted()) {
	        	System.out.println("Goal completed :)");
	        } else {
	        	System.out.println("Goal not completed :(");
	        }
    	}
    }

    public void moveLeft() {
    	if(alive) {
	        if (getX() > 0 && canMove(getX() - 1, getY(), 0, 0, -1, 0)) {
	            x().set(getX() - 1);
	        }
	        dungeon.enemyMove();
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,0,-1,0);
	    	}
	        if(goalCompleted()) {
	        	System.out.println("Goal completed :)");
	        } else {
	        	System.out.println("Goal not completed :(");
	        }
    	}
    }

    public void moveRight() {
    	if(alive) {
	        if (getX() < dungeon.getWidth() - 1 && canMove(getX() + 1, getY(), 0, 0, 0, 1)) {
	            x().set(getX() + 1);
	        }
	        dungeon.enemyMove();
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,0,0,1);
	    	}
	        if(goalCompleted()) {
	        	System.out.println("Goal completed :)");
	        } else {
	        	System.out.println("Goal not completed :(");
	        }
    	}
    }
    
    public boolean canMove(int x, int y, int up, int down, int left, int right) {   	
    	List<Entity> entities = new ArrayList<>();
    	Boolean canMove = true;
		entities = dungeon.getEntityOnSquare(x, y);
		
		if(!entities.isEmpty()) {
			for(Entity entity : entities) {
				if(!entity.canPlayerMove(this, up, down, left, right)) {
					canMove = false;
					break;
				}
			}
		}
		
		return canMove;
    }
    
    public String name() {
    	return "player";
    }
    
    public void attach(EntityObserver entity) {
    	carriedEntity = entity;
    }
    
    public void detach() {
    	if(Objects.nonNull(carriedEntity)) {
    		carriedEntity = null;
    	}
    }
    
    public void notifyEntity(int up, int down, int left, int right) {
    	// tell the carried entity to move with the player
    	carriedEntity.update(this, up, down, left, right);
    }
    
    public EntityObserver getCarriedEntity() {
    	return carriedEntity;
    }
    
     
    public void checkSameSquare() {
    	// check if there is any entity on the same square as the player
    	System.out.println("Checking same square");
    	List<Entity> entities = new ArrayList<>();
    	entities = dungeon.getEntityOnSquare(getX(), getY());
    	if(!entities.isEmpty()) {
    		for(Entity entity : entities) {
	    		if(entity.canBePickedUp()) {
	    			System.out.println("can be picked up");
	    			pickUp(entity);
	    		} else {
	    			System.out.println("hello");
	    			entity.canPlayerMove(this, 0, 0, 0, 0);
	    		}
    		}
    	}
    }
    
    public void increaseTreasureAmount() {
    	treasureAmount += 1;
    }
    
    public int treasureAmount() {
    	return treasureAmount;
    }
    
    public Dungeon getDungeon() {
    	return dungeon;
    }
}


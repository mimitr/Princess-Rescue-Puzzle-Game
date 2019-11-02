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
    	//notifyEntity();
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
    	// check the next square contains any entity
    	// wall, door, boulder
    	if(alive) {
	    	if(getY() > 0 && canMove(x().get(), y().get() - 1, -1, 0, 0, 0)) {
	    		y().set(getY() - 1);
	    	}
	    	checkSameSquare();
	    	if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(1,0,0,0);
	    	}
	    	dungeon.enemyMove();
    	}
        // after move to the next square, check if there is any entity0  
    }

    public void moveDown() {
    	if(alive) {
	        if ((getY() < dungeon.getHeight() - 1) && canMove(x().get(), y().get() + 1, 0, 1, 0, 0))
	            y().set(getY() + 1);
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,1,0,0);
	    	}
	        dungeon.enemyMove();
    	}
    }

    public void moveLeft() {
    	if(alive) {
	        if (getX() > 0 && canMove(getX() - 1, getY(), 0, 0, -1, 0)) {
	            x().set(getX() - 1);
	            dungeon.enemyMove();
	        }
	        //System.out.println("Inside move left");
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,0,-1,0);
	    	}
	        //dungeon.enemyMove();
    	}
    }

    public void moveRight() {
    	if(alive) {
	        if (getX() < dungeon.getWidth() - 1 && canMove(getX() + 1, getY(), 0, 0, 0, 1)) {
	            x().set(getX() + 1);
	            dungeon.enemyMove();
	        }
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,0,0,1);
	    	}
    	}
    }
    
    public boolean canMove(int x, int y, int up, int down, int left, int right) {
    	//System.out.println("inside can move");
    	List<Entity> entities = new ArrayList<>();
    	Boolean canMove = true;
    	//System.out.println(dungeon);
		entities = dungeon.getEntityOnSquare(x, y);
		//System.out.println(entity);
		if(!entities.isEmpty()) {
			//System.out.println("shouldn't be here");
			for(Entity entity : entities) {
				if(!entity.canPlayerMove(this, up, down, left, right)) {
					canMove = false;
					break;
				}
				//canMove = entity.canPlayerMove(this, up, down, left, right);
			}
		}
			/*
			switch(entity.name()) {
		###	case "door":
				// check if the door is closed or open
				Door door = (Door) entity;
				canMove = door.canPlayerMove(this);
				if(!door.isOpen()) {
					canMove = false;
				}
				if(Objects.nonNull(carriedEntity) && ((Entity)carriedEntity).name().equals("boudler")) {
					canMove = false;
				}
				break;
			case "boulder":
				// make the boulder to move with the player in the same direction
				// notify boulder here
				System.out.println("Next square contains a boulder");
				if(Objects.isNull(carriedEntity)) {
					System.out.println("carrying nothing");
					attach((EntityObserver)entity);
					//if(!((Boulder)entity).canMoveFurther()) {
					//	canMove = false;
					//}
					canMove = canMove(getX() + left + right, getY() + up + down, 0, 0, 0, 0);
					if(canMove) {
						System.out.println("can move");
						notifyEntity(up, down, left, right);
					}
					// check if the boulder can move
					detach();
				} else {
					canMove = false;
				}
				System.out.println("After checking next square of the boulder");
				//canMove = false;
				break;
		###	case "wall":
				//System.out.println("there is a wall");
				canMove = false;
				break;
		###	case "key":
				System.out.println("Next square contains a key");
				System.out.println("Carrying " + carriedEntity);
				if(Objects.nonNull(carriedEntity) && ((Entity)carriedEntity).name().equals("boulder")) {
					canMove = false;
				}
				// add key to player's bag
				break;
			case "sword":
				break;
			case "potion":
				break;
			case "portal":
				break;
			case "exit"	:
				break;
			}
		}
		if(entity == null) {
			System.out.println("null null");
		}
		*/
		//System.out.println("can move");
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
    		//System.out.println("Entity on the same square is " + entity);
    		for(Entity entity : entities) {
	    		if(entity.canBePickedUp()) {
	    			System.out.println("can be picked up");
	    			pickUp(entity);
	    			break;
	    		} else {
	    			entity.canPlayerMove(this, 0, 0, 0, 0);
	    			//entity.canEnemyMove();
	    		}
    		}
    	}
    	
    	/*
    	if(Objects.nonNull(entity)) {
    		System.out.println("Entity on the same square is " + entity);
    		if(entity.canBePickedUp()) {
    			System.out.println("can be picked up");
    			pickUp(entity);
    		} else {
    			entity.canPlayerMove(this, 0, 0, 0, 0);
    			//entity.canEnemyMove();
    		}
    	}
    	*/
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


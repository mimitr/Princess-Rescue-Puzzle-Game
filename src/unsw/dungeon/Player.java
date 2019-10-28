package unsw.dungeon;

import java.util.Objects;
import java.util.ArrayList;
/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements PlayerSubject, PlayerState {

    private Dungeon dungeon;
    private ArrayList<Entity> bag;
    private EntityObserver carriedEntity;
    private PlayerState noWeaponState;
    private PlayerState hasSwordState;
    private PlayerState hasPotionState;
    private PlayerState hasKeyState;
    private PlayerState currState = noWeaponState;
    private int treasureAmount;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.bag = new ArrayList<Entity>();
        carriedEntity = null;
        noWeaponState = new NoWeapon(this);
        hasSwordState = new HasSword(this);
        hasPotionState = new HasPotion(this);
        hasKeyState = new HasKey(this);
        currState = noWeaponState;
        treasureAmount = 0;
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
    	currState.pickUp(entity);
    }
    
    public void putDown() {
    	currState.putDown();
    }
    
    public Boolean activeWeapon() {
    	return currState.activeWeapon();
    }
    
    public void killEnemy() {
    	currState.killEnemy();
    }
    
    public void moveUp() {
    	// check the next square contains any entity
    	// wall, door, boulder
    	if(getY() > 0 && canMove(x().get(), y().get() - 1, -2, 0, 0, 0)) {
    		y().set(getY() - 1);
    	}
    	checkSameSquare();
    	if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
    		notifyEntity(1,0,0,0);
    	}
        // after move to the next square, check if there is any entity0  
    }

    public void moveDown() {
        if ((getY() < dungeon.getHeight() - 1) && canMove(x().get(), y().get() + 1, 0, 2, 0, 0))
            y().set(getY() + 1);
        checkSameSquare();
        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
    		notifyEntity(0,1,0,0);
    	}
    }

    public void moveLeft() {
        if (getX() > 0 && canMove(getX() - 1, getY(), 0, 0, -2, 0))
            x().set(getX() - 1);
        //System.out.println("Inside move left");
        checkSameSquare();
        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
    		notifyEntity(0,0,-1,0);
    	}
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && canMove(getX() + 1, getY(), 0, 0, 0, 2))
            x().set(getX() + 1);
        checkSameSquare();
        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
    		notifyEntity(0,0,0,1);
    	}
    }
    
    public boolean canMove(int x, int y, int up, int down, int left, int right) {
    	//System.out.println("inside can move");
    	Entity entity = null;
    	Boolean canMove = true;
    	//System.out.println(dungeon);
		entity = dungeon.getEntityOnSquare(x, y);
		//System.out.println(entity);
		if(Objects.nonNull(entity)) {
			//System.out.println("shouldn't be here");
			switch(entity.name()) {
			case "door":
				// check if the door is closed or open
				Door door = (Door) entity;
				door.meetPlayer(this);
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
			case "wall":
				//System.out.println("there is a wall");
				canMove = false;
				break;
			case "key":
				System.out.println("Next square contains a key");
				System.out.println("Carrying " + carriedEntity);
				if(Objects.nonNull(carriedEntity) && ((Entity)carriedEntity).name().equals("boulder")) {
					canMove = false;
				}
				// add key to player's bag
				break;
			}
		}
		if(entity == null) {
			System.out.println("null null");
		}
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
    	Entity entity = null;
    	entity = dungeon.getEntityOnSquare(getX(), getY());
    	if(Objects.nonNull(entity)) {
    		if(entity.canBePickedUp()) {
    			pickUp(entity);
    		} else {
    			/*
    			if(entity.name().equals("enemy")) {
    				killEnemy();
    			}
    			if(entity.name().equals("exit")) {
    				// check if the player has finished the goal
    			}	
    			if(entity.name().equals("portal")) {
    				// find the corresponding portal in dungeon's entities list
    				// and the player should disappear from the current location and appear at the corresponding portal
    			}
    			*/
    			entity.meetPlayer(this);
    		}
    	}
    }
    
    public void increaseTreasureAmount() {
    	treasureAmount += 1;
    }
    
    public int treasureAmount() {
    	return treasureAmount;
    }
}


package unsw.dungeon;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    private IntegerProperty treasureAmount;
    private GoalComponent goal;
    private BooleanProperty alive;
    private BooleanProperty goalCompleted;
    private BooleanProperty foundLove;

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
        treasureAmount = new SimpleIntegerProperty(0);
        alive = new SimpleBooleanProperty(true);
        goalCompleted = new SimpleBooleanProperty(false);
        foundLove = new SimpleBooleanProperty(false);
    }
    
    public void setFoundLove(Boolean b) {
    	foundLove.setValue(b);
    }
    
    public BooleanProperty FoundLove() {
    	return foundLove;
    }
    
    public void setGoal(GoalComponent goal) {
    	this.goal = goal;
    }
    
    public GoalComponent getGoal() {
    	return goal;
    }
    
    public BooleanProperty getAliveProperty() {
    	return alive;
    }
    
    public BooleanProperty goalCompleted() {
    	if(Objects.nonNull(goal)) {
    		goalCompleted.setValue(goal.completed(getX(), getY()));
    		//return goal.completed();
    	}
    	return goalCompleted;
    }
    public Boolean stillAlive() {
    	return alive.getValue();
    }
    
    public void setAlive(Boolean alive) {
    	System.out.println("Player will be " + alive);
    	this.alive.setValue(alive);
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
    	if(stillAlive()) {
	    	if(getY() > 0 && canMove(x().get(), y().get() - 1, -1, 0, 0, 0)) {
	    		y().set(getY() - 1);
	    	}
	    	dungeon.enemyMove();
	    	checkSameSquare();
	    	if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(1,0,0,0);
	    	}
    	}
        // after move to the next square, check if there is any entity0  
    }

    public void moveDown() {
    	if(stillAlive()) {
    		System.out.println(alive.getValue());
	        if ((getY() < dungeon.getHeight() - 1) && canMove(x().get(), y().get() + 1, 0, 1, 0, 0))
	            y().set(getY() + 1);
	        dungeon.enemyMove();
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,1,0,0);
	    	}
	        
    	}
    }

    public void moveLeft() {
    	if(stillAlive()) {
	        if (getX() > 0 && canMove(getX() - 1, getY(), 0, 0, -1, 0)) {
	            x().set(getX() - 1);
	            //dungeon.enemyMove();
	        }
	        dungeon.enemyMove();
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,0,-1,0);
	    	}
    	}
    }

    public void moveRight() {
    	if(stillAlive()) {
	        if (getX() < dungeon.getWidth() - 1 && canMove(getX() + 1, getY(), 0, 0, 0, 1)) {
	            x().set(getX() + 1);
	        }
	        dungeon.enemyMove();
	        checkSameSquare();
	        if(Objects.nonNull(carriedEntity) && !((Entity) carriedEntity).name().equals("boulder")) {
	    		notifyEntity(0,0,0,1);
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
    	List<Entity> entities = new ArrayList<>();
    	entities = dungeon.getEntityOnSquare(getX(), getY());
    	if(!entities.isEmpty()) {
    		
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
    }
    
    public void increaseTreasureAmount() {
    	int treasureAmountInt = treasureAmount.get();
    	treasureAmountInt += 1;
    	treasureAmount.set(treasureAmountInt);
    }
    
    public IntegerProperty treasureAmount() {
    	return treasureAmount;
    }
    
    public Dungeon getDungeon() {
    	return dungeon;
    }
}


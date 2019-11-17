package unsw.dungeon;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    
    //Images
    private Image playerImage;
    private Image wallImage;
    private Image keyImage;
    private Image closedDoorImage;
    private Image openDoorImage;
    private Image swordImage;
    private Image potionImage;
    private Image treasureImage;
    private Image boulderImage;
    private Image portalImage;
    private Image enemyImage;
    private Image switchImage;
    private Image exitImage;
    private Image princessImage;
    private Image grassImage;
    private Image heartImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/prince.png");
        wallImage = new Image("/shell_wall.png");
        keyImage = new Image("/key.png");
        closedDoorImage = new Image("/closed_door.png");
        openDoorImage = new Image("/open_door.png");
        swordImage = new Image("/greatsword_1_new.png");
        potionImage = new Image("/bubbly.png");
        treasureImage = new Image("/treasure.png");
        boulderImage = new Image("/boulder.png");
        portalImage = new Image("/portal.png");
        enemyImage = new Image("/enemy.png");
        switchImage = new Image("pressure_plate.png");
        exitImage = new Image("/exit.png");
        princessImage = new Image("/princess.png");
        grassImage = new Image("/grass.png");
        heartImage = new Image("/heart.png");
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        ((Player) player).FoundLove().addListener((observable, oldValue, newValue) -> {
        	view.setImage(heartImage);
        });
        //player.getAliveProperty();
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }
    
    @Override
    public void onLoad(Door door) {
    	ImageView view = new ImageView(closedDoorImage);
    	view.setId("doorImage");
    	door.isOpen().addListener((observable, oldValue, newValue) -> {
        	view.setImage(openDoorImage);
        	System.out.println("Door is now open");
        });
        addEntity(door, view);
    }
    
    @Override
    public void onLoad(Key key) {
    	ImageView view = new ImageView(keyImage);
    	view.setId("keyImage");
    	key.functional().addListener((observable, oldValue, newValue) -> {
        	view.setVisible(false);
        });
        addEntity(key, view);
    }
    
    @Override
    public void onLoad(Princess princess) {
    	ImageView view = new ImageView(princessImage);
    	princess.FoundLove().addListener((observable, oldValue, newValue) -> {
        	view.setVisible(false);	
        });
        addEntity(princess, view);
    }
    
    @Override
    public void onLoad(Grass grass) {
    	ImageView view = new ImageView(grassImage);
    	view.setId("grassImage");
    	grass.shouldAppear().addListener((observable, oldValue, newValue) -> {
    		if(grass.shouldAppear().getValue()) {
    			view.setVisible(true);
    		} else {
    			view.setVisible(false);
    		}
        });
    	KeyFrame k = new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				if(grass.shouldAppear().getValue()) {
					grass.setAppear(false);
				} else {
					grass.setAppear(true);
				}
			}
		});
    	
    	Timeline t = new Timeline(k);
    	t.setCycleCount(Animation.INDEFINITE);
		t.play();
        addEntity(grass, view);
    }
    
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        view.setId("swordImage");
        sword.functional().addListener((observable, oldValue, newValue) -> {
        	view.setVisible(false);
        });
        addEntity(sword, view);
    }
    
    
    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        view.setId("potionImage");
        potion.functional().addListener((observable, oldValue, newValue) -> {
        	view.setVisible(false);
        });
        addEntity(potion, view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        view.setId("treasureImage");
        treasure.isPickedUp().addListener((observable, oldValue, newValue) -> {
        	view.setVisible(false);
        });
        addEntity(treasure, view);
    }
    
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        view.setId("enemyImage");
        enemy.stillAlive().addListener((observable, oldValue, newValue) -> {
        	view.setVisible(false);
        });
        addEntity(enemy, view);
    }
    
    
    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);
    }
    
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }
    
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }
    
    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }


}

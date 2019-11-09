package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    
    private Stage stage;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        /*
        completedProperty.bind(initValueProperty.isEqualTo(finalValueProperty));

        completedProperty.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue) 
                root.setTop(new Label("Game Over"));
        });
        */
        
        player.getAliveProperty().addListener((observable, oldValue, newValue) -> {
            // Only if completed
        	System.out.println("GAME OVER !!!!!!");
        	PlayerLoseScene loseScreen;
			try {
				loseScreen = new PlayerLoseScene(stage);
				loseScreen.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	/*
            if (newValue) {
            	// call lose game scene
            	System.out.println("PLAYER LOSES THE GAME");
            }
                //root.setTop(new Label("Game Over"));
                 
        	*/
        });
        
        player.goalCompleted().addListener((observable, oldValue, newValue) -> {
        	System.out.println("WIN !!!!!!");
        	PlayerWinScene winScreen;
			try {
				winScreen = new PlayerWinScene(stage);
				winScreen.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        

    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
        	System.out.println("MOVING DOWN");
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case SPACE:
        	player.putDown();
        	break;
        default:
            break;
        }
        if(player.goalCompleted().getValue()) {
        	System.out.println("Goal completed !!!!!!!!!!!!!!!!!!!!!!!");
        } else {
        	System.out.println("FUCK OFF");
        }
    }

	public void setStage(Stage stage) {
		// TODO Auto-generated method stub
		this.stage = stage;
	}

}


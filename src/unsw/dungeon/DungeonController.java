package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    
    private Princess princess;

    private Dungeon dungeon;
    
    private Stage stage;
    
    private Timeline timeLine;
	
	private KeyFrame frame1;
	
	@FXML
	private ImageView enemyImage;
	
	@FXML
	private ImageView treasureImage;
	
	@FXML
	private ImageView grassImage;
	
	@FXML
	private ImageView potionImage;
	
	@FXML
	private ImageView swordImage;
	
	@FXML
	private ImageView keyImage;
	
	@FXML
	private ImageView doorImage;
	
	@FXML
	private ImageView playerImage;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities){
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);    
    }
    
    
    

    @FXML
    public void initialize() {
        Image ground = new Image("/blue_base.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        
        //BorderPane border = new BorderPane();
        //squares.add(vbox, dungeon.getWidth() - 2, 0, 150, 50);
        VBox vbox = new VBox();
        vbox.setMinWidth(300);
        //vbox.setMinWidth(150);
        squares.add(vbox, dungeon.getWidth(), 0, dungeon.getHeight()+ 500, dungeon.getWidth() + 500);
        //vbox.setStyle("-fx-background-color: #333333");
        //vbox.setStyle("-fx-border-style: solid; -fx-border-width: 3px;");
        vbox.setStyle("-fx-background-image: url(\"milky-way-1031138_1920.jpg\"); -fx-background-size: cover;");
        //vbox.setStyle("-fx-border-style: solid; -fx-border-width: 3px;");
        //vbox.setStyle("-fx-background-image: url(\"progress.png\"); -fx-background-size: cover;");
        
        
        
        Button restart = new Button();
        restart.setText("OPPS RESTART!");
        //squares.add(child, columnIndex, rowIndex);
        //squares.add(child, columnIndex, rowIndex, colspan, rowspan);
        squares.add(restart, dungeon.getWidth(), dungeon.getWidth() - (dungeon.getWidth()/2));
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	String filename = stage.getTitle();
            	stage.setTitle(filename);
        		filename = filename + ".json";
        		System.out.println(filename);
        		DungeonControllerLoader dungeonLoader;
				try {
					dungeonLoader = new DungeonControllerLoader(filename);
					DungeonController controller = dungeonLoader.loadController();
	                controller.setStage(stage);
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
	                loader.setController(controller); 	
	                Parent root = loader.load();
	                Scene scene = new Scene(root);
	                root.requestFocus();
	                stage.setScene(scene);
	                stage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
            }
        });
       
        
        Label myProgLabel =  createMainHeadingLabel("My Progress");
        Label goalsLabel = createHeadingLabels("Goals:");
        
        ArrayList<String> instructions = new ArrayList<>();
        try {
        	System.out.println(stage);
        	String filename = "dungeons/" + stage.getTitle() + ".json";
        	JSONObject json = new JSONObject(new JSONTokener(new FileReader(filename)));
        	instructions.addAll(getInstruction(json.getJSONObject("goal-condition")));
        } catch (FileNotFoundException fnfe) {
        	System.err.println(fnfe.getMessage());
        }
        
        String allInstr = "";
        for (String str : instructions) {
			allInstr += str;
			allInstr += "\n";
        }
        
        
        Label goalsSublabel = createSubheadingLabel(allInstr);

        Label treasureLabel = createHeadingLabels("Treasure collected:");
        
     // add label to vbox 
        vbox.getChildren().add(myProgLabel);
        vbox.getChildren().add(goalsLabel);
        vbox.getChildren().add(goalsSublabel);
        vbox.getChildren().add(treasureLabel);
        
        player.treasureAmount().addListener((observable, oldValue, newValue) -> {
        	Label treasureAmount = createTreasureLabel();
        	vbox.getChildren().add(treasureAmount);
        });

        
        
        
        player.getAliveProperty().addListener((observable, oldValue, newValue) -> {
            // Only if completed
        	System.out.println("GAME OVER !!!!!!");
        	PlayerLoseScene loseScreen;
			try {
				loseScreen = new PlayerLoseScene(stage);
				loseScreen.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        });
        
        player.goalCompleted().addListener((observable, oldValue, newValue) -> {
        	System.out.println("WIN !!!!!!");
        	
        	String level = stage.getTitle();
        	if(level.equals("3")) {
        		try {
    				FoundLoveScene love = new FoundLoveScene(stage);
    				love.start();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	} else {
        		try {
        			PlayerWinScene winScreen = new PlayerWinScene(stage);
    				winScreen.start();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
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
        System.out.println(player.getCarriedEntity());
        if(player.goalCompleted().getValue()) {
        	System.out.println("Goal completed !!!!!!!!!!!!!!!!!!!!!!!");
        } else {
        	System.out.println("Goal has not completed)");
        }
    }

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public Label createHeadingLabels(String name) {
		
		Label label = new Label("\n"+"     "+name); 
        label.setMaxWidth(Region.USE_PREF_SIZE);
        label.setMinWidth(Region.USE_PREF_SIZE);
		label.setFont(Font.font("American Typewriter", 14));
		label.setStyle("-fx-font-weight: bold");
		label.setStyle("-fx-text-fill: white;");
	    label.setWrapText(true);
	    label.setTextAlignment(TextAlignment.CENTER);
	
	    return label;
	    
	}
	
	public Label createSubheadingLabel(String name)  {
		
		Label label = new Label("\n"+"     "+name); 
        label.setMaxWidth(Region.USE_PREF_SIZE);
        label.setMinWidth(Region.USE_PREF_SIZE);
		label.setFont(Font.font("American Typewriter", 12));
		label.setStyle("-fx-font-weight: bold");
		label.setStyle("-fx-text-fill: white;");
	    label.setWrapText(true);
	    label.setTextAlignment(TextAlignment.LEFT);
	   
	    
	    return label;
	}
	
	public Label createMainHeadingLabel(String name)  {
		
		Label label = new Label("\n"+"   "+name+"\n"); 
        label.setMaxWidth(Region.USE_PREF_SIZE);
        label.setMinWidth(Region.USE_PREF_SIZE);
		label.setFont(Font.font("American Typewriter", 18));
		label.setStyle("-fx-font-weight: bold");
		label.setStyle("-fx-text-fill: white;");
	    label.setWrapText(true);
	    label.setTextAlignment(TextAlignment.CENTER);
	   
	    
	    return label;
	}
	
	public Label createTreasureLabel() {
		String name = player.treasureAmount().get() + "/" + dungeon.treasureAmount();
		Label label = new Label("\n"+"     "+name);
		label.setMaxWidth(Region.USE_PREF_SIZE);
        label.setMinWidth(Region.USE_PREF_SIZE);
		label.setFont(Font.font("American Typewriter", 12));
		label.setStyle("-fx-font-weight: bold");
		label.setStyle("-fx-text-fill: white;");
		label.setWrapText(true);
	    label.setTextAlignment(TextAlignment.CENTER);
	    
	    return label;
	}
	
	private ArrayList<String> getInstruction(JSONObject json) {
    	ArrayList<String> instructions = new ArrayList<>();
    	String goal = json.getString("goal");
    	
    	if(goal.equals("AND")) {
    		
    		JSONArray array = json.getJSONArray("subgoals");
    		ArrayList<String> andInstructions = new ArrayList<>();
    		for(int i = 0 ; i < array.length(); i++) {
    			andInstructions = getInstruction(array.getJSONObject(i));
    			instructions.addAll(andInstructions);
    		}
    		return instructions;
    	} else if(goal.equals("OR")) {
    		ArrayList<String> orInstructions = new ArrayList<>();
    		instructions.add("   You must complete one of the follwing goals:");
    		JSONArray array = json.getJSONArray("subgoals");
    		for(int i = 0 ; i < array.length(); i++) {
    			orInstructions = getInstruction(array.getJSONObject(i));
    			instructions.addAll(orInstructions);
    		}
    		return instructions;
    	} else if(goal.equals("enemies")) {
    		instructions.add("           <> enemies");
    		return instructions;
    	} else if(goal.equals("boulders")) {
    		instructions.add("           <> floor switches");
    		return instructions;
    	} else if(goal.equals("exit")) {
    		instructions.add("           <> exit");
    		return instructions;
    	} else if(goal.equals("treasure")) {
    		instructions.add("           <> treasures");
    		return instructions;
    	} else if (goal.equals("princess")) {
    		instructions.add("           <> save princess <3");
    	}
    	
    	return instructions;
    }

}


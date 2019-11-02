package unsw.dungeon;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y);
    }
    public String name() {
    	return "wall";
    }
    public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
    	System.out.println("cannot move");
    	return false;
    }
    
    public Boolean canEnemyMove() {
		return false;
	}
}

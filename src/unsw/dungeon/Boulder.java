package unsw.dungeon;

public class Boulder extends Entity implements EntityObserver {
	
	private Boolean canMoveFurther = false;
	
	public Boulder(int x, int y) {
        super(x, y);
    }
	
	public void update(PlayerSubject player, int up, int down, int left, int right) {
		x().setValue(((Player) player).getX() + left + right);
		y().setValue(((Player) player).getY() + up + down);
	}
	
	public Boolean canMoveFurther() {
		return canMoveFurther;
	}
	
    public String name() {
    	return "boulder";
    }
}

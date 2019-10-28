package unsw.dungeon;

public interface EntityObserver {
	public void update(PlayerSubject player, int up, int down, int left, int right);
}
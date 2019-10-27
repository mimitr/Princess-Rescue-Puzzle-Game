package unsw.dungeon;

public interface PlayerSubject {
	public void attach(EntityObserver entity);
	public void detach();
	public void notifyEntity();
}

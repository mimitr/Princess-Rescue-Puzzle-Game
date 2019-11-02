package unsw.dungeon;

import java.util.Objects;

public class Portal extends Entity {
	
	private int id;

	public Portal(int x, int y, int id) {
		super(x, y);
		this.id = id;
	}
	
	public Boolean canPlayerMove(Player player, int up, int down, int left, int right) {
		Boolean canMove = true;
		if((player.getX() + left + right) == getX() && player.getY() + up + down == getY()) {
			// send the player to the other corresponding portal
			Dungeon dungeon = player.getDungeon();
			Portal otherPortal = dungeon.getOtherPortal(this);
			if(Objects.nonNull(otherPortal)) {
				System.out.println(otherPortal.getX());
				System.out.println(otherPortal.getY());
				if(player.canMove(otherPortal.getX() + left + right, otherPortal.getY() + up + down, up, down, left, right)) {
					//System.out.println(otherPortal.getX());
					//System.out.println(otherPortal.getY());
					player.x().set(otherPortal.getX());
					player.y().set(otherPortal.getY());
					//return true;
				} else {
					canMove = false;
				}
				//canMove = false;
			} else {
				canMove = false;
			}
		}
		return canMove;
	}
	/*
	public Boolean canEnemyMove(Enemy enemy, int up, int down, int left, int right) {
		Boolean canMove = true;
		if((enemy.getX() + left + right) == getX() && enemy.getY() + up + down == getY()) {
			// send the player to the other corresponding portal
			Dungeon dungeon = enemy.getDungeon();
			Portal otherPortal = dungeon.getOtherPortal(this);
			if(Objects.nonNull(otherPortal)) {
				System.out.println(otherPortal.getX());
				System.out.println(otherPortal.getY());
				if(enemy.canEnemyMoveTo(otherPortal.getX() + left + right, otherPortal.getY() + up + down)) {
					//System.out.println(otherPortal.getX());
					//System.out.println(otherPortal.getY());
					enemy.x().set(otherPortal.getX());
					enemy.y().set(otherPortal.getY());
					//return true;
				} else {
					canMove = false;
				}
				//canMove = false;
			} else {
				canMove = false;
			}
		}
		return canMove;
	}
	*/
	public Boolean canEnemyMove() {
		return false;
	}
	public int getID() {
		return id;
	}
}

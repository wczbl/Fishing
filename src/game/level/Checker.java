package game.level;

import game.entity.Entity;

public interface Checker {

	public boolean check(Entity owner, Entity e);
	
}
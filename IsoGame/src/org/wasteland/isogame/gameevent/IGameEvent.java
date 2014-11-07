package org.wasteland.isogame.gameevent;

import org.wasteland.isogame.world.IWorld;

public interface IGameEvent {
	public int getEventType();
	public IWorld getWorld();
}

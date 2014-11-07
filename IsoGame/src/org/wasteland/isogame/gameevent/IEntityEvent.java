package org.wasteland.isogame.gameevent;

import org.wasteland.isogame.entity.IEntity;

public interface IEntityEvent extends IGameEvent {
	public IEntity getEntity();
}

package org.wasteland.isogame.entity.state;

import org.wasteland.isogame.entity.IEntity;

public interface IEntityState {
	void setEntity(IEntity ent);
	int getCode();
}

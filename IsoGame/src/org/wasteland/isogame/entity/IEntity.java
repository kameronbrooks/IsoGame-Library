package org.wasteland.isogame.entity;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.wasteland.isogame.chunk.Chunk;
import org.wasteland.isogame.entity.state.IEntityState;
import org.wasteland.isogame.gameevent.IEntityEvent;
import org.wasteland.isogame.world.IWorld;



public interface IEntity {
	public int getType();
	
	public void setParentChunk(Chunk chunk);
	public Chunk getParentChunk();
	
	public IWorld getWorld();
	
	public float getWorldX();
	public float getWorldY();
	public float getWorldZ();
	public float[] getWorldPosition();
	
	public void setWorldX(float x);
	public void setWorldY(float y);
	public void setWorldZ(float z);
	
	public void setWorldPosition(float x, float y, float z);
	
	public float getScreenX();
	public float getScreenY();
	public float getScreenZ();
	
	public float[] getScreenPosition();
	
	public int getDiscreteWorldX();
	public int getDiscreteWorldY();
	public int getDiscreteWorldZ();
	
	public int getDiscreteChunkX();
	public int getDiscreteChunkY();
	public int getDiscreteChunkZ();
	
	public IEntityState getEntityState();
	public void setEntityState(IEntityState state);
	
	public byte getOrientation();
	public void setOrientation(byte orientation);
	
	public void update(float delta);
	public void handleEntityEvent(IEntityEvent event);
	
	public ITextureRegion getCurrentTextureRegion();
}

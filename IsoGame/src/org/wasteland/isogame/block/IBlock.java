package org.wasteland.isogame.block;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.wasteland.isogame.blockinfo.BlockInfo;
import org.wasteland.isogame.gameevent.IBlockEvent;

public interface IBlock {
	public static final float DEFAULT_WIDTH = 3.5f;
	public static final float DEFAULT_HEIGHT = DEFAULT_WIDTH;
	public static final float DEFAULT_DEPTH = DEFAULT_WIDTH;
	public static final float DEFAULT_SPRITE_WIDTH = DEFAULT_WIDTH * 1.9f;
	
	
	public ITextureRegion getSpriteTexture(int data);
	public short getDisplayType();
	public int getHardness();
	public float getWalkingSpeedMult();
	public String getName();
	public int getID();
	public int getGlobalState();
	public void setGlobalState(int data);
	public boolean hasGlobalState();
	public void handleBlockEvent(IBlockEvent event);
	
}

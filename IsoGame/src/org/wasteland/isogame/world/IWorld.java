package org.wasteland.isogame.world;

import org.wasteland.isogame.chunk.Chunk;
import org.wasteland.isogame.light.LightMap;

public interface IWorld {
	public static final int CHUNK_SIZE = 32;
	public void convertToScreenCoords(float worldX, float worldY, float worldZ, float[] buffer);
	
	public void addToActiveChunks(Chunk chunk, int i);
	public void addToLoadedChunks(Chunk chunk);
	
	public Chunk[] getActiveChunks();
	public LightMap getLightMap();
	
	
	
	public void update(float delta);
}

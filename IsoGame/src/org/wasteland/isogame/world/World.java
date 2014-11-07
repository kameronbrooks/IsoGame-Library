package org.wasteland.isogame.world;

import org.wasteland.isogame.chunk.Chunk;
import org.wasteland.isogame.light.LightMap;


public class World implements IWorld {
	
	public int MAX_ACTIVE_CHUNKS = 4;
	
	LightMap _lightMap;
	Chunk [] _activeChunkList;
	Chunk [] _loadedChunkList;
	
	public World() {
		_lightMap = new LightMap(this);
		_activeChunkList = new Chunk[4];
		for(int i = 0; i < MAX_ACTIVE_CHUNKS; i++) {
			_activeChunkList[i] = null;
		}
	}

	@Override
	public void convertToScreenCoords(float worldX, float worldY, float worldZ,
			float[] buffer) {
		float chunkSize = ((float)CHUNK_SIZE-1)*2;
		float a = worldY + worldX;
		float f = (a<=chunkSize/2)?a+1:chunkSize-a+1;
		float b = worldX + (float)Math.floor((double)(f - a) / 2 );
		
		float x = (-0.95f*f)+(1.9f*b);
		float y = (a*0.475f);
		float z = a;
		buffer[0] = x;
		buffer[1] = y+worldZ;
		buffer[2] = z;
		
		
	}
	
	@Override
	public void addToActiveChunks(Chunk chunk, int i) {
		_activeChunkList[i] = chunk;
	}

	@Override
	public void addToLoadedChunks(Chunk chunk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Chunk[] getActiveChunks() {
		return this._activeChunkList;
	}

	@Override
	public LightMap getLightMap() {
		return _lightMap;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}


}

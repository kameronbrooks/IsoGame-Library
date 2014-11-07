package org.wasteland.isogame.worldrenderer;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.batch3D.SpriteBatch3D;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.wasteland.isogame.block.IBlock;
import org.wasteland.isogame.blockdisplaytype.BlockDisplayType;
import org.wasteland.isogame.blockinfo.BlockInfo;
import org.wasteland.isogame.chunk.Chunk;
import org.wasteland.isogame.world.IWorld;
import org.wasteland.isogame.world.World;

import android.util.Log;



public class WorldRenderer implements IWorldRenderer {

	private BufferSpriteRow[] _bufferRowList;
	private IWorld _world;
	private Object _bufferLock;
	
	float[] _lightBuffer;
	
	public WorldRenderer(IWorld world) {
		_world = world;
		_bufferLock = new Object();
		
		_bufferRowList = new BufferSpriteRow[ROW_COUNT];
		for(int i=0; i < ROW_COUNT; i++) {
			_bufferRowList[i] = new BufferSpriteRow();
		}
		
		_lightBuffer = new float[3];
	}
	public void addToBuffer(int row, float x, float y, float z, float size, float r, float g, float b, float a, ITextureRegion texture) {
		synchronized(_bufferLock) {
			_bufferRowList[row].write(x, y, z, size, r, g, b, a, texture);
		}	
	}
	public void clearBuffer() {
		for(int i=0; i < ROW_COUNT; i++) {
			_bufferRowList[i].clear();
		}
	}
	
	
	public void drawVisibleWorld(Camera camera, SpriteBatch3D batch) {
		//if(!_renderer.isReadyForWriting())return;
		
		float XcameraMin2D = camera.getXMin();
		float XcameraMax2D = camera.getXMax();
		float YcameraMin2D = camera.getYMin();
		float YcameraMax2D = camera.getYMax();
		int chunkSize = (World.CHUNK_SIZE-1)*2;
		
		for(Chunk chunk: _world.getActiveChunks()) {
				if(chunk == null)continue;
				float Xorgin2D = chunk.getScreenX();
				float Yorgin2D = chunk.getScreenY()+IBlock.DEFAULT_SPRITE_WIDTH/2;
				float orginDistY = YcameraMin2D - Yorgin2D;
				
				int startRow = (int)Math.floor(((4*orginDistY)/IBlock.DEFAULT_SPRITE_WIDTH));
				int endRow = startRow+WorldRenderer.ROW_COUNT-1;
				
				if(endRow>chunkSize) {
					endRow=chunkSize;
				}
				//Log.d("Renderer", "odY="+orginDistY+" st_row="+startRow+" end_row="+endRow);
				
				for(int a=startRow;a<=endRow;a++) {
					int f = (a<=chunkSize/2)?a+1:(chunkSize-a)+1;
					//float z = 20.0f*(((float)a-startRow)/(float)(endRow-startRow))-10f;
					float z = 20.0f*(((float)a-startRow)/(float)(WorldRenderer.ROW_COUNT))-10f;
					//float z = 1;
					for(int b=0;b<f;b++) {
						
						if(drawVisibleColumn(a,b,f,a-startRow,z,IBlock.DEFAULT_SPRITE_WIDTH,chunk,camera,batch)) {
							
						}
					}
				}		
		}
		
		
	}
	private boolean drawVisibleColumn(int a, int b, int f, int row, float z, float tileWidth, Chunk chunk, Camera camera, SpriteBatch3D batch) {
		float x = chunk.getScreenX()+(-(tileWidth/2)*f)+(tileWidth*b);
		float y = chunk.getScreenY()+ (a*tileWidth/4);
		int xShift = (int)Math.floor(((double)(f-a))/2.0);
        int xIndex = b-xShift;
        int yIndex = a-xIndex;
        
        float worldX = chunk.getWorldX() + ((float)xIndex)*IBlock.DEFAULT_WIDTH;
		float worldY = chunk.getWorldY() +((float)yIndex)*IBlock.DEFAULT_WIDTH;
		
		
		
		_world.getLightMap().getRGBValues(worldX, worldY, 0, _lightBuffer);
		Chunk.Column[][] columns = chunk.getColumnMatrix();
        Chunk.Column column = columns[xIndex][yIndex];
        for(int c=0;c<column.getDepth();c++) {
        	
			BlockInfo blockInfo = column.getBlockInfo(c);
			
			IBlock block = blockInfo.getBlockType();
			
			if(block.getDisplayType() == BlockDisplayType.NONE)continue;
			float cHeight = c*(-tileWidth/2);
			int state = blockInfo.getData();
			if(block.hasGlobalState()) {
				state = state | block.getGlobalState();
			}
			
			if(!camera.isRectangularShapeVisible(x, y+cHeight, tileWidth*2, tileWidth*2, batch.getLocalToSceneTransformation())) {
				
	        	return false;
	        }
			
			
			addToBuffer(row, x, y+cHeight, z, tileWidth, _lightBuffer[0], _lightBuffer[1],_lightBuffer[2], 1f, block.getSpriteTexture(state));
			
		}
        return true;
	}
	public void renderWorld(SpriteBatch3D batch, Camera camera) {
		
		drawVisibleWorld(camera,batch);
		synchronized(_bufferLock) {
			
			
			for(int i=0; i < ROW_COUNT; i++) {
				BufferSprite sprite;
				
				while((sprite = _bufferRowList[i].read())!=null) {
					//float cc = sprite.z/40.0f+0.5;
					batch.draw(sprite.texture, sprite.x, sprite.y, sprite.z, sprite.size, sprite.size, sprite.r, sprite.g, sprite.b, sprite.a);
					
				}
				_bufferRowList[i].clear();
			}
				
		}
	}

}

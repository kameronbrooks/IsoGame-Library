package org.wasteland.isogame.worldrenderer;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.batch3D.SpriteBatch3D;
import org.andengine.opengl.texture.region.ITextureRegion;


public interface IWorldRenderer {
	public static class BufferSprite {
		float x, y, z, size, r, g, b, a;
		ITextureRegion texture;
		BufferSprite () {
			x = 0;
			y = 0;
			size = 0;
			r = 1;
			g = 1;
			b = 1;
			a = 1;
			texture = null;
		}
		public void set(float x, float y, float z, float size, float r, float g, float b, float a, ITextureRegion texture) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.size = size;
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
			this.texture = texture;
		}
	}
	public static final int ROW_COUNT = 19;
	public static final int COLUMN_COUNT = 16;
	
	public static class BufferSpriteRow {
		public static final int DEFAULT_SIZE = 32;
		BufferSprite[] _buffer;
		int _count;
		int _writeIndex, _readIndex;
		int LimitIncreaseStep = 5;
		public BufferSpriteRow() {
			_buffer = new BufferSprite[DEFAULT_SIZE];
			for(int i=0;i<_buffer.length;i++) {
				_buffer[i] = new BufferSprite();
			}
			clear();
		}
		public void clear() {
			_count = 0;
			_writeIndex = 0;
			_readIndex = 0;
			
		}
		public void write(float x, float y, float z, float size, float r, float g, float b, float a, ITextureRegion texture) {
			if(_writeIndex >= _buffer.length) {
				BufferSprite[] newBuffer = new BufferSprite[_buffer.length+LimitIncreaseStep];
				for(int i=0;i<_buffer.length;i++) {
					newBuffer[i] = _buffer[i];
				}
				for(int i=_buffer.length; i < newBuffer.length;i++) {
					newBuffer[i] = new BufferSprite();
				}
				_buffer = newBuffer;
			}
			
			_buffer[_writeIndex++].set(x, y, z, size, r, g, b, a, texture);
			
			//_writeIndex++;
			_count =_writeIndex > _count ? _writeIndex : _count;
		}
		public BufferSprite read() {
			
			if(_readIndex < _count) {
				return _buffer[_readIndex++];
			}
			else return null;
		}
		public int count() {
			return _count;
		}
	}
	public void addToBuffer(int row, float x, float y, float z, float size, float r, float g, float b, float a, ITextureRegion texture);
	public void clearBuffer();
	public void renderWorld(SpriteBatch3D batch, Camera camera);
}

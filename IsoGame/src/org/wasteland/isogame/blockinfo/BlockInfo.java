package org.wasteland.isogame.blockinfo;

import org.wasteland.isogame.block.IBlock;


public class BlockInfo {
	IBlock _blockType;
	byte _orientation;
	int _data;
	float [] _color;
	float _temperature;
	int _ID;
	float [] _worldPosition;
	int [] _discretePosition;
	
	
	private static int BASE_ID = 0;
	
	/* =-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=
	 * 					CONSTRUCTORS
	 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
	
	public BlockInfo() {
		_ID = ++BASE_ID;
		_blockType = null;
		_orientation = 0;
		_data = 0;
		_temperature = 0.0f;
		_color = new float[4];
		_color[0] = _color[1] = _color[2] = _color[3] = 1.0f;
		_worldPosition = new float[3];
		_worldPosition[0] = _worldPosition[1] = _worldPosition[2] = 0;
		_discretePosition = new int[3];
		_discretePosition[0] = _discretePosition[1] = _discretePosition[2] = 0;
	}
	public BlockInfo(float wx, float wy, float wz) {
		_ID = ++BASE_ID;
		_blockType = null;
		_orientation = 0;
		_data = 0;
		_temperature = 0.0f;
		_color = new float[4];
		_color[0] = _color[1] = _color[2] = _color[3] = 1.0f;
		_worldPosition = new float[3];
		_discretePosition = new int[3];
		this.setWorldPosition(wx, wy, wz);
		
	}
	public void copyFrom(BlockInfo b) {
		_blockType = b._blockType;
		_orientation = b._orientation;
		_data = b._data;
		_temperature = b._temperature;
		_color[0] = b._color[0];
		_color[1] = b._color[1];
		_color[2] = b._color[2];
		_color[3] = b._color[3];
		_worldPosition[0] = b._worldPosition[0];
		_worldPosition[1] = b._worldPosition[1];
		_worldPosition[2] = b._worldPosition[2];
		_discretePosition[0] = b._discretePosition[0];
		_discretePosition[1] = b._discretePosition[1];
		_discretePosition[2] = b._discretePosition[2];
		
	}
	
	/* =-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=
	 * 					GETTERS & SETTERS
	 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
	public int getID() {
		return _ID;
	}
	public IBlock getBlockType() {
		return _blockType;
	}
	public void setBlockType(IBlock block) {
		_blockType = block;
	}
	
	
	public void setOrientation(byte orient) {
		/* Sets the Orientation
		 * 1 	- NORTH
		 * 3 	- EAST
		 * -1 	- SOUTH
		 * -3 	- WEST 
		 * */
		_orientation = orient;
	}
	public void setData(int data) {
		_data = data;
	}
	public void setData(int data, boolean bitwiseOR) {
		if(bitwiseOR) {
			_data |= data;
		} else {
			_data = data;
		}
	}
	public void setColor(float r, float g, float b, float a) {
		_color[0] = r;
		_color[1] = g;
		_color[2] = b;
		_color[3] = a;
	}
	public void setColor(float r, float g, float b, float a, int mask) {
		if((mask & 0x11000000) != 0) {
			_color[0] = r;
		}
		if((mask & 0x00110000) != 0) {
			_color[1] = g;
		}
		if((mask & 0x00001100) != 0) {
			_color[2] = b;
		}
		if((mask & 0x00000011) != 0) {
			_color[3] = a;
		}
		
	}
	public void setWorldPosition(float x, float y, float z) {
		_worldPosition[0] = x;
		_worldPosition[1] = y;
		_worldPosition[2] = z;
		_discretePosition[0] = (int)(x/IBlock.DEFAULT_WIDTH);
		_discretePosition[1] = (int)(y/IBlock.DEFAULT_WIDTH);
		_discretePosition[2] = (int)(z/IBlock.DEFAULT_WIDTH);
	}
	
	public float[] getWorldPosition() {
		return _worldPosition;
	}
	public int[] getDiscretePosition() {
		return _discretePosition;
	}
	public int getData() {
		return _data;
	}
	public int getState() {
		return _data;
	}
	public float[] getColor() {
		return _color;
	}
	
}

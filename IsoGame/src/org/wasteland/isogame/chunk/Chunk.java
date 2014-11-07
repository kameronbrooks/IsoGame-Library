package org.wasteland.isogame.chunk;

import org.wasteland.isogame.block.IBlock;
import org.wasteland.isogame.blockinfo.BlockInfo;
import org.wasteland.isogame.world.IWorld;



public class Chunk {
	public static class Column {
		private BlockInfo [] _blockList;
		private int _depth;
		public Column(int depth, float wx, float wy) {
			_blockList = new BlockInfo[depth];
			for(int i = 0; i < depth; i++) {
				_blockList[i] = new BlockInfo(wx,wy,i*IBlock.DEFAULT_WIDTH);
			}
			_depth = depth;
		}
		public int getDepth() {
			return _depth;
		}
		public BlockInfo getBlockInfo(int i) {
			if(i<0||i>=_depth) {
				return null;
			} else {
				return _blockList[i];
			}
		}
		public void setBlockInfo(int i, BlockInfo blockInfo) {
			if(i<0||i>=_depth) {
				return;
			} else {
				_blockList[i].copyFrom(blockInfo);
			}
		}
		public void setBlockInfo(int i, IBlock block) {
			if(i<0||i>=_depth) {
				return;
			} else {
				_blockList[i].setBlockType(block);
				_blockList[i].setData(0);
			}
		}
		
	}
	
	/* =-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=
	 * 					MEMBER VARIABLES
	 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
	protected IWorld _world;
	protected int _dWidth, _dHeight, _dDepth;
	protected float _rWidth, _rHeight, _rDepth;
	protected float [] _worldCoords;
	protected float [] _screenCoords;
	protected Column [][] _columnMatrix;
	
	/* =-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=
	 * 					CONSTRUCTOR
	 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
	
	public Chunk(IWorld world, int width, int height, int depth) {
		_dWidth = width;
		_dHeight = height;
		_dDepth = depth;
		_world = world;
		_columnMatrix = new Column[_dWidth][_dHeight];
		
		_worldCoords = new float[3];
		_screenCoords = new float[3];
	}
	
	/* =-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=
	 * 					GETTERS & SETTERS
	 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
	
	public void build() {
		for(int a = 0; a < _dWidth; a++) {
			for(int b = 0; b < _dHeight; b++) {
				_columnMatrix[a][b] = new Column(_dDepth,a*IBlock.DEFAULT_WIDTH+_worldCoords[0],b*IBlock.DEFAULT_WIDTH+_worldCoords[1]);
			}
		}
	}
	public void build(IBlock defaultBlock) {
		for(int a = 0; a < _dWidth; a++) {
			for(int b = 0; b < _dHeight; b++) {
				_columnMatrix[a][b] = new Column(_dDepth,a*IBlock.DEFAULT_WIDTH+_worldCoords[0],b*IBlock.DEFAULT_WIDTH+_worldCoords[1]);
				for(int c = 0; c < _dDepth; c++) {
					_columnMatrix[a][b]._blockList[c].setBlockType(defaultBlock);
				}
				
			}
		}
	}
	public void build(IBlock floor, IBlock air) {
		for(int a = 0; a < _dWidth; a++) {
			for(int b = 0; b < _dHeight; b++) {
				_columnMatrix[a][b] = new Column(_dDepth,a*IBlock.DEFAULT_WIDTH+_worldCoords[0],b*IBlock.DEFAULT_WIDTH+_worldCoords[1]);
				_columnMatrix[a][b]._blockList[0].setBlockType(floor);
				for(int c = 1; c < _dDepth; c++) {
					_columnMatrix[a][b]._blockList[c].setBlockType(air);
				}
				
			}
		}
	}
	
	public void setWorldPosition(float x, float y, float z) {
		_worldCoords[0] = x;
		_worldCoords[1] = y;
		_worldCoords[2] = z;
		if(_world != null)_world.convertToScreenCoords(x, y, z, _screenCoords);
	
	}
	public void setDimensions(int w, int h, int d) {
		_dWidth = w;
		_dHeight = h;
		_dDepth = d;
	}
	public float[] getWorldPosition() {
		return _worldCoords;
	}
	public float getWorldX() {
		return _worldCoords[0];
	}
	public float getWorldY() {
		return _worldCoords[1];
	}
	public float getWorldZ() {
		return _worldCoords[2];
	}
	public float[] getScreenPosition() {
		return _screenCoords;
	}
	public float getScreenX() {
		return _screenCoords[0];
	}
	public float getScreenY() {
		return _screenCoords[1];
	}
	public float getScreenZ() {
		return _screenCoords[2];
	}
	public BlockInfo getBlockAt(float x, float y, float z) {
		return getBlockAt(x,y,z,false);
	}
	public BlockInfo getBlockAt(float x, float y, float z, boolean localCoords) {
		float lx = x;
		float ly = y;
		if(localCoords) {
			
		} else {
			lx = x - _worldCoords[0];
			ly = y - _worldCoords[1];
		}
		return _columnMatrix[(int)(lx/IBlock.DEFAULT_WIDTH)][(int)(ly/IBlock.DEFAULT_WIDTH)].getBlockInfo((int)(z/IBlock.DEFAULT_WIDTH));
		
	}
	public BlockInfo getBlockUnder(float x, float y, float z, boolean localCoords) {
		float lx = x;
		float ly = y;
		if(localCoords) {
			
		} else {
			lx = x - _worldCoords[0];
			ly = y - _worldCoords[1];
		}
		return _columnMatrix[(int)(lx/IBlock.DEFAULT_WIDTH)][(int)(ly/IBlock.DEFAULT_WIDTH)].getBlockInfo((int)(z/IBlock.DEFAULT_WIDTH) - 1);
		
	}
	public Column[][] getColumnMatrix() {
		return _columnMatrix;
	}
}

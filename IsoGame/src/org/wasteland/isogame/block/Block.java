package org.wasteland.isogame.block;



public abstract class Block implements IBlock{
	protected int GLOBAL_STATE;
	protected Block() {
		
	}
	@Override
	public float getWalkingSpeedMult() {
		return 0.4f;
	}
	@Override
	public int getGlobalState() {
		return GLOBAL_STATE;
	}
	@Override
	public void setGlobalState(int data) {
		GLOBAL_STATE = data;
		
	}
	@Override
	public boolean hasGlobalState() {
		// TODO Auto-generated method stub
		return false;
	}


}

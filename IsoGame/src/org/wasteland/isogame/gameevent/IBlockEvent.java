package org.wasteland.isogame.gameevent;

import org.wasteland.isogame.blockinfo.BlockInfo;

public interface IBlockEvent extends IGameEvent {
	public BlockInfo getBlockInfo();
	
}

package org.wasteland.isogame.chunkloader;

import java.io.InputStream;
import java.util.Map;

import org.wasteland.isogame.block.IBlock;
import org.wasteland.isogame.chunk.Chunk;

import android.util.SparseArray;

public interface IChunkLoader {
	public boolean loadChunk(Chunk chunk, String filename, SparseArray<IBlock> blockMap);
	public boolean loadChunk(Chunk chunk, InputStream file, SparseArray<IBlock> blockMap);
}

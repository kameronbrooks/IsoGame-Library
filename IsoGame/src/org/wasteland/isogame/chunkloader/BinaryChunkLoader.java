package org.wasteland.isogame.chunkloader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.wasteland.isogame.block.IBlock;
import org.wasteland.isogame.blockinfo.BlockInfo;
import org.wasteland.isogame.chunk.Chunk;
import org.wasteland.isogame.chunk.Chunk.Column;

import android.util.Log;
import android.util.SparseArray;

public class BinaryChunkLoader implements IChunkLoader{
	
	private boolean checkHeader(DataInputStream stream){
		byte [] buffer = new byte[4];
		
		try {
			buffer[0] = stream.readByte();
			buffer[1] = stream.readByte();
			buffer[2] = stream.readByte();
			if(buffer[0] == 'g' && buffer[1] == 'c' && buffer[2] == 'f') {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		
	}
	private boolean checkChunk(DataInputStream stream){
		byte [] buffer = new byte[4];
		
		try {
			buffer[0] = stream.readByte();
			buffer[1] = stream.readByte();
			buffer[2] = stream.readByte();
			buffer[3] = stream.readByte();
			if(buffer[0] == '@' && buffer[1] == 'c' && buffer[2] == 'n' && buffer[3] == 'k') {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		
	}
	private boolean readBlockLittleEndian(DataInputStream stream, BlockInfo block, int atomSize, int atomCount, SparseArray<IBlock> blockMap) throws IOException {
		if(atomSize == 2) {
			short type = convertToBigEndian(stream.readShort());
			short data = convertToBigEndian(stream.readShort());
			
			block.setBlockType(blockMap.get(type));
			
			block.setData(data);
			return true;
		} else if(atomSize == 4) {
			block.setBlockType(blockMap.get(convertToBigEndian(stream.readInt())));
			block.setData(convertToBigEndian(stream.readInt()));
			return true;
		} else {
			return false;
		}
	}
	private boolean readBlockBigEndian(DataInputStream stream, BlockInfo block, int atomSize, int atomCount, SparseArray<IBlock> blockMap) throws IOException {
		if(atomSize == 2) {
			short type = stream.readShort();
			short data = stream.readShort();
			
			block.setBlockType(blockMap.get(type));
			
			block.setData(data);
			return true;
		} else if(atomSize == 4) {
			block.setBlockType(blockMap.get(stream.readInt()));
			block.setData(stream.readInt());
			return true;
		} else {
			return false;
		}
	}
	private int convertToBigEndian(int in) {
		int out = ((in & 0xFF) << 24) | ((in & 0xFF00) << 8) | ((in & 0xFF0000) >> 8)| ((in & 0xFF000000) >> 24);
		return out;
	}
	private short convertToBigEndian(short in) {
		short out = (short) (((in & 0xFF) << 8) |((in & 0xFF00) >> 8));
		return out;
	}
	private float convertToBigEndian(float in) {
		int i = Float.floatToIntBits(in);
		float out = Float.intBitsToFloat(((i & 0xFF) << 24) | ((i & 0xFF00) << 8) | ((i & 0xFF0000) >> 8)| ((i & 0xFF000000) >> 24));
		return out;
	}
	@Override
	public boolean loadChunk(Chunk chunk, String filename, SparseArray<IBlock> blockMap) {
		DataInputStream stream = null;
		try {
			stream = new DataInputStream( new FileInputStream(new File(filename)));
			
			if(checkHeader(stream)) {
				
			}
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean loadChunk(Chunk chunk, InputStream file, SparseArray<IBlock> blockMap) {
		DataInputStream stream = null;
		try {
			stream = new DataInputStream(file);
			
			if(checkHeader(stream)) {
				boolean bigendian = false;
				
				int version = stream.readInt();
				if((version & 0xFFFF0000) != 0) {
					bigendian = true;
				}
				if(bigendian) {
					int width = stream.readInt();
					int height = stream.readInt();
					int depth = stream.readInt();
					float x = stream.readFloat();
					float y = stream.readFloat();
					float z = stream.readFloat();
					int atomSize = stream.readInt();
					int blockAtomCount = stream.readInt();
					int order = stream.readInt();
					Log.d("Loader","width = "+width+" heigt = "+height+" depth = "+depth);
					Log.d("Loader","x = "+x+" y = "+y+" z = "+z);
					Log.d("Loader","atomsize = "+atomSize+" blockAtomCount = "+blockAtomCount+" order = "+order);
					BlockInfo buffer = new BlockInfo();
					if(checkChunk(stream)) {
						chunk.setDimensions(width, height, depth);
						chunk.setWorldPosition(x, y, z);
						chunk.build();
						if(order == 1) {
							for(int b=0; b<height; b++) {
								for(int a=0; a<width; a++) {
									for(int c=0; c<depth; c++) {
										readBlockBigEndian(stream,buffer,atomSize,blockAtomCount,blockMap);
										chunk.getColumnMatrix()[a][b].setBlockInfo(c, buffer);
									}
								}
							}
						} else {
							for(int a=0; a<height; a++) {
								for(int b=0; b<width; b++) {
									for(int c=0; c<depth; c++) {
										readBlockBigEndian(stream,buffer,atomSize,blockAtomCount,blockMap);
										chunk.getColumnMatrix()[a][b].setBlockInfo(c, buffer);
									}
								}
							}
						}
					}
				} else {
					int width = convertToBigEndian(stream.readInt());
					int height = convertToBigEndian(stream.readInt());
					int depth = convertToBigEndian(stream.readInt());
					float x = convertToBigEndian(stream.readFloat());
					float y = convertToBigEndian(stream.readFloat());
					float z = convertToBigEndian(stream.readFloat());
					int atomSize = convertToBigEndian(stream.readInt());
					int blockAtomCount = convertToBigEndian(stream.readInt());
					int order = convertToBigEndian(stream.readInt());
					Log.d("Loader","width = "+width+" heigt = "+height+" depth = "+depth);
					Log.d("Loader","x = "+x+" y = "+y+" z = "+z);
					Log.d("Loader","atomsize = "+atomSize+" blockAtomCount = "+blockAtomCount+" order = "+order);
					BlockInfo buffer = new BlockInfo();
					if(checkChunk(stream)) {
						chunk.setDimensions(width, height, depth);
						chunk.setWorldPosition(x, y, z);
						chunk.build();
						if(order == 1) {
							for(int b=0; b<height; b++) {
								for(int a=0; a<width; a++) {
									for(int c=0; c<depth; c++) {
										readBlockLittleEndian(stream,buffer,atomSize,blockAtomCount,blockMap);
										chunk.getColumnMatrix()[a][b].setBlockInfo(c, buffer);
									}
								}
							}
						} else {
							for(int a=0; a<height; a++) {
								for(int b=0; b<width; b++) {
									for(int c=0; c<depth; c++) {
										readBlockLittleEndian(stream,buffer,atomSize,blockAtomCount,blockMap);
										chunk.getColumnMatrix()[a][b].setBlockInfo(c, buffer);
									}
								}
							}
						}
					}
				}
				
				
			}
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

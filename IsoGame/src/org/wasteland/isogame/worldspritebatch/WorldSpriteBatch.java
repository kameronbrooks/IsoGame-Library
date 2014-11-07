package org.wasteland.isogame.worldspritebatch;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.batch3D.DynamicSpriteBatch3D;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.wasteland.isogame.world.IWorld;
import org.wasteland.isogame.worldrenderer.WorldRenderer;

public class WorldSpriteBatch extends DynamicSpriteBatch3D{
	IWorld _world;
	WorldRenderer _renderer;
	Camera _mCamera;
	public WorldSpriteBatch(IWorld world, Camera camera, ITexture pTexture, int pCapacity,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pTexture, pCapacity, pVertexBufferObjectManager);
		_world = world;
		_mCamera = camera;
		_renderer = new WorldRenderer(_world);
	}

	@Override
	protected boolean onUpdateSpriteBatch() {
		_renderer.renderWorld(this, _mCamera);
		
		return true;
	}

}

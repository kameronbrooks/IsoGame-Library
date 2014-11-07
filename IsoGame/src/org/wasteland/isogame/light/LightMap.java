package org.wasteland.isogame.light;

import java.util.ArrayList;
import java.util.List;

import org.wasteland.isogame.world.IWorld;
import org.wasteland.isogame.world.World;



public class LightMap {
	private List<ILightSource> _lights;
	private float[] _ambientLightColor;
	private float[] _currentLightColor;
	private IWorld _world;
	
	public LightMap(IWorld world) {
		_world = world;
		_lights = new ArrayList<ILightSource>();
		_ambientLightColor = new float[3];
		_currentLightColor = new float[3];
	}
	private void refreshCurrentColor() {
		_currentLightColor[0] = 0.0f;
		_currentLightColor[1] = 0.0f;
		_currentLightColor[2] = 0.0f;
	}
	public synchronized void setAmbientLight(float[] light) {
		_ambientLightColor[0] = light[0];
		_ambientLightColor[1] = light[1];
		_ambientLightColor[2] = light[2];
	}
	public synchronized void addLight(ILightSource l) {
		_lights.add(l);
	}
	public synchronized void removeLight(ILightSource l) {
		_lights.remove(l);
	}
	public synchronized void getRGBValues(float x, float y, float z, float[] values) {
		values[0] = _ambientLightColor[0];
		values[1] = _ambientLightColor[1];
		values[2] = _ambientLightColor[2];
		refreshCurrentColor();
		for(ILightSource l: _lights) {
			float lx = l.getLightX();
			float ly = l.getLightY();
			l.getColor(_currentLightColor);
			float dist = (float)Math.sqrt((double)(x-lx)*(x-lx)+(y-ly)*(y-ly));
			float distRatio = 1-dist/l.getRadius();
			if(distRatio<0.0f)distRatio = 0.0f;
			
			values[0] = values[0] + (_currentLightColor[0] - values[0])*distRatio;
			values[1] = values[1] + (_currentLightColor[1] - values[1])*distRatio;
			values[2] = values[2] + (_currentLightColor[2] - values[2])*distRatio;
			
		}
		
	}
}

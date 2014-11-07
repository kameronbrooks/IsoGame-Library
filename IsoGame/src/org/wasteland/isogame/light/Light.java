package org.wasteland.isogame.light;

public class Light implements ILightSource {
	private Object _colorLock;
	private Object _positionLock;
	
	float[] _color;
	float _radius;
	float _intensity;
	float _x,_y, _z;
	
	
	public Light() {
		_colorLock = new Object();
		_positionLock = new Object();
		_color = new float[3];
		_radius = 3;
		_intensity = 1.0f;
		_x = 0;
		_y = 0;
		_z = 0;
	}
	public Light(float r, float g, float b, float x, float y, float z, float rad, float i) {
		_colorLock = new Object();
		_positionLock = new Object();
		_color = new float[3];
		_color[0] = r;
		_color[1] = g;
		_color[2] = b;
		_radius = rad;
		_intensity = i;
		_x = x;
		_y = y;
		_z = z;
	}
	public Light(float r, float g, float b, float x, float y, float z, float rad) {
		_colorLock = new Object();
		_positionLock = new Object();
		_color = new float[3];
		_color[0] = r;
		_color[1] = g;
		_color[2] = b;
		_radius = rad;
		_intensity = 1.0f;
		_x = x;
		_y = y;
		_z = z;
	}
	@Override
	public float[] getColor() {
		return _color;
	}

	@Override
	public void getColor(float[] buffer) {
		synchronized(_colorLock) {
			buffer[0] = _color[0];
			buffer[1] = _color[1];
			buffer[2] = _color[2];
		}
		
		
	}

	@Override
	public void setColor(float r, float g, float b) {
		synchronized(_colorLock) {
			_color[0] = r;
			_color[1] = g;
			_color[2] = b;
		}
		
	}

	@Override
	public void setColor(float[] color) {
		synchronized(_colorLock) {
			_color[0] = color[0];
			_color[1] = color[1];
			_color[2] = color[2];
		}
	}

	@Override
	public void setIntensity(float i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getIntensity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRadius(float rad) {
		synchronized(_positionLock) {
			_radius = rad;
		}
		
	}

	@Override
	public float getRadius() {
		synchronized(_positionLock) {
			return _radius;
		}
	}

	@Override
	public float getLightX() {
		synchronized(_positionLock) {
			return _x;
		}
	}

	@Override
	public float getLightY() {
		synchronized(_positionLock) {
			return _y;
		}
	}

	@Override
	public float getLightZ() {
		synchronized(_positionLock) {
			return _z;
		}
	}

	@Override
	public void setLightX(float x) {
		synchronized(_positionLock) {
			_x = x;
		}
	}

	@Override
	public void setLightY(float y) {
		synchronized(_positionLock) {
			_y = y;
		}
	}

	@Override
	public void setLightZ(float z) {
		synchronized(_positionLock) {
			_z = z;
		}
	}
}

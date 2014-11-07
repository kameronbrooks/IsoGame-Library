package org.wasteland.isogame.light;

public interface ILightSource {
	float[] getColor();
	void getColor(float[] buffer);
	void setColor(float r, float g, float b);
	void setColor(float[] color);
	void setIntensity(float i);
	float getIntensity();
	
	void setRadius(float rad);
	float getRadius();
	
	float getLightX();
	float getLightY();
	float getLightZ();
	
	void setLightX(float x);
	void setLightY(float y);
	void setLightZ(float z);
	
}

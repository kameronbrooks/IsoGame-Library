package org.wasteland.isogame.blockhardness;

public class BlockHardness {
	public static final int GAS = 0x01;
	public static final int LIQUID = 0x02;
	public static final int PARTICULATE = 0x04;
	public static final int SOLID_PARTICULATE = 0x08;
	public static final int LIGHT_SOLID = 0x10;
	public static final int DENSE_SOLID = 0x20;
	public static final int METAL = 0x40;
	public static final int INDESTRUCTABLE = 0x80;
	
	public static float LIQUID_ENTITY_VELOCITY_MULTIPLIER = 0.5f;
	
	public static float getVelocityMultiplier(final int hardness) {
		switch(hardness) {
		case GAS:
			return 1.0f;
		case LIQUID:
			return LIQUID_ENTITY_VELOCITY_MULTIPLIER;
		default:
			return 0.0f;
		}
	}
}

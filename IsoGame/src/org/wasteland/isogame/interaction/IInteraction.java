package org.wasteland.isogame.interaction;

public interface IInteraction {
	public String getName();
	public int getID();
	public float getDuration();
	
	public boolean begin(IInteractor ent1, IInteractor ent2);
	public void interupt();
	public boolean end();
	
	
	
}

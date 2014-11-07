package org.wasteland.isogame.interaction;

public interface IInteractor {
	public void beginInteraction(IInteraction interaction);
	public void requestInteruptInteraction();
	public void endInteraction();
}

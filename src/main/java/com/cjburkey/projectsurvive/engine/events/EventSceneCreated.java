package com.cjburkey.projectsurvive.engine.events;

import com.cjburkey.projectsurvive.engine.Scene;
import com.cjburkey.projectsurvive.engine.event.Event;

public class EventSceneCreated extends Event {
	
	private Scene scene;
	
	public EventSceneCreated(Scene scene) {
		this.scene = scene;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public String getName() {
		return getClass().getName();
	}

	public boolean canCancel() {
		return false;
	}
	
}
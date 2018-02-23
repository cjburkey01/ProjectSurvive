package com.cjburkey.projectsurvive.engine;

public abstract class Game {
	
	public abstract String getName();
	public abstract String getVersion();
	
	public void onInit() {
	}
	
	public void onUpdate() {
	}
	
	public GameObject getMainCamera() {
		return Scene.getActiveScene().getMainCamera();
	}
	
}
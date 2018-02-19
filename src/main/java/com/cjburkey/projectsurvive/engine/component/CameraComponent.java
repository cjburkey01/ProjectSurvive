package com.cjburkey.projectsurvive.engine.component;

import com.cjburkey.projectsurvive.engine.Camera;
import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameObject;

public class CameraComponent extends GameComponent {
	
	private Camera camera;
	
	public CameraComponent(GameObject parent, Camera camera) {
		super(parent);
		this.camera = camera;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
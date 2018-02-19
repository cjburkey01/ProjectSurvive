package com.cjburkey.projectsurvive.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderEngine {
	
	private Camera mainCamera;
	
	public abstract void init();
	public abstract void render(GameObject root);
	public abstract void destroy();
	
	public Camera getMainCamera() {
		return mainCamera;
	}
	
	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
	
	private static final List<RenderEngine> engines = new ArrayList<RenderEngine>();
	
	public static void onInit() {
		for (RenderEngine engine : engines) {
			engine.init();
		}
	}
	
	public static void onRender(GameObject root) {
		for (RenderEngine engine : engines) {
			engine.render(root);
		}
	}
	
	public static void addEngine(RenderEngine engine) {
		if (!engines.contains(engine)) {
			engines.add(engine);
		}
	}
	
}
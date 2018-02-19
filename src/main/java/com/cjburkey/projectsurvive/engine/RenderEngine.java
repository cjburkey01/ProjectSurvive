package com.cjburkey.projectsurvive.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderEngine {
	
	private GameObject mainCamera;
	
	public abstract void init();
	public abstract void render(GameObject root);
	public abstract void destroy();
	
	public GameObject getMainCamera() {
		return mainCamera;
	}
	
	public void setMainCamera(GameObject mainCamera) {
		this.mainCamera = mainCamera;
		Logger.log("Camera set to: " + mainCamera);
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
	
	public static RenderEngine[] getEngines() {
		return engines.toArray(new RenderEngine[engines.size()]);
	}
	
}
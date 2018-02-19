package com.cjburkey.projectsurvive.engine;

import com.cjburkey.projectsurvive.engine.component.CameraComponent;
import com.cjburkey.projectsurvive.engine.event.EventHandler;
import com.cjburkey.projectsurvive.engine.events.EventSceneCreated;

public class Scene {
	
	private static Scene scene;
	
	private Camera cam;
	private GameObject root;
	
	private Scene() {
		root = new GameObject("RootObject");
		
		cam = new Camera();
		GameObject camObj = root.addChild("MainCamera");
		camObj.addComponent(new CameraComponent(camObj, cam));
		for (RenderEngine engine : RenderEngine.getEngines()) {
			engine.setMainCamera(camObj);
		}
	}
	
	public GameObject getRoot() {
		return root;
	}
	
	public static void create() {
		scene = new Scene();
		EventHandler.getEventHandler().triggerEvent(new EventSceneCreated(scene));
	}
	
	public static Scene getActiveScene() {
		return scene;
	}
	
}
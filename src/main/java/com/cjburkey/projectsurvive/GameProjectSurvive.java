package com.cjburkey.projectsurvive;

import org.lwjgl.glfw.GLFW;
import com.cjburkey.projectsurvive.engine.Game;
import com.cjburkey.projectsurvive.engine.GameEngine;
import com.cjburkey.projectsurvive.engine.GameObject;
import com.cjburkey.projectsurvive.engine.Input;
import com.cjburkey.projectsurvive.engine.Mesh;
import com.cjburkey.projectsurvive.engine.Scene;
import com.cjburkey.projectsurvive.engine.component.KeyboardController;
import com.cjburkey.projectsurvive.engine.component.MeshComponent;
import com.cjburkey.projectsurvive.engine.component.MouseRotation;
import com.cjburkey.projectsurvive.engine.event.EventHandler;
import com.cjburkey.projectsurvive.engine.events.EventSceneCreated;

public class GameProjectSurvive extends Game {
	
	public String getName() {
		return "Project Survive";
	}
	
	public String getVersion() {
		return "0.0.1";
	}
	
	private Mesh demoMesh() {
		float[] verts = new float[] {
			-0.5f, 0.5f, 0.5f,		// 0
			-0.5f, -0.5f, 0.5f,		// 1
			0.5f, -0.5f, 0.5f,		// 2
			0.5f, 0.5f, 0.5f,		// 3
			
			-0.5f, 0.5f, -0.5f,		// 4
			-0.5f, -0.5f, -0.5f,	// 5
			0.5f, -0.5f, -0.5f,		// 6
			0.5f, 0.5f, -0.5f		// 7
		};
		
		int[] inds = new int[] {
			0, 1, 2,	// Front
			2, 3, 0,
			
			6, 5, 4,	// Back
			4, 7, 6,
		};
		
		Mesh mesh = new Mesh(1);
		mesh.setMesh(verts, inds);
		return mesh;
	}
	
	public void onInit() {
		Mesh mesh = demoMesh();
		EventHandler.getEventHandler().addListener(EventSceneCreated.class, (e) -> {
			// Create a test mesh in the world
			GameObject meshObj = Scene.getActiveScene().getRoot().addChild("MeshTest");
			meshObj.getTransform().getPosition().z = -1.0f;
			meshObj.addComponent(new MeshComponent(meshObj));
			meshObj.getComponent(MeshComponent.class).setMesh(mesh);
			
			// Allow camera control.
			getMainCamera().addComponent(new KeyboardController(getMainCamera()));
			getMainCamera().addComponent(new MouseRotation(getMainCamera()));
		});
	}
	
	public void onUpdate() {
		if (Input.isKeyFirstDown(GLFW.GLFW_KEY_ESCAPE)) {
			GameEngine.getEngine().exit();
		}
	}
	
}
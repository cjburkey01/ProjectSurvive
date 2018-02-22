package com.cjburkey.projectsurvive.engine;

import com.cjburkey.projectsurvive.engine.component.CameraComponent;
import com.cjburkey.projectsurvive.engine.component.KeyboardController;
import com.cjburkey.projectsurvive.engine.component.MeshComponent;
import com.cjburkey.projectsurvive.engine.event.EventHandler;
import com.cjburkey.projectsurvive.engine.events.EventSceneCreated;

public class BasicRenderer extends RenderEngine {
	
	private ShaderBasic shader;
	
	private Mesh mesh;
	
	public void init() {
		shader = new ShaderBasic();
		shader.init();
		
		float[] verts = new float[] {
			-0.5f, 0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f,
			0.5f, 0.5f, 0.0f
		};
		
		int[] inds = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		mesh = new Mesh(1);
		mesh.setMesh(verts, inds);
		
		EventHandler.getEventHandler().addListener(EventSceneCreated.class, (e) -> {
			GameObject meshObj = Scene.getActiveScene().getRoot().addChild("MeshTest");
			meshObj.getTransform().getPosition().z = -1.0f;
			meshObj.addComponent(new MeshComponent(meshObj));
			//meshObj.addComponent(new KeyboardController(meshObj));
			meshObj.getComponent(MeshComponent.class).setMesh(mesh);
			
			getMainCamera().addComponent(new KeyboardController(getMainCamera()));
		});
	}
	
	public void render(GameObject root) {
		shader.bind();
		shader.render(getMainCamera());
		
		renderObj(root);
		
		Shader.unbind();
	}
	
	private void renderObj(GameObject obj) {
		shader.setUniform("modelViewMatrix", getMainCamera().getComponent(CameraComponent.class).getModelViewMatrix(obj));
		obj.onRender();
		for (GameObject child : obj.getChildren()) {
			renderObj(child);
		}
	}
	
	public void destroy() {
		shader.destroy();
		
		mesh.cleanup();
	}
	
}
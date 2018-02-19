package com.cjburkey.projectsurvive.engine;

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
			-0.5f, 0.5f, -0.5f,
			-0.5f, -0.5f, -0.5f,
			0.5f, -0.5f, -0.5f,
			0.5f, 0.5f, -0.5f
		};
		
		int[] inds = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		mesh = new Mesh();
		mesh.setMesh(verts, inds);
		
		EventHandler.getEventHandler().addListener(EventSceneCreated.class, (e) -> {
			GameObject meshObj = Scene.getActiveScene().getRoot().addChild("MeshTest");
			meshObj.addComponent(new MeshComponent(meshObj));
			meshObj.getComponent(MeshComponent.class).setMesh(mesh);
		});
	}
	
	public void render(GameObject root) {
		shader.bind();
		shader.render(getMainCamera());
		
		renderObj(root);
		
		Shader.unbind();
	}
	
	private void renderObj(GameObject obj) {
		shader.setUniform("worldMatrix", obj.getTransform().getWorldMatrix());
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
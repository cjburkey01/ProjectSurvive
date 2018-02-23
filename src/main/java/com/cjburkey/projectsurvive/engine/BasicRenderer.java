package com.cjburkey.projectsurvive.engine;

import com.cjburkey.projectsurvive.engine.component.CameraComponent;

public class BasicRenderer extends RenderEngine {
	
	private ShaderBasic shader;
	
	private Mesh mesh;
	
	public void init() {
		shader = new ShaderBasic();
		shader.init();
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
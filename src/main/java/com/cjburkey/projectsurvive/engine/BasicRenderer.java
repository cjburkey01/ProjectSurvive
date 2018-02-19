package com.cjburkey.projectsurvive.engine;

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
	}
	
	public void render(GameObject root) {
		shader.bind();
		shader.render(getMainCamera());
		
		mesh.render();
		
		Shader.unbind();
		
		//renderObj(root);
	}
	
//	private void renderObj(GameObject obj) {
//		for (GameObject child : obj.getChildren()) {
//			renderObj(child);
//		}
//	}
	
	public void destroy() {
		shader.destroy();
		
		mesh.cleanup();
	}
	
}
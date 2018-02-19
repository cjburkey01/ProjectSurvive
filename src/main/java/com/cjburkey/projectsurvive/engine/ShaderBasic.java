package com.cjburkey.projectsurvive.engine;

import static org.lwjgl.opengl.GL20.*;

public class ShaderBasic extends Shader {
	
	public static final String VERTEX_SHADER = "#version 330\n\nlayout (location = 0) in vec3 position;\n\nvoid main() {\n\tgl_Position = vec4(position, 1.0);\n}";
	public static final String FRAGMENT_SHADER = "#version 330\n\nout vec4 fragColor;\n\nvoid main() {\n\tfragColor = vec4(1.0, 1.0, 1.0, 1.0);\n}";
	
	public void init() {
		addShaderFromFile("/shader/basic/shaderBasicVertex.glsl", GL_VERTEX_SHADER);
		addShaderFromFile("/shader/basic/shaderBasicFragment.glsl", GL_FRAGMENT_SHADER);
		link();
		
		addUniform("projectionMatrix");
	}
	
	public void render(Camera camera) {
		setUniform("projectionMatrix", camera.getProjection());
	}
	
}
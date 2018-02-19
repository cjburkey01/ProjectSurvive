package com.cjburkey.projectsurvive.engine;

import static org.lwjgl.opengl.GL20.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class Shader {
	
	private int program;
	private final List<Integer> shaders;
	private final Map<String, Integer> uniforms;
	
	protected Shader() {
		program = glCreateProgram();
		shaders = new ArrayList<Integer>();
		uniforms = new HashMap<String, Integer>();
	}
	
	public abstract void init();
	public abstract void render(GameObject camera);
	
	public void destroy() {
		unbind();
		if (program != 0) {
			glDeleteProgram(program);
		}
	}
	
	protected void addShaderFromFile(String path, int type) {
		InputStream stream = getClass().getResourceAsStream(path);
		if (stream == null) {
			throw new GameEngineException("Failed to locate file for shader: " + path);
		}
		StringBuilder in;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			in = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				in.append(line);
				in.append('\n');
			}
			reader.close();
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
			throw new GameEngineException("Failed to read file for shader: " + path);
		}
		if (in != null) {
			addShader(in.toString(), type);
		}
	}
	
	protected void addShader(String source, int type) {
		int id = glCreateShader(type);
		if (id == 0) {
			throw new GameEngineException("Failed to create shader in shader program: " + getClass().getName());
		}
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
			throw new GameEngineException("Shader failed to compile: " + glGetShaderInfoLog(id));
		}
		shaders.add(id);
	}
	
	protected void link() {
		for (Integer i : shaders) {
			glAttachShader(program, i);
		}
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			throw new GameEngineException("Failed to link shader program: " + glGetProgramInfoLog(program));
		}
		for (Integer i : shaders) {
			glDetachShader(program, i);
		}
		shaders.clear();
		glValidateProgram(program);
		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			Logger.logErr("Shader program failed validation: " + glGetProgramInfoLog(program));
		}
	}
	
	protected void addUniform(String name) {
		int loc = glGetUniformLocation(program, name);
		if (loc < 0) {
			throw new GameEngineException("Failed to locate uniform: " + name);
		}
		uniforms.put(name, loc);
	}
	
	protected void setUniform(String name, float value) {
		glUniform1f(getUniformLocation(name), value);
	}
	
	protected void setUniform(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}
	
	protected void setUniform(String name, Matrix4f value) {
		glUniformMatrix4fv(getUniformLocation(name), false, value.get(new float[16]));
	}
	
	private int getUniformLocation(String name) {
		if (!uniforms.containsKey(name)) {
			throw new GameEngineException("Uniform has not been initialized: " + name);
		}
		return uniforms.get(name);
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	public static void unbind() {
		glUseProgram(0);
	}
	
}
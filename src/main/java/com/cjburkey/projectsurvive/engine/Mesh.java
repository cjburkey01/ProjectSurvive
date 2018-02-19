package com.cjburkey.projectsurvive.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryUtil;

public class Mesh {
	
	private final int vao;
	private final int vbo;
	private final int ebo;
	
	private int vertexCount;
	
	public Mesh() {
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		ebo = glGenBuffers();
	}
	
	public void setMesh(float[] verts, int[] indices) {
		vertexCount = indices.length;
		
		glBindVertexArray(vao);
		
		FloatBuffer vertBuff = MemoryUtil.memAllocFloat(verts.length);
		vertBuff.put(verts).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertBuff, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		IntBuffer indBuff = MemoryUtil.memAllocInt(indices.length);
		indBuff.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indBuff, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
		
		MemoryUtil.memFree(vertBuff);
		MemoryUtil.memFree(indBuff);
	}
	
	public void render() {
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glEnableVertexAttribArray(0);
		
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void cleanup() {
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDeleteBuffers(vbo);
		glDeleteBuffers(ebo);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
	}
	
}
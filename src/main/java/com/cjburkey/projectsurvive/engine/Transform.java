package com.cjburkey.projectsurvive.engine;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
	
	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;
	
	public Transform() {
		position = new Vector3f(0.0f, 0.0f, 0.0f);
		rotation = new Quaternionf().identity();
		scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
	}
	
	public Quaternionf getRotation() {
		return rotation;
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
}
package com.cjburkey.projectsurvive.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
	
	private static final float DEG_RAD = (float) (Math.PI / 180.0d);
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	
	public Transform() {
		position = new Vector3f(0.0f, 0.0f, 0.0f);
		rotation = new Vector3f(0.0f, 0.0f, 0.0f);
		scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public Matrix4f getWorldMatrix() {
		return new Matrix4f().identity().translate(position).rotateX(DEG_RAD * rotation.x).rotateY(DEG_RAD * rotation.y).rotateZ(DEG_RAD * rotation.z).scale(scale);
	}
	
}
package com.cjburkey.projectsurvive.engine;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public class Camera {
	
	private Transform transform;
	
	private float fov = 0.0f;
	private float zNear = 0.0f;
	private float zFar = 0.0f;
	private float aspectRatio = 0.0f;
	
	private Matrix4f projection;
	
	public Camera(GameWindow window) {
		transform = new Transform();
		
		fov = 90.0f;
		zNear = 0.01f;
		zFar = 1000.0f;
		Vector2i windowS = window.getWindowSize();
		aspectRatio = (float) windowS.x / windowS.y;
		updateProjection();
		
		cams.add(this);
	}
	
	private void updateProjection() {
		projection = new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
	}
	
	public float getFov() {
		return fov;
	}
	
	public void setFov(float fov) {
		this.fov = fov;
		updateProjection();
	}
	
	public float getzNear() {
		return zNear;
	}
	
	public void setzNear(float zNear) {
		this.zNear = zNear;
		updateProjection();
	}
	
	public float getzFar() {
		return zFar;
	}
	
	public void setzFar(float zFar) {
		this.zFar = zFar;
		updateProjection();
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
		updateProjection();
	}

	public Matrix4f getProjection() {
		return projection;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void destroy() {
		cams.remove(this);
	}
	
	private static final List<Camera> cams = new ArrayList<Camera>();
	
	public static Camera[] getCameras() {
		return cams.toArray(new Camera[cams.size()]);
	}
	
}
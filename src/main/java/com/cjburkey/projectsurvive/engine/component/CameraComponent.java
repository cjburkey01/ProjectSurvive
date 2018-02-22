package com.cjburkey.projectsurvive.engine.component;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.cjburkey.projectsurvive.engine.Camera;
import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameObject;

public class CameraComponent extends GameComponent {
	
	private Camera camera;
	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f modelViewMatrix = new Matrix4f();
	
	public CameraComponent(GameObject parent, Camera camera) {
		super(parent);
		this.camera = camera;
	}
	
	public void onRender() {
		viewMatrix.identity();
		Vector3f pos = getParent().getTransform().getPosition();
		viewMatrix.rotate(new Quaternionf(getParent().getTransform().getRotation()).invert());
		viewMatrix.translate(-pos.x, -pos.y, -pos.z);
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Matrix4f getModelViewMatrix(GameObject obj) {
		modelViewMatrix.identity();
		modelViewMatrix.translate(obj.getTransform().getPosition());
		modelViewMatrix.rotate(obj.getTransform().getRotation());
		modelViewMatrix.scale(obj.getTransform().getScale());
		return viewMatrix.mul(modelViewMatrix);
	}
	
}
package com.cjburkey.projectsurvive.engine.component;

import org.joml.Matrix4f;
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
		Vector3f rot = getParent().getTransform().getRotation();
		viewMatrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1.0f, 0.0f, 0.0f)).rotate((float) Math.toRadians(rot.y), new Vector3f(0.0f, 1.0f, 0.0f)).rotate((float) Math.toRadians(rot.z), new Vector3f(0.0f, 0.0f, 1.0f));
		viewMatrix.translate(-pos.x, -pos.y, -pos.z);
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Matrix4f getModelViewMatrix(GameObject obj) {
		Vector3f rot = obj.getTransform().getRotation();
		modelViewMatrix.identity();
		modelViewMatrix.translate(obj.getTransform().getPosition());
		modelViewMatrix.rotateX((float) Math.toRadians(rot.x)).rotateY((float) Math.toRadians(rot.y)).rotateZ((float) Math.toRadians(rot.z));
		modelViewMatrix.scale(obj.getTransform().getScale());
		return viewMatrix.mul(modelViewMatrix);
	}
	
}
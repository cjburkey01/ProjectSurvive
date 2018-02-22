package com.cjburkey.projectsurvive.engine.component;

import org.joml.Vector2f;
import org.joml.Vector3f;
import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameEngine;
import com.cjburkey.projectsurvive.engine.GameObject;
import com.cjburkey.projectsurvive.engine.Input;
import com.cjburkey.projectsurvive.engine.Logger;

public class MouseRotation extends GameComponent {
	
	public float rotationSpeed = 8.0f;

	private Vector2f rotVel = new Vector2f();
	private Vector2f rotation = new Vector2f();

	public MouseRotation(GameObject parent) {
		super(parent);
	}
	
	public void onUpdate() {
		rotVel.set(0.0f, 0.0f);
		
		Vector2f delta = Input.getMouseDelta();
		rotVel.x -= delta.y;
		rotVel.y -= delta.x;
		if (rotVel.x != 0.0f && rotVel.y != 0.0f) {
			rotVel.normalize();
		}
		rotVel.mul(rotationSpeed * (float) GameEngine.getEngine().getDeltaTime());
		rotation.add(rotVel);
		
		// TODO: DO ROTATION RELATIVE TO CAMERA'S PREVIOUS ROTATION
		
		Vector3f right = new Vector3f(1.0f, 0.0f, 0.0f);
		Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
		
		getParent().getTransform().getRotation().identity();
		getParent().getTransform().getRotation().rotateX(rotation.x).rotateY(rotation.y);
		
		Logger.log(rotation.x + ", " + rotation.y);
	}
	
}
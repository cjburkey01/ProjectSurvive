package com.cjburkey.projectsurvive.engine.component;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameEngine;
import com.cjburkey.projectsurvive.engine.GameObject;
import com.cjburkey.projectsurvive.engine.Input;
import com.cjburkey.projectsurvive.engine.MathHelper;

public class KeyboardController extends GameComponent {

	public float speed = 2.0f;
	
	private Vector3f velocity = new Vector3f();
	
	public KeyboardController(GameObject parent) {
		super(parent);
	}
	
	public void onUpdate() {
		velocity.set(0.0f, 0.0f, 0.0f);
		
		if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) {
			velocity.z -= 1.0f;
		}
		if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) {
			velocity.z += 1.0f;
		}
		if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) {
			velocity.x += 1.0f;
		}
		if (Input.isKeyPressed(GLFW.GLFW_KEY_A)) {
			velocity.x -= 1.0f;
		}
		if (velocity.x != 0.0f || velocity.y != 0.0f || velocity.z != 0.0f) {
			velocity.normalize();
		}
		
		velocity = MathHelper.transform(getParent().getTransform().getRotation(), velocity);
		velocity.mul(speed * (float) GameEngine.getEngine().getDeltaTime());
		getParent().getTransform().getPosition().add(velocity);
	}
	
}
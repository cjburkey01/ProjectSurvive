package com.cjburkey.projectsurvive.engine.component;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameEngine;
import com.cjburkey.projectsurvive.engine.GameObject;
import com.cjburkey.projectsurvive.engine.Input;

public class KeyboardController extends GameComponent {

	public float speed = 2.0f;
	
	private Vector2f velocity = new Vector2f();
	
	public KeyboardController(GameObject parent) {
		super(parent);
	}
	
	public void onUpdate() {
		velocity.set(0.0f, 0.0f);
		if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) {
			velocity.y += 1.0f;
		}
		if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) {
			velocity.y -= 1.0f;
		}
		if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) {
			velocity.x += 1.0f;
		}
		if (Input.isKeyPressed(GLFW.GLFW_KEY_A)) {
			velocity.x -= 1.0f;
		}
		if (velocity.x != 0.0f && velocity.y != 0.0f) {
			velocity.normalize();
		}
		velocity.mul(speed * (float) GameEngine.getEngine().getDeltaTime());
		Vector3f pos = getParent().getTransform().getPosition();
		pos.x += velocity.x;
		pos.y += velocity.y;
		getParent().getTransform().setPosition(pos);
	}
	
}
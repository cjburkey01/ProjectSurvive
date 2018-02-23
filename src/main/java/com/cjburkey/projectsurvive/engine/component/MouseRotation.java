package com.cjburkey.projectsurvive.engine.component;

import org.joml.Vector2f;
import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameObject;
import com.cjburkey.projectsurvive.engine.Input;
import com.cjburkey.projectsurvive.engine.MathHelper;

public class MouseRotation extends GameComponent {
	
	private static final float DEG_RAD = (float) Math.PI / 180.0f;
	
	public float rotationSpeed = 0.3f;

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
		rotVel.mul(rotationSpeed/* * (float) GameEngine.getEngine().getDeltaTime()*/);
		rotation.add(rotVel);
		rotation.x = MathHelper.clamp(rotation.x, -90.0f, 90.0f);
		
		getParent().getTransform().getRotation().rotationYXZ(rotation.y * DEG_RAD, rotation.x * DEG_RAD, 0.0f);
	}
	
}
package com.cjburkey.projectsurvive.engine;

import java.util.HashMap;
import java.util.Map;
import org.joml.Vector2f;

public class Input {
	
	private static final Map<Integer, Boolean> keysDown = new HashMap<>();
	private static final Vector2f mousePos = new Vector2f();
	private static final Vector2f mouseDelta = new Vector2f();
	private static final Vector2f prevMousePos = new Vector2f();
	
	public static void onUpdate() {
		for (Integer i : keysDown.keySet()) {
			keysDown.put(i, false);
		}
		prevMousePos.set(mousePos);
		mouseDelta.set(0.0f, 0.0f);
	}
	
	/**
	 * Checks whether or not this key is currently pressed by the user.
	 * @param key	The GLFW Key to check.
	 * @return	Whether or not this key is currently down.
	 */
	public static boolean isKeyPressed(int key) {
		return keysDown.containsKey(key);
	}
	
	/**
	 * Checks whether or not this key has just been pressed down this frame.
	 * @param key	The GLFW Key to check.
	 * @return	Whether or not this is the first frame that the key is being held down.
	 */
	public static boolean isKeyFirstDown(int key) {
		if (keysDown.containsKey(key)) {
			return keysDown.get(key);
		}
		return false;
	}
	
	public static Vector2f getMouseDelta() {
		return mouseDelta;
	}
	
	public static void onMouseMove(Vector2f pos) {
		mousePos.set(pos);
		mousePos.sub(prevMousePos, mouseDelta);
	}
	
	public static void onKeyPress(int key) {
		keysDown.put(key, true);
	}
	
	public static void onKeyRelease(int key) {
		keysDown.remove(key);
	}
	
}
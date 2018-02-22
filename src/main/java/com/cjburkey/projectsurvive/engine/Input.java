package com.cjburkey.projectsurvive.engine;

import java.util.HashMap;
import java.util.Map;

public class Input {
	
	private static Map<Integer, Boolean> keysDown = new HashMap<>();
	
	public static void onUpdate() {
		for (Integer i : keysDown.keySet()) {
			keysDown.put(i, false);
		}
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
	
	public static void onKeyPress(int key) {
		keysDown.put(key, true);
	}
	
	public static void onKeyRelease(int key) {
		keysDown.remove(key);
	}
	
}
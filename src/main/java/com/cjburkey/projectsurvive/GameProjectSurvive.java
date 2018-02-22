package com.cjburkey.projectsurvive;

import org.lwjgl.glfw.GLFW;
import com.cjburkey.projectsurvive.engine.Game;
import com.cjburkey.projectsurvive.engine.GameEngine;
import com.cjburkey.projectsurvive.engine.Input;

public class GameProjectSurvive extends Game {
	
	public String getName() {
		return "Project Survive";
	}
	
	public String getVersion() {
		return "0.0.1";
	}
	
	public void onUpdate() {
		if (Input.isKeyFirstDown(GLFW.GLFW_KEY_ESCAPE)) {
			GameEngine.getEngine().exit();
		}
	}
	
}
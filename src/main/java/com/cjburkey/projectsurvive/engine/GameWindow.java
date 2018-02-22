package com.cjburkey.projectsurvive.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class GameWindow {
	
	private final long window;
	private final GLCapabilities glCaps;
	
	private Vector2i size;
	private String title;
	
	public GameWindow(int width, int height, String name) {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new GameEngineException("Failed to initialize GLFW.");
		}
		
		size = new Vector2i(width, height);
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(size.x, size.y, title = name, 0, 0);
		if (window == 0) {
			throw new GameEngineException("Failed to initialize GLFW window.");
		}
		center();
		glfwMakeContextCurrent(window);
		setVsync(false);
		show();
		
		glfwSetWindowSizeCallback(window, (window, w, h) -> onResize(new Vector2i(w, h)));
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (window != this.window) {
				return;
			}
			if (action == GLFW_PRESS) {
				Input.onKeyPress(key);
			}
			if (action == GLFW_RELEASE) {
				Input.onKeyRelease(key);
			}
		});
		
		glfwSetCursorPosCallback(window, (window, x, y) -> {
			if (window != this.window) {
				return;
			}
			Input.onMouseMove(new Vector2f((float) x, (float) y));
		});
		
		glCaps = GL.createCapabilities();
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glClearColor(100 / 255.0f, 149 / 255.0f, 237 / 255.0f, 1.0f);
		
		lockCursor(true);
	}
	
	public void lockCursor(boolean lock) {
		glfwSetInputMode(window, GLFW_CURSOR, (lock) ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
	}
	
	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(window, title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setVsync(boolean vsync) {
		glfwSwapInterval((vsync) ? 1 : 0);
	}
	
	public void setSize(Vector2i size) {
		if (window == 0) {
			throw new GameEngineException("The window has not yet been initialized.");
		}
		glfwSetWindowSize(window, size.x, size.y);
	}
	
	private void onResize(Vector2i newSize) {
		size = new Vector2i(newSize);
		glViewport(0, 0, size.x, size.y);
		for (Camera cam : Camera.getCameras()) {
			cam.setAspectRatio((float) size.x / size.y);
		}
		Logger.log("Window size set to: " + newSize.x + "x" + newSize.y);
	}
	
	public void center() {
		if (window == 0) {
			throw new GameEngineException("The window has not yet been initialized.");
		}
		Vector2i mSize = getMonitorSize();
		Vector2i wSize = getWindowSize();
		glfwSetWindowPos(window, (mSize.x - wSize.x) / 2, (mSize.y - wSize.y) / 2);
	}
	
	public void show() {
		glfwShowWindow(window);
	}
	
	public void hide() {
		glfwHideWindow(window);
	}
	
	public Vector2i getMonitorSize() {
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		return new Vector2i(vid.width(), vid.height());
	}
	
	public Vector2i getWindowSize() {
		if (window == 0) {
			throw new GameEngineException("The window has not yet been initialized.");
		}
		int[] w = new int[1];
		int[] h = new int[1];
		glfwGetWindowSize(window, w, h);
		if (w[0] <= 0 || h[0] <= 0) {
			throw new GameEngineException("Failed to determine GLFW window size.");
		}
		return new Vector2i(w[0], h[0]);
	}
	
	public boolean getShouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void pollEvents() {
		glfwPollEvents();
	}
	
	public void destroy() {
		hide();
		Callbacks.glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		Logger.log("Destroyed window.");
	}
	
	public GLCapabilities getGlCapabilities() {
		return glCaps;
	}
	
}
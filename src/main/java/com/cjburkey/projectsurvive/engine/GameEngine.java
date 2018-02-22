package com.cjburkey.projectsurvive.engine;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

public class GameEngine {
	
	public static final long EXPECTED_TPS = 60;
	public static Game loadedGame = null;
	private static GameEngine gameEngine;
	
	private Reflections ref;
	private GameWindow window;
	
	private double steps = 0.0f;
	private double delta = 0.0f;
	private long fps = 0;
	private boolean running = false;
	
	private BasicRenderer renderer;
	
	private String windowStartTitle;
	
	private GameEngine() {
		ref = new Reflections("com.cjburkey");
	}
	
	private void loadGame() {
		Set<Class<? extends Game>> classes = ref.getSubTypesOf(Game.class);
		List<Class<? extends Game>> games = new ArrayList<>(classes);
		if (games.size() != 1) {
			throw new GameEngineException("Multiple game classes found, unable to select game.");
		}
		try {
			loadedGame = games.get(0).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new GameEngineException("Failed to create an instance of the game class.");
		}
		if (loadedGame == null) {
			throw new GameEngineException("No game was loaded...failure to launch game.");
		}
		Logger.log("Loading game: " + loadedGame.getName() + ". Version: " + loadedGame.getVersion());
	}
	
	private void createWindow() {
		Logger.log("Creating GLFW window...");
		window = new GameWindow(640, 480, "CJ Burkey's Game Engine");
		Logger.log("Created window.");
	}
	
	private void initRender() {
		Logger.log("Initializing renderers.");
		renderer = new BasicRenderer();
		RenderEngine.addEngine(renderer);
	}
	
	private void startGameLoop() {
		Logger.log("Starting game loop.");
		double last = getTime();
		double lastFpsCheck = getTime();
		double fpsTime = 0.3873f;
		int rfps = 0;
		if (windowStartTitle == null) {
			windowStartTitle = window.getTitle();
		}
		running = true;
		while (running) {
			double loopStart = getTime();
			delta = loopStart - last;
			steps += delta;
			
			tick();
			if (window.getShouldClose()) {
				break;
			}
			
			rfps ++;
			if (loopStart - lastFpsCheck >= fpsTime) {
				lastFpsCheck = loopStart;
				double efps = 1 / delta;
				fps = (int) (rfps / ((fpsTime != 0.0f) ? (fpsTime) : (0.001f)));
				rfps = 0;
				window.setTitle(windowStartTitle + " | CFPS: " + MathHelper.format(efps, 2) + " | TFPS: " + fps + " | TIME: " + MathHelper.format(getGameTime(), 2));
			}
			
			last = loopStart;
		}
		Logger.log("Exiting game.");
		window.destroy();
	}
	
	public void exit() {
		running = false;
	}
	
	public double getGameTime() {
		return steps;
	}
	
	public long getFps() {
		return fps;
	}
	
	public double getDeltaTime() {
		return delta;
	}
	
	private void tick() {
		window.pollEvents();
		
		Scene.getActiveScene().getRoot().onUpdate();
		loadedGame.onUpdate();
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		RenderEngine.onRender(Scene.getActiveScene().getRoot());
		
		Input.onUpdate();
		window.swapBuffers();
	}
	
	private double getTime() {
		return System.nanoTime() / 1000000000.0d;
	}
	
	public GameWindow getWindow() {
		return window;
	}
	
	public static void main(String[] args) {
		gameEngine = new GameEngine();
		gameEngine.loadGame();
		gameEngine.createWindow();
		gameEngine.initRender();
		RenderEngine.onInit();
		Scene.create();
		gameEngine.startGameLoop();
		Scene.getActiveScene().getRoot().onDestroy();
	}
	
	public static GameEngine getEngine() {
		return gameEngine;
	}
	
}
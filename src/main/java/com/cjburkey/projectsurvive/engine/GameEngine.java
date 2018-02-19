package com.cjburkey.projectsurvive.engine;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

public class GameEngine {
	
	public static final long EXPECTED_TPS = 60;
	private static GameEngine gameEngine;
	
	private Reflections ref;
	private GameWindow window;
	
	private double steps = 0.0f;
	private double delta = 0.0f;
	private long fps = 0;
	
	private BasicRenderer renderer;
	
	private GameEngine() {
		ref = new Reflections("com.cjburkey");
	}
	
	private void loadGame() {
		Set<Class<? extends Game>> classes = ref.getSubTypesOf(Game.class);
		List<Class<? extends Game>> games = new ArrayList<>(classes);
		if (games.size() != 1) {
			throw new GameEngineException("Multiple game classes found, unable to select game.");
		}
		Game game = null;
		try {
			game = games.get(0).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new GameEngineException("Failed to create an instance of the game class.");
		}
		Logger.log("Loading game: " + game.getName() + ". Version: " + game.getVersion());
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
		double previous = getTime();
		double lastFps = getTime();
		long fps = 0;
		while (true) {
			double loopStartTime = getTime();
			delta = loopStartTime - previous;
			previous = loopStartTime;
			steps += delta;
			
			tick();
			if (window.getShouldClose()) {
				window.destroy();
				break;
			}
			fps++;
			if (loopStartTime - lastFps > 1.0f) {
				this.fps = fps;
				fps = 0;
				lastFps = loopStartTime;
				Logger.log("TPS: " + getFps() + ". Steps: " + MathHelper.format(getGameTime(), 2));
			}
			
			sync(loopStartTime);
		}
		Logger.log("Exiting game.");
	}
	
	private void sync(double loopStartTime) {
		float loopSlot = 1.0f / EXPECTED_TPS;
		double endTime = loopStartTime + loopSlot;
		while (getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch(Exception e) { throw new GameEngineException("Failed to sleep for 1ms in game loop."); }
		}
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
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		RenderEngine.onRender(Scene.getActiveScene().getRoot());
		
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
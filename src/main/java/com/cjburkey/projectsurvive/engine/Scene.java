package com.cjburkey.projectsurvive.engine;

public class Scene {
	
	private static Scene scene;
	
	private GameObject root;
	
	private Scene() {
		root = new GameObject("RootObject");
	}
	
	public GameObject getRoot() {
		return root;
	}
	
	public static void create() {
		scene = new Scene();
	}
	
	public static Scene getActiveScene() {
		return scene;
	}
	
}
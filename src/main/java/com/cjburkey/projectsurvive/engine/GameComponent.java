package com.cjburkey.projectsurvive.engine;

public abstract class GameComponent {
	
	private GameObject parent;
	
	public GameComponent(GameObject parent) {
		this.parent = parent;
	}
	
	public void onUpdate() {  }
	public void onRender() {  }
	public void onDestroy() {  }
	
	public GameObject getParent() {
		return parent;
	}
	
	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	
}
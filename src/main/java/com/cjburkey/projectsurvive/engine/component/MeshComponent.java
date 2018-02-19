package com.cjburkey.projectsurvive.engine.component;

import com.cjburkey.projectsurvive.engine.GameComponent;
import com.cjburkey.projectsurvive.engine.GameEngine;
import com.cjburkey.projectsurvive.engine.GameObject;
import com.cjburkey.projectsurvive.engine.Mesh;

public class MeshComponent extends GameComponent {

	private Mesh mesh;
	
	public MeshComponent(GameObject parent) {
		super(parent);
	}
	
	public void onUpdate() {
		// TODO: FIX DOUBLE BUG
		getParent().getTransform().getPosition().x += 1.0f * GameEngine.getEngine().getDeltaTime();
	}
	
	public void onRender() {
		if (mesh != null) {
			mesh.render();
		}
	}
	
	public void onDestroy() {
		if (mesh != null) {
			mesh.cleanup();
		}
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
}
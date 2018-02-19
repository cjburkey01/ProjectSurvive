package com.cjburkey.projectsurvive.engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
	
	private String name;
	private Transform transform;
	private List<GameComponent> components;
	private List<GameObject> children;
	
	public GameObject() {
		this("GameObject");
	}
	
	public GameObject(String name) {
		setName(name);
		transform = new Transform();
		components = new ArrayList<GameComponent>();
		children = new ArrayList<GameObject>();
	}
	
	public void onUpdate() {
		for (GameComponent obj : getComponents()) {
			obj.onUpdate(this);
		}
		for (GameObject obj : getChildren()) {
			obj.onUpdate();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public GameObject[] getChildren() {
		return children.toArray(new GameObject[children.size()]);
	}
	
	public GameComponent[] getComponents() {
		return components.toArray(new GameComponent[components.size()]);
	}
	
	public void addChild(GameObject child) {
		if (!children.contains(child)) {
			children.add(child);
		}
	}
	
	public void addComponent(GameComponent component) {
		if (!components.contains(component)) {
			components.add(component);
		}
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public String toString() {
		return getName();
	}
	
}
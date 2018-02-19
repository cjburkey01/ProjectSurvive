package com.cjburkey.projectsurvive.engine.event;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
	
	private static EventHandler eventHandler;
	
	private final List<EventListener> listeners;
	private final List<EventListener> toRemove;
	
	private EventHandler() {
		listeners = new ArrayList<EventListener>();
		toRemove = new ArrayList<EventListener>();
	}
	
	public void addListener(Class<? extends Event> event, EventCall onCall) {
		listeners.add(new EventListener(event, onCall));
	}
	
	public boolean triggerEvent(Event event) {
		for (EventListener listener : listeners) {
			if (event.canCancel() && event.isCancelled()) {
				return true;
			}
			if (listener.getType().equals(event.getClass())) {
				listener.getOnCall().onCall(event);
			}
		}
		return false;
	}
	
	public void removeListeners(Class<? extends Event> event) {
		for (EventListener listener : listeners) {
			if (listener.getType().equals(event)) {
				toRemove.add(listener);
			}
		}
		for (EventListener listener : toRemove) {
			listeners.remove(listener);
		}
		toRemove.clear();
	}
	
	public void removeListeners() {
		listeners.clear();
	}
	
	public static EventHandler getEventHandler() {
		if (eventHandler == null) {
			eventHandler = new EventHandler();
		}
		return eventHandler;
	}
	
}
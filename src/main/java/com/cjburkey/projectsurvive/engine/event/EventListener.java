package com.cjburkey.projectsurvive.engine.event;

public class EventListener {
	
	private Class<? extends Event> type;
	private EventCall onCall;
	
	public EventListener(Class<? extends Event> type, EventCall onCall) {
		this.type = type;
		this.onCall = onCall;
	}
	
	public Class<? extends Event> getType() {
		return type;
	}
	
	public EventCall getOnCall() {
		return onCall;
	}
	
}
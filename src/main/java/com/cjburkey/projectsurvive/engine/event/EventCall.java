package com.cjburkey.projectsurvive.engine.event;

@FunctionalInterface
public interface EventCall {
	
	void onCall(Event e);
	
}
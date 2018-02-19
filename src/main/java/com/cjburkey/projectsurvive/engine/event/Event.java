package com.cjburkey.projectsurvive.engine.event;

public abstract class Event {
	
	private boolean cancelled;
	
	public abstract String getName();
	public abstract boolean canCancel();
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void cancel() {
		cancelled = true;
	}
	
}
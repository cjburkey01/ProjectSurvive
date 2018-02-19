package com.cjburkey.projectsurvive.engine;

public class Logger {
	
	public static void log(Object msg) {
		String out = (msg == null) ? "null" : msg.toString();
		System.out.println("[L] " + out);
	}
	
	public static void logErr(Object msg) {
		String out = (msg == null) ? "null" : msg.toString();
		System.err.println("[E] " + out);
	}
	
}
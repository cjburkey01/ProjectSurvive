package com.cjburkey.projectsurvive.engine;

public class MathHelper {
	
	public static String format(double num, int places) {
		return String.format("%." + places + "f", num);
	}
	
}
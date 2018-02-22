package com.cjburkey.projectsurvive.engine;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class MathHelper {
	
	private static Matrix4f lookAt = new Matrix4f();
	
	public static String format(double num, int places) {
		return String.format("%." + places + "f", num);
	}
	
	/**
	 * Calculates a rotation that would make the supplied object look towards the supplied target coordinates.
	 * @param self		The position of the object to rotate.
	 * @param target	The target vector3 at which to look.
	 * @return			The rotation at which "self" would be looking at "target"
	 */
	public static Quaternionf lookAt(Vector3f self, Vector3f target) {
		lookAt.identity();
		lookAt.lookAt(self.x, self.y, self.z, target.x, target.y, target.z, 0.0f, 1.0f, 0.0f);
		Quaternionf quat = new Quaternionf().identity();
		lookAt.getNormalizedRotation(quat);
		return quat;
	}
	
	public static Vector3f transform(Quaternionf rotation, Vector3f direction) {
		direction = new Vector3f(direction);
		return rotation.transform(direction);
	}
	
}
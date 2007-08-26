package org.jagatoo.loaders.models.ms3d.utils;

import org.openmali.vecmath.Matrix3f;
import org.openmali.vecmath.Quat4f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;
import org.openmali.vecmath.util.MatrixUtils;

public class RotationUtils {


	   /**
	 * Convert Euler angles (in degrees) to a (normalized) Quaternion.
	 *
	 * @param rotX
	 *            Rotation about the X axis, in degrees
	 * @param rotY
	 *            Rotation about the Y axis, in degrees
	 * @param rotZ
	 *            Rotation about the Z axis, in degrees
	 * @return The Quaternion representing the same rotation.
	 * @author Martin Baker (euclideanspace.com)
	 */
	public static Quat4f toQuaternion(float rotX, float rotY, float rotZ) {

		float x = (float) Math.toRadians(rotX);
		float y = (float) Math.toRadians(rotY);
		float z = (float) Math.toRadians(rotZ);
		Matrix3f matrix = MatrixUtils.eulerToMatrix3f(x, y, z);
		Quat4f quat = new Quat4f();
		quat.set(matrix);
		quat.normalize();
		return quat;

	}

	/**
	 * Convert Euler angles (in degrees) to a (normalized) Quaternion.
	 * @param tuple
	 * @return
	 */
	public static Quat4f toQuaternion( Tuple3f tuple ) {
		return toQuaternion( tuple.x, tuple.y, tuple.z );
	}

	/**
	 * Convert a Quaternion to Euler angles (in degrees) Note : the Quaternion
	 * can be non-normalized.
	 *
	 * @param quaternion
	 *            The quaternion
	 * @return The euler angles (in degrees)
	 * @author Martin Baker (euclideanspace.com)
	 */
	public static Vector3f toEuler(Quat4f quaternion) {

		Vector3f euler = new Vector3f();
		Matrix3f matrix = new Matrix3f();
		matrix.set(quaternion);
		euler = MatrixUtils.matrixToEuler(matrix);
		euler.x = (float) (Math.toDegrees(euler.x));
		euler.y = (float) (Math.toDegrees(euler.y));
		euler.z = (float) (Math.toDegrees(euler.z));
		return euler;

	}


}

package org.jagatoo.loaders.models.collada.datastructs.animation;

import org.jagatoo.loaders.models.collada.jibx.XMLAnimation;
import org.openmali.vecmath.Point3f;

public class KeyFrame {

	public final static byte X_AXIS = 0;
	public final static byte Y_AXIS = 1;
	public final static byte Z_AXIS = 2;

	/**
	 * Key frame time
	 */
	private float time;

	/**
	 * Key frame transform values: translation or rotation (in radians)
	 */
	private Point3f values;


	/**
	 * Creates a translation key frame
	 * @param bone
	 * @param time frame time
	 * @param values float values of the translation
	 * @param valueIndex first value index
	 * @return a new key frame
	 */
	public static KeyFrame buildTranslationKeyFrame(float time, float[] values, int valueIndex) {
		KeyFrame frame = new KeyFrame();
		frame.time = time;
		frame.values = new Point3f( values[ valueIndex ], values[ valueIndex + 1 ], values[ valueIndex + 2 ] );
		return frame;
	}


	/**
	 * Creates a rotation key frame
	 * @param time frame time
	 * @param angle rotation angle in degrees
	 * @return a new key frame
	 */
	public static KeyFrame buildRotationKeyFrame( float time, float angle, byte axis ) {
		KeyFrame frame = new KeyFrame();
		frame.time = time;
		float radians = (float)Math.toRadians( angle );
		if( axis == X_AXIS ) {
			frame.values = new Point3f( radians, 0, 0 );
		} else if( axis == Y_AXIS ) {
			frame.values = new Point3f( 0, radians, 0 );
		} else {
			frame.values = new Point3f( 0, 0, radians );
		}
		return frame;
	}


	public float getTime() {
		return time;
	}


	public Point3f getValues() {
		return values;
	}

}





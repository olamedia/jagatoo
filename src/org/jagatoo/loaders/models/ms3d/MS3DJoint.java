package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Point3f;

public class MS3DJoint {

	/*public int flags;*/
	public String name;
	public String parent;
	public Point3f rotation;
	public Point3f position;
	public int numRotFrames;
	public int numTransFrames;

	public MS3DKeyFrame[] rotKeyFrames;
	public MS3DKeyFrame[] transKeyFrames;


	//logic data
	public int parentIndex;
	public Matrix4f matLocal;
	public Matrix4f matAbs;
	public Matrix4f matFinal;
	public  int curRotFrame;
	public  int curTransFrame;

	public MS3DJoint(LittleEndianDataInputStream in) throws IOException {
		curRotFrame = 0;
		curTransFrame = 0;
		parentIndex = -1;
		matLocal = new Matrix4f();
		matAbs = new Matrix4f();
		matFinal = new Matrix4f();

		/*flags = */in.readByte();
		name = BinaryUtils.readString(in, 32);
		parent = BinaryUtils.readString(in, 32);
		rotation = BinaryUtils.readPoint3f( in );
		position = BinaryUtils.readPoint3f( in );
		numRotFrames = in.readUnsignedShort();
		numTransFrames = in.readUnsignedShort();

		rotKeyFrames = new MS3DKeyFrame[ numRotFrames ];
		for (int i = 0; i < rotKeyFrames.length; i++) {
			rotKeyFrames[ i ] = new MS3DKeyFrame( in );
		}

		transKeyFrames = new MS3DKeyFrame[ numTransFrames ];
		for (int i = 0; i < transKeyFrames.length; i++) {
			transKeyFrames[ i ] = new MS3DKeyFrame( in );
		}
	}

	/**
	 * Selects the current transformation key frame, based on the current time
	 * @param currenTime beetween 0 and the end of the animation, in seconds
	 * @return frame index selected
	 */
	public int selectCurrentTransFrame(float currentTime) {
		this.curTransFrame = searchNextFrame( transKeyFrames, currentTime );
		return this.curTransFrame;
	}

	/**
	 * Selects the current rotation key frame, based on the current time
	 * @param currenTime beetween 0 and the end of the animation, in seconds
	 * @return frame index selected
	 */
	public int selectCurrentRotFrame(float currentTime) {
		this.curRotFrame = searchNextFrame( rotKeyFrames, currentTime );
		return this.curRotFrame;
	}

	/**
	 * Searchs the next key frame according to the current time
	 * @param frames
	 * @param currentTime in seconds
	 * @return selected key frame index
	 */
	private int searchNextFrame( MS3DKeyFrame[] frames, float currentTime ) {
		int frame = 0;
		while( frame < frames.length && frames[ frame ].time < currentTime ) {
			frame++;
		}
		return frame;
	}

	/**
	 * Says if the joint has at least one key frame to animate
	 * @return
	 */
	public boolean hasKeyFrames() {
		return this.numRotFrames != 0 || this.numTransFrames != 0;
	}

}










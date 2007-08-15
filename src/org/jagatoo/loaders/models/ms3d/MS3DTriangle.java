package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath.Point3f;

public class MS3DTriangle {

	/*public int flags;*/
	public int[] vertIndices = new int[3];
	public Point3f[] normals = new Point3f[3];
	public float texCoords[][] = new float[2][3];
	public int smoothing;
	public int group;

	public MS3DTriangle(LittleEndianDataInputStream in) throws IOException {
		/*flags = */in.readUnsignedShort();

		for (int i = 0; i < vertIndices.length; i++) {
			vertIndices[ i ] = in.readUnsignedShort();
		}

		for (int i = 0; i < normals.length; i++) {
			normals[ i ] = BinaryUtils.readPoint3f(in);
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				texCoords[ i ][ j ] = in.readFloat();
			}
		}

		smoothing = in.readUnsignedByte();
		group = in.readUnsignedByte();
	}

}

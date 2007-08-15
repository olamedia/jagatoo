package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;

public class MS3DMesh {

	/*public int flags;*/
	public String name;
	public int numTris;
	public int[] triIndices;
	public int material;


	public MS3DMesh(LittleEndianDataInputStream in) throws IOException {
		/*flags = */in.readByte();
		name = BinaryUtils.readString(in, 32);
		numTris = in.readUnsignedShort();
		triIndices = new int[ numTris ];
		for (int i = 0; i < triIndices.length; i++) {
			triIndices[ i ] = in.readUnsignedShort();
		}

		material = in.readUnsignedByte();
	}

}

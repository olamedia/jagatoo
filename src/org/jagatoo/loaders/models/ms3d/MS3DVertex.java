package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath.Point3f;

public class MS3DVertex {

	/*public int flags;*/
	public Point3f vert;
	public int bone;
	/*public int unused;*/

	public MS3DVertex(LittleEndianDataInputStream in) throws IOException {
		/*flags = */in.readUnsignedByte();
		vert = BinaryUtils.readPoint3f(in);
		bone = in.readUnsignedByte();
		/*unused = */in.readUnsignedByte();
	}

}

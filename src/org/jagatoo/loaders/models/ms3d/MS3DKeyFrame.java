package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath2.Point3f;

public class MS3DKeyFrame {

	public float time;
	public Point3f param;


	public MS3DKeyFrame(LittleEndianDataInputStream in) throws IOException {
		time = in.readFloat();
		param = BinaryUtils.readPoint3f(in);
	}

}

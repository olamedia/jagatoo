package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath.Point4f;

public class MS3DMaterial {

	public String name;
	public Point4f ambient;
	public Point4f diffuse;
	public Point4f specular;
	public Point4f emissive;
	public float shininess;
	public float transparency;
	public int mode;
	public int[] textureMap = new int[128];
	public int[] alpha = new int[128];
	//public CImage m_Texture;


	public MS3DMaterial(LittleEndianDataInputStream in) throws IOException {
		name = BinaryUtils.readString(in, 32);
		ambient = BinaryUtils.readPoint4f(in);
		diffuse = BinaryUtils.readPoint4f(in);
		specular = BinaryUtils.readPoint4f(in);
		emissive = BinaryUtils.readPoint4f(in);
		shininess = in.readFloat();
		transparency = in.readFloat();
		mode = in.readUnsignedByte();
		textureMap = BinaryUtils.readIntArray( in, textureMap.length );
		alpha = BinaryUtils.readIntArray( in, alpha.length );
	}

}








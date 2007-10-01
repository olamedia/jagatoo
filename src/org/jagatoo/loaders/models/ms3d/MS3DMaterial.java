package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;

import org.openmali.vecmath2.Colorf;

public class MS3DMaterial {
    
	public String name;
	public Colorf ambient;
	public Colorf diffuse;
	public Colorf specular;
	public Colorf emissive;
	public float shininess;
	public float transparency;
	public int mode;
	public int[] textureMap = new int[128];
	public int[] alpha = new int[128];
	//public CImage m_Texture;
	
	
	public MS3DMaterial(LittleEndianDataInputStream in) throws IOException {
		name = BinaryUtils.readString(in, 32);
		ambient = BinaryUtils.readColorf(in);
		diffuse = BinaryUtils.readColorf(in);
		specular = BinaryUtils.readColorf(in);
		emissive = BinaryUtils.readColorf(in);
		shininess = in.readFloat();
		transparency = in.readFloat();
		mode = in.readUnsignedByte();
		textureMap = BinaryUtils.readIntArray( in, textureMap.length );
		alpha = BinaryUtils.readIntArray( in, alpha.length );
	}

}

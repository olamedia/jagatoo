package org.jagatoo.loaders.models.ms3d.utils;

import java.io.IOException;

import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Point3f;

public class BinaryUtils {

	public static String readString(LittleEndianDataInputStream in, int length)
			throws IOException {
		byte[] array = new byte[length];
		in.read(array, 0, length);

		int len = length;
		for (int i = 0; i < length; i++) {
			if (array[i] == 0) {
				len = i;
				break;
			}
		}
		if( len == 0 ) {
			return null;
		} else {
			return new String(array, 0, len);
		}
	}

	public static int[] readIntArray(LittleEndianDataInputStream in, int length)
			throws IOException {
		byte[] array = new byte[length];
		in.read(array, 0, length);
		int[] arrayInt = new int[length];
		for (int i = 0; i < array.length; i++) {
			arrayInt[i] = array[i];
		}
		return arrayInt;
	}

	public static Point3f readPoint3f(LittleEndianDataInputStream in)
			throws IOException {
		return new Point3f(in.readFloat(), in.readFloat(), in.readFloat());
	}

	public static Colorf readColorf(LittleEndianDataInputStream in)
			throws IOException {
		return new Colorf(in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat());
	}

}

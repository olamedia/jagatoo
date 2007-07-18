package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * An array of Floats.
 * Child of Source.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class FloatArray {

    public int count;
    public String id;

    public float[] floats;

    public static float[] toArray(String floatValues) {
        StringTokenizer tknz = new StringTokenizer(floatValues);
        int count = tknz.countTokens();
        float[] floats = new float[count];
        for(int i = 0; i < count; i++) {
            floats[i] = Float.parseFloat(tknz.nextToken());
        }
        return floats;
    }

}

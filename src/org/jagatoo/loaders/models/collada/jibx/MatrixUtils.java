package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * Utils to read Matrix from a COLLADA file.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class MatrixUtils {

    /**
     * Read a Blender-COLLADA row-major matrix and
     * returns a column-major Vecmath matrix.
     * @param str
     * @return
     */
    public static Matrix4x4 readRowMajor(String str) {
        Matrix4x4 matrix = new Matrix4x4();
        StringTokenizer tknz = new StringTokenizer(str);
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                matrix.matrix4f.setElement(x, y, Float.parseFloat(tknz.nextToken()));
            }
        }
        return matrix;
    }


    /**
     * Read a Blender-COLLADA column-major matrix and
     * returns a column-major Vecmath matrix.
     * @param str
     * @return
     */
    public static Matrix4x4 readColumnMajor(String str) {
        Matrix4x4 matrix = new Matrix4x4();
        StringTokenizer tknz = new StringTokenizer(str);
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                matrix.matrix4f.setElement(x, y, Float.parseFloat(tknz.nextToken()));
            }
        }
        return matrix;
    }

}

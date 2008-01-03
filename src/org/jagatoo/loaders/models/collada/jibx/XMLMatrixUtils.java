package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * Utils to read Matrix from a COLLADA file.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLMatrixUtils {
    
    /**
     * Read a Blender-COLLADA row-major matrix and
     * returns a column-major Vecmath matrix.
     * @param str
     * @return the 4x4 XML matrix
     */
    public static XMLMatrix4x4 readRowMajor(String str) {
        XMLMatrix4x4 matrix = new XMLMatrix4x4();
        StringTokenizer tknz = new StringTokenizer(str);
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                matrix.matrix4f.set(x, y, Float.parseFloat(tknz.nextToken()));
            }
        }
        return matrix;
    }
    
    
    /**
     * Read a Blender-COLLADA column-major matrix and
     * returns a column-major Vecmath matrix.
     * @param str
     * @return the 4x4 XML matrix
     */
    public static XMLMatrix4x4 readColumnMajor(String str) {
        XMLMatrix4x4 matrix = new XMLMatrix4x4();
        StringTokenizer tknz = new StringTokenizer(str);
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                matrix.matrix4f.set(x, y, Float.parseFloat(tknz.nextToken()));
            }
        }
        return matrix;
    }
    
}

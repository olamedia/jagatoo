package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * An array of Integers.
 * Child of Source.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLIntArray {
    
    public int count;
    public String id;
    
    public int[] ints;
    
    public static int[] toArray(String intValues) {
        StringTokenizer tknz = new StringTokenizer(intValues);
        int count = tknz.countTokens();
        int[] ints = new int[count];
        for(int i = 0; i < count; i++) {
            ints[i] = Integer.parseInt(tknz.nextToken());
        }
        return ints;
    }
    
}

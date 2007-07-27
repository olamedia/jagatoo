package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * An array of Booleans.
 * Child of Source.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLBoolArray {

    public int count;
    public String id;

    public boolean[] bools;

    public static boolean[] toArray(String boolValues) {
        StringTokenizer tknz = new StringTokenizer(boolValues);
        int count = tknz.countTokens();
        boolean[] bools = new boolean[count];
        for(int i = 0; i < count; i++) {
            bools[i] = Boolean.parseBoolean(tknz.nextToken());
        }
        return bools;
    }

}

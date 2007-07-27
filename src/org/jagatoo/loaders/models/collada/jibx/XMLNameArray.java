package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * An array of Names (String).
 * Child of Source.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLNameArray {

    public int count;
    public String id;

    public String[] names;

    public static String[] toArray(String nameValues) {
        StringTokenizer tknz = new StringTokenizer(nameValues);
        int count = tknz.countTokens();
        String[] names = new String[count];
        for(int i = 0; i < count; i++) {
            names[i] = tknz.nextToken();
        }
        return names;
    }

}

package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

/**
 * An array of IDREFs (String).
 * Child of Source.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLIDREFArray {

    public int count;
    public String id;

    public String[] idrefs;

    public static String[] toArray(String idrefValues) {
        StringTokenizer tknz = new StringTokenizer(idrefValues);
        int count = tknz.countTokens();
        String[] idrefs = new String[count];
        for(int i = 0; i < count; i++) {
            idrefs[i] = XMLIDREFUtils.parse(tknz.nextToken());
        }
        return idrefs;
    }

}

package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

import org.openmali.vecmath.Color4f;

/**
 * Container for a 4-component color definition :
 * Red, Green, Blue, Alpha-transparency.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLColor4 {
    
    public Color4f color4f;
    
    public XMLColor4(String str) {
        StringTokenizer tknz = new StringTokenizer(str);
        color4f = new Color4f(
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken())
        );
    }
    
}

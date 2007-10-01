package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

import org.openmali.vecmath2.Colorf;

/**
 * Container for a 4-component color definition :
 * Red, Green, Blue, Alpha-transparency.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLColor4 {
    
    public Colorf color;
    
    public XMLColor4(String str) {
        StringTokenizer tknz = new StringTokenizer(str);
        color = new Colorf(
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken()),
                Float.parseFloat(tknz.nextToken())
        );
    }
    
}

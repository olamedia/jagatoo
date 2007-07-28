package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * Accessor contains the information needed
 * to interpret a Source's data.
 * Child of TechniqueCommon.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLAccessor {
    
    public int count;
    public String source;
    public int stride;
    
    public ArrayList<XMLParam> params;
    
}

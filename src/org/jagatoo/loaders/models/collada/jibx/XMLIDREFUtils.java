package org.jagatoo.loaders.models.collada.jibx;

/**
 * Utils about IDREFs.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLIDREFUtils {
    
    /**
     * Parse an IDREF, which means, currently,
     * to remove the trailing "#" if any.
     * @param idref
     * @return the parsed String
     */
    public static String parse(String idref) {
        
        if(idref.startsWith("#")) {
            idref = idref.substring(1);
        }
        
        return idref;
        
    }
    
}

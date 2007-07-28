package org.jagatoo.loaders.models.collada.jibx;

/**
 * A Technique used to define the Appearance of an object in
 * the ProfileCOMMON.
 * Child of ProfileCOMMON.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLProfileCOMMON_Technique {
    
    public String id = null;
    public String sid = null;
    
    public XMLShadingParameters constant = null;
    public XMLShadingParameters lambert = null;
    public XMLShadingParameters phong = null;
    public XMLShadingParameters blinn = null;
    
}

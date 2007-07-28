package org.jagatoo.loaders.models.collada.jibx;

/**
 * Parameters for a Constan, Lambert, Phong, or Blinn shading.
 * Child of ProfileCOMMON_Technique
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLShadingParameters {
    
    public XMLColorOrTexture emission = null;
    public XMLColorOrTexture ambient = null;
    public XMLColorOrTexture diffuse = null;
    public XMLColorOrTexture specular = null;
    public XMLFloat shininess = null;
    public XMLColorOrTexture reflective = null;
    public XMLFloat reflectivity = null;
    public XMLColorOrTexture transparent = null;
    public XMLFloat transparency = null;
    
}

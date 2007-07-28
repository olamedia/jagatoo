package org.jagatoo.loaders.models.collada.jibx;

/**
 * A Color or Texture used in ShadingParameters.
 * Child of ShadingParameters (Constant, Lambert,
 * Phong, or Blinn shading)
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLColorOrTexture {
    
    public XMLColor4 color4 = null;
    public XMLTexture texture = null;
    
}

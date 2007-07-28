package org.jagatoo.loaders.models.collada.jibx;

/**
 * A 2D Sampler.
 * Child of NewParam in ProfileGLES, ProfileCG, ProfileGLSL and ProfileCOMMON.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSampler2D {
    
    public String source;
    
    public static enum Filter {
        NEAREST,
        LINEAR,
        NEAREST_MIPMAP_NEAREST,
        NEAREST_MIPMAP_LINEAR,
        LINEAR_MIPMAP_NEAREST,
        LINEAR_MIPMAP_LINEAR
    }
    
    public Filter minFilter = Filter.LINEAR_MIPMAP_LINEAR;
    public Filter maxFilter = Filter.LINEAR_MIPMAP_LINEAR;
    
}

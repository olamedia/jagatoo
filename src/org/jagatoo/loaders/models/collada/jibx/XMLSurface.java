package org.jagatoo.loaders.models.collada.jibx;

/**
 * A Surface definition.
 * Child of NewParam in ProfileGLES, ProfileCG, ProfileGLSL and ProfileCOMMON
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSurface {
    
    public static enum Type {
        _1D,
        _2D,
        _3D,
        _CUBE,
        _DEPTH,
        _RECT
    }
    
    public XMLSurface.Type type;
    
    public static Type readTypeString(String typeString) {
        return Type.valueOf("_"+typeString);
    }
    
    public String initFrom;
    public String format;
    
}

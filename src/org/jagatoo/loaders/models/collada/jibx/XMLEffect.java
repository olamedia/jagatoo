package org.jagatoo.loaders.models.collada.jibx;

/**
 * An Effect.
 * Child of LibraryEffects.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLEffect {
    
    public XMLAsset asset = null;
    // It is required, but Blender sometimes forget it,
    // in which case an empty String does the trick
    public String id = "";
    public String name = null;
    
    // CG profile not yet implemented
    public XMLProfileCG profileCG = null;
    
    // GLSL profile not yet implemented
    public XMLProfileGLSL profileGLSL = null;
    
    public XMLProfileCOMMON profileCOMMON = null;
    
}

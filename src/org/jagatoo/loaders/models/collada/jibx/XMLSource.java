package org.jagatoo.loaders.models.collada.jibx;

/**
 * Declares a data repository that provides values
 * according to the semantics of an <input> element
 * that refers to it.
 * Child of Morph, Animation, Mesh, ConvexMesh, Skin, Spline
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSource {
    
    public XMLAsset asset = null;
    
    public String id = null;
    
    /*
     * From the COLLADA doc : No more than one of
     * the array elements (<bool_array>, <float_array>,
     * <int_array>, <Name_array>, or <IDREF_array>) may occur.
     * They are mutually exclusive.
     */
    public XMLBoolArray boolArray = null;
    public XMLFloatArray floatArray = null;
    public XMLIntArray intArray = null;
    public XMLNameArray nameArray = null;
    public XMLIDREFArray idrefArray = null;
    
    
    /**
     * TechniqueCommon, as a child of Source, contains an acessor,
     * which contains the information needed to read a Source.
     * (in fact, it's the meaning of the data in the Source)
     * Child of Source.
     * 
     * @author Amos Wenger (aka BlueSky)
     */
    public static class TechniqueCommon {
        
        public XMLAccessor accessor;
        
    }
    
    public XMLSource.TechniqueCommon techniqueCommon = null;
    
}

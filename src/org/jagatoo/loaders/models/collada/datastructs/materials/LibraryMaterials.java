package org.jagatoo.loaders.models.collada.datastructs.materials;

import java.util.HashMap;

/**
 * The Materials in the COLLADA file
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryMaterials {
    
    /** All materials. Key = id, value = material */
    private final HashMap<String, Material> materials;
    
    /**
     * Creates a new COLLADALibraryMaterials
     */
    public LibraryMaterials() {
        
        materials = new HashMap<String, Material>();
        
    }
    
    /**
     * @return the materials
     */
    public HashMap<String, Material> getMaterials() {
        return materials;
    }
    
}

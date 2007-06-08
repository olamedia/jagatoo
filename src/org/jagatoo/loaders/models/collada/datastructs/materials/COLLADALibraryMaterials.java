package org.jagatoo.loaders.models.collada.datastructs.materials;

import java.util.HashMap;

/**
 * The Materials in the COLLADA file
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryMaterials {
    
    /** All materials. Key = id, value = material */
    private final HashMap<String, COLLADAMaterial> materials;
    
    /**
     * Creates a new COLLADALibraryMaterials
     */
    public COLLADALibraryMaterials() {
        
        materials = new HashMap<String, COLLADAMaterial>();
        
    }

    /**
     * @return the materials
     */
    public HashMap<String, COLLADAMaterial> getMaterials() {
        return materials;
    }
    
}

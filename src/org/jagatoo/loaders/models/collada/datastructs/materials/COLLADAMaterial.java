package org.jagatoo.loaders.models.collada.datastructs.materials;


/**
 * A COLLADA Material
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAMaterial {
    
    /** The ID */
    private final String id;
    
    /** All effects used in this material */
    private final String effect;
    
    /**
     * Creates a new COLLADAMaterial
     * @param id The ID of this Material
     * @param effect The effect associated to this Material
     */
    public COLLADAMaterial(String id, String effect) {
        
        this.id = id;
        this.effect = effect;
        
    }

    /**
     * @return the effects
     */
    public String getEffect() {
        return effect;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
}

package org.jagatoo.loaders.models.collada.datastructs.materials;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;


/**
 * A COLLADA Material
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Material {
    
    /** The ID */
    private final String id;
    
    /** All effects used in this material */
    private final String effect;
    
    /** The file this COLLADAMaterial belongs to */
    private final AssetFolder file;
    
    /**
     * Creates a new COLLADAMaterial
     * @param file the file this COLLADAMaterial belongs to
     * @param id The ID of this Material
     * @param effect The effect associated to this Material
     */
    public Material(AssetFolder file, String id, String effect) {
        
        this.file = file;
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
    
    /**
     * @return the file
     */
    public AssetFolder getFile() {
        return file;
    }
    
}

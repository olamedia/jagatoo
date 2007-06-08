package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

/**
 * A COLLADA Node
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADANode {
    
    /** The id of this node */
    private final String id;
    
    /** The name of this node */
    private final String name;
    
    /** The transform of this node */
    private final COLLADATransform transform;
    
    /**
     * Creates a new COLLADANode
     * @param id The id of this Node
     * @param name The name of this Node
     * @param transform The transform of this Node
     *
     */
    public COLLADANode(String id, String name, COLLADATransform transform) {
        
        this.id = id;
        this.name = name;
        this.transform = transform;
        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the transform
     */
    public COLLADATransform getTransform() {
        return transform;
    }
    
}

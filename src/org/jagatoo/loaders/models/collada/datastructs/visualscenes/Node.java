package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;

/**
 * A COLLADA Node
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class Node {
    
    /** The COLLADA file this node belongs to */
    protected final AssetFolder file;
    
    /** The id of this node */
    protected final String id;
    
    /** The name of this node */
    protected final String name;
    
    /** The transform of this node */
    protected final COLLADATransform transform;
    
    /**
     * Creates a new COLLADANode
     * @param file the COLLADA file this node belongs to
     * @param id The id of this Node
     * @param name The name of this Node
     * @param transform The transform of this Node
     */
    public Node(AssetFolder file, String id, String name, COLLADATransform transform) {
        
        this.file = file;
        this.id = id;
        this.name = name;
        this.transform = transform;
        
    }
    
    /**
     * @return the file
     */
    public AssetFolder getFile() {
        return file;
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

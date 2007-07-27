package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.HashMap;

/**
 * A COLLADA Scene
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Scene {
    
    /** The id of the scene */
    private final String id;
    
    /** The name of the scene */
    private String name;
    
    /** A map of all nodes */
    private final HashMap<String, Node> nodes;

    /**
     * Create a new COLLADAScene
     * @param id The id of the scene
     * @param name The name of the scene
     * 
     */
    public Scene(String id, String name) {
        
        this.id = id;
        this.name = name;
        this.nodes = new HashMap<String, Node>();
        
    }

    /**
     * @return the nodes
     */
    public HashMap<String, Node> getNodes() {
        return nodes;
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
    
}

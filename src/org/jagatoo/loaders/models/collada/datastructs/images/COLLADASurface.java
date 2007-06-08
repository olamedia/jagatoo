package org.jagatoo.loaders.models.collada.datastructs.images;

import java.util.List;

/**
 * Represent a surface : nearly the same thing as a texture, but
 * could be used either for texture mapping or bump mapping or
 * normal mapping. It's image data + additionnal info.
 * (See the COLLADA Specification for more details)
 * 
 * Note : for now it only stores
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADASurface {
    
    /** Images used by this COLLADA surface */
    public List<String> imageIds;
    private final String id;
    
    /**
     * Creates a new COLLADA Surface
     * @param id The source id of this surface
     */
    public COLLADASurface(String id) {
        
        this.id = id;
        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
}

package org.jagatoo.loaders.models.collada.datastructs.images;

import java.util.HashMap;

/**
 * Contains a list of images which are referred to in the COLLADA file
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryImages {
    
    /** Map of images. Key = id, Value = image path */
    private final HashMap<String, String> images;
    
    /**
     * Creates a new COLLADALibraryImages
     */
    public LibraryImages() {
        
        images = new HashMap<String, String>();
        
    }
    
    /**
     * @return the images
     */
    public HashMap<String, String> getImages() {
        return images;
    }
    
}

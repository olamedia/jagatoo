package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Library of Images.
 * Child of COLLADA
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLLibraryImages {
    
    /**
     * This field is written by JiBX and then parsed by the
     * readImages() method and then the imageMap HashMap
     * is written.
     */
    private ArrayList<XMLImage> imagesList = null;
    
    /**
     * A map of all images, which is filled by the readImages()
     * method just after the images ArrayList has been written.
     * key = ID
     * value = Image
     */
    public HashMap<String, XMLImage> images = null;
    
    /**
     * Called just after images has been read, fill
     * the imageMap.
     */
    public void readImages() {
        images = new HashMap<String, XMLImage>();
        for (XMLImage image : imagesList) {
            images.put(image.id, image);
        }
        imagesList = null;
    }
    
}

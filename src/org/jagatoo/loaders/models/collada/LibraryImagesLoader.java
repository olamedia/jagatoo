package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.images.LibraryImages;
import org.jagatoo.loaders.models.collada.jibx.XMLImage;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryImages;

/**
 * Loader for LibraryImages
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryImagesLoader {
    
    /**
     * Load LibraryImages
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libImages
     *            The JAXB data to load from
     */
    static void loadLibraryImages(AssetFolder colladaFile,
            XMLLibraryImages libImages) {
        
        LibraryImages colLibImages = colladaFile.getLibraryImages();
        HashMap<String, String> colImages = colLibImages.getImages();
        
        Collection<XMLImage> images = libImages.images.values();
        
        COLLADALoader.logger.increaseTabbing();
        for (XMLImage image : images) {
            
            COLLADALoader.logger.print("TT] Found image [" + image.id + ":"
                    + image.initFrom + "]");
            colImages.put(image.id, image.initFrom);
            
        }
        COLLADALoader.logger.decreaseTabbing();
        
    }
    
}

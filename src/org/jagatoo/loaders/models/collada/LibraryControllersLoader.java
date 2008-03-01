package org.jagatoo.loaders.models.collada;

import java.util.Collection;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.jibx.XMLController;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryControllers;

/**
 * Loader for LibraryMaterials
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryControllersLoader {
    
    /**
     * Load LibraryControllers
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param controllers
     *            The JAXB data to load from
     */
    static void loadLibraryControllers(AssetFolder colladaFile,
            XMLLibraryControllers controllers) {

        Collection<XMLController> controllersList = controllers.controllers.values();
        
        for (XMLController controller : controllersList) {
            String source = controller.skin.source.replaceAll(" ", "_");
            String id = controller.id;
            COLLADALoader.logger.print("TT] Found controller with Id : \"" + id
                    + "\" and source : \"" + source + "\"");
            colladaFile.getLibraryControllers().getControllers()
            	.put(id, new SkeletalController(colladaFile.getLibraryGeometries(), source, controller, colladaFile.getLibraryAnimations(), colladaFile.getLibraryVisualsScenes().getSkeletons().get(id.replace("-skin", ""))));
        }
        
    }
    
}
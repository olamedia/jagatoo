package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADAController;
import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADASkeletalController;
import org.jagatoo.loaders.models.collada.jibx.Controller;
import org.jagatoo.loaders.models.collada.jibx.LibraryControllers;

/**
 * Loader for LibraryMaterials
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryControllersLoader {

    /**
     * Load LibraryControllers
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param controllers
     *            The JAXB data to load from
     */
    static void loadLibraryControllers(COLLADAFile colladaFile,
            LibraryControllers controllers) {

        HashMap<String, COLLADAController> controllersMap = colladaFile
        .getLibraryControllers().getControllers();
        Collection<Controller> controllersList = controllers.controllers.values();

        for (Controller controller : controllersList) {
            // FIXME : this is strange... a bug in Blender's exporter ?
            String source = controller.skin.source.replaceAll(" ", "_");
            String id = controller.id;
            COLLADALoader.logger.print("TT] Found controller with Id : \"" + id
                    + "\" and source : \"" + source + "\"");
            controllersMap.put(id, new COLLADASkeletalController(colladaFile.getLibraryGeometries(), source, controller));
        }

    }

}

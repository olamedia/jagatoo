package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometryProvider;

/**
 * This class is the equivalent of LibraryControllers in a
 * COLLADA file. It contains every Controller and relevant information
 * about it.
 *
 * Note : for now it's over-simplified : there is just a map between
 * the id of the controller and the id of the source mesh...
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryControllers {

    /** Map of all geoms : key is id of the controller, value is controller */
    private final HashMap<String, COLLADAController> controllers;

    /**
     * Create a new COLLADALibraryControllers
     */
    public COLLADALibraryControllers() {

        controllers = new HashMap<String, COLLADAController>();

    }

    /**
     * @return the controllers in this COLLADALibraryControllers
     */
    public HashMap<String, COLLADAController> getControllers() {
        return controllers;
    }

}

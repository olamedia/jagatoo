package org.jagatoo.loaders.models.collada.datastructs.controllers;

import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.Controller;

/**
 * A COLLADA Controller.
 * It's what used, for example, to compute frame animations or skeletal animations.
 *
 * @author Amos Wenger (aka BlueSky)
 * @see COLLADASkeletalController
 */
public abstract class COLLADAController extends COLLADAGeometryProvider {

    private final Controller controller;

    /**
     * Creates a new COLLADA Controller
     * @param libGeoms
     * @param controller
     */
    public COLLADAController(COLLADALibraryGeometries libGeoms, Controller controller) {

        super(libGeoms);
        this.controller = controller;


    }

    /**
     * @return the instanceController
     */
    public Controller getController() {
        return controller;
    }

}

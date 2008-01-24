package org.jagatoo.loaders.models.collada.datastructs.controllers;

import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.XMLController;

/**
 * A COLLADA Controller.
 * It's what used, for example, to compute frame animations or skeletal animations.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @see SkeletalController
 */
public abstract class Controller extends GeometryProvider {
    
    private final XMLController controller;
    
    /**
     * Creates a new COLLADA Controller
     * @param libGeoms
     * @param controller
     */
    public Controller(LibraryGeometries libGeoms, XMLController controller) {
        super(libGeoms);
        this.controller = controller;
    }
    
    /**
     * @return the instanceController
     */
    public XMLController getController() {
        return controller;
    }
    
}

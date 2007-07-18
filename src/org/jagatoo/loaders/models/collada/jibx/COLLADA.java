package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * Root element of an XML file.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADA {

    public String version = null;
    public Asset asset = null;
    public ArrayList<LibraryControllers> libraryControllers = null;
    public ArrayList<LibraryEffects> libraryEffects = null;
    public ArrayList<LibraryImages> libraryImages = null;
    public ArrayList<LibraryMaterials> libraryMaterials = null;
    public ArrayList<LibraryGeometries> libraryGeometries = null;
    public ArrayList<LibraryVisualScenes> libraryVisualScenes = null;

}

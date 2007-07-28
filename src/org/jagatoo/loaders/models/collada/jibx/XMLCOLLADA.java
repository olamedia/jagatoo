package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * Root element of an XML file.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLCOLLADA {
    
    public String version = null;
    public XMLAsset asset = null;
    public ArrayList<XMLLibraryControllers> libraryControllers = null;
    public ArrayList<XMLLibraryEffects> libraryEffects = null;
    public ArrayList<XMLLibraryImages> libraryImages = null;
    public ArrayList<XMLLibraryMaterials> libraryMaterials = null;
    public ArrayList<XMLLibraryGeometries> libraryGeometries = null;
    public ArrayList<XMLLibraryVisualScenes> libraryVisualScenes = null;
    
}

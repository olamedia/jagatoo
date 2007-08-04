package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A library of Geometries, that one COLLADA
 * file contains.
 * Child of COLLADA.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLLibraryGeometries {
    
    /**
     * This field is written by JiBX and then parsed by the
     * readGeometries() method and then the geometryMap HashMap
     * is written.
     */
    private ArrayList<XMLGeometry> geometriesList = null;
    
    /**
     * A map of all geometries, which is filled by the readGeometries()
     * method just after the geometries ArrayList has been written.
     * key = ID
     * value = Geometry
     */
    public HashMap<String, XMLGeometry> geometries = null;
    
    /**
     * Called just after geometries has been read, fill
     * the geometryMap.
     */
    public void readGeometries() {
        geometries = new HashMap<String, XMLGeometry>();
        for (XMLGeometry geometry : geometriesList) {
            geometries.put(geometry.id, geometry);
        }
        geometriesList = null;
    }
    
}
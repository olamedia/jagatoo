package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Library of Materials.
 * Child of COLLADA.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryMaterials {

    /**
     * This field is written by JiBX and then parsed by the
     * readMaterials() method and then the materialMap HashMap
     * is written.
     */
    private ArrayList<Material> materialsList = null;

    /**
     * A map of all materials, which is filled by the readMaterials()
     * method just after the materials ArrayList has been written.
     * key = ID
     * value = Material
     */
    public HashMap<String, Material> materials = null;

    /**
     * Called just after materials has been read, fill
     * the materialMap.
     */
    public void readMaterials() {
        materials = new HashMap<String, Material>();
        for (Material material : materialsList) {
            materials.put(material.id, material);
        }
        materialsList = null;
    }

}

package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADALibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADAMaterial;
import org.jagatoo.loaders.models.collada.jibx.LibraryMaterials;
import org.jagatoo.loaders.models.collada.jibx.Material;

/**
 * Loader for LibraryMaterials
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryMaterialsLoader {

    /**
     * Load LibraryMaterials
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libMaterials
     *            The JAXB data to load from
     */
    static void loadLibraryMaterials(COLLADAFile colladaFile,
            LibraryMaterials libMaterials) {

        COLLADALibraryMaterials colLibMaterials = colladaFile
        .getLibraryMaterials();
        HashMap<String, COLLADAMaterial> colMaterials = colLibMaterials
        .getMaterials();

        Collection<Material> materials = libMaterials.materials.values();

        COLLADALoader.logger.increaseTabbing();
        for (Material material : materials) {

            COLLADAMaterial colMaterial = new COLLADAMaterial(colladaFile,
                    material.id, material.instanceEffect.url);
            COLLADALoader.logger.print("TT] Found material [" + colMaterial.getId() + ":"
                    + colMaterial.getEffect() + "]");
            colMaterials.put(colMaterial.getId(), colMaterial);

        }
        COLLADALoader.logger.decreaseTabbing();

    }

}

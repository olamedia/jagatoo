package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.materials.LibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.materials.Material;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryMaterials;
import org.jagatoo.loaders.models.collada.jibx.XMLMaterial;

/**
 * Loader for LibraryMaterials
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryMaterialsLoader {
    
    /**
     * Load LibraryMaterials
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libMaterials
     *            The JAXB data to load from
     */
    static void loadLibraryMaterials(AssetFolder colladaFile,
            XMLLibraryMaterials libMaterials) {
        
        LibraryMaterials colLibMaterials = colladaFile
        .getLibraryMaterials();
        HashMap<String, Material> colMaterials = colLibMaterials
        .getMaterials();
        
        Collection<XMLMaterial> materials = libMaterials.materials.values();
        
        COLLADALoader.logger.increaseTabbing();
        for (XMLMaterial material : materials) {
            
            Material colMaterial = new Material(colladaFile,
                    material.id, material.instanceEffect.url);
            COLLADALoader.logger.print("TT] Found material [" + colMaterial.getId() + ":"
                    + colMaterial.getEffect() + "]");
            colMaterials.put(colMaterial.getId(), colMaterial);
            
        }
        COLLADALoader.logger.decreaseTabbing();
        
    }
    
}
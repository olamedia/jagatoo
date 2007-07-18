package org.jagatoo.loaders.models.collada.datastructs;

import java.net.URL;

import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADALibraryControllers;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADALibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.images.COLLADALibraryImages;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADALibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADALibraryVisualScenes;


/**
 * This class contains every information which has been loaded
 * from a COLLADA file and that the loader handles. The reason
 * this class exists is that data provided directly by XMlBeans when
 * loading a COLLADA file isn't really convenient to handle. So
 * in the org.collada.xith3d.COLLADALoader file, XMLBeans data (which
 * is composed of classes contained in the
 * org.jagatoo.loaders.models.collada.schema.org.collada.x2005.x11.colladaSchema
 * package) is converted to a COLLADAFile. Then it can be used in a scenegraph,
 * e.g. Xith3D.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAFile {

    /** LibraryControllers : contains all controllers */
    private COLLADALibraryControllers libraryControllers;

    /** LibraryEffects : contains all effects */
    private COLLADALibraryEffects libraryEffects;

    /** LibraryImages : contains all images */
    private COLLADALibraryImages libraryImages;

    /** LibraryMaterials : contains all materials */
    private COLLADALibraryMaterials libraryMaterials;

    /** LibraryGeometries : contains all geometries */
    private COLLADALibraryGeometries libraryGeometries;

    /** LibraryVisualScenes : contains all visual scenes */
    private COLLADALibraryVisualScenes libraryVisualsScenes;

    /**
     * The base path, used e.g. when loading textures.
     * Basically it's where this file has been loaded from.
     */
    private final URL basePath;

    /**
     * Creates a new COLLADAFile
     * @param basePath The base path, used e.g. when loading textures
     */
    public COLLADAFile(URL basePath) {

        this.basePath = basePath;

        libraryControllers = new COLLADALibraryControllers();
        libraryEffects = new COLLADALibraryEffects();
        libraryImages = new COLLADALibraryImages();
        libraryMaterials = new COLLADALibraryMaterials();
        libraryGeometries = new COLLADALibraryGeometries();
        libraryVisualsScenes = new COLLADALibraryVisualScenes();

    }

    /**
     * @return the libraryControllers
     */
    public COLLADALibraryControllers getLibraryControllers() {
        return libraryControllers;
    }

    /**
     * @return the libraryEffects
     */
    public COLLADALibraryEffects getLibraryEffects() {
        return libraryEffects;
    }

    /**
     * @return the libraryImages
     */
    public COLLADALibraryImages getLibraryImages() {
        return libraryImages;
    }

    /**
     * @return the libraryMaterials
     */
    public COLLADALibraryMaterials getLibraryMaterials() {
        return libraryMaterials;
    }

    /**
     * @return the libraryGeometries
     */
    public COLLADALibraryGeometries getLibraryGeometries() {
        return libraryGeometries;
    }

    /**
     * @return the libraryVisualsScenes
     */
    public COLLADALibraryVisualScenes getLibraryVisualsScenes() {
        return libraryVisualsScenes;
    }

    /**
     * @return the basePath
     */
    public URL getBasePath() {
        return basePath;
    }

}

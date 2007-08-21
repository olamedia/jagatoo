package org.jagatoo.loaders.models.collada.datastructs;

import java.net.URL;

import org.jagatoo.loaders.models.collada.datastructs.controllers.LibraryControllers;
import org.jagatoo.loaders.models.collada.datastructs.effects.LibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.images.LibraryImages;
import org.jagatoo.loaders.models.collada.datastructs.materials.LibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.LibraryVisualScenes;


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
public class AssetFolder {

    /** LibraryControllers : contains all controllers */
    private LibraryControllers libraryControllers;

    /** LibraryEffects : contains all effects */
    private LibraryEffects libraryEffects;

    /** LibraryImages : contains all images */
    private LibraryImages libraryImages;

    /** LibraryMaterials : contains all materials */
    private LibraryMaterials libraryMaterials;

    /** LibraryGeometries : contains all geometries */
    private LibraryGeometries libraryGeometries;

    /** LibraryVisualScenes : contains all visual scenes */
    private LibraryVisualScenes libraryVisualsScenes;

    private ColladaProtoypeModel model;

    /**
     * The base path, used e.g. when loading textures.
     * Basically it's where this file has been loaded from.
     */
    private final URL basePath;

    /**
     * Creates a new COLLADAFile
     * @param basePath The base path, used e.g. when loading textures
     */
    public AssetFolder(URL basePath) {

        this.basePath = basePath;

        libraryControllers = new LibraryControllers();
        libraryEffects = new LibraryEffects();
        libraryImages = new LibraryImages();
        libraryMaterials = new LibraryMaterials();
        libraryGeometries = new LibraryGeometries();
        libraryVisualsScenes = new LibraryVisualScenes();

    }

    /**
     * @return the libraryControllers
     */
    public LibraryControllers getLibraryControllers() {
        return libraryControllers;
    }

    /**
     * @return the libraryEffects
     */
    public LibraryEffects getLibraryEffects() {
        return libraryEffects;
    }

    /**
     * @return the libraryImages
     */
    public LibraryImages getLibraryImages() {
        return libraryImages;
    }

    /**
     * @return the libraryMaterials
     */
    public LibraryMaterials getLibraryMaterials() {
        return libraryMaterials;
    }

    /**
     * @return the libraryGeometries
     */
    public LibraryGeometries getLibraryGeometries() {
        return libraryGeometries;
    }

    /**
     * @return the libraryVisualsScenes
     */
    public LibraryVisualScenes getLibraryVisualsScenes() {
        return libraryVisualsScenes;
    }

    /**
     * @return the basePath
     */
    public URL getBasePath() {
        return basePath;
    }

	public ColladaProtoypeModel getModel() {
		return model;
	}

	public void setModel(ColladaProtoypeModel model) {
		this.model = model;
	}

}

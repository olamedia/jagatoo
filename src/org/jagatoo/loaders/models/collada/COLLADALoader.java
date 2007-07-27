package org.jagatoo.loaders.models.collada;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.jibx.COLLADA;
import org.jagatoo.loaders.models.collada.jibx.LibraryControllers;
import org.jagatoo.loaders.models.collada.jibx.LibraryEffects;
import org.jagatoo.loaders.models.collada.jibx.LibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.LibraryImages;
import org.jagatoo.loaders.models.collada.jibx.LibraryMaterials;
import org.jagatoo.loaders.models.collada.jibx.LibraryVisualScenes;
import org.jagatoo.loaders.models.collada.logging.HierarchicalOutputter;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 * This is a really simple COLLADA file loader. Its features are limited for now
 * but improving every minute :)
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALoader {

    /** Are debug messages printed ? */
    static HierarchicalOutputter logger = new HierarchicalOutputter();

    /** The unmarshalling context used to read COLLADA files */
    IUnmarshallingContext unmarshallingContext;

    /**
     * Create a new COLLADA Loader.
     */
    public COLLADALoader() {

        IBindingFactory factory;

        try {

            factory = BindingDirectory.getFactory(COLLADA.class);

            long t1 = System.nanoTime();

            this.unmarshallingContext = factory.createUnmarshallingContext();

            long t2 = System.nanoTime();

            System.out.println("Unmarshalling context creation time = "+((t2 - t1) / 1000000)+" ms");

        } catch (JiBXException e) {
            // We throw an Error created from this Exception, because we don't want
            // to annoy users with try/catch clauses. If Xith3D has been compiled correctly,
            // no JiBXException is thrown.
            throw new Error(e);
        }

    }

    /**
     * Loads a COLLADA file from a file
     *
     * @param path
     *            The file to load the scene from
     * @return the loaded file
     */
    public COLLADAFile load(String path) {

        COLLADAFile collada = null;

        try {
            collada = load(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collada;

    }

    /**
     * Loads a COLLADA file from an URL
     *
     * @param url
     *            The URL to load the scene from
     * @return the loaded file
     */
    public COLLADAFile load(URL url) {

        logger.print("Loading the URL : " + url);

        COLLADAFile collada = null;

        try {
            // Find out the parent URL
            String parentURLString = url.toURI().toString();
            parentURLString = parentURLString.substring(0, parentURLString.lastIndexOf("/") + 1);
            URL parentURL = new URL(parentURLString);
            logger.print("Found parent URL : "+parentURL);
            collada = load(parentURL, url.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collada;

    }

    /**
     * Loads a COLLADA file from a file
     *
     * @param file
     *            The file to load the scene from
     * @return the loaded file
     */
    public COLLADAFile load(File file) {

        logger.print("Loading the file : " + file.getPath());

        COLLADAFile collada = null;

        try {
            collada = load(file.getParentFile().toURI().toURL(), new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collada;

    }

    /**
     * Loads a COLLADA file from a stream
     * @param basePath The base path, used e.g. when there are textures to load
     *
     * @param stream
     *            The stream to load the scene from
     * @return the loaded file
     */
    public COLLADAFile load(URL basePath, InputStream stream) {

        long t1 = System.nanoTime();

        COLLADAFile colladaFile = new COLLADAFile(basePath);

        try {

            logger.print("TT] Parsing...");
            logger.increaseTabbing();
            long l1 = System.nanoTime();
            // The second argument of unmarshalDocument is null, cause it's up to the unmarshaller
            // to figure out the encoding of the file.
            COLLADA collada = (COLLADA) unmarshallingContext.unmarshalDocument(stream, null);
            long l2 = System.nanoTime();
            logger.print("TT] Took " + ((l2 - l1) / 1000000)
                    + " milliseconds to parse");
            logger.decreaseTabbing();

            logger.print("--] This is a COLLADA " + collada.version
                    + " file");
            logger
            .print("--] Note that the loader don't care whether it's 1.4.0 or 1.4.1, though"
                    + "\n the COLLADA schema used for parsing is the for 1.4.1, tests for development"
                    + "\n have been done with 1.4.0 files exported by Blender (Illusoft script)");

            logger.print("TT] Exploring libs...");

            logger.increaseTabbing();

            /*List<LibraryAnimations> libraryAnimationsList = collada.libraryAnimations;
            if (!libraryAnimationsList.isEmpty()) {
                logger.print("SS] Found LibraryAnimations ! Hmm we gotta implement animations soon !");
            }*/
            /*
            for (LibraryAnimations libraryAnimations : libraryAnimationsList) {
                logger
                .print("SS] Found LibraryAnimations ! Hmm we gotta implement animations soon !");
            }
             */

            List<LibraryControllers> libraryControllersList = collada.libraryControllers;
            if(libraryControllersList != null) {
                for (LibraryControllers libraryControllers : libraryControllersList) {
                    logger.print("CC] Found LibraryControllers ! Investigating... !");
                    logger.increaseTabbing();
                    COLLADALibraryControllersLoader.loadLibraryControllers(colladaFile, libraryControllers);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryEffects> libraryEffectsList = collada.libraryEffects;
            if(libraryEffectsList != null) {
                for (LibraryEffects libraryEffects : libraryEffectsList) {
                    logger.print("CC] Found LibraryEffects ! Investigating... !");
                    logger.increaseTabbing();
                    COLLADALibraryEffectsLoader.loadLibraryEffects(colladaFile, libraryEffects);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryImages> libraryImagesList = collada.libraryImages;
            if(libraryImagesList != null) {
                for (LibraryImages libraryImages : libraryImagesList) {
                    logger.print("CC] Found LibraryImages ! We know that !");
                    logger.increaseTabbing();
                    COLLADALibraryImagesLoader.loadLibraryImages(colladaFile, libraryImages);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryMaterials> libraryMaterialsList = collada.libraryMaterials;
            if(libraryMaterialsList != null) {
                for (LibraryMaterials libraryMaterials : libraryMaterialsList) {
                    logger.print("CC] Found LibraryMaterials ! We know that !");
                    logger.increaseTabbing();
                    COLLADALibraryMaterialsLoader.loadLibraryMaterials(colladaFile, libraryMaterials);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryGeometries> libraryGeometriesList = collada.libraryGeometries;
            if(libraryGeometriesList != null) {
                for (LibraryGeometries libraryGeometries : libraryGeometriesList) {
                    logger.print("CC] Found LibraryGeometries ! We know that !");
                    logger.increaseTabbing();
                    COLLADALibraryGeometriesLoader.loadLibraryGeometries(colladaFile, libraryGeometries);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryVisualScenes> libraryVisualScenesList = collada.libraryVisualScenes;
            if(libraryVisualScenesList != null) {
                for (LibraryVisualScenes libraryVisualScenes : libraryVisualScenesList) {
                    logger
                    .print("CC] Found LibraryVisualScenes ! Investigating... !");
                    logger.increaseTabbing();
                    COLLADALibraryVisualScenesLoader.loadLibraryVisualScenes(colladaFile, libraryVisualScenes);
                    logger.decreaseTabbing();
                }
            }

            logger.decreaseTabbing();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long t2 = System.nanoTime();

        logger.print("TT] Took " + ((t2 - t1) / 1000 / 1000)
                + " milliseconds to load..");

        // We still don't know what we will return..
        return colladaFile;

    }

    /**
     *
     * @return true if debug messages print is enabled
     */
    public static boolean isPrintEnabled() {

        return logger.isPrintEnabled();

    }

    /**
     * Enable/disable the printing of debug messages
     * @param printEnabled
     */
    public static void setPrintEnabled(boolean printEnabled) {

        logger.setPrintEnabled(printEnabled);

    }

}

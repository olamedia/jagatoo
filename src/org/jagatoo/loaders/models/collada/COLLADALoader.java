package org.jagatoo.loaders.models.collada;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADAController;
import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADASkeletalController;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAEffect;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADALibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAProfile;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAProfileCommon;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAMesh;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAMeshDataInfo;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAMeshSources;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADATrianglesGeometry;
import org.jagatoo.loaders.models.collada.datastructs.images.COLLADALibraryImages;
import org.jagatoo.loaders.models.collada.datastructs.images.COLLADASurface;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADALibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADAMaterial;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAGeometryInstanceNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADALibraryVisualScenes;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAMatrixTransform;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADANode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAScene;
import org.jagatoo.loaders.models.collada.jibx.BindMaterial;
import org.jagatoo.loaders.models.collada.jibx.COLLADA;
import org.jagatoo.loaders.models.collada.jibx.Controller;
import org.jagatoo.loaders.models.collada.jibx.Effect;
import org.jagatoo.loaders.models.collada.jibx.Geometry;
import org.jagatoo.loaders.models.collada.jibx.Image;
import org.jagatoo.loaders.models.collada.jibx.Input;
import org.jagatoo.loaders.models.collada.jibx.InstanceController;
import org.jagatoo.loaders.models.collada.jibx.InstanceGeometry;
import org.jagatoo.loaders.models.collada.jibx.InstanceMaterial;
import org.jagatoo.loaders.models.collada.jibx.LibraryControllers;
import org.jagatoo.loaders.models.collada.jibx.LibraryEffects;
import org.jagatoo.loaders.models.collada.jibx.LibraryGeometries;
import org.jagatoo.loaders.models.collada.jibx.LibraryImages;
import org.jagatoo.loaders.models.collada.jibx.LibraryMaterials;
import org.jagatoo.loaders.models.collada.jibx.LibraryVisualScenes;
import org.jagatoo.loaders.models.collada.jibx.Material;
import org.jagatoo.loaders.models.collada.jibx.Mesh;
import org.jagatoo.loaders.models.collada.jibx.Node;
import org.jagatoo.loaders.models.collada.jibx.ProfileCOMMON;
import org.jagatoo.loaders.models.collada.jibx.ProfileCOMMON_NewParam;
import org.jagatoo.loaders.models.collada.jibx.Sampler2D;
import org.jagatoo.loaders.models.collada.jibx.Source;
import org.jagatoo.loaders.models.collada.jibx.Surface;
import org.jagatoo.loaders.models.collada.jibx.Triangles;
import org.jagatoo.loaders.models.collada.jibx.VisualScene;
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
    private static HierarchicalOutputter logger = new HierarchicalOutputter();

    /** The unmarshalling context used to read COLLADA files */
    private IUnmarshallingContext unmarshallingContext;

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
     * @param path
     *            The file to load the scene from
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
                    logger
                    .print("CC] Found LibraryControllers ! Investigating... !");
                    logger.increaseTabbing();
                    loadLibraryControllers(colladaFile, libraryControllers);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryEffects> libraryEffectsList = collada.libraryEffects;
            if(libraryEffectsList != null) {
                for (LibraryEffects libraryEffects : libraryEffectsList) {
                    logger.print("CC] Found LibraryEffects ! Investigating... !");
                    logger.increaseTabbing();
                    loadLibraryEffects(colladaFile, libraryEffects);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryImages> libraryImagesList = collada.libraryImages;
            if(libraryImagesList != null) {
                for (LibraryImages libraryImages : libraryImagesList) {
                    logger.print("CC] Found LibraryImages ! We know that !");
                    logger.increaseTabbing();
                    loadLibraryImages(colladaFile, libraryImages);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryMaterials> libraryMaterialsList = collada.libraryMaterials;
            if(libraryMaterialsList != null) {
                for (LibraryMaterials libraryMaterials : libraryMaterialsList) {
                    logger.print("CC] Found LibraryMaterials ! We know that !");
                    logger.increaseTabbing();
                    loadLibraryMaterials(colladaFile, libraryMaterials);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryGeometries> libraryGeometriesList = collada.libraryGeometries;
            if(libraryGeometriesList != null) {
                for (LibraryGeometries libraryGeometries : libraryGeometriesList) {
                    logger.print("CC] Found LibraryGeometries ! We know that !");
                    logger.increaseTabbing();
                    loadLibraryGeometries(colladaFile, libraryGeometries);
                    logger.decreaseTabbing();
                }
            }

            List<LibraryVisualScenes> libraryVisualScenesList = collada.libraryVisualScenes;
            if(libraryVisualScenesList != null) {
                for (LibraryVisualScenes libraryVisualScenes : libraryVisualScenesList) {
                    logger
                    .print("CC] Found LibraryVisualScenes ! Investigating... !");
                    logger.increaseTabbing();
                    loadLibraryVisualScenes(colladaFile, libraryVisualScenes);
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
     * Load LibraryVisualScenes
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libScenes
     *            The JAXB data to load from
     */
    @SuppressWarnings("unchecked")
    private void loadLibraryVisualScenes(COLLADAFile colladaFile,
            LibraryVisualScenes libScenes) {

        COLLADALibraryVisualScenes colLibVisualScenes = colladaFile
        .getLibraryVisualsScenes();
        HashMap<String, COLLADAScene> scenes = colLibVisualScenes.getScenes();

        Collection<VisualScene> visualScenes = libScenes.scenes.values();

        logger.increaseTabbing();
        for (VisualScene visualScene : visualScenes) {

            COLLADAScene colScene = new COLLADAScene(visualScene.id,
                    visualScene.name);
            scenes.put(colScene.getId(), colScene);

            logger.print("TT] Found scene [" + colScene.getId() + ":"
                    + colScene.getName() + "]");
            logger.increaseTabbing();
            for (Node node : visualScene.nodes.values()) {

                logger.print("TT] Found node [" + node.id + ":"
                        + node.name + "]");
                logger.increaseTabbing();

                COLLADANode colNode = null;

                if (node.type == Node.Type.NODE) {

                    logger.print("TT] Alright, it's a basic node");

                    COLLADAMatrixTransform transform = new COLLADAMatrixTransform(node.matrix.matrix4f);

                    // FIXME : applying YAGNI here : we don't need to know whether these nodes are grouped or separate
                    if(node.instanceGeometries != null) {
                        for (InstanceGeometry instanceGeometry : node.instanceGeometries) {
                            colNode = newCOLLADANode(colladaFile, node, transform, instanceGeometry.url, instanceGeometry.bindMaterial);
                        }
                    } else if(node.instanceControllers != null) {
                        for (InstanceController instanceController : node.instanceControllers) {
                            COLLADAController controller = colladaFile.getLibraryControllers().getControllers().get(instanceController.url);
                            controller.updateDestinationGeometry();
                            colNode = newCOLLADANode(colladaFile, node, transform, controller.getDestinationGeometry().getGeometry().id, instanceController.bindMaterial);
                        }
                    }


                } else if (node.type == Node.Type.JOINT) {

                    logger
                    .print("TT] Joint nodes unsupported in the main loader yet (but successfully read in another program)");

                } else {

                    logger.print("TT] Node is of type : " + node.type
                            + " we don't support specific nodes yet...");

                }

                logger.decreaseTabbing();

                if (colNode != null) {
                    colScene.getNodes().put(colNode.getId(), colNode);
                } else {
                    logger.print("TT] NULL node !! Something went wrong...");
                }

            }
            logger.decreaseTabbing();
        }
        logger.decreaseTabbing();

    }

    private COLLADANode newCOLLADANode(COLLADAFile colladaFile, Node node,
            COLLADAMatrixTransform transform, String geometryUrl, BindMaterial bindMaterial) {
        COLLADANode colNode;
        String materialUrl = null;
        BindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
        List<InstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
        for (InstanceMaterial instanceMaterial : instanceMaterialList) {
            if(materialUrl == null) {
                materialUrl = instanceMaterial.target;
            } else {
                logger.print("TT] Several materials for the same geometry instance ! Skipping....");
            }
        }

        colNode = new COLLADAGeometryInstanceNode(
                colladaFile,
                node.id,
                node.name,
                transform,
                geometryUrl,
                materialUrl
        );
        return colNode;
    }

    /**
     * Convert a List<Double> to a float[]
     *
     * @param doubles
     *            The list of doubles
     * @return a float array
     */
    private float[] toFloats(List<Double> doubles) {
        float[] floats = new float[doubles.size()];
        for (int i = 0; i < doubles.size(); i++) {
            floats[i] = doubles.get(i).floatValue();
        }
        return floats;
    }

    /**
     * Load LibraryImages
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libImages
     *            The JAXB data to load from
     */
    private void loadLibraryImages(COLLADAFile colladaFile,
            LibraryImages libImages) {

        COLLADALibraryImages colLibImages = colladaFile.getLibraryImages();
        HashMap<String, String> colImages = colLibImages.getImages();

        Collection<Image> images = libImages.images.values();

        logger.increaseTabbing();
        for (Image image : images) {

            logger.print("TT] Found image [" + image.id + ":"
                    + image.initFrom + "]");
            colImages.put(image.id, image.initFrom);

        }
        logger.decreaseTabbing();

    }

    /**
     * Load LibraryMaterials
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libMaterials
     *            The JAXB data to load from
     */
    private void loadLibraryMaterials(COLLADAFile colladaFile,
            LibraryMaterials libMaterials) {

        COLLADALibraryMaterials colLibMaterials = colladaFile
        .getLibraryMaterials();
        HashMap<String, COLLADAMaterial> colMaterials = colLibMaterials
        .getMaterials();

        Collection<Material> materials = libMaterials.materials.values();

        logger.increaseTabbing();
        for (Material material : materials) {

            COLLADAMaterial colMaterial = new COLLADAMaterial(colladaFile,
                    material.id, material.instanceEffect.url);
            logger.print("TT] Found material [" + colMaterial.getId() + ":"
                    + colMaterial.getEffect() + "]");
            colMaterials.put(colMaterial.getId(), colMaterial);

        }
        logger.decreaseTabbing();

    }

    /**
     * Load LibraryControllers
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param controllers
     *            The JAXB data to load from
     */
    private void loadLibraryControllers(COLLADAFile colladaFile,
            LibraryControllers controllers) {

        HashMap<String, COLLADAController> controllersMap = colladaFile
        .getLibraryControllers().getControllers();
        Collection<Controller> controllersList = controllers.controllers.values();

        for (Controller controller : controllersList) {
            // FIXME : this is strange... a bug in Blender's exporter ?
            String source = controller.skin.source.replaceAll(" ", "_");
            String id = controller.id;
            logger.print("TT] Found controller with Id : \"" + id
                    + "\" and source : \"" + source + "\"");
            controllersMap.put(id, new COLLADASkeletalController(colladaFile.getLibraryGeometries(), source, controller));
        }

    }

    /**
     * Load LibraryEffects
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libEffects
     *            The JAXB data to load from
     */
    private void loadLibraryEffects(COLLADAFile colladaFile,
            LibraryEffects libEffects) {

        COLLADALibraryEffects colLibEffects = colladaFile.getLibraryEffects();
        Collection<Effect> effects = libEffects.effects;

        for (Effect effect : effects) {

            logger.print("TT] Effect \"" + effect.id + "\"");
            logger.increaseTabbing();
            COLLADAEffect colladaEffect = new COLLADAEffect(effect.id);
            colLibEffects.getEffects().put(colladaEffect.getId(), colladaEffect);
            colladaEffect.profiles = new ArrayList<COLLADAProfile>();

            if (effect.profileCOMMON != null) {
                logger.print("TT] Profile COMMON : loading...");
                logger.increaseTabbing();
                colladaEffect.profiles.add(loadCommonProfile(effect, effect.profileCOMMON));
                logger.decreaseTabbing();
            }
            if (effect.profileCG != null) {
                logger
                .print("EE] CG shaders profile isn't implemented yet !");
            }
            if (effect.profileGLSL != null) {
                logger
                .print("EE] GLSL shaders profile isn't implemented yet !");
            }
            logger.decreaseTabbing();

        }

    }

    /**
     * Loads a profile common
     * @param effect
     * @param profile
     * @param type
     * @return the loaded profile
     */
    private COLLADAProfile loadCommonProfile(Effect effect, ProfileCOMMON profile) {

        COLLADAProfileCommon colladaProfile = new COLLADAProfileCommon();
        colladaProfile.surfaces = new HashMap<String, COLLADASurface>();

        List<ProfileCOMMON_NewParam> newParams = profile.newParams;

        if(newParams != null) {

            for (ProfileCOMMON_NewParam newParam : newParams) {

                if (newParam.surface != null) {

                    Surface surface = newParam.surface;
                    COLLADASurface colladaSurface = new COLLADASurface(newParam.sid);
                    colladaSurface.imageIds = new ArrayList<String>();
                    logger.print("TT] Found surface ! (id = " + newParam.sid + ")");
                    logger.increaseTabbing();

                    String imageId = surface.initFrom;
                    logger.print("TT] Image id : " + imageId);
                    colladaSurface.imageIds.add(imageId);
                    colladaProfile.surfaces.put(colladaSurface.getId(), colladaSurface);
                    logger.decreaseTabbing();

                } else if (newParam.sampler2D != null) {

                    logger.print("TT] Found sampler ! (id = "+ newParam.sid + ")");
                    logger.increaseTabbing();
                    Sampler2D sampler2D = newParam.sampler2D;
                    logger.print("TT] Sampler using source : " + sampler2D.source);
                    logger.decreaseTabbing();

                }

            }

        }

        return colladaProfile;

    }

    /**
     * Load LibraryGeometries
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libGeoms
     *            The JAXB data to load from
     */
    private static COLLADALibraryGeometries loadLibraryGeometries(
            COLLADAFile colladaFile, LibraryGeometries libGeoms) {

        // LibraryGeometries
        COLLADALibraryGeometries colladaLibGeoms = colladaFile
        .getLibraryGeometries();

        Collection<Geometry> geoms = libGeoms.geometries.values();
        logger.print("There " + (geoms.size() > 1 ? "are" : "is") + " "
                + geoms.size() + " geometr" + (geoms.size() > 1 ? "ies" : "y")
                + " in this file.");

        int i = 0;
        for (Geometry geom : geoms) {

            logger.print("Handling geometry " + i++);

            COLLADAGeometry loadedGeom = loadGeom(geom);
            if(loadedGeom != null) {
                colladaLibGeoms.getGeometries().put(loadedGeom.getId(), loadedGeom);
            }

        }

        return colladaLibGeoms;

    }

    /**
     * Load a specific geometry
     *
     * @param geom
     */
    @SuppressWarnings("unchecked")
    private static COLLADAGeometry loadGeom(Geometry geom) {

        COLLADAGeometry colGeom = null;

        Mesh mesh = geom.mesh;

        String verticesSource = mesh.vertices.inputs.get(0).source;

        // First we get all the vertices/normal data we need
        List<Source> sources = mesh.sources;
        HashMap<String, Source> sourcesMap = getSourcesMap(mesh,verticesSource, sources);

        // Supported types :
        Triangles tris = mesh.triangles;

        // Unsupported types :
        //Polygons polys = mesh.polygons;

        // Try triangles
        if(tris != null) {
            logger.print("TT] Primitives of type triangles");
            logger.print("TT] Polygon count = " + tris.count);

            colGeom = loadTriangles(geom, tris, sourcesMap);
        }
        // Try polys
        /*else if(!polys.isEmpty()) {
            logger.print("TT] Primitives of type polygons");
            logger.print("TT] Polygon count = " + polys.count);

            colGeom = loadPolygons(geom, polys, sourcesMap);
        }*/
        // Well, no luck
        else {

            logger.print("EE] Can't load object : " + geom.name
                    + " because couldn't find a supported element type..." +
                    "\n (note that the only well supported type is triangles, so e.g." +
            "\n in Blender, activate the appropriate option in the export script");

        }

        return colGeom;

    }

    /**
     * Create the sources map from the information provided by JAXB
     *
     * @param mesh
     * @param verticesSource
     * @param sources
     * @return The sources map
     */
    private static HashMap<String, Source> getSourcesMap(Mesh mesh,
            String verticesSource, List<Source> sources) {
        HashMap<String, Source> sourcesMap;
        sourcesMap = new HashMap<String, Source>();
        for (int i = 0; i < sources.size(); i++) {

            Source source = sources.get(i);

            // FIXME There may be more
            if (verticesSource.equals(source.id)) {
                sourcesMap.put(mesh.vertices.id, source);
                logger.print("TT] Source " + i + " ID = "
                        + mesh.vertices.id);
            } else {
                // FIXME Are there other special cases ?
                sourcesMap.put(source.id, source);
                logger.print("TT] Source " + i + " ID = " + source.id);
            }

        }
        return sourcesMap;
    }

    /**
     * @param geom
     * @param sourcesMap
     * @param tris
     * @return the loaded geometry
     */
    @SuppressWarnings("unchecked")
    private static COLLADATrianglesGeometry loadTriangles(Geometry geom,
            Triangles tris, HashMap<String, Source> sourcesMap) {

        COLLADATrianglesGeometry trianglesGeometry = new COLLADATrianglesGeometry(
                null, geom.id, geom.name, geom);

        COLLADAMeshSources sources = new COLLADAMeshSources();
        COLLADAMeshDataInfo meshDataInfo = getMeshDataInfo(tris.inputs, sourcesMap, sources);

        COLLADAMesh mesh = new COLLADAMesh(sources);
        trianglesGeometry.mesh = mesh;

        int[] indices = tris.p;
        int count = 0;
        int indexCount = indices.length / meshDataInfo.maxOffset;

        if (meshDataInfo.vertexOffset != -1) {
            mesh.vertexIndices = new int[indexCount];
        }
        if (meshDataInfo.normalOffset != -1) {
            mesh.normalIndices = new int[indexCount];
        }
        if (meshDataInfo.colorOffset != -1) {
            mesh.colorIndices = new int[indexCount];
        }
        if (meshDataInfo.uvOffsets != null) {
            mesh.uvIndices = new ArrayList<int[]>(meshDataInfo.uvOffsets.size());
            for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                mesh.uvIndices.add(new int[indexCount]);
            }
        }

        /**
         * FILLING
         */
        for (int k = 0; k < indices.length; k += meshDataInfo.maxOffset) {
            if (meshDataInfo.vertexOffset != -1) {
                mesh.vertexIndices[count] = indices[k + meshDataInfo.vertexOffset];
            }
            if (meshDataInfo.normalOffset != -1) {
                mesh.normalIndices[count] = indices[k + meshDataInfo.normalOffset];
            }
            if (meshDataInfo.colorOffset != -1) {
                mesh.colorIndices[count] = indices[k + meshDataInfo.colorOffset];
            }
            if (meshDataInfo.uvOffsets != null) {
                for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                    mesh.uvIndices.get(i)[count] = indices[k + meshDataInfo.uvOffsets.get(i)];
                }
            }
            count++;
        }
        /**
         * FILLING
         */

        return trianglesGeometry;

    }

    /**
     * @param geom
     * @param poly
     * @param sourcesMap
     * @return the loaded geometry
     */
    /*@SuppressWarnings("unchecked")
    private static COLLADAPolygonsGeometry loadPolygons(Geometry geom,
            Polygons poly, HashMap<String, Source> sourcesMap) {

        COLLADAPolygonsGeometry polygonsGeometry = new COLLADAPolygonsGeometry(
                null, geom.id, geom.name, poly.getCount()
                , geom);

        COLLADAMeshSources sources = new COLLADAMeshSources();
        COLLADAMeshDataInfo meshDataInfo = getMeshDataInfo(poly.getInputList(),
                sourcesMap, sources);

        // FIXME : Currently conversion to Xith3D from Polygons mesh isn't
        // supported : but it's loaded anyway
        // We have to implement tesselation

        List<List> pList = poly.getPList();

        int count = 0;
        for (List valueList : pList) {

            List<BigInteger> indices = (List<BigInteger>) valueList;
            COLLADAMesh colMesh = new COLLADAMesh(sources);

            int indexCount = indices.size() / meshDataInfo.maxOffset;

            if (meshDataInfo.vertexOffset != -1) {
                colMesh.vertexIndices = new int[indexCount];
            }
            if (meshDataInfo.normalOffset != -1) {
                colMesh.normalIndices = new int[indexCount];
            }
            if (meshDataInfo.colorOffset != -1) {
                colMesh.colorIndices = new int[indexCount];
            }
            if (meshDataInfo.uvOffsets != null) {
                colMesh.uvIndices = new ArrayList<int[]>(meshDataInfo.uvOffsets
                        .size());
                for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                    colMesh.uvIndices.add(new int[indexCount]);
                }
            }

            /**
             * FILLING
             */
            /*for (int k = 0; k < indices.size(); k += meshDataInfo.maxOffset) {
                if (meshDataInfo.vertexOffset != -1) {
                    colMesh.vertexIndices[count] = indices.get(
                            k + meshDataInfo.vertexOffset);
                }
                if (meshDataInfo.normalOffset != -1) {
                    colMesh.normalIndices[count] = indices.get(
                            k + meshDataInfo.normalOffset);
                }
                if (meshDataInfo.colorOffset != -1) {
                    colMesh.normalIndices[count] = indices.get(
                            k + meshDataInfo.colorOffset);
                }
                if (meshDataInfo.uvOffsets != null) {
                    for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                        colMesh.uvIndices.get(i)[count] = indices.get(
                                k + meshDataInfo.uvOffsets.get(i));
                    }
                }
                count++;
            }
            /**
             * FILLING
             */

        /*}

        return polygonsGeometry;

    }*/

    /**
     * Get the mesh data info and fill the sources
     *
     * @param inputs
     * @param sourcesMap
     *            The sources map precedently filled by
     * @param sources
     *            The sources which are going to be filled. May NOT be null.
     * @return The mesh data info
     */
    @SuppressWarnings("unchecked")
    private static COLLADAMeshDataInfo getMeshDataInfo(List<Input> inputs, HashMap<String, Source> sourcesMap,
            COLLADAMeshSources sources) {

        COLLADAMeshDataInfo meshDataInfo = new COLLADAMeshDataInfo();

        sources.uvs = new ArrayList<float[]>();
        meshDataInfo.uvOffsets = new ArrayList<Integer>();

        logger.print("TT] Parsing semantics....");

        for (int j = 0; j < inputs.size(); j++) {
            Input input = inputs.get(j);
            logger.print("TT] Input semantic " + input.semantic
                    + ", offset " + input.offset + ", from source = "
                    + input.source);

            if (input.offset > meshDataInfo.maxOffset) {
                meshDataInfo.maxOffset = input.offset;
            }

            if (input.semantic.equals("VERTEX")) {

                meshDataInfo.vertexOffset = input.offset;
                Source source = sourcesMap.get(input.source);
                sources.vertices = source.floatArray.floats;


            } else if (input.semantic.equals("NORMAL")) {

                meshDataInfo.normalOffset = input.offset;
                Source source = sourcesMap.get(input.source);
                sources.normals = source.floatArray.floats;

            } else if (input.semantic.equals("TEXCOORD")) {

                meshDataInfo.uvOffsets.add(input.offset);
                Source source = sourcesMap.get(input.source);
                sources.uvs.add(source.floatArray.floats);

            } else if (input.semantic.equals("COLOR")) {

                meshDataInfo.colorOffset = input.offset;
                Source source = sourcesMap.get(input.source);
                sources.colors = source.floatArray.floats;

            } else {

                logger.print("EE] We don't know that semantic :"
                        + input.semantic + " ! Ignoring..");

            }
        }

        // It says it needs to be max + 1
        meshDataInfo.maxOffset += 1;

        return meshDataInfo;

    }

    public boolean isPrintEnabled() {

        return logger.isPrintEnabled();

    }

    public void setPrintEnabled(boolean printEnabled) {

        logger.setPrintEnabled(printEnabled);

    }

}

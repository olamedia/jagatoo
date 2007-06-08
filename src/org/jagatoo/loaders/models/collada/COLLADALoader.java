package org.jagatoo.loaders.models.collada;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Point3f;

import org.apache.xmlbeans.XmlObject;
import org.collada.x2005.x11.colladaSchema.COLLADADocument;
import org.collada.x2005.x11.colladaSchema.CommonNewparamType;
import org.collada.x2005.x11.colladaSchema.FxNewparamCommon;
import org.collada.x2005.x11.colladaSchema.FxSampler2DCommon;
import org.collada.x2005.x11.colladaSchema.FxSurfaceCommon;
import org.collada.x2005.x11.colladaSchema.FxSurfaceInitFromCommon;
import org.collada.x2005.x11.colladaSchema.InputLocalOffset;
import org.collada.x2005.x11.colladaSchema.NodeType;
import org.collada.x2005.x11.colladaSchema.TargetableFloat3;
import org.collada.x2005.x11.colladaSchema.COLLADADocument.COLLADA;
import org.collada.x2005.x11.colladaSchema.ControllerDocument.Controller;
import org.collada.x2005.x11.colladaSchema.EffectDocument.Effect;
import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.ImageDocument.Image;
import org.collada.x2005.x11.colladaSchema.LibraryAnimationsDocument.LibraryAnimations;
import org.collada.x2005.x11.colladaSchema.LibraryControllersDocument.LibraryControllers;
import org.collada.x2005.x11.colladaSchema.LibraryEffectsDocument.LibraryEffects;
import org.collada.x2005.x11.colladaSchema.LibraryGeometriesDocument.LibraryGeometries;
import org.collada.x2005.x11.colladaSchema.LibraryImagesDocument.LibraryImages;
import org.collada.x2005.x11.colladaSchema.LibraryMaterialsDocument.LibraryMaterials;
import org.collada.x2005.x11.colladaSchema.LibraryVisualScenesDocument.LibraryVisualScenes;
import org.collada.x2005.x11.colladaSchema.MaterialDocument.Material;
import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;
import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.collada.x2005.x11.colladaSchema.PolygonsDocument.Polygons;
import org.collada.x2005.x11.colladaSchema.ProfileCGDocument.ProfileCG;
import org.collada.x2005.x11.colladaSchema.ProfileCOMMONDocument.ProfileCOMMON;
import org.collada.x2005.x11.colladaSchema.ProfileGLESDocument.ProfileGLES;
import org.collada.x2005.x11.colladaSchema.ProfileGLSLDocument.ProfileGLSL;
import org.collada.x2005.x11.colladaSchema.RotateDocument.Rotate;
import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;
import org.collada.x2005.x11.colladaSchema.TrianglesDocument.Triangles;
import org.collada.x2005.x11.colladaSchema.VisualSceneDocument.VisualScene;
import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADASkeletalController;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAEffect;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADALibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAProfile;
import org.jagatoo.loaders.models.collada.datastructs.effects.COLLADAProfileCommon;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADALibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAMesh;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAMeshDataInfo;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAMeshSources;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAPolygonsGeometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADATrianglesGeometry;
import org.jagatoo.loaders.models.collada.datastructs.images.COLLADALibraryImages;
import org.jagatoo.loaders.models.collada.datastructs.images.COLLADASurface;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADALibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADAMaterial;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADALibraryVisualScenes;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAMatrixTransform;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADANode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAScene;
import org.jagatoo.loaders.models.collada.logging.HierarchicalOutputter;
import org.openmali.vecmath.Matrix4f;

/**
 * This is a really simple COLLADA file loader. Its features are limited for now
 * but improving every minute :)
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALoader {
    
    /** Are debug messages printed ? */
    private static HierarchicalOutputter logger = new HierarchicalOutputter();
    
    /**
     * Create a new COLLADA Loader.
     */
    public COLLADALoader() {
        
        // Nothing to do... XMLBeans doesn't require initialisation (as opposed
        // to JAXB2)
        
    }
    
    /**
     * Loads a COLLADA scene from a file
     * 
     * @param path
     *            The file to load the scene from
     * @return the loaded *thing*
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
     * Loads a COLLADA scene from a file
     * 
     * @param file
     *            The file to load the scene from
     * @return the loaded *thing*
     */
    public COLLADAFile load(File file) {
        
        logger.print("Loading the file : " + file.getPath());
        
        COLLADAFile collada = null;
        
        try {
            collada = load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return collada;
        
    }
    
    /**
     * Loads a COLLADA scene from a stream
     * 
     * @param stream
     *            The stream to load the scene from
     * @return the loaded *thing*
     */
    public COLLADAFile load(InputStream stream) {
        
        long t1 = System.nanoTime();
        
        COLLADAFile colladaFile = new COLLADAFile();
        
        try {
            
            logger.print("TT] Parsing...");
            logger.increaseTabbing();
            long l1 = System.nanoTime();
            COLLADA collada = COLLADADocument.Factory.parse(stream)
            .getCOLLADA();
            long l2 = System.nanoTime();
            logger.print("TT] Took " + ((l2 - l1) / 1000 / 1000)
                    + " milliseconds to parse");
            logger.decreaseTabbing();
            
            logger.print("--] This is a COLLADA " + collada.getVersion()
                    + " file");
            logger
            .print("--] Note that the loader don't care whether it's 1.4.0 or 1.4.1, though"
                    + "\n the COLLADA schema used for parsing is the for 1.4.1, tests for development"
                    + "\n have been done with 1.4.0 files exported by Blender (Illusoft script)");
            
            logger.print("TT] Exploring libs...");
            
            logger.increaseTabbing();
            
            List<LibraryAnimations> libraryAnimationsList = collada
            .getLibraryAnimationsList();
            for (LibraryAnimations libraryAnimations : libraryAnimationsList) {
                logger
                .print("SS] Found LibraryAnimations ! Hmm we gotta implement animations soon !");
            }
            
            List<LibraryControllers> libraryControllersList = collada
            .getLibraryControllersList();
            for (LibraryControllers libraryControllers : libraryControllersList) {
                logger
                .print("CC] Found LibraryControllers ! Investigating... !");
                logger.increaseTabbing();
                loadLibraryControllers(colladaFile, libraryControllers);
                logger.decreaseTabbing();
            }
            
            List<LibraryEffects> libraryEffectsList = collada
            .getLibraryEffectsList();
            for (LibraryEffects libraryEffects : libraryEffectsList) {
                logger.print("CC] Found LibraryEffects ! Investigating... !");
                logger.increaseTabbing();
                loadLibraryEffects(colladaFile, libraryEffects);
                logger.decreaseTabbing();
            }
            
            List<LibraryImages> libraryImagesList = collada
            .getLibraryImagesList();
            for (LibraryImages libraryImages : libraryImagesList) {
                logger.print("CC] Found LibraryImages ! We know that !");
                logger.increaseTabbing();
                loadLibraryImages(colladaFile, libraryImages);
                logger.decreaseTabbing();
            }
            
            List<LibraryMaterials> libraryMaterialsList = collada
            .getLibraryMaterialsList();
            for (LibraryMaterials libraryMaterials : libraryMaterialsList) {
                logger.print("CC] Found LibraryMaterials ! We know that !");
                logger.increaseTabbing();
                loadLibraryMaterials(colladaFile, libraryMaterials);
                logger.decreaseTabbing();
            }
            
            List<LibraryGeometries> libraryGeometriesList = collada
            .getLibraryGeometriesList();
            for (LibraryGeometries libraryGeometries : libraryGeometriesList) {
                logger.print("CC] Found LibraryGeometries ! We know that !");
                logger.increaseTabbing();
                loadLibraryGeometries(colladaFile, libraryGeometries);
                logger.decreaseTabbing();
            }
            
            List<LibraryVisualScenes> libraryVisualScenesList = collada
            .getLibraryVisualScenesList();
            for (LibraryVisualScenes libraryVisualScenes : libraryVisualScenesList) {
                logger
                .print("CC] Found LibraryVisualScenes ! Investigating... !");
                logger.increaseTabbing();
                loadLibraryVisualScenes(colladaFile, libraryVisualScenes);
                logger.decreaseTabbing();
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
        
        List<VisualScene> visualScenes = libScenes.getVisualSceneList();
        
        logger.increaseTabbing();
        for (VisualScene visualScene : visualScenes) {
            
            COLLADAScene colScene = new COLLADAScene(visualScene.getId(),
                    visualScene.getName());
            scenes.put(colScene.getId(), colScene);
            
            logger.print("TT] Found scene [" + colScene.getId() + ":"
                    + colScene.getName() + "]");
            logger.increaseTabbing();
            for (Node node : visualScene.getNodeList()) {
                
                logger.print("TT] Found node [" + node.getId() + ":"
                        + node.getName() + "]");
                logger.increaseTabbing();
                
                COLLADANode colNode = null;
                
                if (node.getType() == NodeType.NODE) {
                    
                    logger.print("TT] Alright, it's a basic node");
                    
                    COLLADAMatrixTransform transform = new COLLADAMatrixTransform();
                    
                    List<Matrix> matrixList = node.getMatrixList();
                    if(!matrixList.isEmpty()) {
                        // We have some matrices (usually one)
                        // That means the node transform has been baked to
                        // a matrix by Blender (I take Blender as a reference)
                        if(matrixList.size() != 1) {
                            logger.print("EE] Error ! We have several matrices.. Taking only the first one");
                        }
                        Matrix matrix = matrixList.get(0);
                        Matrix4f mat = new Matrix4f(toFloats(matrix.getListValue()));
                        
                        transform.getMatrix().mul(mat);
                        logger.print("TT] Transform by a matrix : \n"
                                + ((COLLADAMatrixTransform) transform)
                                .getMatrix());
                    } else {
                        
                        // No matrices ? ok we have transform splitted into
                        // translate, rotateX/Y/Z and scale elements
                        
                        // Let's first do translate
                        List<TargetableFloat3> translateList = node.getTranslateList();
                        
                        for (TargetableFloat3 float3 : translateList) {
                            float[] floats = toFloats(float3.getListValue());
                            Point3f translation = new Point3f(floats[0],
                                    floats[1], floats[2]);
                            transform.getMatrix().setTranslation(
                                    translation);
                            logger.print("TT] Transform by a translation : " + translation);
                            
                            logger.print("TT] Matrix is now : "
                                    + transform.getMatrix());
                        }
                        
                        // Then rotate
                        List<Rotate> rotateList = node.getRotateList();
                        
                        for (Rotate rotate : rotateList) {
                            
                            float angle = ((Double)rotate.getListValue().get(3)).floatValue();
                            String axis = "";
                            Matrix4f rotation = new Matrix4f();
                            rotation.setIdentity();
                            if (rotate.getSid().endsWith("Z")) {
                                axis = "Z";
                                rotation.rotZ((float) Math.toRadians(angle));
                            } else if (rotate.getSid().endsWith("Y")) {
                                axis = "Y";
                                rotation.rotY((float) Math.toRadians(angle));
                            } else if (rotate.getSid().endsWith("X")) {
                                axis = "X";
                                rotation.rotX((float) Math.toRadians(angle));
                            } else {
                                logger
                                .print("EE] ERROR ! Cannot recognize the sid of a rotate : "
                                        + rotate.getSid());
                            }
                            transform.getMatrix().mul(rotation);
                            logger
                            .print("TT] Transform by a rotation around the "
                                    + axis
                                    + " axis of "
                                    + angle
                                    + " degrees");
                            
                            logger.print("TT] Matrix is now : "
                                    + transform.getMatrix());
                            
                        }
                        
                        // And finally, scale
                        List<TargetableFloat3> scaleList = node.getScaleList();
                        
                        for (TargetableFloat3 float3 : scaleList) {
                            
                            float[] floats = toFloats(float3.getListValue());
                            Matrix4f scaling = new Matrix4f();
                            scaling.setIdentity();
                            scaling.m30 = floats[0];
                            scaling.m31 = floats[1];
                            scaling.m32 = floats[2];
                            transform.getMatrix().mul(scaling);
                            logger.print("TT] Transform by a scaling of ["
                                    + floats[0] + ", " + floats[1] + ", "
                                    + floats[2] + "]");
                            
                            logger.print("TT] Matrix is now : "
                                    + transform.getMatrix());
                            
                        }
                        
                    }
                    
                    colNode = new COLLADANode(node.getId(), node.getName(), transform);
                    
                } else if (node.getType() == NodeType.JOINT) {
                    
                    logger
                    .print("TT] Joint nodes unsupported yet !! (but to come soon...)");
                    
                } else {
                    
                    logger.print("TT] Node is of type : " + node.getType()
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
        
        List<Image> images = libImages.getImageList();
        
        logger.increaseTabbing();
        for (Image image : images) {
            
            logger.print("TT] Found image [" + image.getId() + ":"
                    + image.getInitFrom() + "]");
            colImages.put(image.getId(), image.getInitFrom());
            
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
        
        List<Material> materials = libMaterials.getMaterialList();
        
        logger.increaseTabbing();
        for (Material material : materials) {
            
            COLLADAMaterial colMaterial = new COLLADAMaterial(material.getId(),
                    removeSharp(material.getInstanceEffect().getUrl()));
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
        
        HashMap<String, COLLADAGeometryProvider> controllersMap = colladaFile
        .getLibraryControllers().getControllers();
        List<Controller> controllersList = controllers.getControllerList();
        
        for (Controller controller : controllersList) {
            String source = removeSharp(controller.getSkin().getSource2())
            .replaceAll(" ", "_");
            String id = controller.getId();
            logger.print("TT] Found controller with Id : \"" + id
                    + "\" and source : \"" + source + "\"");
            controllersMap.put(id, new COLLADASkeletalController(colladaFile
                    .getLibraryGeometries(), source));
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
        List<Effect> effects = libEffects.getEffectList();
        
        for (Effect effect : effects) {
            
            logger.print("TT] Effect \"" + effect.getId() + "\"");
            logger.increaseTabbing();
            COLLADAEffect colladaEffect = new COLLADAEffect(effect.getId());
            colLibEffects.getEffects()
            .put(colladaEffect.getId(), colladaEffect);
            colladaEffect.profiles = new ArrayList<COLLADAProfile>();
            
            List<XmlObject> fxProfileAbstract = effect.getFxProfileAbstractList();
            
            for (XmlObject element : fxProfileAbstract) {
                
                Object profileType = element;
                
                if (profileType instanceof ProfileCOMMON) {
                    logger.print("TT] Profile COMMON : loading...");
                    logger.increaseTabbing();
                    colladaEffect.profiles.add(loadCommonProfile(effect, (ProfileCOMMON) profileType));
                    logger.decreaseTabbing();
                } else if (profileType instanceof ProfileCG) {
                    logger
                    .print("EE] CG shaders profile isn't implemented yet !");
                } else if (profileType instanceof ProfileGLSL) {
                    logger
                    .print("EE] GLSL shaders profile isn't implemented yet !");
                } else if (profileType instanceof ProfileGLES) {
                    logger
                    .print("EE] GLES shaders profile isn't implemented yet !");
                } else {
                    logger.print("EE] Unknown profile type : "
                            + profileType.getClass().getName());
                }
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
        
        List<CommonNewparamType> newParams = profile.getNewparamList();
        
        for (CommonNewparamType newParam : newParams) {
            
            if (newParam.getSurface() != null) {
                
                FxSurfaceCommon surface = newParam.getSurface();
                COLLADASurface colladaSurface = new COLLADASurface(newParam
                        .getSid());
                colladaSurface.imageIds = new ArrayList<String>();
                logger.print("TT] Found surface ! (id = "
                        + newParam.getSid() + ")");
                logger.increaseTabbing();
                
                List<FxSurfaceInitFromCommon> initFroms = surface.getInitFromList();
                for (FxSurfaceInitFromCommon initFrom : initFroms) {
                    logger.print("TT] Alright : found an image");
                    String imageId = initFrom.getStringValue();
                    logger.print("TT] Image id : " + imageId);
                    colladaSurface.imageIds.add(imageId);
                }
                colladaProfile.surfaces.put(colladaSurface.getId(),
                        colladaSurface);
                logger.decreaseTabbing();
                
            } else if (newParam.getSampler2D() != null) {
                
                logger.print("TT] Found sampler ! (id = "
                        + newParam.getSid() + ")");
                logger.increaseTabbing();
                FxSampler2DCommon sampler2D = newParam.getSampler2D();
                logger.print("TT] Sampler using source : "
                        + sampler2D.getSource());
                logger.decreaseTabbing();
                
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
        
        List<Geometry> geoms = libGeoms.getGeometryList();
        logger.print("There " + (geoms.size() > 1 ? "are" : "is") + " "
                + geoms.size() + " geometr" + (geoms.size() > 1 ? "ies" : "y")
                + " in this file.");
        
        for (int i = 0; i < geoms.size(); i++) {
            
            logger.print("Handling geometry " + i);
            Geometry geom = (Geometry) geoms.get(i);
            
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
        
        Mesh mesh = geom.getMesh();
        
        String verticesSource = removeSharp(mesh.getVertices().getInputList()
                .get(0).getSource());
        
        // First we get all the vertices/normal data we need
        List<Source> sources = mesh.getSourceList();
        
        HashMap<String, Source> sourcesMap = getSourcesMap(mesh,
                verticesSource, sources);
        
        // Supported types
        List<Triangles> trianglesList = mesh.getTrianglesList();
        List<Polygons> polygonsList = mesh.getPolygonsList(); // Well, more or less... only if polys are all triangles
        
        // Try triangles
        if(!trianglesList.isEmpty()) {
            if(trianglesList.size() != 1) {
                logger.print("EE] There are more than one triangles element.. taking the first one");
            }
            Triangles tris = trianglesList.get(0);
            logger.print("TT] Primitives of type triangles");
            logger.print("TT] Polygon count = " + tris.getCount());
            
            colGeom = loadTriangles(geom, tris, sourcesMap);
        }
        // Try polys
        else if(!polygonsList.isEmpty()) {
            if(trianglesList.size() != 1) {
                logger.print("EE] There are more than one triangles element.. taking the first one");
            }
            Polygons polys = polygonsList.get(0);
            logger.print("TT] Primitives of type polygons");
            logger.print("TT] Polygon count = " + polys.getCount());
            
            colGeom = loadPolygons(geom, polys, sourcesMap);
        }
        // Well, no luck
        else {
                
            logger.print("EE] Can't load object : " + geom.getName()
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
            if (verticesSource.equals(source.getId())) {
                sourcesMap.put(mesh.getVertices().getId(), source);
                logger.print("TT] Source " + i + " ID = "
                        + mesh.getVertices().getId());
            } else {
                // FIXME Are there other special cases ?
                sourcesMap.put(source.getId(), source);
                logger.print("TT] Source " + i + " ID = " + source.getId());
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
                geom.getId(), geom.getName());
        
        COLLADAMeshSources sources = new COLLADAMeshSources();
        COLLADAMeshDataInfo meshDataInfo = getMeshDataInfo(tris.getInputList(),
                sourcesMap, sources);
        
        COLLADAMesh mesh = new COLLADAMesh(sources);
        trianglesGeometry.mesh = mesh;
        
        List<BigInteger> indices = tris.getP();
        int count = 0;
        int indexCount = indices.size() / meshDataInfo.maxOffset;
        
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
        for (int k = 0; k < indices.size(); k += meshDataInfo.maxOffset) {
            if (meshDataInfo.vertexOffset != -1) {
                mesh.vertexIndices[count] = indices.get(
                        k + meshDataInfo.vertexOffset).intValue();
            }
            if (meshDataInfo.normalOffset != -1) {
                mesh.normalIndices[count] = indices.get(
                        k + meshDataInfo.normalOffset).intValue();
            }
            if (meshDataInfo.colorOffset != -1) {
                mesh.colorIndices[count] = indices.get(
                        k + meshDataInfo.colorOffset).intValue();
            }
            if (meshDataInfo.uvOffsets != null) {
                for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                    mesh.uvIndices.get(i)[count] = indices.get(
                            k + meshDataInfo.uvOffsets.get(i)).intValue();
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
     * @param geometry
     * @param poly
     * @param sourcesMap
     * @return the loaded geometry
     */
    @SuppressWarnings("unchecked")
    private static COLLADAPolygonsGeometry loadPolygons(Geometry geometry,
            Polygons poly, HashMap<String, Source> sourcesMap) {
        
        COLLADAPolygonsGeometry polygonsGeometry = new COLLADAPolygonsGeometry(
                geometry.getId(), geometry.getName(), poly.getCount()
                .intValue());
        
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
            for (int k = 0; k < indices.size(); k += meshDataInfo.maxOffset) {
                if (meshDataInfo.vertexOffset != -1) {
                    colMesh.vertexIndices[count] = indices.get(
                            k + meshDataInfo.vertexOffset).intValue();
                }
                if (meshDataInfo.normalOffset != -1) {
                    colMesh.normalIndices[count] = indices.get(
                            k + meshDataInfo.normalOffset).intValue();
                }
                if (meshDataInfo.colorOffset != -1) {
                    colMesh.normalIndices[count] = indices.get(
                            k + meshDataInfo.colorOffset).intValue();
                }
                if (meshDataInfo.uvOffsets != null) {
                    for (int i = 0; i < meshDataInfo.uvOffsets.size(); i++) {
                        colMesh.uvIndices.get(i)[count] = indices.get(
                                k + meshDataInfo.uvOffsets.get(i)).intValue();
                    }
                }
                count++;
            }
            /**
             * FILLING
             */
            
        }
        
        return polygonsGeometry;
        
    }
    
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
    private static COLLADAMeshDataInfo getMeshDataInfo(
            List<InputLocalOffset> inputs, HashMap<String, Source> sourcesMap,
            COLLADAMeshSources sources) {
        
        COLLADAMeshDataInfo meshDataInfo = new COLLADAMeshDataInfo();
        
        sources.uvs = new ArrayList<float[]>();
        meshDataInfo.uvOffsets = new ArrayList<Integer>();
        
        logger.print("TT] Parsing semantics....");
        
        for (int j = 0; j < inputs.size(); j++) {
            InputLocalOffset input = inputs.get(j);
            logger.print("TT] Input semantic " + input.getSemantic()
                    + ", offset " + input.getOffset() + ", from source = "
                    + input.getSource());
            
            if (input.getOffset().intValue() > meshDataInfo.maxOffset) {
                meshDataInfo.maxOffset = input.getOffset().intValue();
            }
            
            if (input.getSemantic().equals("VERTEX")) {
                
                meshDataInfo.vertexOffset = input.getOffset().intValue();
                
                Source source = sourcesMap.get(removeSharp(input.getSource()));
                List<Double> verticesList = source.getFloatArray1().getListValue();
                sources.vertices = new float[verticesList.size()];
                for (int k = 0; k < sources.vertices.length; k++) {
                    sources.vertices[k] = verticesList.get(k).floatValue();
                }
                
            } else if (input.getSemantic().equals("NORMAL")) {
                
                meshDataInfo.normalOffset = input.getOffset().intValue();
                Source source = sourcesMap.get(removeSharp(input.getSource()));
                List<Double> normalsList = source.getFloatArray1().getListValue();
                sources.normals = new float[normalsList.size()];
                for (int k = 0; k < sources.normals.length; k++) {
                    sources.normals[k] = normalsList.get(k).floatValue();
                }
                
            } else if (input.getSemantic().equals("TEXCOORD")) {
                
                meshDataInfo.uvOffsets.add(input.getOffset().intValue());
                Source source = sourcesMap.get(removeSharp(input.getSource()));
                List<Double> uvList = source.getFloatArray1().getListValue();
                float[] uvs = new float[uvList.size()];
                for (int k = 0; k < uvs.length; k++) {
                    uvs[k] = uvList.get(k).floatValue();
                }
                sources.uvs.add(uvs);
                
            } else if (input.getSemantic().equals("COLOR")) {
                
                meshDataInfo.colorOffset = input.getOffset().intValue();
                Source source = sourcesMap.get(removeSharp(input.getSource()));
                List<Double> colorsList = source.getFloatArray1().getListValue();
                sources.colors = new float[colorsList.size()];
                for (int k = 0; k < sources.colors.length; k++) {
                    sources.colors[k] = colorsList.get(k).floatValue();
                }
                
            } else {
                
                logger.print("EE] We don't know that semantic :"
                        + input.getSemantic() + " ! Ignoring..");
                
            }
        }
        
        // It says it needs to be max + 1
        meshDataInfo.maxOffset += 1;
        
        return meshDataInfo;
        
    }
    
    /**
     * Removes the trailing "#" in a source name if there's one.
     * 
     * @param source
     *            The name of the source to operate on.
     * @return A copy of the name, without the trailing "#"
     */
    private static String removeSharp(String source) {
        
        String result = new String(source);
        
        // Remove the "#" FIXME : this is not nearly optimal !
        // Maybe there are other URLs hacks to do...
        if (result.startsWith("#")) {
            result = result.substring(1);
        }
        
        return result;
        
    }
    
    /**
     * This is the main method of the program. It reads a file from my (Amos
     * Wenger) local file system in order to increase my knowledge of the
     * COLLADA file format.
     * 
     * @param argv
     */
    public static void main(String argv[]) {
        
        final String file = "/home/bluesky/Desktop/Bureau/Armatured.dae";
        COLLADALoader loader = new COLLADALoader();
        loader.load(file);
        
    }
    
}

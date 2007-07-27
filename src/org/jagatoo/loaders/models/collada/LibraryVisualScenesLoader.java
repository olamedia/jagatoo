package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.GeometryInstanceNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.LibraryVisualScenes;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.MatrixTransform;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Node;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Scene;
import org.jagatoo.loaders.models.collada.jibx.XMLBindMaterial;
import org.jagatoo.loaders.models.collada.jibx.XMLInstanceController;
import org.jagatoo.loaders.models.collada.jibx.XMLInstanceGeometry;
import org.jagatoo.loaders.models.collada.jibx.XMLInstanceMaterial;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryVisualScenes;
import org.jagatoo.loaders.models.collada.jibx.XMLNode;
import org.jagatoo.loaders.models.collada.jibx.XMLVisualScene;

/**
 * Class used to load LibraryVisualScenes
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryVisualScenesLoader {

    /**
     * Load LibraryVisualScenes
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libScenes
     *            The JAXB data to load from
     */
    @SuppressWarnings("unchecked")
    static void loadLibraryVisualScenes(AssetFolder colladaFile,
            XMLLibraryVisualScenes libScenes) {

        LibraryVisualScenes colLibVisualScenes = colladaFile
        .getLibraryVisualsScenes();
        HashMap<String, Scene> scenes = colLibVisualScenes.getScenes();

        Collection<XMLVisualScene> visualScenes = libScenes.scenes.values();

        COLLADALoader.logger.increaseTabbing();
        for (XMLVisualScene visualScene : visualScenes) {

            Scene colScene = new Scene(visualScene.id,
                    visualScene.name);
            scenes.put(colScene.getId(), colScene);

            COLLADALoader.logger.print("TT] Found scene [" + colScene.getId() + ":"
                    + colScene.getName() + "]");
            COLLADALoader.logger.increaseTabbing();
            for (XMLNode node : visualScene.nodes.values()) {

                COLLADALoader.logger.print("TT] Found node [" + node.id + ":"
                        + node.name + "]");
                COLLADALoader.logger.increaseTabbing();

                Node colNode = null;

                if (node.type == XMLNode.Type.NODE) {

                    COLLADALoader.logger.print("TT] Alright, it's a basic node");

                    MatrixTransform transform = new MatrixTransform(node.matrix.matrix4f);

                    // FIXME : applying YAGNI here : we don't need to know whether these nodes are grouped or separate
                    if(node.instanceGeometries != null) {
                        for (XMLInstanceGeometry instanceGeometry : node.instanceGeometries) {
                            colNode = newCOLLADANode(colladaFile, node, transform, instanceGeometry.url, instanceGeometry.bindMaterial);
                        }
                    } else if(node.instanceControllers != null) {
                        for (XMLInstanceController instanceController : node.instanceControllers) {
                            Controller controller = colladaFile.getLibraryControllers().getControllers().get(instanceController.url);
                            controller.updateDestinationGeometry();
                            colNode = newCOLLADANode(colladaFile, node, transform, controller.getDestinationGeometry().getGeometry().id, instanceController.bindMaterial);
                        }
                    }


                } else if (node.type == XMLNode.Type.JOINT) {

                    Skeleton skeleton = SkeletonLoader.loadSkeleton(node);

                    COLLADALoader.logger
                    .print("TT] Joint nodes unsupported in the main loader yet (but successfully read in another program)");

                } else {

                    COLLADALoader.logger.print("TT] Node is of type : " + node.type
                            + " we don't support specific nodes yet...");

                }

                COLLADALoader.logger.decreaseTabbing();

                if (colNode != null) {
                    colScene.getNodes().put(colNode.getId(), colNode);
                } else {
                    COLLADALoader.logger.print("TT] NULL node !! Something went wrong...");
                }

            }
            COLLADALoader.logger.decreaseTabbing();
        }
        COLLADALoader.logger.decreaseTabbing();

    }

    /**
     * Creates a new COLLADA node from the informations given
     * @param colladaFile
     * @param node
     * @param transform
     * @param geometryUrl
     * @param bindMaterial
     * @return
     */
    static Node newCOLLADANode(AssetFolder colladaFile, XMLNode node,
            MatrixTransform transform, String geometryUrl, XMLBindMaterial bindMaterial) {
        Node colNode;
        String materialUrl = null;
        XMLBindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
        List<XMLInstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
        for (XMLInstanceMaterial instanceMaterial : instanceMaterialList) {
            if(materialUrl == null) {
                materialUrl = instanceMaterial.target;
            } else {
                COLLADALoader.logger.print("TT] Several materials for the same geometry instance ! Skipping....");
            }
        }

        colNode = new GeometryInstanceNode(
                colladaFile,
                node.id,
                node.name,
                transform,
                geometryUrl,
                materialUrl
        );
        return colNode;
    }

}

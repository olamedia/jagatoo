package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.controllers.COLLADAController;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAGeometryInstanceNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADALibraryVisualScenes;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAMatrixTransform;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADANode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADAScene;
import org.jagatoo.loaders.models.collada.jibx.BindMaterial;
import org.jagatoo.loaders.models.collada.jibx.InstanceController;
import org.jagatoo.loaders.models.collada.jibx.InstanceGeometry;
import org.jagatoo.loaders.models.collada.jibx.InstanceMaterial;
import org.jagatoo.loaders.models.collada.jibx.LibraryVisualScenes;
import org.jagatoo.loaders.models.collada.jibx.Node;
import org.jagatoo.loaders.models.collada.jibx.VisualScene;

/**
 * Class used to load LibraryVisualScenes
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALibraryVisualScenesLoader {

    /**
     * Load LibraryVisualScenes
     *
     * @param colladaFile
     *            The collada file to add them to
     * @param libScenes
     *            The JAXB data to load from
     */
    @SuppressWarnings("unchecked")
    static void loadLibraryVisualScenes(COLLADAFile colladaFile,
            LibraryVisualScenes libScenes) {

        COLLADALibraryVisualScenes colLibVisualScenes = colladaFile
        .getLibraryVisualsScenes();
        HashMap<String, COLLADAScene> scenes = colLibVisualScenes.getScenes();

        Collection<VisualScene> visualScenes = libScenes.scenes.values();

        COLLADALoader.logger.increaseTabbing();
        for (VisualScene visualScene : visualScenes) {

            COLLADAScene colScene = new COLLADAScene(visualScene.id,
                    visualScene.name);
            scenes.put(colScene.getId(), colScene);

            COLLADALoader.logger.print("TT] Found scene [" + colScene.getId() + ":"
                    + colScene.getName() + "]");
            COLLADALoader.logger.increaseTabbing();
            for (Node node : visualScene.nodes.values()) {

                COLLADALoader.logger.print("TT] Found node [" + node.id + ":"
                        + node.name + "]");
                COLLADALoader.logger.increaseTabbing();

                COLLADANode colNode = null;

                if (node.type == Node.Type.NODE) {

                    COLLADALoader.logger.print("TT] Alright, it's a basic node");

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
    static COLLADANode newCOLLADANode(COLLADAFile colladaFile, Node node,
            COLLADAMatrixTransform transform, String geometryUrl, BindMaterial bindMaterial) {
        COLLADANode colNode;
        String materialUrl = null;
        BindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
        List<InstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
        for (InstanceMaterial instanceMaterial : instanceMaterialList) {
            if(materialUrl == null) {
                materialUrl = instanceMaterial.target;
            } else {
                COLLADALoader.logger.print("TT] Several materials for the same geometry instance ! Skipping....");
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

}

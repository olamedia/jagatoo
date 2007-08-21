package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.materials.Material;

/**
 * A node containing an instance of a controller.
 * Note that the COLLADA file format is more like a scenegraph
 * than a list of nodes. But I'm applying the YAGNI here : I use
 * files from Blender only. If we ever need more, then we'll change
 * it.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class ControllerInstanceNode extends Node {

    /** Our controller */
    private String controllerUrl;
    private String materialUrl;
    
    /**
     * Create a new {@link ControllerInstanceNode}
     * @param file The COLLADA file this node belongs to
     * @param id The id of this node
     * @param name The name of this node
     * @param transform The transform of this node
     * @param controllerUrl The URL of the geometry this node is an instance of
     * @param materialUrl The URL of the material bound to this node
     */
    public ControllerInstanceNode(AssetFolder file, String id, String name, COLLADATransform transform, String controllerUrl, String materialUrl) {
        
        super(file, id, name, transform);
        this.controllerUrl = controllerUrl;
        this.materialUrl = materialUrl;
        
    }
    
    /**
     * @return the geometry
     */
    public Controller getController() {
        
        return file.getLibraryControllers().getControllers().get(controllerUrl);
        
    }
    
    /**
     * @return the material
     */
    public Material getMaterial() {
        
        return file.getLibraryMaterials().getMaterials().get(materialUrl);
        
    }
    
}

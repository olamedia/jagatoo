package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.materials.Material;

/**
 * A node containing an instance of a geometry.
 * Note that the COLLADA file format is more like a scenegraph
 * than a list of nodes. But I'm applying the YAGNI here : I use
 * files from Blender only. If we ever need more, then we'll change
 * it.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class GeometryInstanceNode extends Node {

    /** Our geometry */
    private String geometryID;
    private String materialID;
    
    /**
     * Create a new {@link GeometryInstanceNode}
     * @param file The COLLADA file this node belongs to
     * @param id The id of this node
     * @param name The name of this node
     * @param transform The transform of this node
     * @param geometryUrl The URL of the geometry this node is an instance of
     * @param materialUrl The URL of the material bound to this node
     */
    public GeometryInstanceNode(AssetFolder file, String id, String name, COLLADATransform transform, String geometryUrl, String materialUrl) {
        
        super(file, id, name, transform);
        this.geometryID = geometryUrl;
        this.materialID = materialUrl;
        
    }
    
    /**
     * @return the geometry
     */
    public Geometry getGeometry() {
        
        return file.getLibraryGeometries().getGeometries().get(geometryID);
        
    }
    
    /**
     * @return the material
     */
    public Material getMaterial() {
        
        return file.getLibraryMaterials().getMaterials().get(materialID);
        
    }
    
}

package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import org.jagatoo.loaders.models.collada.datastructs.COLLADAFile;
import org.jagatoo.loaders.models.collada.datastructs.geometries.COLLADAGeometry;
import org.jagatoo.loaders.models.collada.datastructs.materials.COLLADAMaterial;

/**
 * A node containing an instance of a geometry.
 * Note that the COLLADA file format is more like a scenegraph
 * than a list of nodes. But I'm applying the YAGNI here : I use
 * files from Blender only. If we ever need more, then we'll change
 * it.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADAGeometryInstanceNode extends COLLADANode {

    /** Our geometry */
    private String geometryUrl;
    private String materialUrl;

    /**
     * Create a new {@link COLLADAGeometryInstanceNode}
     * @param file The COLLADA file this node belongs to
     * @param id The id of this node
     * @param name The name of this node
     * @param transform The transform of this node
     * @param geometryUrl The URL of the geometry this node is an instance of
     * @param materialUrl The URL of the material bound to this node
     */
    public COLLADAGeometryInstanceNode(COLLADAFile file, String id, String name, COLLADATransform transform, String geometryUrl, String materialUrl) {

        super(file, id, name, transform);
        this.geometryUrl = geometryUrl;
        this.materialUrl = materialUrl;

    }

    /**
     *
     * @return the geometry
     */
    public COLLADAGeometry getGeometry() {

        return file.getLibraryGeometries().getGeometries().get(geometryUrl);

    }

    /**
    *
    * @return the material
    */
   public COLLADAMaterial getMaterial() {

       return file.getLibraryMaterials().getMaterials().get(materialUrl);

   }

}

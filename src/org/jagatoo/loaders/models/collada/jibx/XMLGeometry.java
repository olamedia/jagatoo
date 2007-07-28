package org.jagatoo.loaders.models.collada.jibx;

/**
 * A geometry contained in a COLLADA file.
 * Child of LibraryGeometries.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLGeometry {
    
    public XMLAsset asset = null;
    
    public String id = null;
    public String name = null;
    public XMLMesh mesh = null;
    /**
     * Convex meshes are unsupported for now. It will
     * stay null after loading even if the file has
     * a convex_mesh. Please use Mesh instead.
     */
    public Object convexMesh = null;
    
}

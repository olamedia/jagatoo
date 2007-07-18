package org.jagatoo.loaders.models.collada.jibx;

/**
 * A geometry contained in a COLLADA file.
 * Child of LibraryGeometries.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class Geometry {

    public Asset asset = null;

    public String id = null;
    public String name = null;
    public Mesh mesh = null;
    /**
     * Convex meshes are unsupported for now. It will
     * stay null after loading even if the file has
     * a convex_mesh. Please use Mesh instead.
     */
    public Object convexMesh = null;

}

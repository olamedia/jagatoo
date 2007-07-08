package org.jagatoo.spatial;

import org.jagatoo.spatial.polygons.Triangle;

/**
 * Provides writeable access to the triangles in an object.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface WriteableTriangleContainer extends TriangleContainer
{
    /**
     * This allows the spatial container to have access to all the triangles
     * in the object. This allows for the computation of bounding boxes which
     * completely enclose geometry. The triangles need to be returned in
     * world coordinates, not local coordinates.
     *
     * @param i
     * @param triangle
     */
    public boolean setTriangle( int i, Triangle triangle );
}

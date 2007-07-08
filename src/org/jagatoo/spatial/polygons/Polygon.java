package org.jagatoo.spatial.polygons;

import org.jagatoo.datatypes.Vertex3f;

/**
 * A Polygon is a shape in 3D-space.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class Polygon
{
    private int features;
    
    public void setFeatures( int features )
    {
        this.features = features;
    }
    
    public int getFeatures()
    {
        return( features );
    }
    
    public void addFeature(int feature)
    {
        this.features |= feature;
    }
    
    public boolean hasFeature( int feature )
    {
        return( ( this.features & feature ) > 0 );
    }
    
    public Polygon( int features )
    {
        this.features = features | Vertex3f.COORDINATES;
    }
}

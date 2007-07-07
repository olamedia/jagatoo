package org.jagatoo.spatial.polygons;

/**
 * A Polygon is a shape in 3D-space.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class Polygon
{
    public static final int COORDINATES            = 1;
    public static final int NORMALS                = 2;
    public static final int COLORS_3               = 4;
    public static final int TEXTURE_COORDINATES_2  = 8;
    
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
        this.features = features | COORDINATES;
    }
}

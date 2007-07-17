package org.jagatoo.datatypes;

import java.io.Serializable;

import org.jagatoo.datatypes.pools.TexCoord1fPool;

/**
 * A simple Texture-Coordinate implementation for 1 value.<br>
 * The order is (s).
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord1f extends TexCoordf implements Serializable
{
    private static final long serialVersionUID = -8577168782997168074L;
    
    private static final TexCoord1fPool POOL = new TexCoord1fPool( 32 );
    
    /**
     * Sets all values of this texCoord to the specified ones.
     * 
     * @param s the s element to use
     */
    public void set( float s )
    {
        setS( s );
    }
    
    /**
     * Sets the S (1st) texCoord component.
     */
    public void setS( float s )
    {
        this.values[ 0 ] = s;
    }
    
    /**
     * @return the S (1st) texCoord component.
     */
    public float getS()
    {
        return( values[ 0 ] );
    }
    
    /**
     * Adds v to this texCoord's S value.
     * 
     * @param v
     */
    public void addS( float v )
    {
        this.values[ 0 ] += v;
    }
    
    /**
     * Subtracts v from this texCoord's S value.
     * 
     * @param v
     */
    public void subS( float v )
    {
        this.values[ 0 ] -= v;
    }
    
    /**
     * Multiplies this texCoord's S value with v.
     * 
     * @param v
     */
    public void mulS( float v )
    {
        this.values[ 0 ] *= v;
    }
    
    /**
     * Multiplies this texCoord's values with vs.
     * 
     * @param vs
     */
    public void mul( float vs )
    {
        this.values[ 0 ] *= vs;
    }
    
    /**
     * Divides this texCoord's S value by v.
     * 
     * @param v
     */
    public void divS( float v )
    {
        this.values[ 0 ] /= v;
    }
    
    /**
     * Divides this texCoord's values by vs.
     * 
     * @param vs
     */
    public void div( float vs )
    {
        this.values[ 0 ] /= vs;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param s
     */
    public void add( float s )
    {
        this.values[ 0 ] += s;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param s
     */
    public void sub( float s )
    {
        this.values[ 0 ] -= s;
    }
    
    /**
     * Returns true if the Object t1 is of type Tuple3f and all of the
     * data members of t1 are equal to the corresponding data members in
     * this Tuple3f.
     * 
     * @param o  the Object with which the comparison is made
     * @return  true or false
     */ 
    @Override
    public boolean equals( Object o )
    {
        return( ( o != null ) && ( ( o instanceof TexCoord1f ) && equals( (TexCoord1f)o ) ) );
    }
    
    /**
     * Creates and returns a copy of this object.
     * 
     * @return a clone of this instance.
     * @exception OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     */
    @Override
    public TexCoord1f clone()
    {
        try
        {
            return( (TexCoord1f)super.clone() );
        }
        catch (CloneNotSupportedException ex)
        {
            throw( new InternalError() );
        }
    }
    
    
    /**
     * Creates a new TexCoord1f instance.
     * 
     * @param s the S element to use
     */
    public TexCoord1f( float s )
    {
        super( new float[] { s } );
    }
    
    /**
     * Creates a new TexCoord1f instance.
     * 
     * @param values the values array (must be at least size 1)
     */
    public TexCoord1f( float[] values )
    {
        this( values[ 0 ] );
    }
    
    /**
     * Creates a new TexCoord1f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord1f( TexCoordf texCoord )
    {
        super( newArray( texCoord.values, 1 ) );
    }
    
    /**
     * Creates a new TexCoord1f instance.
     * 
     * @param vec the TexCoord1f to copy the values from
     */
    public TexCoord1f()
    {
        this( 0f );
    }
    
    /**
     * Allocates an TexCoord1f instance from the pool.
     */
    public static TexCoord1f fromPool()
    {
        return( POOL.alloc() );
    }
    
    /**
     * Allocates an TexCoord1f instance from the pool.
     */
    public static TexCoord1f fromPool( float s )
    {
        return( POOL.alloc( s ) );
    }
    
    /**
     * Stores the given TexCoord1f instance in the pool.
     * 
     * @param o
     */
    public static void toPool( TexCoord1f o )
    {
        POOL.free( o );
    }
}

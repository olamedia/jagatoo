package org.jagatoo.datatypes;

import java.io.Serializable;

import org.jagatoo.datatypes.pools.TexCoord2fPool;

/**
 * A simple Texture-Coordinate implementation for 2 values.<br>
 * The order is (s, t).
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord2f extends TexCoordf implements Serializable
{
    private static final long serialVersionUID = 4961153246436722156L;
    
    private static final TexCoord2fPool POOL = new TexCoord2fPool( 32 );
    
    /**
     * Sets all values of this texCoord to the specified ones.
     * 
     * @param s the s element to use
     * @param t the t element to use
     */
    public void set( float s, float t )
    {
        setS( s );
        setT( t );
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
     * Sets the T (2nd) texCoord component.
     */
    public void setT( float t )
    {
        this.values[ 1 ] = t;
    }
    
    /**
     * @return the T (2nd) texCoord component.
     */
    public float getT()
    {
        return( values[ 1 ] );
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
     * Adds v to this texCoord's T value.
     * 
     * @param v
     */
    public void addT( float v )
    {
        this.values[ 1 ] += v;
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
     * Subtracts v from this texCoord's T value.
     * 
     * @param v
     */
    public void subT( float v )
    {
        this.values[ 1 ] -= v;
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
     * Multiplies this texCoord's T value with v.
     * 
     * @param v
     */
    public void mulT( float v )
    {
        this.values[ 1 ] *= v;
    }
    
    /**
     * Multiplies this texCoord's values with vs, vt.
     * 
     * @param vs
     * @param vt
     */
    public void mul( float vs, float vt )
    {
        this.values[ 0 ] *= vs;
        this.values[ 1 ] *= vt;
    }
    
    /**
     * Sets the value of this tuple to the scalar multiplication of itself.
     * 
     * @param factor the scalar value
     */
    public void mul( float factor )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ i ] *= factor;
        }
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
     * Divides this texCoord's T value by v.
     * 
     * @param v
     */
    public void divT( float v )
    {
        this.values[ 1 ] /= v;
    }
    
    /**
     * Divides this texCoord's values by vs, vt.
     * 
     * @param vs
     * @param vt
     */
    public void div( float vs, float vt )
    {
        this.values[ 0 ] /= vs;
        this.values[ 1 ] /= vt;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param s
     * @param t
     */
    public void add( float s, float t )
    {
        this.values[ 0 ] += s;
        this.values[ 1 ] += t;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param s
     * @param t
     */
    public void sub( float s, float t )
    {
        this.values[ 0 ] -= s;
        this.values[ 1 ] -= t;
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
        return( ( o != null ) && ( ( o instanceof TexCoord2f ) && equals( (TexCoord2f)o ) ) );
    }
    
    /**
     * Creates and returns a copy of this object.
     * 
     * @return a clone of this instance.
     * @exception OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     */
    @Override
    public TexCoord2f clone()
    {
        try
        {
            return( (TexCoord2f)super.clone() );
        }
        catch (CloneNotSupportedException ex)
        {
            throw( new InternalError() );
        }
    }
    
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     */
    public TexCoord2f( float s, float t )
    {
        super( new float[] { s, t } );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param values the values array (must be at least size 2)
     */
    public TexCoord2f( float[] values )
    {
        this( values[ 0 ], values[ 1 ] );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord2f( TexCoordf texCoord )
    {
        super( newArray( texCoord.values, 2 ) );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param vec the TexCoord2f to copy the values from
     */
    public TexCoord2f()
    {
        this( 0f, 0f );
    }
    
    /**
     * Allocates an TexCoord2f instance from the pool.
     */
    public static TexCoord2f fromPool()
    {
        return( POOL.alloc() );
    }
    
    /**
     * Allocates an TexCoord2f instance from the pool.
     */
    public static TexCoord2f fromPool( float s, float t )
    {
        return( POOL.alloc( s, t ) );
    }
    
    /**
     * Stores the given TexCoord2f instance in the pool.
     * 
     * @param o
     */
    public static void toPool( TexCoord2f o )
    {
        POOL.free( o );
    }
}

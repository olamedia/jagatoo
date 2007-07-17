package org.jagatoo.datatypes;

import java.io.Serializable;

import org.jagatoo.datatypes.pools.TexCoord4fPool;

/**
 * A simple Texture-Coordinate implementation for 4 values.<br>
 * The order is (s, t, p, q).
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord4f extends TexCoordf implements Serializable
{
    private static final long serialVersionUID = -8625802351660888699L;
    
    private static final TexCoord4fPool POOL = new TexCoord4fPool( 32 );
    
    /**
     * Sets all values of this texCoord to the specified ones.
     * 
     * @param s the s element to use
     * @param t the t element to use
     * @param p the p element to use
     * @param q the q element to use
     */
    public void set( float s, float t, float p, float q )
    {
        setS( s );
        setT( t );
        setP( p );
        setQ( q );
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
     * Sets the P (3rd) texCoord component.
     */
    public void setP( float p )
    {
        this.values[ 2 ] = p;
    }
    
    /**
     * @return the P (3rd) texCoord component.
     */
    public float getP()
    {
        return( values[ 2 ] );
    }
    
    /**
     * Sets the Q (4th) texCoord component.
     * 
     * @param alpha
     */
    public void setQ( float q )
    {
        this.values[ 3 ] = q;
    }
    
    /**
     * @return the Q (4th) texCoord component.
     */
    public float getQ()
    {
        return( values[ 3 ] );
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
     * Adds v to this texCoord's P value.
     * 
     * @param v
     */
    public void addP( float v )
    {
        this.values[ 2 ] += v;
    }
    
    /**
     * Adds v to this texCoord's Q value.
     * 
     * @param v
     */
    public void addQ( float v )
    {
        this.values[ 3 ] += v;
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
     * Subtracts v from this texCoord's P value.
     * 
     * @param v
     */
    public void subP( float v )
    {
        this.values[ 2 ] -= v;
    }
    
    /**
     * Subtracts v from this texCoord's Q value.
     * 
     * @param v
     */
    public void subQ( float v )
    {
        this.values[ 3 ] -= v;
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
     * Multiplies this texCoord's P value with v.
     * 
     * @param v
     */
    public void mulP( float v )
    {
        this.values[ 2 ] *= v;
    }
    
    /**
     * Multiplies this texCoord's Q value with v.
     * 
     * @param v
     */
    public void mulQ( float v )
    {
        this.values[ 3 ] *= v;
    }
    
    /**
     * Multiplies this texCoord's values with vr, vg, vb, va.
     * 
     * @param vs
     * @param vt
     * @param vp
     * @param vq
     */
    public void mul( float vs, float vt, float vp, float vq )
    {
        this.values[ 0 ] *= vs;
        this.values[ 1 ] *= vt;
        this.values[ 2 ] *= vp;
        this.values[ 3 ] *= vq;
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
     * Divides this texCoord's P value by v.
     * 
     * @param v
     */
    public void divP( float v )
    {
        this.values[ 2 ] /= v;
    }
    
    /**
     * Divides this texCoord's Q value by v.
     * 
     * @param v
     */
    public void divQ( float v )
    {
        this.values[ 3 ] /= v;
    }
    
    /**
     * Divides this texCoord's values by vs, vt, vp, vq.
     * 
     * @param vs
     * @param vt
     * @param vp
     * @param vq
     */
    public void div( float vs, float vt, float vp, float vq )
    {
        this.values[ 0 ] /= vs;
        this.values[ 1 ] /= vt;
        this.values[ 2 ] /= vp;
        this.values[ 3 ] /= vq;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param s
     * @param t
     * @param p
     * @param q
     */
    public void add( float s, float t, float p, float q )
    {
        this.values[ 0 ] += s;
        this.values[ 1 ] += t;
        this.values[ 2 ] += p;
        this.values[ 3 ] += q;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param s
     * @param t
     * @param p
     * @param q
     */
    public void sub( float s, float t, float p, float q )
    {
        this.values[ 0 ] -= s;
        this.values[ 1 ] -= t;
        this.values[ 2 ] -= p;
        this.values[ 3 ] -= q;
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
        return( ( o != null ) && ( ( o instanceof TexCoord4f ) && equals( (TexCoord4f)o ) ) );
    }
    
    /**
     * Creates and returns a copy of this object.
     * 
     * @return a clone of this instance.
     * @exception OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     */
    @Override
    public TexCoord4f clone()
    {
        try
        {
            return( (TexCoord4f)super.clone() );
        }
        catch (CloneNotSupportedException ex)
        {
            throw( new InternalError() );
        }
    }
    
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     * @param p the P element to use
     * @param q the Q channel to use
     */
    public TexCoord4f( float s, float t, float p, float q )
    {
        super( new float[] { s, t, p, q } );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param values the values array (must be at least size 4)
     */
    public TexCoord4f( float[] values )
    {
        this( values[ 0 ], values[ 1 ], values[ 2 ], values[ 3 ] );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord4f( TexCoordf texCoord )
    {
        super( newArray( texCoord.values, 4 ) );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param vec the Vector4f to copy the values from
     */
    public TexCoord4f()
    {
        this( 0f, 0f, 0f, 0f );
    }
    
    /**
     * Allocates an TexCoord4f instance from the pool.
     */
    public static TexCoord4f fromPool()
    {
        return( POOL.alloc() );
    }
    
    /**
     * Allocates an TexCoord4f instance from the pool.
     */
    public static TexCoord4f fromPool( float s, float t, float p, float q )
    {
        return( POOL.alloc( s, t, p, q ) );
    }
    
    /**
     * Stores the given TexCoord4f instance in the pool.
     * 
     * @param o
     */
    public static void toPool( TexCoord4f o )
    {
        POOL.free( o );
    }
}

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
    public final void set( float s, float t, float p, float q )
    {
        setS( s );
        setT( t );
        setP( p );
        setQ( q );
    }
    
    /**
     * Sets the S (1st) texCoord component.
     */
    public final void setS( float s )
    {
        this.values[ roTrick + 0 ] = s;
        
        this.isDirty = true;
    }
    
    /**
     * @return the S (1st) texCoord component.
     */
    public final float getS()
    {
        return( values[ 0 ] );
    }
    
    /**
     * Sets the T (2nd) texCoord component.
     */
    public final void setT( float t )
    {
        this.values[ roTrick + 1 ] = t;
        
        this.isDirty = true;
    }
    
    /**
     * @return the T (2nd) texCoord component.
     */
    public final float getT()
    {
        return( values[ 1 ] );
    }
    
    /**
     * Sets the P (3rd) texCoord component.
     */
    public final void setP( float p )
    {
        this.values[ roTrick + 2 ] = p;
        
        this.isDirty = true;
    }
    
    /**
     * @return the P (3rd) texCoord component.
     */
    public final float getP()
    {
        return( values[ 2 ] );
    }
    
    /**
     * Sets the Q (4th) texCoord component.
     * 
     * @param alpha
     */
    public final void setQ( float q )
    {
        this.values[ roTrick + 3 ] = q;
        
        this.isDirty = true;
    }
    
    /**
     * @return the Q (4th) texCoord component.
     */
    public final float getQ()
    {
        return( values[ 3 ] );
    }
    
    /**
     * Adds v to this texCoord's S value.
     * 
     * @param v
     */
    public final void addS( float v )
    {
        this.values[ roTrick + 0 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this texCoord's T value.
     * 
     * @param v
     */
    public final void addT( float v )
    {
        this.values[ roTrick + 1 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this texCoord's P value.
     * 
     * @param v
     */
    public final void addP( float v )
    {
        this.values[ roTrick + 2 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this texCoord's Q value.
     * 
     * @param v
     */
    public final void addQ( float v )
    {
        this.values[ roTrick + 3 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this texCoord's S value.
     * 
     * @param v
     */
    public final void subS( float v )
    {
        this.values[ roTrick + 0 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this texCoord's T value.
     * 
     * @param v
     */
    public final void subT( float v )
    {
        this.values[ roTrick + 1 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this texCoord's P value.
     * 
     * @param v
     */
    public final void subP( float v )
    {
        this.values[ roTrick + 2 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this texCoord's Q value.
     * 
     * @param v
     */
    public final void subQ( float v )
    {
        this.values[ roTrick + 3 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this texCoord's S value with v.
     * 
     * @param v
     */
    public final void mulS( float v )
    {
        this.values[ roTrick + 0 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this texCoord's T value with v.
     * 
     * @param v
     */
    public final void mulT( float v )
    {
        this.values[ roTrick + 1 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this texCoord's P value with v.
     * 
     * @param v
     */
    public final void mulP( float v )
    {
        this.values[ roTrick + 2 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this texCoord's Q value with v.
     * 
     * @param v
     */
    public final void mulQ( float v )
    {
        this.values[ roTrick + 3 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this texCoord's values with vr, vg, vb, va.
     * 
     * @param vs
     * @param vt
     * @param vp
     * @param vq
     */
    public final void mul( float vs, float vt, float vp, float vq )
    {
        this.values[ roTrick + 0 ] *= vs;
        this.values[ roTrick + 1 ] *= vt;
        this.values[ roTrick + 2 ] *= vp;
        this.values[ roTrick + 3 ] *= vq;
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this tuple to the scalar multiplication of itself.
     * 
     * @param factor the scalar value
     */
    public final void mul( float factor )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] *= factor;
        }
        
        this.isDirty = true;
    }
    
    /**
     * Divides this texCoord's S value by v.
     * 
     * @param v
     */
    public final void divS( float v )
    {
        this.values[ roTrick + 0 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this texCoord's T value by v.
     * 
     * @param v
     */
    public final void divT( float v )
    {
        this.values[ roTrick + 1 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this texCoord's P value by v.
     * 
     * @param v
     */
    public final void divP( float v )
    {
        this.values[ roTrick + 2 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this texCoord's Q value by v.
     * 
     * @param v
     */
    public final void divQ( float v )
    {
        this.values[ roTrick + 3 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this texCoord's values by vs, vt, vp, vq.
     * 
     * @param vs
     * @param vt
     * @param vp
     * @param vq
     */
    public final void div( float vs, float vt, float vp, float vq )
    {
        this.values[ roTrick + 0 ] /= vs;
        this.values[ roTrick + 1 ] /= vt;
        this.values[ roTrick + 2 ] /= vp;
        this.values[ roTrick + 3 ] /= vq;
        
        this.isDirty = true;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param s
     * @param t
     * @param p
     * @param q
     */
    public final void add( float s, float t, float p, float q )
    {
        this.values[ roTrick + 0 ] += s;
        this.values[ roTrick + 1 ] += t;
        this.values[ roTrick + 2 ] += p;
        this.values[ roTrick + 3 ] += q;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param s
     * @param t
     * @param p
     * @param q
     */
    public final void sub( float s, float t, float p, float q )
    {
        this.values[ roTrick + 0 ] -= s;
        this.values[ roTrick + 1 ] -= t;
        this.values[ roTrick + 2 ] -= p;
        this.values[ roTrick + 3 ] -= q;
        
        this.isDirty = true;
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
     * @param readOnly
     * @param s the S element to use
     * @param t the T element to use
     * @param p the P element to use
     * @param q the Q channel to use
     */
    public TexCoord4f( boolean readOnly, float s, float t, float p, float q )
    {
        super( readOnly, new float[] { s, t, p, q } );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param readOnly
     * @param values the values array (must be at least size 4)
     */
    public TexCoord4f( boolean readOnly, float[] values )
    {
        this( readOnly, values[ 0 ], values[ 1 ], values[ 2 ], values[ 3 ] );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param readOnly
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord4f( boolean readOnly, TexCoordf texCoord )
    {
        super( readOnly, newArray( texCoord.values, 4 ) );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param readOnly
     * @param vec the Vector4f to copy the values from
     */
    public TexCoord4f( boolean readOnly )
    {
        this( readOnly, 0f, 0f, 0f, 0f );
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
        this( false, s, t, p, q );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param values the values array (must be at least size 4)
     */
    public TexCoord4f( float[] values )
    {
        this( false, values );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord4f( TexCoordf texCoord )
    {
        this( false, texCoord );
    }
    
    /**
     * Creates a new TexCoord4f instance.
     * 
     * @param vec the Vector4f to copy the values from
     */
    public TexCoord4f()
    {
        this( false );
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

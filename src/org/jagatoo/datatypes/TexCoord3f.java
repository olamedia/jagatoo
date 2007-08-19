package org.jagatoo.datatypes;

import java.io.Serializable;

import org.jagatoo.datatypes.pools.TexCoord3fPool;

/**
 * A simple Texture-Coordinate implementation for 3 values.<br>
 * The order is (s, t, p).
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord3f extends TexCoordf implements Serializable
{
    private static final long serialVersionUID = -7616743403485094339L;
    
    private static final TexCoord3fPool POOL = new TexCoord3fPool( 32 );
    
    /**
     * Sets all values of this texCoord to the specified ones.
     * 
     * @param s the s element to use
     * @param t the t element to use
     * @param p the p element to use
     */
    public final void set( float s, float t, float p )
    {
        setS( s );
        setT( t );
        setP( p );
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
     * Multiplies this texCoord's values with vs, vt, vp.
     * 
     * @param vs
     * @param vt
     * @param vp
     */
    public final void mul( float vs, float vt, float vp )
    {
        this.values[ roTrick + 0 ] *= vs;
        this.values[ roTrick + 1 ] *= vt;
        this.values[ roTrick + 2 ] *= vp;
        
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
     * Divides this texCoord's values by vs, vt, vp.
     * 
     * @param vs
     * @param vt
     * @param vp
     */
    public final void div( float vs, float vt, float vp )
    {
        this.values[ roTrick + 0 ] /= vs;
        this.values[ roTrick + 1 ] /= vt;
        this.values[ roTrick + 2 ] /= vp;
        
        this.isDirty = true;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param s
     * @param t
     * @param p
     */
    public final void add( float s, float t, float p )
    {
        this.values[ roTrick + 0 ] += s;
        this.values[ roTrick + 1 ] += t;
        this.values[ roTrick + 2 ] += p;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param s
     * @param t
     * @param p
     */
    public final void sub( float s, float t, float p )
    {
        this.values[ roTrick + 0 ] -= s;
        this.values[ roTrick + 1 ] -= t;
        this.values[ roTrick + 2 ] -= p;
        
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
        return( ( o != null ) && ( ( o instanceof TexCoord3f ) && equals( (TexCoord3f)o ) ) );
    }
    
    /**
     * Creates and returns a copy of this object.
     * 
     * @return a clone of this instance.
     * @exception OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     */
    @Override
    public TexCoord3f clone()
    {
        try
        {
            return( (TexCoord3f)super.clone() );
        }
        catch (CloneNotSupportedException ex)
        {
            throw( new InternalError() );
        }
    }
    
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param readOnly
     * @param s the S element to use
     * @param t the T element to use
     * @param p the P element to use
     */
    public TexCoord3f( boolean readOnly, float s, float t, float p )
    {
        super( readOnly, new float[] { s, t, p } );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param readOnly
     * @param values the values array (must be at least size 3)
     */
    public TexCoord3f( boolean readOnly, float[] values )
    {
        this( readOnly, values[ 0 ], values[ 1 ], values[ 2 ] );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param readOnly
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord3f( boolean readOnly, TexCoordf texCoord )
    {
        super( readOnly, newArray( texCoord.values, 3 ) );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param readOnly
     * @param vec the TexCoord3f to copy the values from
     */
    public TexCoord3f( boolean readOnly )
    {
        this( readOnly, 0f, 0f, 0f );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     * @param p the P element to use
     */
    public TexCoord3f( float s, float t, float p )
    {
        this( false, s, t, p );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param values the values array (must be at least size 3)
     */
    public TexCoord3f( float[] values )
    {
        this( false, values );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord3f( TexCoordf texCoord )
    {
        this( false, texCoord );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param vec the TexCoord3f to copy the values from
     */
    public TexCoord3f()
    {
        this( false );
    }
    
    /**
     * Allocates an TexCoord3f instance from the pool.
     */
    public static TexCoord3f fromPool()
    {
        return( POOL.alloc() );
    }
    
    /**
     * Allocates an TexCoord3f instance from the pool.
     */
    public static TexCoord3f fromPool( float s, float t, float p )
    {
        return( POOL.alloc( s, t, p ) );
    }
    
    /**
     * Stores the given TexCoord3f instance in the pool.
     * 
     * @param o
     */
    public static void toPool( TexCoord3f o )
    {
        POOL.free( o );
    }
}

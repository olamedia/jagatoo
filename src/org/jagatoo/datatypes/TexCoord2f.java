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
    public final void set( float s, float t )
    {
        setS( s );
        setT( t );
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
     * Multiplies this texCoord's values with vs, vt.
     * 
     * @param vs
     * @param vt
     */
    public final void mul( float vs, float vt )
    {
        this.values[ roTrick + 0 ] *= vs;
        this.values[ roTrick + 1 ] *= vt;
        
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
     * Divides this texCoord's values by vs, vt.
     * 
     * @param vs
     * @param vt
     */
    public final void div( float vs, float vt )
    {
        this.values[ roTrick + 0 ] /= vs;
        this.values[ roTrick + 1 ] /= vt;
        
        this.isDirty = true;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param s
     * @param t
     */
    public final void add( float s, float t )
    {
        this.values[ roTrick + 0 ] += s;
        this.values[ roTrick + 1 ] += t;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param s
     * @param t
     */
    public final void sub( float s, float t )
    {
        this.values[ roTrick + 0 ] -= s;
        this.values[ roTrick + 1 ] -= t;
        
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
     * @param readOnly
     * @param s the S element to use
     * @param t the T element to use
     */
    public TexCoord2f( boolean readOnly, float s, float t )
    {
        super( readOnly, new float[] { s, t } );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param readOnly
     * @param values the values array (must be at least size 2)
     */
    public TexCoord2f( boolean readOnly, float[] values )
    {
        this( readOnly, values[ 0 ], values[ 1 ] );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param readOnly
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord2f( boolean readOnly, TexCoordf texCoord )
    {
        super( readOnly, newArray( texCoord.values, 2 ) );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param readOnly
     * @param vec the TexCoord2f to copy the values from
     */
    public TexCoord2f( boolean readOnly )
    {
        this( readOnly, 0f, 0f );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     */
    public TexCoord2f( float s, float t )
    {
        this( false, s, t );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param values the values array (must be at least size 2)
     */
    public TexCoord2f( float[] values )
    {
        this( false, values );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public TexCoord2f( TexCoordf texCoord )
    {
        this( false, texCoord );
    }
    
    /**
     * Creates a new TexCoord2f instance.
     * 
     * @param vec the TexCoord2f to copy the values from
     */
    public TexCoord2f()
    {
        this( false );
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

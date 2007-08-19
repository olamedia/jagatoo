package org.jagatoo.datatypes;

import java.util.Arrays;

/**
 * A simple abstract Texture-Coordinate.<br>
 * The order is (s, t, p, q).
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class TexCoordf
{
    protected final int N;
    protected final float[] values;
    
    protected final int roTrick;
    protected boolean isDirty = false;
    
    
    /**
     * @return Is this tuple a read-only one?
     */
    public final boolean isReadOnly()
    {
        return( roTrick != 0 );
    }
    
    /**
     * Marks this tuple non-dirty.
     * Any value-manipulation will mark it dirty again.
     * 
     * @return the old value
     */
    public final boolean setClean()
    {
        final boolean oldValue = this.isDirty;
        
        this.isDirty = false;
        
        return( oldValue );
    }
    
    /**
     * @return This tuple's dirty-flag
     */
    public final boolean isDirty()
    {
        return( isDirty );
    }
    
    /**
     * @return this Vector's size().
     */
    public final int getSize()
    {
        return( N );
    }
    
    /**
     * Sets all values of this TexCoord to the specified ones.
     * 
     * @param values the values array (must be at least size 1)
     */
    public final void set( float[] values )
    {
        System.arraycopy( values, 0, this.values, roTrick + 0, N );
        
        this.isDirty = true;
    }
    
    /**
     * Sets all values of this Tuple to the specified ones.
     * 
     * @param texCoord the texCoord to be copied
     */
    public final void set( TexCoordf texCoord )
    {
        System.arraycopy( texCoord.values, 0, this.values, roTrick + 0, N );
        
        this.isDirty = true;
    }
    
    /**
     * Writes all values of this Tuple to the specified buffer.
     * 
     * @param buffer the buffer array to write the values to
     */
    public final void get( float[] buffer )
    {
        System.arraycopy( this.values, 0, buffer, 0, N );
        
        this.isDirty = true;
    }
    
    /**
     * Writes all values of this vector to the specified buffer vector.
     * 
     * @param buffer the buffer vector to write the values to
     */
    public final void get( TexCoordf buffer )
    {
        System.arraycopy( this.values, 0, buffer.values, 0, Math.min( this.N, buffer.N ) );
        
        this.isDirty = true;
    }
    
    /**
     * Sets all components to zero.
     *
     */
    public final void setZero()
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = 0f;
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this texCoord to the vector sum of colors texCoord1 and texCoord2.
     * 
     * @param texCoord1 the first texCoord
     * @param texCoord2 the second texCoord
     */
    public final void add( TexCoordf texCoord1, TexCoordf texCoord2 )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = texCoord1.values[ i ] + texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this tuple to the vector sum of itself and tuple t1.
     * 
     * @param texCoord2 the other tuple
     */
    public final void add( TexCoordf texCoord2 )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] += texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this texCoord to the vector difference of texCoord texCoord1 and texCoord2
     * (this = texCoord1 - texCoord2).
     * 
     * @param texCoord1 the first texCoord
     * @param texCoord2 the second texCoord
     */
    public final void sub( TexCoordf texCoord1, TexCoordf texCoord2 )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = texCoord1.values[ i ] - texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this texCoord to the vector difference of itself and texCoord2
     * (this = this - texCoord2).
     * 
     * @param texCoord2 the other texCoord
     * 
     */
    public final void sub( TexCoordf texCoord2 )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] -= texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this tuple to the scalar multiplication of tuple t1 and
     * then adds tuple t2 (this = s*t1 + t2).
     * 
     * @param factor the scalar value
     * @param texCoord1 the tuple to be multipled
     * @param texCoord2 the tuple to be added
     */
    public final void scaleAdd( float factor, TexCoordf texCoord1, TexCoordf texCoord2 )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = factor * texCoord1.values[ i ] + texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this tuple to the scalar multiplication of itself and
     * then adds tuple t1 (this = s*this + t1).
     * 
     * @param factor the scalar value
     * @param texCoord2 the tuple to be added
     */
    public final void scaleAdd( float factor, TexCoordf texCoord2 )
    {
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = factor * this.values[ i ] + texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Clamps the minimum value of this tuple to the min parameter.
     * 
     * @param min the lowest value in this tuple after clamping
     */
    public final void clampMin( float min )
    {
        for ( int i = 0; i < N; i++ )
        {
            if (this.values[ i ] < min )
                this.values[ roTrick + i ] = min;
        }
        
        this.isDirty = true;
    }
    
    /**
     * Clamps the maximum value of this tuple to the max parameter.
     * 
     * @param max the highest value in the tuple after clamping
     */
    public final void clampMax( float max )
    {
        for ( int i = 0; i < N; i++ )
        {
            if (this.values[ i ] > max )
                this.values[ roTrick + i ] = max;
        }
        
        this.isDirty = true;
    }
    
    /**
     * Clamps this tuple to the range [min, max].
     * 
     * @param min the lowest value in this tuple after clamping
     * @param max the highest value in this tuple after clamping
     */
    public final void clamp( float min, float max )
    {
        clampMin( min );
        clampMax( max );
    }
    
    /**
     * Clamps the tuple parameter to the range [min, max] and places the values
     * into this tuple.
     * 
     * @param min the lowest value in the tuple after clamping
     * @param max the highest value in the tuple after clamping
     * @param vec the source tuple, which will not be modified
     */
    public final void clamp( float min, float max, TexCoordf vec )
    {
        set( vec );
        
        clamp( min, max );
    }
    
    /**
     * Clamps the minimum value of the tuple parameter to the min parameter and
     * places the values into this tuple.
     * 
     * @param min the lowest value in the tuple after clamping
     * @parm that the source tuple, which will not be modified
     */
    public final void clampMin( float min, TexCoordf vec )
    {
        set( vec );
        clampMin( min );
    }
    
    /**
     * Clamps the maximum value of the tuple parameter to the max parameter and
     * places the values into this tuple.
     * 
     * @param max the highest value in the tuple after clamping
     * @param vec the source tuple, which will not be modified
     */
    public final void clampMax( float max, TexCoordf vec )
    {
        set( vec );
        clampMax( max );
    }
    
    /**
     * Linearly interpolates between this tuple and tuple t2 and places the
     * result into this tuple: this = (1 - alpha) * this + alpha * t1.
     * 
     * @param t2 the first tuple
     * @param alpha the alpha interpolation parameter
     */
    public final void interpolate( TexCoordf texCoord2, float alpha )
    {
        final float beta = 1.0f - alpha;
        
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = beta * this.values[ i ] + alpha * texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Linearly interpolates between tuples t1 and t2 and places the result into
     * this tuple: this = (1 - alpha) * t1 + alpha * t2.
     * 
     * @param texCoord1 the first tuple
     * @param texCoord2 the second tuple
     * @param alpha the interpolation parameter
     */
    public final void interpolate( TexCoordf texCoord1, TexCoordf texCoord2, float alpha )
    {
        final float beta = 1.0f - alpha;
        
        for ( int i = 0; i < N; i++ )
        {
            this.values[ roTrick + i ] = beta * texCoord1.values[ i ] + alpha * texCoord2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    private static int floatToIntBits( final float f )
    {
        // Check for +0 or -0
        return( (f == 0.0f) ? 0 : Float.floatToIntBits( f ) );
    }
    
    /**
     * Returns a hash number based on the data values in this object. Two
     * different Tuple3f objects with identical data values (ie, returns true
     * for equals(Tuple3f) ) will return the same hash number. Two vectors with
     * different data members may return the same hash value, although this is
     * not likely.
     */
    @Override
    public int hashCode()
    {
        int bits = 0;
        
        for ( int i = 0; i < N; i++ )
            bits ^= floatToIntBits( values[ i ] );
        
        return( bits );
    }
    
    /**
     * Returns true if all of the data members of Tuple3f t1 are equal to the
     * corresponding data members in this
     * 
     * @param texCoord2 the texCoord with which the comparison is made.
     */
    public boolean equals( TexCoordf texCoord2 )
    {
        if ( this.N != texCoord2.N )
            return( false );
        
        for ( int i = 0; i < N; i++ )
        {
            if ( texCoord2.values[ i ] != this.values[ i ] )
                return( false );
        }
        
        return( true );
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
        return( ( o != null ) && ( ( o instanceof TexCoordf ) && equals( (TexCoordf)o ) ) );
    }
    
    /**
     * Returns true if the L-infinite distance between this tuple and tuple t1
     * is less than or equal to the epsilon parameter, otherwise returns false.
     * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
     * 
     * @param texCoord2 the texCoord to be compared to this texCoord
     * @param epsilon the threshold value
     */
    public boolean epsilonEquals( TexCoordf texCoord2, float epsilon )
    {
        if ( this.N != texCoord2.N )
            return( false );
        
        for ( int i = 0; i < N; i++ )
        {
            if ( Math.abs( texCoord2.values[ i ] - this.values[ i ] ) > epsilon )
                return( false );
        }
        
        return( true );
    }
    
    /**
     * Returns a string that contains the values of this TexCoordf.
     * The form is ( S = s, T = t, blue = p, Q = q ).
     * 
     * @return the String representation
     */
    @Override
    public String toString()
    {
        String str = "TexCoord ( ";
        if ( N >= 1 )
            str += "S = " + values[ 0 ];
        if ( N >= 2 )
            str += ", T = " + values[ 1 ];
        if ( N >= 3 )
            str += ", P = " + values[ 2 ];
        if ( N >= 4 )
            str += ", Q = " + values[ 3 ];
        
        return( str + " )" );
    }
    
    
    protected static final float[] newArray( final float[] template, final int length )
    {
        final float[] result = new float[ length ];
        
        System.arraycopy( template, 0, result, 0, Math.min( template.length, length ) );
        
        if ( template.length < length )
        {
            Arrays.fill( result, template.length, length, 0.0f );
        }
        
        return( result );
    }
    
    /**
     * Creates a new TexCoord1f instance.
     * 
     * @param values the values array (must be at least size 1)
     */
    public TexCoordf( boolean readOnly, float[] values )
    {
        super();
        
        this.values = values;
        this.N = values.length;
        
        this.roTrick = readOnly ? -Integer.MAX_VALUE + values.length : 0;
    }
    
    /**
     * Creates a new TexCoord1f instance.
     * 
     * @param values the values array (must be at least size 1)
     */
    public TexCoordf( float[] values )
    {
        this( false, values );
    }
}

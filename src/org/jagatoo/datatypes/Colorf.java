package org.jagatoo.datatypes;

import java.io.Serializable;

import org.jagatoo.datatypes.pools.ColorPool;
import org.jagatoo.datatypes.readonly.ROColorf;

/**
 * A simple float-based color implementation with or without alpha channel.
 * 
 * Inspired by Kenji Hiranabe's Color3f/Color4f implementation
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Colorf implements Serializable
{
    private static final long serialVersionUID = -818575512943622856L;
    
    private static final ColorPool POOL = new ColorPool( 32 );
    
    protected static final int N = 4;
    protected final float[] values;
    protected boolean hasAlpha;
    
    
    /**
     * Creates a gray of the given intensity.<br>
     * This is euqal to new Color3f(intensity, intensity, intensity);
     * 
     * @param intensity the gray's intensity to be applied to all three components.
     * 
     * @return the new gray color
     */
    public static Colorf createGray( float intensity )
    {
        return( new Colorf( intensity, intensity, intensity ) );
    }
    
    /**
     * The color white. In the default sRGB space.
     */
    public static final ROColorf WHITE = new ROColorf( java.awt.Color.WHITE );
    
    /**
     * The color light gray. In the default sRGB space.
     */
    public static final ROColorf LIGHT_GRAY = new ROColorf( java.awt.Color.GRAY );
    
    /**
     * A 10% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY10 = new ROColorf( 0.9f );
    
    /**
     * A 20% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY20 = new ROColorf( 0.8f );
    
    /**
     * A 30% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY30 = new ROColorf( 0.7f );
    
    /**
     * A 40% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY40 = new ROColorf( 0.6f );
    
    /**
     * A 50% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY50 = new ROColorf( 0.5f );
    
    /**
     * A 60% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY60 = new ROColorf( 0.4f );
    
    /**
     * A 70% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY70 = new ROColorf( 0.3f );
    
    /**
     * A 80% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY80 = new ROColorf( 0.2f );
    
    /**
     * A 90% gray. In the default sRGB space.
     */
    public static final ROColorf GRAY90 = new ROColorf( 0.1f );
    
    /**
     * The color gray. In the default sRGB space.
     */
    public static final ROColorf GRAY = new ROColorf( java.awt.Color.GRAY );
    
    /**
     * The color dark gray. In the default sRGB space.
     */
    public static final ROColorf DARK_GRAY = new ROColorf( java.awt.Color.DARK_GRAY );
    
    /**
     * The color black. In the default sRGB space.
     */
    public static final ROColorf BLACK = new ROColorf( java.awt.Color.BLACK );
    
    /**
     * The color red. In the default sRGB space.
     */
    public static final ROColorf RED = new ROColorf( java.awt.Color.RED );
    
    /**
     * The color pink. In the default sRGB space.
     */
    public static final ROColorf PINK = new ROColorf( java.awt.Color.PINK );
    
    /**
     * The color orange. In the default sRGB space.
     */
    public static final ROColorf ORANGE = new ROColorf( java.awt.Color.ORANGE );
    
    /**
     * The color yellow. In the default sRGB space.
     */
    public static final ROColorf YELLOW = new ROColorf( java.awt.Color.YELLOW );
    
    /**
     * The color green. In the default sRGB space.
     */
    public static final ROColorf GREEN = new ROColorf( java.awt.Color.GREEN );
    
    /**
     * The color magenta. In the default sRGB space.
     */
    public static final ROColorf MAGENTA = new ROColorf( java.awt.Color.MAGENTA );
    
    /**
     * The color cyan. In the default sRGB space.
     */
    public static final ROColorf CYAN = new ROColorf( java.awt.Color.CYAN );
    
    /**
     * The color blue. In the default sRGB space.
     */
    public static final ROColorf BLUE = new ROColorf( java.awt.Color.BLUE );
    
    /**
     * @return this Vector's size().
     */
    public int getSize()
    {
        return( N );
    }
    
    /**
     * @return if this Colorf has an alpha channel.
     */
    public boolean hasAlpha()
    {
        return( hasAlpha );
    }
    
    /**
     * Sets color from awt.Color.
     * 
     * @param color awt color
     */
    public void set( java.awt.Color color )
    {
        setRed( ((float)color.getRed()) / 255.0f );
        setGreen( ((float)color.getGreen()) / 255.0f );
        setBlue( ((float)color.getBlue()) / 255.0f );
        
        hasAlpha = ( color.getAlpha() > 0 );
        if ( hasAlpha )
            setAlpha( ((float)color.getAlpha()) / 255.0f );
    }
    
    /**
     * Sets all values of this Tuple to the specified ones.
     * 
     * @param values the values array (must be at least size 4)
     */
    public void set( float[] values )
    {
        if ( values.length > 3 )
        {
            System.arraycopy( values, 0, this.values, 0, N );
            hasAlpha = ( values[ 3 ] > 0.0f );
        }
        else
        {
            System.arraycopy( values, 0, this.values, 0, 3 );
            hasAlpha = false;
        }
    }
    
    /**
     * Sets all three values of this Tuple to the specified ones.
     * 
     * @param color the tuple to be copied
     */
    public void set( Colorf color )
    {
        System.arraycopy( color.values, 0, this.values, 0, N );
        hasAlpha = color.hasAlpha;
    }
    
    /**
     * Sets all values of this color to the specified ones.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     * @param a the alpha element to use
     */
    public void set( float r, float g, float b, float a )
    {
        setRed( r );
        setGreen( g );
        setBlue( b );
        setAlpha( a );
        
        this.hasAlpha = ( a >= 0.0f );
    }
    
    /**
     * Sets all values of this color to the specified ones.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     */
    public void set( float r, float g, float b )
    {
        setRed( r );
        setGreen( g );
        setBlue( b );
        
        this.hasAlpha = false;
    }
    
    /**
     * Gets java.awt.Color.
     * 
     * @return AWT color
     */
    public java.awt.Color get()
    {
        if ( hasAlpha() )
            return( new java.awt.Color( getRed(), getGreen(), getBlue(), getAlpha() ) );
        else
            return( new java.awt.Color( getRed(), getGreen(), getBlue() ) );
    }
    
    /**
     * Writes all values of this Tuple to the specified buffer.
     * 
     * @param buffer the buffer array to write the values to
     */
    public void get( float[] buffer )
    {
        final int n = hasAlpha() ? 4 : 3;
        System.arraycopy( this.values, 0, buffer, 0, n );
    }
    
    /**
     * Writes all values of this vector to the specified buffer vector.
     * 
     * @param buffer the buffer vector to write the values to
     */
    public void get( Colorf buffer )
    {
        System.arraycopy( this.values, 0, buffer.values, 0, N );
        buffer.hasAlpha = this.hasAlpha;
    }
    
    /**
     * Sets the Red color component.
     */
    public void setRed( float red )
    {
        this.values[ 0 ] = red;
    }
    
    /**
     * @return the Red color component.
     */
    public float getRed()
    {
        return( values[ 0 ] );
    }
    
    /**
     * Sets the Green color component.
     */
    public void setGreen( float green )
    {
        this.values[ 1 ] = green;
    }
    
    /**
     * @return the Green color component.
     */
    public float getGreen()
    {
        return( values[ 1 ] );
    }
    
    /**
     * Sets the Blue color component.
     */
    public void setBlue( float blue )
    {
        this.values[ 2 ] = blue;
    }
    
    /**
     * @return the Blue color component.
     */
    public float getBlue()
    {
        return( values[ 2 ] );
    }
    
    /**
     * Sets the value of the alpha-element of this color.
     * 
     * @param alpha
     */
    public void setAlpha( float alpha )
    {
        this.values[ 3 ] = alpha;
        
        this.hasAlpha = ( alpha >= 0.0f );
    }
    
    /**
     * @return the value of the alpha-element of this color.
     * 
     * @see #getTransparency()
     */
    public float getAlpha()
    {
        if ( hasAlpha() )
            return( values[ 3 ] );
        else
            return( 0f );
    }
    
    /**
     * Sets all components to zero.
     *
     */
    public void setZero()
    {
        for ( int i = 0; i < 3; i++ )
        {
            this.values[ i ] = 0f;
        }
        
        if ( hasAlpha() )
            this.values[ 3 ] = 0.0f;
    }
    
    /**
     * Adds v to this color's red value.
     * 
     * @param v
     */
    public void addRed( float v )
    {
        this.values[ 0 ] += v;
    }
    
    /**
     * Adds v to this color's green value.
     * 
     * @param v
     */
    public void addGreen( float v )
    {
        this.values[ 1 ] += v;
    }
    
    /**
     * Adds v to this color's blue value.
     * 
     * @param v
     */
    public void addBlue( float v )
    {
        this.values[ 2 ] += v;
    }
    
    /**
     * Adds v to this color's alpha value.
     * 
     * @param v
     */
    public void addAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 3 ] += v;
    }
    
    /**
     * Subtracts v from this color's red value.
     * 
     * @param v
     */
    public void subRed( float v )
    {
        this.values[ 0 ] -= v;
    }
    
    /**
     * Subtracts v from this color's green value.
     * 
     * @param v
     */
    public void subGreen( float v )
    {
        this.values[ 1 ] -= v;
    }
    
    /**
     * Subtracts v from this color's blue value.
     * 
     * @param v
     */
    public void subBlue( float v )
    {
        this.values[ 2 ] -= v;
    }
    
    /**
     * Subtracts v from this color's alpha value.
     * 
     * @param v
     */
    public void subAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 3 ] -= v;
    }
    
    /**
     * Multiplies this color's red value with v.
     * 
     * @param v
     */
    public void mulRed( float v )
    {
        this.values[ 0 ] *= v;
    }
    
    /**
     * Multiplies this color's green value with v.
     * 
     * @param v
     */
    public void mulGreen( float v )
    {
        this.values[ 1 ] *= v;
    }
    
    /**
     * Multiplies this color's blue value with v.
     * 
     * @param v
     */
    public void mulBlue( float v )
    {
        this.values[ 2 ] *= v;
    }
    
    /**
     * Multiplies this color's alpha value with v.
     * 
     * @param v
     */
    public void mulAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 3 ] *= v;
    }
    
    /**
     * Multiplies this color's values with vr, vg, vb, va.
     * 
     * @param vr
     * @param vg
     * @param vb
     * @param va
     */
    public void mul( float vr, float vg, float vb, float va )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 0 ] *= vr;
        this.values[ 1 ] *= vg;
        this.values[ 2 ] *= vb;
        this.values[ 3 ] *= va;
    }
    
    /**
     * Multiplies this color's values with vr, vg, vb.
     * 
     * @param vr
     * @param vg
     * @param vb
     */
    public void mul( float vr, float vg, float vb )
    {
        this.values[ 0 ] *= vr;
        this.values[ 1 ] *= vg;
        this.values[ 2 ] *= vb;
    }
    
    /**
     * Sets the value of this tuple to the scalar multiplication of itself.
     * 
     * @param factor the scalar value
     */
    public void mul( float factor )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] *= factor;
        }
    }
    
    /**
     * Divides this color's red value by v.
     * 
     * @param v
     */
    public void divRed( float v )
    {
        this.values[ 0 ] /= v;
    }
    
    /**
     * Divides this color's green value by v.
     * 
     * @param v
     */
    public void divGreen( float v )
    {
        this.values[ 1 ] /= v;
    }
    
    /**
     * Divides this color's blue value by v.
     * 
     * @param v
     */
    public void divBlue( float v )
    {
        this.values[ 2 ] /= v;
    }
    
    /**
     * Divides this color's alpha value by v.
     * 
     * @param v
     */
    public void divAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 3 ] /= v;
    }
    
    /**
     * Divides this color's values by vr, vg, vb, va.
     * 
     * @param vr
     * @param vg
     * @param vb
     * @param va
     */
    public void div( float vr, float vg, float vb, float va )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 0 ] /= vr;
        this.values[ 1 ] /= vg;
        this.values[ 2 ] /= vb;
        this.values[ 3 ] /= va;
    }
    
    /**
     * Divides this color's values by vr, vg, vb.
     * 
     * @param vr
     * @param vg
     * @param vb
     */
    public void div( float vr, float vg, float vb )
    {
        this.values[ 0 ] /= vr;
        this.values[ 1 ] /= vg;
        this.values[ 2 ] /= vb;
    }
    
    /**
     * Sets the value of this color to the vector sum of colors color1 and color2.
     * 
     * @param color1 the first color
     * @param color2 the second color
     */
    public void add( Colorf color1, Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] = color1.values[ i ] + color2.values[ i ];
        }
    }
    
    /**
     * Sets the value of this tuple to the vector sum of itself and tuple t1.
     * 
     * @param color2 the other tuple
     */
    public void add( Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] += color2.values[ i ];
        }
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public void add( float r, float g, float b, float a )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 0 ] += r;
        this.values[ 1 ] += g;
        this.values[ 2 ] += b;
        this.values[ 3 ] += a;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     */
    public void add( float r, float g, float b )
    {
        this.values[ 0 ] += r;
        this.values[ 1 ] += g;
        this.values[ 2 ] += b;
    }
    
    /**
     * Sets the value of this color to the vector difference of color color1 and color2
     * (this = color1 - color2).
     * 
     * @param color1 the first color
     * @param color2 the second color
     */
    public void sub( Colorf color1, Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] = color1.values[ i ] - color2.values[ i ];
        }
    }
    
    /**
     * Sets the value of this color to the vector difference of itself and color2
     * (this = this - color2).
     * 
     * @param color2 the other color
     * 
     */
    public void sub( Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] -= color2.values[ i ];
        }
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public void sub( float r, float g, float b, float a )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ 0 ] -= r;
        this.values[ 1 ] -= g;
        this.values[ 2 ] -= b;
        this.values[ 3 ] -= a;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     */
    public void sub( float r, float g, float b )
    {
        this.values[ 0 ] -= r;
        this.values[ 1 ] -= g;
        this.values[ 2 ] -= b;
    }
    
    /**
     * Clamps the minimum value of this tuple to the min parameter.
     * 
     * @param min the lowest value in this tuple after clamping
     */
    public void clampMin( float min )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            if (this.values[ i ] < min )
                this.values[ i ] = min;
        }
    }
    
    /**
     * Clamps the maximum value of this tuple to the max parameter.
     * 
     * @param max the highest value in the tuple after clamping
     */
    public void clampMax( float max )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            if (this.values[ i ] > max )
                this.values[ i ] = max;
        }
    }
    
    /**
     * Clamps this tuple to the range [min, max].
     * 
     * @param min the lowest value in this tuple after clamping
     * @param max the highest value in this tuple after clamping
     */
    public void clamp( float min, float max )
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
    public void clamp( float min, float max, Colorf vec )
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
    public void clampMin( float min, Colorf vec )
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
    public void clampMax( float max, Colorf vec )
    {
        set( vec );
        clampMax( max );
    }
    
    /**
     * Linearly interpolates between this tuple and tuple t2 and places the
     * result into this tuple: this = (1 - alpha) * this + alpha * t1.
     * 
     * @param t2 the first tuple
     * @param val the alpha interpolation parameter
     */
    public void interpolate( Colorf color2, float val )
    {
        final float beta = 1.0f - val;
        
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] = beta * this.values[ i ] + val * color2.values[ i ];
        }
    }
    
    /**
     * Linearly interpolates between tuples t1 and t2 and places the result into
     * this tuple: this = (1 - alpha) * t1 + alpha * t2.
     * 
     * @param color1 the first tuple
     * @param color2 the second tuple
     * @param val the interpolation parameter
     */
    public void interpolate( Colorf color1, Colorf color2, float val )
    {
        final float beta = 1.0f - val;
        
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ i ] = beta * color1.values[ i ] + val * color2.values[ i ];
        }
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
        final int rbits = floatToIntBits( values[ 0 ] );
        final int gbits = floatToIntBits( values[ 1 ] );
        final int bbits = floatToIntBits( values[ 2 ] );
        
        if ( hasAlpha() )
            return( rbits ^ gbits ^ bbits ^ floatToIntBits( values[ 3 ] ) );
        else
            return( rbits ^ gbits ^ bbits );
    }
    
    /**
     * Returns true if all of the data members of Tuple3f t1 are equal to the
     * corresponding data members in this
     * 
     * @param color2 the color with which the comparison is made.
     */
    public boolean equals( Colorf color2 )
    {
        if ( this.hasAlpha() != color2.hasAlpha() )
            return( false );
        
        final int n = hasAlpha() ? 4 : 3;
        for ( int i = 0; i < n; i++ )
        {
            if ( color2.values[ i ] != this.values[ i ] )
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
        return( ( o != null ) && ( ( o instanceof Colorf ) && equals( (Colorf)o ) ) );
    }
    
    /**
     * Returns true if the L-infinite distance between this tuple and tuple t1
     * is less than or equal to the epsilon parameter, otherwise returns false.
     * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
     * 
     * @param color2 the color to be compared to this color
     * @param epsilon the threshold value
     */
    public boolean epsilonEquals( Colorf color2, float epsilon )
    {
        if ( this.hasAlpha() != color2.hasAlpha() )
            return( false );
        
        final int n = hasAlpha() ? 4 : 3;
        for ( int i = 0; i < n; i++ )
        {
            if ( Math.abs( color2.values[ i ] - this.values[ i ] ) > epsilon )
                return( false );
        }
        
        return( true );
    }
    
    /**
     * Returns a string that contains the values of this Colorf.
     * The form is ( red = f, green = f, blue = f, alpha = f ).
     * 
     * @return the String representation
     */
    @Override
    public String toString()
    {
        String str = "Color ( red = " + getRed() + ", green = " + getGreen() + ", blue = " + getBlue();
        
        if ( hasAlpha() )
            return( str + ", alpha = " + getAlpha() + " )" );
        else
            return( str + " )" );
    }
    
    /**
     * Creates and returns a copy of this object.
     * 
     * @return a clone of this instance.
     * @exception OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     */
    @Override
    public Colorf clone()
    {
        try
        {
            return( (Colorf)super.clone() );
        }
        catch (CloneNotSupportedException ex)
        {
            throw( new InternalError() );
        }
    }
    
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     * @param a the aalpha channel to use
     */
    public Colorf( float r, float g, float b, float a )
    {
        this.values = new float[] { r, g, b, a };
        this.hasAlpha = ( a >= 0.0f );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     */
    public Colorf( float r, float g, float b )
    {
        this( r, g, b, -1f );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param intensity the gray intensity (used for all three r,g,b values
     */
    public Colorf( float intensity )
    {
        this( intensity, intensity, intensity );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param values the values array (must be at least size 3)
     */
    public Colorf( float[] values )
    {
        this( values[ 0 ], values[ 1 ], values[ 2 ], ( values.length > 3 ) ? values[ 3 ] : -1f );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param color the Colorf to copy the values from
     */
    public Colorf( Colorf color )
    {
        this( color.values );
        
        this.hasAlpha = color.hasAlpha;
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param vec the Vector4f to copy the values from
     */
    public Colorf()
    {
        this( 0f, 0f, 0f, -1f );
    }
    
    public Colorf( java.awt.Color color )
    {
        super();
        
        this.values = new float[] { 0f, 0f, 0f, 0f };
        
        set( color );
    }
    
    /**
     * Allocates an Colorf instance from the pool.
     */
    public static Colorf fromPool()
    {
        return( POOL.alloc() );
    }
    
    /**
     * Allocates an Colorf instance from the pool.
     */
    public static Colorf fromPool( float r, float g, float b, float a )
    {
        return( POOL.alloc( r, g, b, a ) );
    }
    
    /**
     * Allocates an Colorf instance from the pool.
     */
    public static Colorf fromPool( float r, float g, float b )
    {
        return( POOL.alloc( r, g, b ) );
    }
    
    /**
     * Stores the given Colorf instance in the pool.
     * 
     * @param o
     */
    public static void toPool( Colorf o )
    {
        POOL.free( o );
    }
}

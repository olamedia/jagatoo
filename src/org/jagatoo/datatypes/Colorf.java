package org.jagatoo.datatypes;

import java.io.Serializable;

import org.jagatoo.datatypes.pools.ColorPool;

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
    public static final Colorf WHITE = new Colorf( true, java.awt.Color.WHITE );
    
    /**
     * The color light gray. In the default sRGB space.
     */
    public static final Colorf LIGHT_GRAY = new Colorf( true, java.awt.Color.GRAY );
    
    /**
     * A 10% gray. In the default sRGB space.
     */
    public static final Colorf GRAY10 = new Colorf( true, 0.9f );
    
    /**
     * A 20% gray. In the default sRGB space.
     */
    public static final Colorf GRAY20 = new Colorf( true, 0.8f );
    
    /**
     * A 30% gray. In the default sRGB space.
     */
    public static final Colorf GRAY30 = new Colorf( true, 0.7f );
    
    /**
     * A 40% gray. In the default sRGB space.
     */
    public static final Colorf GRAY40 = new Colorf( true, 0.6f );
    
    /**
     * A 50% gray. In the default sRGB space.
     */
    public static final Colorf GRAY50 = new Colorf( true, 0.5f );
    
    /**
     * A 60% gray. In the default sRGB space.
     */
    public static final Colorf GRAY60 = new Colorf( true, 0.4f );
    
    /**
     * A 70% gray. In the default sRGB space.
     */
    public static final Colorf GRAY70 = new Colorf( true, 0.3f );
    
    /**
     * A 80% gray. In the default sRGB space.
     */
    public static final Colorf GRAY80 = new Colorf( true, 0.2f );
    
    /**
     * A 90% gray. In the default sRGB space.
     */
    public static final Colorf GRAY90 = new Colorf( true, 0.1f );
    
    /**
     * The color gray. In the default sRGB space.
     */
    public static final Colorf GRAY = new Colorf( true, java.awt.Color.GRAY );
    
    /**
     * The color dark gray. In the default sRGB space.
     */
    public static final Colorf DARK_GRAY = new Colorf( true, java.awt.Color.DARK_GRAY );
    
    /**
     * The color black. In the default sRGB space.
     */
    public static final Colorf BLACK = new Colorf( true, java.awt.Color.BLACK );
    
    /**
     * The color red. In the default sRGB space.
     */
    public static final Colorf RED = new Colorf( true, java.awt.Color.RED );
    
    /**
     * The color pink. In the default sRGB space.
     */
    public static final Colorf PINK = new Colorf( true, java.awt.Color.PINK );
    
    /**
     * The color orange. In the default sRGB space.
     */
    public static final Colorf ORANGE = new Colorf( true, java.awt.Color.ORANGE );
    
    /**
     * The color yellow. In the default sRGB space.
     */
    public static final Colorf YELLOW = new Colorf( true, java.awt.Color.YELLOW );
    
    /**
     * The color green. In the default sRGB space.
     */
    public static final Colorf GREEN = new Colorf( true, java.awt.Color.GREEN );
    
    /**
     * The color magenta. In the default sRGB space.
     */
    public static final Colorf MAGENTA = new Colorf( true, java.awt.Color.MAGENTA );
    
    /**
     * The color cyan. In the default sRGB space.
     */
    public static final Colorf CYAN = new Colorf( true, java.awt.Color.CYAN );
    
    /**
     * The color blue. In the default sRGB space.
     */
    public static final Colorf BLUE = new Colorf( true, java.awt.Color.BLUE );
    
    /**
     * @return this Vector's size().
     */
    public final int getSize()
    {
        return( N );
    }
    
    /**
     * @return if this Colorf has an alpha channel.
     */
    public final boolean hasAlpha()
    {
        return( hasAlpha );
    }
    
    /**
     * Sets color from awt.Color.
     * 
     * @param color awt color
     */
    public final void set( java.awt.Color color )
    {
        setRed( ((float)color.getRed()) / 255.0f );
        setGreen( ((float)color.getGreen()) / 255.0f );
        setBlue( ((float)color.getBlue()) / 255.0f );
        
        hasAlpha = ( color.getAlpha() > 0 );
        if ( hasAlpha )
            setAlpha( ((float)color.getAlpha()) / 255.0f );
        
        this.isDirty = true;
    }
    
    /**
     * Sets all values of this Tuple to the specified ones.
     * 
     * @param values the values array (must be at least size 4)
     */
    public final void set( float[] values )
    {
        if ( values.length > 3 )
        {
            System.arraycopy( values, 0, this.values, roTrick + 0, N );
            hasAlpha = ( values[ 3 ] > 0.0f );
        }
        else
        {
            System.arraycopy( values, 0, this.values, roTrick + 0, 3 );
            hasAlpha = false;
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets all three values of this Tuple to the specified ones.
     * 
     * @param color the tuple to be copied
     */
    public final void set( Colorf color )
    {
        System.arraycopy( color.values, 0, this.values, roTrick + 0, N );
        hasAlpha = color.hasAlpha;
        
        this.isDirty = true;
    }
    
    /**
     * Sets all values of this color to the specified ones.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     * @param a the alpha element to use
     */
    public final void set( float r, float g, float b, float a )
    {
        setRed( r );
        setGreen( g );
        setBlue( b );
        setAlpha( a );
        
        this.hasAlpha = ( a >= 0.0f );
        
        this.isDirty = true;
    }
    
    /**
     * Sets all values of this color to the specified ones.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     */
    public final void set( float r, float g, float b )
    {
        setRed( r );
        setGreen( g );
        setBlue( b );
        
        this.hasAlpha = false;
        
        this.isDirty = true;
    }
    
    /**
     * Gets java.awt.Color.
     * 
     * @return AWT color
     */
    public final java.awt.Color getAWTColor()
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
    public final void get( float[] buffer )
    {
        final int n = hasAlpha() ? 4 : 3;
        System.arraycopy( this.values, 0, buffer, 0, n );
    }
    
    /**
     * Writes all values of this vector to the specified buffer vector.
     * 
     * @param buffer the buffer vector to write the values to
     */
    public final void get( Colorf buffer )
    {
        System.arraycopy( this.values, 0, buffer.values, 0, N );
        buffer.hasAlpha = this.hasAlpha;
    }
    
    /**
     * Sets the Red color component.
     */
    public final void setRed( float red )
    {
        this.values[ roTrick + 0 ] = red;
        
        this.isDirty = true;
    }
    
    /**
     * @return the Red color component.
     */
    public final float getRed()
    {
        return( values[ 0 ] );
    }
    
    /**
     * Sets the Green color component.
     */
    public final void setGreen( float green )
    {
        this.values[ roTrick + 1 ] = green;
        
        this.isDirty = true;
    }
    
    /**
     * @return the Green color component.
     */
    public final float getGreen()
    {
        return( values[ 1 ] );
    }
    
    /**
     * Sets the Blue color component.
     */
    public final void setBlue( float blue )
    {
        this.values[ roTrick + 2 ] = blue;
        
        this.isDirty = true;
    }
    
    /**
     * @return the Blue color component.
     */
    public final float getBlue()
    {
        return( values[ 2 ] );
    }
    
    /**
     * Sets the value of the alpha-element of this color.
     * 
     * @param alpha
     */
    public final void setAlpha( float alpha )
    {
        this.values[ roTrick + 3 ] = alpha;
        
        this.hasAlpha = ( alpha >= 0.0f );
        
        this.isDirty = true;
    }
    
    /**
     * @return the value of the alpha-element of this color.
     * 
     * @see #getTransparency()
     */
    public final float getAlpha()
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
    public final void setZero()
    {
        for ( int i = 0; i < 3; i++ )
        {
            this.values[ roTrick + i ] = 0f;
        }
        
        if ( hasAlpha() )
            this.values[ roTrick + 3 ] = 0.0f;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this color's red value.
     * 
     * @param v
     */
    public final void addRed( float v )
    {
        this.values[ roTrick + 0 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this color's green value.
     * 
     * @param v
     */
    public final void addGreen( float v )
    {
        this.values[ roTrick + 1 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this color's blue value.
     * 
     * @param v
     */
    public final void addBlue( float v )
    {
        this.values[ roTrick + 2 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Adds v to this color's alpha value.
     * 
     * @param v
     */
    public final void addAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 3 ] += v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this color's red value.
     * 
     * @param v
     */
    public final void subRed( float v )
    {
        this.values[ roTrick + 0 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this color's green value.
     * 
     * @param v
     */
    public final void subGreen( float v )
    {
        this.values[ roTrick + 1 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this color's blue value.
     * 
     * @param v
     */
    public final void subBlue( float v )
    {
        this.values[ roTrick + 2 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts v from this color's alpha value.
     * 
     * @param v
     */
    public final void subAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 3 ] -= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this color's red value with v.
     * 
     * @param v
     */
    public final void mulRed( float v )
    {
        this.values[ roTrick + 0 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this color's green value with v.
     * 
     * @param v
     */
    public final void mulGreen( float v )
    {
        this.values[ roTrick + 1 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this color's blue value with v.
     * 
     * @param v
     */
    public final void mulBlue( float v )
    {
        this.values[ roTrick + 2 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this color's alpha value with v.
     * 
     * @param v
     */
    public final void mulAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 3 ] *= v;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this color's values with vr, vg, vb, va.
     * 
     * @param vr
     * @param vg
     * @param vb
     * @param va
     */
    public final void mul( float vr, float vg, float vb, float va )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 0 ] *= vr;
        this.values[ roTrick + 1 ] *= vg;
        this.values[ roTrick + 2 ] *= vb;
        this.values[ roTrick + 3 ] *= va;
        
        this.isDirty = true;
    }
    
    /**
     * Multiplies this color's values with vr, vg, vb.
     * 
     * @param vr
     * @param vg
     * @param vb
     */
    public final void mul( float vr, float vg, float vb )
    {
        this.values[ roTrick + 0 ] *= vr;
        this.values[ roTrick + 1 ] *= vg;
        this.values[ roTrick + 2 ] *= vb;
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this tuple to the scalar multiplication of itself.
     * 
     * @param factor the scalar value
     */
    public final void mul( float factor )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] *= factor;
        }
        
        this.isDirty = true;
    }
    
    /**
     * Divides this color's red value by v.
     * 
     * @param v
     */
    public final void divRed( float v )
    {
        this.values[ roTrick + 0 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this color's green value by v.
     * 
     * @param v
     */
    public final void divGreen( float v )
    {
        this.values[ roTrick + 1 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this color's blue value by v.
     * 
     * @param v
     */
    public final void divBlue( float v )
    {
        this.values[ roTrick + 2 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this color's alpha value by v.
     * 
     * @param v
     */
    public final void divAlpha( float v )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 3 ] /= v;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this color's values by vr, vg, vb, va.
     * 
     * @param vr
     * @param vg
     * @param vb
     * @param va
     */
    public final void div( float vr, float vg, float vb, float va )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 0 ] /= vr;
        this.values[ roTrick + 1 ] /= vg;
        this.values[ roTrick + 2 ] /= vb;
        this.values[ roTrick + 3 ] /= va;
        
        this.isDirty = true;
    }
    
    /**
     * Divides this color's values by vr, vg, vb.
     * 
     * @param vr
     * @param vg
     * @param vb
     */
    public final void div( float vr, float vg, float vb )
    {
        this.values[ roTrick + 0 ] /= vr;
        this.values[ roTrick + 1 ] /= vg;
        this.values[ roTrick + 2 ] /= vb;
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this color to the vector sum of colors color1 and color2.
     * 
     * @param color1 the first color
     * @param color2 the second color
     */
    public final void add( Colorf color1, Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] = color1.values[ i ] + color2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this tuple to the vector sum of itself and tuple t1.
     * 
     * @param color2 the other tuple
     */
    public final void add( Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] += color2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public final void add( float r, float g, float b, float a )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 0 ] += r;
        this.values[ roTrick + 1 ] += g;
        this.values[ roTrick + 2 ] += b;
        this.values[ roTrick + 3 ] += a;
        
        this.isDirty = true;
    }
    
    /**
     * Adds the given parameters to this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     */
    public final void add( float r, float g, float b )
    {
        this.values[ roTrick + 0 ] += r;
        this.values[ roTrick + 1 ] += g;
        this.values[ roTrick + 2 ] += b;
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this color to the vector difference of color color1 and color2
     * (this = color1 - color2).
     * 
     * @param color1 the first color
     * @param color2 the second color
     */
    public final void sub( Colorf color1, Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] = color1.values[ i ] - color2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Sets the value of this color to the vector difference of itself and color2
     * (this = this - color2).
     * 
     * @param color2 the other color
     * 
     */
    public final void sub( Colorf color2 )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] -= color2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public final void sub( float r, float g, float b, float a )
    {
        if ( !hasAlpha() )
            throw( new UnsupportedOperationException( "no alpha channel" ) );
        
        this.values[ roTrick + 0 ] -= r;
        this.values[ roTrick + 1 ] -= g;
        this.values[ roTrick + 2 ] -= b;
        this.values[ roTrick + 3 ] -= a;
        
        this.isDirty = true;
    }
    
    /**
     * Subtracts the given parameters from this tuple's values.
     * 
     * @param r
     * @param g
     * @param b
     */
    public final void sub( float r, float g, float b )
    {
        this.values[ roTrick + 0 ] -= r;
        this.values[ roTrick + 1 ] -= g;
        this.values[ roTrick + 2 ] -= b;
        
        this.isDirty = true;
    }
    
    /**
     * Clamps the minimum value of this tuple to the min parameter.
     * 
     * @param min the lowest value in this tuple after clamping
     */
    public final void clampMin( float min )
    {
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
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
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
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
        
        this.isDirty = true;
    }
    
    /**
     * Clamps the tuple parameter to the range [min, max] and places the values
     * into this tuple.
     * 
     * @param min the lowest value in the tuple after clamping
     * @param max the highest value in the tuple after clamping
     * @param vec the source tuple, which will not be modified
     */
    public final void clamp( float min, float max, Colorf vec )
    {
        set( vec );
        
        clamp( min, max );
        
        this.isDirty = true;
    }
    
    /**
     * Clamps the minimum value of the tuple parameter to the min parameter and
     * places the values into this tuple.
     * 
     * @param min the lowest value in the tuple after clamping
     * @parm that the source tuple, which will not be modified
     */
    public final void clampMin( float min, Colorf vec )
    {
        set( vec );
        clampMin( min );
        
        this.isDirty = true;
    }
    
    /**
     * Clamps the maximum value of the tuple parameter to the max parameter and
     * places the values into this tuple.
     * 
     * @param max the highest value in the tuple after clamping
     * @param vec the source tuple, which will not be modified
     */
    public final void clampMax( float max, Colorf vec )
    {
        set( vec );
        clampMax( max );
        
        this.isDirty = true;
    }
    
    /**
     * Linearly interpolates between this tuple and tuple t2 and places the
     * result into this tuple: this = (1 - alpha) * this + alpha * t1.
     * 
     * @param t2 the first tuple
     * @param val the alpha interpolation parameter
     */
    public final void interpolate( Colorf color2, float val )
    {
        final float beta = 1.0f - val;
        
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] = beta * this.values[ i ] + val * color2.values[ i ];
        }
        
        this.isDirty = true;
    }
    
    /**
     * Linearly interpolates between tuples t1 and t2 and places the result into
     * this tuple: this = (1 - alpha) * t1 + alpha * t2.
     * 
     * @param color1 the first tuple
     * @param color2 the second tuple
     * @param val the interpolation parameter
     */
    public final void interpolate( Colorf color1, Colorf color2, float val )
    {
        final float beta = 1.0f - val;
        
        final int n = hasAlpha() ? 4 : 3;
        
        for ( int i = 0; i < n; i++ )
        {
            this.values[ roTrick + i ] = beta * color1.values[ i ] + val * color2.values[ i ];
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
    public Colorf( boolean readOnly, float r, float g, float b, float a )
    {
        this.values = new float[] { r, g, b, a };
        this.hasAlpha = ( a >= 0.0f );
        
        this.roTrick = readOnly ? -Integer.MAX_VALUE + values.length : 0;
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     */
    public Colorf( boolean readOnly, float r, float g, float b )
    {
        this( readOnly, r, g, b, -1f );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param intensity the gray intensity (used for all three r,g,b values
     */
    public Colorf( boolean readOnly, float intensity )
    {
        this( readOnly, intensity, intensity, intensity );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param values the values array (must be at least size 3)
     */
    public Colorf( boolean readOnly, float[] values )
    {
        this( readOnly, values[ 0 ], values[ 1 ], values[ 2 ], ( values.length > 3 ) ? values[ 3 ] : -1f );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param color the Colorf to copy the values from
     */
    public Colorf( boolean readOnly, Colorf color )
    {
        this( readOnly, color.values );
        
        this.hasAlpha = color.hasAlpha;
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param vec the Vector4f to copy the values from
     */
    public Colorf( boolean readOnly )
    {
        this( readOnly, 0f, 0f, 0f, -1f );
    }
    
    public Colorf( boolean readOnly, java.awt.Color color )
    {
        super();
        
        this.values = new float[] { 0f, 0f, 0f, 0f };
        
        this.values[ 0 ] = ((float)color.getRed()) / 255.0f;
        this.values[ 1 ] = ((float)color.getGreen()) / 255.0f;
        this.values[ 2 ] = ((float)color.getBlue()) / 255.0f;
        
        this.hasAlpha = ( color.getAlpha() > 0 );
        if ( hasAlpha )
            this.values[ 3 ] = ((float)color.getAlpha()) / 255.0f;
        
        this.roTrick = readOnly ? -Integer.MAX_VALUE + values.length : 0;
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
        this( false, r, g, b, a );
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
        this( false, r, g, b );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param intensity the gray intensity (used for all three r,g,b values
     */
    public Colorf( float intensity )
    {
        this( false, intensity );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param values the values array (must be at least size 3)
     */
    public Colorf( float[] values )
    {
        this( false, values );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param color the Colorf to copy the values from
     */
    public Colorf( Colorf color )
    {
        this( false, color );
    }
    
    /**
     * Creates a new Colorf instance.
     * 
     * @param vec the Vector4f to copy the values from
     */
    public Colorf()
    {
        this( false );
    }
    
    public Colorf( java.awt.Color color )
    {
        this( false, color );
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

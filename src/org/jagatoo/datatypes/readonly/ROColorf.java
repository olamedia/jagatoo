package org.jagatoo.datatypes.readonly;

import org.jagatoo.datatypes.Colorf;

/**
 * A read-only implementation of Colorf.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class ROColorf extends Colorf
{
    private static final long serialVersionUID = -8639037295725571148L;
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( java.awt.Color color )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float[] values )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( Colorf color )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float r, float g, float b, float a )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float r, float g, float b )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void setRed( float red )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void setGreen( float green )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void setBlue( float blue )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void setAlpha( float alpha )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void setZero()
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void addRed( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void addGreen( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void addBlue( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void addAlpha( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void subRed( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void subGreen( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void subBlue( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void subAlpha( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mulRed( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mulGreen( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mulBlue( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mulAlpha( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float vr, float vg, float vb, float va )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float vr, float vg, float vb )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float factor )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void divRed( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void divGreen( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void divBlue( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void divAlpha( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void div( float vr, float vg, float vb, float va )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void div( float vr, float vg, float vb )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( Colorf color1, Colorf color2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( Colorf color2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( float r, float g, float b, float a )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( float r, float g, float b )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( Colorf color1, Colorf color2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( Colorf color2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( float r, float g, float b, float a )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( float r, float g, float b )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clampMin( float min )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clampMax( float max )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clamp( float min, float max )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clamp( float min, float max, Colorf vec )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clampMin( float min, Colorf vec )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clampMax( float max, Colorf vec )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void interpolate( Colorf color2, float val )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void interpolate( Colorf color1, Colorf color2, float val )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    
    /**
     * Creates a new read-only Colorf instance.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     * @param a the aalpha channel to use
     */
    public ROColorf( float r, float g, float b, float a )
    {
        super( r, g, b, a );
    }
    
    /**
     * Creates a new read-only Colorf instance.
     * 
     * @param r the red element to use
     * @param g the green element to use
     * @param b the blue element to use
     */
    public ROColorf( float r, float g, float b )
    {
        super( r, g, b );
    }
    
    /**
     * Creates a new read-only Colorf instance.
     * 
     * @param intensity the gray intensity (used for all three r,g,b values
     */
    public ROColorf( float intensity )
    {
        super( intensity, intensity, intensity );
    }
    
    /**
     * Creates a new read-only Colorf instance.
     * 
     * @param values the values array (must be at least size 3)
     */
    public ROColorf( float[] values )
    {
        super( values );
    }
    
    /**
     * Creates a new read-only Colorf instance.
     * 
     * @param color the Colorf to copy the values from
     */
    public ROColorf( Colorf color )
    {
        super( color );
    }
    
    public ROColorf( java.awt.Color color )
    {
        super( color );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROColorf fromPool()
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROColorf fromPool( float r, float g, float b, float a )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROColorf fromPool( float r, float g, float b )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static void toPool( ROColorf o )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
}

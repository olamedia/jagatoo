package org.jagatoo.datatypes.readonly;

import org.jagatoo.datatypes.TexCoord1f;
import org.jagatoo.datatypes.TexCoordf;

/**
 * A read-only implementation of TexCoord1f.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ROTexCoord1f extends TexCoord1f
{
    private static final long serialVersionUID = -1293364662847458740L;
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float s )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void setS( float s )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void addS( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void subS( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mulS( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float vs )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void divS( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void div( float vs )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( float s )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( float s )
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
    public void set( TexCoordf texCoord )
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
    public void add( TexCoordf texCoord1, TexCoordf texCoord2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( TexCoordf texCoord2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( TexCoordf texCoord1, TexCoordf texCoord2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( TexCoordf texCoord2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void scaleAdd( float factor, TexCoordf texCoord1, TexCoordf texCoord2 )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void scaleAdd( float factor, TexCoordf texCoord2 )
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
    public void clamp( float min, float max, TexCoordf vec )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clampMin( float min, TexCoordf vec )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void clampMax( float max, TexCoordf vec )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void interpolate( TexCoordf texCoord2, float alpha )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void interpolate( TexCoordf texCoord1, TexCoordf texCoord2, float alpha )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    
    /**
     * Creates a new read-only TexCoord1f instance.
     * 
     * @param s the S element to use
     */
    public ROTexCoord1f( float s )
    {
        super( s );
    }
    
    /**
     * Creates a new rad-only TexCoord1f instance.
     * 
     * @param values the values array (must be at least size 1)
     */
    public ROTexCoord1f( float[] values )
    {
        super( values );
    }
    
    /**
     * Creates a new read-only TexCoord1f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public ROTexCoord1f( TexCoordf texCoord )
    {
        super( texCoord );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord1f fromPool()
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord1f fromPool( float s )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static void toPool( ROTexCoord1f o )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
}

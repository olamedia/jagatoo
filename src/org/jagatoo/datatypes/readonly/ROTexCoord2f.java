package org.jagatoo.datatypes.readonly;

import org.jagatoo.datatypes.TexCoord2f;
import org.jagatoo.datatypes.TexCoordf;

/**
 * A read-only implementation of TexCoord2f.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ROTexCoord2f extends TexCoord2f
{
    private static final long serialVersionUID = 35304108961766665L;
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float s, float t )
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
    public void setT( float t )
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
    public void addT( float v )
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
    public void subT( float v )
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
    public void mulT( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float vs, float vt )
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
    public void divS( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void divT( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void div( float vs, float vt )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( float s, float t )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( float s, float t )
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
     * Creates a new read-only TexCoord2f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     */
    public ROTexCoord2f( float s, float t )
    {
        super( s, t );
    }
    
    /**
     * Creates a new read-only TexCoord2f instance.
     * 
     * @param values the values array (must be at least size 2)
     */
    public ROTexCoord2f( float[] values )
    {
        super( values );
    }
    
    /**
     * Creates a new read-only TexCoord2f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public ROTexCoord2f( TexCoordf texCoord )
    {
        super( texCoord );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord2f fromPool()
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord2f fromPool( float s, float t )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static void toPool( ROTexCoord2f o )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
}

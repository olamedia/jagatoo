package org.jagatoo.datatypes.readonly;

import org.jagatoo.datatypes.TexCoord3f;
import org.jagatoo.datatypes.TexCoordf;

/**
 * A read-only implementation of TexCoord3f.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ROTexCoord3f extends TexCoord3f
{
    private static final long serialVersionUID = -7351984044233849871L;
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float s, float t, float p )
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
    public void setP( float p )
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
    public void addP( float v )
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
    public void subP( float v )
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
    public void mulP( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float vs, float vt, float vp )
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
    public void divP( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void div( float vs, float vt, float vp )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( float s, float t, float p )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( float s, float t, float p )
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
     * Creates a new TexCoord3f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     * @param p the P element to use
     */
    public ROTexCoord3f( float s, float t, float p )
    {
        super( s, t, p );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param values the values array (must be at least size 3)
     */
    public ROTexCoord3f( float[] values )
    {
        super( values );
    }
    
    /**
     * Creates a new TexCoord3f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public ROTexCoord3f( TexCoordf texCoord )
    {
        super( texCoord );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord3f fromPool()
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord3f fromPool( float s, float t, float p )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static void toPool( ROTexCoord3f o )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
}

package org.jagatoo.datatypes.readonly;

import org.jagatoo.datatypes.TexCoord4f;
import org.jagatoo.datatypes.TexCoordf;

/**
 * A read-only implementation fo TexCoord4f.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ROTexCoord4f extends TexCoord4f
{
    private static final long serialVersionUID = -3163000319409427999L;
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void set( float s, float t, float p, float q )
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
    public void setQ( float q )
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
    public void addQ( float v )
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
    public void subQ( float v )
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
    public void mulQ( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void mul( float vs, float vt, float vp, float vq )
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
    public void divQ( float v )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void div( float vs, float vt, float vp, float vq )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void add( float s, float t, float p, float q )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void sub( float s, float t, float p, float q )
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
     * Creates a new read-only TexCoord4f instance.
     * 
     * @param s the S element to use
     * @param t the T element to use
     * @param p the P element to use
     * @param q the Q channel to use
     */
    public ROTexCoord4f( float s, float t, float p, float q )
    {
        super( s, t, p, q );
    }
    
    /**
     * Creates a new read-only TexCoord4f instance.
     * 
     * @param values the values array (must be at least size 4)
     */
    public ROTexCoord4f( float[] values )
    {
        super( values );
    }
    
    /**
     * Creates a new read-only TexCoord4f instance.
     * 
     * @param texCoord the TexCoordf to copy the values from
     */
    public ROTexCoord4f( TexCoordf texCoord )
    {
        super( texCoord );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord4f fromPool()
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static ROTexCoord4f fromPool( float s, float t, float p, float q )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
    
    /**
     * @throws UnsupportedOperationException
     */
    public static void toPool( ROTexCoord4f o )
    {
        throw( new UnsupportedOperationException( "This object is read-only" ) );
    }
}

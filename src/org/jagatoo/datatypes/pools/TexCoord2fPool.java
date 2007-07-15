package org.jagatoo.datatypes.pools;

import org.openmali.vecmath2.pools.ObjectPool;
import org.jagatoo.datatypes.TexCoord2f;

/**
 * An instance pool for TexCoord1f instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord2fPool extends ObjectPool< TexCoord2f >
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected TexCoord2f newInstance()
    {
        return( new TexCoord2f() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TexCoord2f alloc()
    {
        TexCoord2f o = super.alloc();
        
        o.setZero();
        
        return( o );
    }
    
    public TexCoord2f alloc( float s, float t )
    {
        TexCoord2f o = super.alloc();
        
        o.set( s, t );
        
        return( o );
    }
    
    public TexCoord2fPool( int initialSize )
    {
        super( initialSize );
    }
}

package org.jagatoo.datatypes.pools;

import org.openmali.vecmath2.pools.ObjectPool;
import org.jagatoo.datatypes.TexCoord1f;

/**
 * An instance pool for TexCoord1f instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord1fPool extends ObjectPool< TexCoord1f >
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected TexCoord1f newInstance()
    {
        return( new TexCoord1f() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TexCoord1f alloc()
    {
        TexCoord1f o = super.alloc();
        
        o.setZero();
        
        return( o );
    }
    
    public TexCoord1f alloc( float s )
    {
        TexCoord1f o = super.alloc();
        
        o.set( s );
        
        return( o );
    }
    
    public TexCoord1fPool( int initialSize )
    {
        super( initialSize );
    }
}

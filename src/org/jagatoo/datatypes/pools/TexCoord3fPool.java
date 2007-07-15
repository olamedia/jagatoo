package org.jagatoo.datatypes.pools;

import org.openmali.vecmath2.pools.ObjectPool;
import org.jagatoo.datatypes.TexCoord3f;

/**
 * An instance pool for TexCoord1f instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord3fPool extends ObjectPool< TexCoord3f >
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected TexCoord3f newInstance()
    {
        return( new TexCoord3f() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TexCoord3f alloc()
    {
        TexCoord3f o = super.alloc();
        
        o.setZero();
        
        return( o );
    }
    
    public TexCoord3f alloc( float s, float t, float p )
    {
        TexCoord3f o = super.alloc();
        
        o.set( s, t, p );
        
        return( o );
    }
    
    public TexCoord3fPool( int initialSize )
    {
        super( initialSize );
    }
}

package org.jagatoo.datatypes.pools;

import org.openmali.vecmath2.pools.ObjectPool;
import org.jagatoo.datatypes.TexCoord4f;

/**
 * An instance pool for TexCoord1f instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexCoord4fPool extends ObjectPool< TexCoord4f >
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected TexCoord4f newInstance()
    {
        return( new TexCoord4f() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TexCoord4f alloc()
    {
        TexCoord4f o = super.alloc();
        
        o.setZero();
        
        return( o );
    }
    
    public TexCoord4f alloc( float s, float t, float p, float q )
    {
        TexCoord4f o = super.alloc();
        
        o.set( s, t, p, q );
        
        return( o );
    }
    
    public TexCoord4fPool( int initialSize )
    {
        super( initialSize );
    }
}

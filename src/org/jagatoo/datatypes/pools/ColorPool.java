package org.jagatoo.datatypes.pools;

import org.jagatoo.datatypes.Colorf;
import org.openmali.vecmath2.pools.ObjectPool;

/**
 * An instance pool for Colorf instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ColorPool extends ObjectPool< Colorf >
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected Colorf newInstance()
    {
        return( new Colorf() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Colorf alloc()
    {
        Colorf o = super.alloc();
        
        o.setZero();
        
        return( o );
    }
    
    public Colorf alloc( float r, float g, float b )
    {
        Colorf o = super.alloc();
        
        o.set( r, g, b );
        
        return( o );
    }
    
    public Colorf alloc( float r, float g, float b, float a )
    {
        Colorf o = super.alloc();
        
        o.set( r, g, b, a );
        
        return( o );
    }
    
    public ColorPool( int initialSize )
    {
        super( initialSize );
    }
}

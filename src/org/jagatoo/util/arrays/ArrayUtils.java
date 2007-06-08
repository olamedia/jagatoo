package org.jagatoo.util.arrays;

/**
 * Helper class for array operations.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class ArrayUtils
{
    /**
     * @returns true, if both float arrays are either the same instance (or both null),
     * or the same langth and all elements are equal.
     * 
     * @param a
     * @param b
     */
    public static final boolean equals( float[] a, float[] b )
    {
        if ( a == b )
            return( true );
        
        if ( a == null )
            return( false );
        
        if ( b == null )
            return( false );
        
        if ( a.length != b.length )
            return( false );
        
        for ( int i = 0; i < a.length; i++ )
        {
            if ( a[ i ] != b[ i ] )
                return( false );
        }
        
        return( true );
    }
}

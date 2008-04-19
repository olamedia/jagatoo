/**
 * Copyright (c) 2007-2008, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
package org.jagatoo.util.arrays;

import java.util.Arrays;

/**
 * Helper class for array operations.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class ArrayUtils
{
    /**
     * @return true, if both float arrays are either the same instance (or both null),
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
    
    /**
     * Dumps the contents of the given array to stdout.
     * 
     * @param array
     * @param maxElemsPerLine
     */
    public static final void dumpArray( final float[] array, final int maxElemsPerLine )
    {
        if ( array == null )
        {
            System.out.println( (String)null );
            return;
        }
        
        int line = 0;
        
        System.out.print( "[ " );
        for ( int i = 0; i < array.length; i++ )
        {
            if ( ( i > 0 ) && ( ( i % maxElemsPerLine ) == 0 ) )
            {
                System.out.println();
                System.out.print( "  " );
                line++;
            }
            
            System.out.print( array[ i ] );
            
            if ( i < array.length - 1 )
            {
                System.out.print( ", " );
            }
        }
        
        if ( line > 0 )
        {
            System.out.println();
            System.out.println( "]" );
        }
        else
        {
            System.out.println( " ]" );
        }
    }
    
    /**
     * Dumps the contents of the given array to stdout (in one line).
     * 
     * @param array
     */
    public static final void dumpArray( final float[] array )
    {
        dumpArray( array, Integer.MAX_VALUE );
    }
    
    /**
     * Ensures, the given int array has the desired length.<br>
     * <b>The ensured array is returned!</b>
     * 
     * @param array the input array
     * @param minCapacity the desired (minimal) capacity
     * 
     * @return the array with the ensured length
     */
    public static final int[] ensureCapacity( int[] array, int minCapacity )
    {
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final int[] oldArray = array;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            array = new int[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
        }
        
        return( array );
    }
    
    /**
     * Ensures, the given int array has the desired length.<br>
     * <b>The ensured array is returned!</b>
     * 
     * @param array the input array
     * @param minCapacity the desired (minimal) capacity
     * @param paddValue the value to be written to appended elements
     * 
     * @return the array with the ensured length
     */
    public static final int[] ensureCapacity( int[] array, int minCapacity, int paddValue )
    {
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final int[] oldArray = array;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            array = new int[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
            Arrays.fill( array, oldCapacity, newCapacity - 1, paddValue );
        }
        
        return( array );
    }
    
    /**
     * Ensures, the given int array has the desired length.<br>
     * <b>The ensured array is returned!</b>
     * 
     * @param array the input array
     * @param minCapacity the desired (minimal) capacity
     * 
     * @return the array with the ensured length
     */
    public static final long[] ensureCapacity( long[] array, int minCapacity )
    {
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final long[] oldArray = array;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            array = new long[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
        }
        
        return( array );
    }
    
    /**
     * Ensures, the given int array has the desired length.<br>
     * <b>The ensured array is returned!</b>
     * 
     * @param array the input array
     * @param minCapacity the desired (minimal) capacity
     * @param paddValue the value to be written to appended elements
     * 
     * @return the array with the ensured length
     */
    public static final long[] ensureCapacity( long[] array, int minCapacity, int paddValue )
    {
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final long[] oldArray = array;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            array = new long[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
            Arrays.fill( array, oldCapacity, newCapacity - 1, paddValue );
        }
        
        return( array );
    }
    
    /**
     * Ensures, the given int array has the desired length.<br>
     * <b>The ensured array is returned!</b>
     * 
     * @param array the input array
     * @param minCapacity the desired (minimal) capacity
     * 
     * @return the array with the ensured length
     */
    public static final float[] ensureCapacity( float[] array, int minCapacity )
    {
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final float[] oldArray = array;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            array = new float[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
        }
        
        return( array );
    }
    
    /**
     * Ensures, the given int array has the desired length.<br>
     * <b>The ensured array is returned!</b>
     * 
     * @param array the input array
     * @param minCapacity the desired (minimal) capacity
     * @param paddValue the value to be written to appended elements
     * 
     * @return the array with the ensured length
     */
    public static final float[] ensureCapacity( float[] array, int minCapacity, int paddValue )
    {
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final float[] oldArray = array;
            final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            array = new float[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
            Arrays.fill( array, oldCapacity, newCapacity - 1, paddValue );
        }
        
        return( array );
    }
    
    /**
     * Checks, if the specified array contains the specified element.
     * 
     * @param array the array to check
     * @param element the element to search
     * @param strict if <code>true</code>, a == check is used to identify the element, otherwise, the equals method is used.
     * 
     * @return <code>true</code>, if the array contains the specified element.
     */
    public static final boolean contains( Object[] array, Object element, boolean strict )
    {
        for ( int i = 0; i < array.length; i++ )
        {
            if ( strict || ( array[ i ] == null ) )
            {
                if ( array[ i ] == element )
                    return( true );
            }
            else if ( array[ i ].equals( element ) )
            {
                return( true );
            }
        }
        
        return( false );
    }
    
    /**
     * Searches the specified element inside the specified array.
     * 
     * @param array the array to check
     * @param element the element to search
     * @param strict if <code>true</code>, a == check is used to identify the element, otherwise, the equals method is used.
     * 
     * @return the element's index within the array or -1, if the array does not contain the specified element.
     */
    public static final int indexOf( Object[] array, Object element, boolean strict )
    {
        for ( int i = 0; i < array.length; i++ )
        {
            if ( strict || ( array[ i ] == null ) )
            {
                if ( array[ i ] == element )
                    return( i );
            }
            else if ( array[ i ].equals( element ) )
            {
                return( i );
            }
        }
        
        return( -1 );
    }
    
    private ArrayUtils()
    {
    }
}

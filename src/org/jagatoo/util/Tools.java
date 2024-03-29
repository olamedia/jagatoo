/**
 * Copyright (c) 2007-2011, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.util;

/**
 * General utility methods.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Tools
{
    public static final long KILO_BYTE = 1024L;
    public static final long MEGA_BYTE = KILO_BYTE * 1024L;
    public static final long GIGA_BYTE = MEGA_BYTE * 1024L;
    public static final long TERA_BYTE = GIGA_BYTE * 1024L;
    
    public static final String formatBytes( long bytes )
    {
        //final double factor = 1024.0;
        final double factor = 1000.0;
        
        if ( bytes >= TERA_BYTE )
        {
            return ( String.valueOf( Math.round( bytes * factor / TERA_BYTE ) / factor ) + " TB" );
        }
        
        if ( bytes >= GIGA_BYTE )
        {
            return ( String.valueOf( Math.round( bytes * factor / GIGA_BYTE ) / factor ) + " GB" );
        }
        
        if ( bytes >= MEGA_BYTE )
        {
            return ( String.valueOf( Math.round( bytes * factor / MEGA_BYTE ) / factor ) + " MB" );
        }
        
        if ( bytes >= KILO_BYTE )
        {
            return ( String.valueOf( Math.round( bytes * factor / KILO_BYTE ) / factor ) + " KB" );
        }
        
        return ( String.valueOf( bytes ) + " bytes" );
    }
    
    public static final Number getNumber( String string )
    {
        try
        {
            return ( Integer.parseInt( string ) );
        }
        catch ( NumberFormatException e )
        {
            try
            {
                return ( Double.parseDouble( string ) );
            }
            catch ( NumberFormatException e2 )
            {
                return ( null );
            }
        }
    }
    
    public static final boolean objectsEqual( Object o1, Object o2 )
    {
        if ( o1 == o2 )
            return ( true );
        
        if ( ( o1 == null ) && ( o2 != null ) )
            return ( false );
        
        if ( ( o1 != null ) && ( o2 == null ) )
            return ( false );
        
        return ( o1.equals( o2 ) );
    }
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public static final int compareObjects( Comparable o1, Comparable o2 )
    {
        if ( o1 == o2 )
            return ( 0 );
        
        if ( o1 == null )
            return ( -1 );
        
        if ( o2 == null )
            return ( +1 );
        
        return ( o1.compareTo( o2 ) );
    }
    
    public static final String padLeft( int number, int length, String padStr )
    {
        String s = String.valueOf( number );
        
        while ( s.length() < length )
            s = padStr + s;
        
        return ( s );
    }
    
    public static final boolean sleep( long milliseconds )
    {
        try
        {
            Thread.sleep( milliseconds );
            
            return ( true );
        }
        catch ( InterruptedException e )
        {
            return ( false );
        }
    }
    
    public static final boolean sleep( long milliseconds, int nanos )
    {
        try
        {
            Thread.sleep( milliseconds, nanos );
            
            return ( true );
        }
        catch ( InterruptedException e )
        {
            return ( false );
        }
    }
}

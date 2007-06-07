/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.datatypes;

import org.jagatoo.util.heaps.CoordHeap;
import org.openmali.vecmath.Tuple3d;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3d;
import org.openmali.vecmath.Vector3f;

/**
 * A Coord3f combines the functionality of a vector with the functionality of a
 * point. Transformations on a Coord3f are as a point, so translation is taken
 * into account. You can also find the distance of one Coord3f to another. <p/>
 * Originally Coded by David Yazel on Dec 21, 2003 at 12:05:55 AM.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class Coord3f extends Vector3f
{
    private static final long serialVersionUID = -395810789638724215L;
    
    public Coord3f()
    {
    }
    
    public Coord3f( float v, float v1, float v2 )
    {
        super( v, v1, v2 );
    }
    
    public Coord3f( float[] floats )
    {
        super( floats );
    }
    
    public Coord3f( Tuple3d tuple3d )
    {
        super( tuple3d );
    }
    
    public Coord3f( Tuple3f tuple3f )
    {
        super( tuple3f );
    }
    
    public Coord3f( Vector3d vector3d )
    {
        super( (float)vector3d.x, (float)vector3d.y, (float)vector3d.z );
    }
    
    public Coord3f( Vector3f vector3f )
    {
        super( vector3f );
    }
    
    public Coord3f( Coord3f coord )
    {
        super( coord.x, coord.y, coord.z );
    }
    
    public void set( Coord3f coord )
    {
        set( coord.x, coord.y, coord.z );
    }
    
    public void get( Coord3f buffer )
    {
        buffer.x = this.x;
        buffer.y = this.y;
        buffer.z = this.z;
    }
    
    public float distanceSquared( Tuple3f a, Tuple3f b )
    {
        float dx = a.x - b.x;
        float dy = a.y - b.y;
        float dz = a.z - b.z;
        
        return( (dx * dx) + (dy * dy) + (dz * dz) );
    }
    
    public float distanceSquared( Tuple3f a )
    {
        float dx = x - a.x;
        float dy = y - a.y;
        float dz = z - a.z;
        
        return( (dx * dx) + (dy * dy) + (dz * dz) );
    }
    
    public float distance( Tuple3f a )
    {
        
        return( (float)Math.sqrt( distanceSquared( a ) ) );
        
    }
    
    public float distance( Tuple3f a, Tuple3f b )
    {
        return( (float)Math.sqrt( distanceSquared( a, b ) ) );
    }
    
    public static Coord3f subNew( Coord3f c1, Coord3f c2 )
    {
        Coord3f c = CoordHeap.alloc();
        c.sub( c1, c2 );
        
        return( c );
    }
    
    public static Coord3f crossNew( Coord3f c1, Coord3f c2 )
    {
        Coord3f c = CoordHeap.alloc();
        c.cross( c1, c2 );
        
        return( c );
    }
    
    public static Coord3f scaleNew( Coord3f c1, float scale )
    {
        Coord3f c = CoordHeap.alloc();
        c.set( (Tuple3f)c1 );
        c.scale( scale );
        
        return( c );
    }
}

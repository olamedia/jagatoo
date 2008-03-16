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
/**
 * Copyright (c) 2006 KProject
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'KProject' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jagatoo.loaders.models.md5.reader;

import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;

/**
 * @author kman
 */
public class MD5MathUtil
{
    public static void Quat_normalize( Quaternion4f q )
    {
        // compute magnitude of the quaternion
        float mag = FastMath.sqrt( ( q.getA() * q.getA() ) + ( q.getB() * q.getB() ) + ( q.getC() * q.getC() ) + ( q.getD() * q.getD() ) );
        
        //check for bogus length, to protect against divide by zero
        if ( mag > 0.0f )
        {
            // normalize it
            float oneOverMag = 1.0f / mag;
            
            q.mul( oneOverMag );
        }
    }
    
    public static Quaternion4f Quat_multTuple( Quaternion4f q, Tuple3f t )
    {
        Quaternion4f out = new Quaternion4f();
        out.setD( -( q.getA() * t.getX() ) - ( q.getB() * t.getY() ) - ( q.getC() * t.getZ() ) );
        out.setA( ( q.getD() * t.getX() ) + ( q.getB() * t.getZ() ) - ( q.getC() * t.getY() ) );
        out.setB( ( q.getD() * t.getY() ) + ( q.getC() * t.getX() ) - ( q.getA() * t.getZ() ) );
        out.setC( ( q.getD() * t.getZ() ) + ( q.getA() * t.getY() ) - ( q.getB() * t.getX() ) );
        
        return( out );
    }
    
    public static Quaternion4f Quat_multQuat( Quaternion4f qa, Quaternion4f qb )
    {
        Quaternion4f out = new Quaternion4f();
        out.setD( ( qa.getD() * qb.getD() ) - ( qa.getA() * qb.getA() ) - ( qa.getB() * qb.getB() ) - ( qa.getC() * qb.getC() ) );
        out.setA( ( qa.getA() * qb.getD() ) + ( qa.getD() * qb.getA() ) + ( qa.getB() * qb.getC() ) - ( qa.getC() * qb.getB() ) );
        out.setB( ( qa.getB() * qb.getD() ) + ( qa.getD() * qb.getB() ) + ( qa.getC() * qb.getA() ) - ( qa.getA() * qb.getC() ) );
        out.setC( ( qa.getC() * qb.getD() ) + ( qa.getD() * qb.getC() ) + ( qa.getA() * qb.getB() ) - ( qa.getB() * qb.getA() ) );
        
        return( out );
    }
    
    public static Point3f Quat_rotatePoint( Quaternion4f q, Tuple3f in )
    {
        Point3f out = new Point3f();
        Quaternion4f tmp = new Quaternion4f();
        Quaternion4f inv = new Quaternion4f();
        Quaternion4f final1 = new Quaternion4f();
        
        inv.setA( -q.getA() );
        inv.setB( -q.getB() );
        inv.setC( -q.getC() );
        inv.setD( q.getD() );
        
        Quat_normalize( inv );
        
        tmp = Quat_multTuple( q, in );
        final1 = Quat_multQuat( tmp, inv );
        
        out.setX( final1.getA() );
        out.setY( final1.getB() );
        out.setZ( final1.getC() );
        
        return( out );
    }
    
    public static void Quat_computeD( Quaternion4f q )
    {
        float t = 1.0f - ( q.getA() * q.getA() ) - ( q.getB() * q.getB() ) - ( q.getC() * q.getC() );
        if ( t < 0.0f )
            q.setD( 0.0f );
        else
            q.setD( -FastMath.sqrt( t ) );
    }
    
    public static Quaternion4f toQuaternion( float x, float y, float z )
    {
        float term = 1.0f - x * x - y * y - z * z;
        float w;
        if ( term < 0 )
            w = 0.0f;
        else
            w = -FastMath.sqrt( term );
        
        return( new Quaternion4f( x, y, z, w ) );
    }
    
    public static Quaternion4f toQuaternion( Tuple3f tuple )
    {
        return( toQuaternion( tuple.getX(), tuple.getY(), tuple.getZ() ) );
    }
}

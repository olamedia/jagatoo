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
package org.jagatoo.spatial.bodies;

import org.jagatoo.datatypes.Ray3f;
import org.jagatoo.util.heaps.PointHeap;
import org.jagatoo.util.heaps.VectorHeap;
import org.jagatoo.util.math.TupleUtil;
import org.openmali.FastMath;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;

/**
 * This class provides static methods to test different bodies for intersection.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class IntersectionFactory
{
    /**
     * Tests two Spheres for intersection.
     * 
     * @param x1
     * @param y1
     * @param z1
     * @param r1
     * @param x2
     * @param y2
     * @param z2
     * @param r2
     * 
     * @return true, if the two Bodies intersect
     */
    public static boolean sphereIntersectsSphere( float x1, float y1, float z1, float r1,
                                                  float x2, float y2, float z2, float r2 )
    {
        final float dx = x1 - x2;
        final float dy = y1 - y2;
        final float dz = z1 - z2;
        final float d_sq = (dx * dx) + (dy * dy) + (dz * dz);
        
        if ( d_sq > ( (r1 + r2) * (r1 + r2) ) )
            return( false );
        else
            return( true );
    }
    
    /**
     * Tests two Spheres for intersection.
     * 
     * @param sphere1
     * @param x2
     * @param y2
     * @param z2
     * @param r2
     * 
     * @return true, if the two Bodies intersect
     */
    public static boolean sphereIntersectsSphere( Sphere sphere1, float x2, float y2, float z2, float r2 )
    {
        return( sphereIntersectsSphere( sphere1.getCenterX(), sphere1.getCenterY(), sphere1.getCenterZ(), sphere1.getRadius(),
                                        x2, y2, z2, r2 ) );
    }
    
    /**
     * Tests two Spheres for intersection.
     * 
     * @param sphere1
     * @param sphere2
     * 
     * @return true, if the two Bodies intersect
     */
    public static boolean sphereIntersectsSphere( Sphere sphere1, Sphere sphere2 )
    {
        return( sphereIntersectsSphere( sphere1.getCenterX(), sphere1.getCenterY(), sphere1.getCenterZ(), sphere1.getRadius(),
                                        sphere2.getCenterX(), sphere2.getCenterY(), sphere2.getCenterZ(), sphere2.getRadius() ) );
    }
    
    private static float distance( float point, float min, float max )
    {
        if ( point < min )
            return( min - point );
        else if ( point > max )
            return( point - max );
        else
            return( 0 );
    }
    
    /**
     * Tests a Sphere and a Box for intersection.
     * 
     * @param sphereX
     * @param sphereY
     * @param sphereZ
     * @param sphereR
     * @param boxLowerX
     * @param boxLowerY
     * @param boxLowerZ
     * @param boxUpperX
     * @param boxUpperY
     * @param boxUpperZ
     * 
     * @return true, if the two Bodies intersect
     */
    public static final boolean sphereIntersectsBox( float sphereX, float sphereY, float sphereZ, float sphereR,
                                                     float boxLowerX, float boxLowerY, float boxLowerZ,
                                                     float boxUpperX, float boxUpperY, float boxUpperZ )
    {
        float d = 0.0f;
        float s;
        
        //find the square of the distance from the sphere to the box...
        
        s = distance( sphereX, boxLowerX, boxUpperX );
        d += s * s;
        
        s = distance( sphereY, boxLowerY, boxUpperY );
        d += s * s;
        
        s = distance( sphereZ, boxLowerZ, boxUpperZ );
        d += s * s;
        
        return( d <= sphereR * sphereR );
    }
    
    /**
     * Tests a Sphere and a Box for intersection.
     * 
     * @param sphereX
     * @param sphereY
     * @param sphereZ
     * @param sphereR
     * @param boxLower
     * @param boxUpper
     * 
     * @return true, if the two Bodies intersect
     */
    public static final boolean sphereIntersectsBox( float sphereX, float sphereY, float sphereZ, float sphereR,
                                                     Tuple3f boxLower, Tuple3f boxUpper )
    {
        return( sphereIntersectsBox( sphereX, sphereY, sphereZ, sphereR, boxLower.x, boxLower.y, boxLower.z, boxUpper.x, boxUpper.y, boxUpper.z ) );
    }
    
    /**
     * Tests a Sphere and a Box for intersection.
     * 
     * @param sphere
     * @param boxLower
     * @param boxUpper
     * 
     * @return true, if the two Bodies intersect
     */
    public static final boolean sphereIntersectsBox( Sphere sphere, Tuple3f boxLower, Tuple3f boxUpper )
    {
        return( sphereIntersectsBox( sphere, boxLower, boxUpper ) );
    }
    
    /**
     * Tests a Sphere and a Box for intersection.
     * 
     * @param sphereX
     * @param sphereY
     * @param sphereZ
     * @param sphereR
     * @param box
     * 
     * @return true, if the two Bodies intersect
     */
    public static final boolean sphereIntersectsBox( float sphereX, float sphereY, float sphereZ, float sphereR, Box box )
    {
        return( sphereIntersectsBox( sphereX, sphereY, sphereZ, sphereR, box.getLower(), box.getUpper() ) );
    }
    
    /**
     * Tests a Sphere and a Box for intersection.
     * 
     * @param sphere
     * @param box
     * 
     * @return true, if the two Bodies intersect
     */
    public static final boolean sphereIntersectsBox( Sphere sphere, Box box )
    {
        return( sphereIntersectsBox( sphere, box.getLower(), box.getUpper() ) );
    }
    
    /**
     * Tests two Boxes for intersection.
     * 
     * @param lowerX1
     * @param lowerY1
     * @param lowerZ1
     * @param upperX1
     * @param upperY1
     * @param upperZ1
     * @param lowerX2
     * @param lowerY2
     * @param lowerZ2
     * @param upperX2
     * @param upperY2
     * @param upperZ2
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsBox( float lowerX1, float lowerY1, float lowerZ1,
                                            float upperX1, float upperY1, float upperZ1,
                                            float lowerX2, float lowerY2, float lowerZ2,
                                            float upperX2, float upperY2, float upperZ2 )
    {
        if ( upperX1 < lowerX2 )
            return( false );
        
        if ( upperY1 < lowerY2 )
            return( false );
        
        if ( upperZ1 < lowerZ2 )
            return( false );
        
        if ( upperX2 < lowerX1 )
            return( false );
        
        if ( upperY2 < lowerY1 )
            return( false );
        
        if ( upperZ2 < lowerZ1 )
            return( false );
        
        return( true );
    }
    
    /**
     * Tests two Boxes for intersection.
     * 
     * @param lower1
     * @param upper1
     * @param lower2
     * @param upper2
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsBox( Tuple3f lower1, Tuple3f upper1, Tuple3f lower2, Tuple3f upper2 )
    {
        return( boxIntersectsBox( lower1.x, lower1.y, lower1.z, upper1.x, upper1.y, upper1.z,
                                  lower2.x, lower2.y, lower2.z, upper2.x, upper2.y, upper2.z ) );
    }
    
    /**
     * Tests two Boxes for intersection.
     * 
     * @param box1
     * @param box2
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsBox( Box box1, Box box2 )
    {
        return( boxIntersectsBox( box1.getLower(), box1.getUpper(), box2.getLower(), box2.getUpper() ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param rayOrigin
     * @param rayDirection
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRay( Tuple3f sphereCenter, float sphereRadius,
                                               Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection )
    {
        /*
         * ray-sphere intersection test from Graphics Gems p.388
         * TODO: There is a bug in this Graphics Gem. If the origin of the ray
         *       is *inside* the sphere being tested, it reports the wrong
         *       intersection location. This code has a fix for the bug.
         */
        
        // notation:
        // point E  = rayOrigin
        // point O  = sphere center
        Vector3f EO = VectorHeap.alloc();
        EO.set( sphereCenter );
        EO.sub( rayOrigin );
        
        Vector3f V = VectorHeap.alloc();
        V.set( rayDirection );
        
        float dist2 = (EO.x * EO.x) + (EO.y * EO.y) + (EO.z * EO.z);
        
        final float radius_sq = sphereRadius * sphereRadius;
        
        // Bug Fix For Gem, if origin is *inside* the sphere, invert the
        // direction vector so that we get a valid intersection location.
        if ( dist2 < radius_sq )
        {
            V.negate();
        }
        
        float v = EO.dot( V );
        
        final float dist = radius_sq - ( EO.lengthSquared() - ( v * v ) );
        
        boolean result = false;
        
        if ( dist > 0.0f )
        {
            if ( intersection != null )
            {
                float d = FastMath.sqrt( dist );
                dist2 = TupleUtil.distanceSquared( rayOrigin, sphereCenter );
                V.scale( v - d );
                intersection.add( rayOrigin, V );
            }
            
            result = true;
        }
        
        VectorHeap.free( V );
        VectorHeap.free( EO );
        
        return( result );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param sphereCenter
     * @param sphereRadius
     * @param rayOrigin
     * @param rayDirection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRay( Tuple3f sphereCenter, float sphereRadius, Point3f rayOrigin, Vector3f rayDirection )
    {
        return( sphereIntersectsRay( sphereCenter, sphereRadius, rayOrigin, rayDirection, null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param sphereCenter
     * @param sphereRadius
     * @param ray
     * @param intersect
     * 
     * @return true for an intersection
     */
    public static boolean intersectsRay( Tuple3f sphereCenter, float sphereRadius, Ray3f ray, Tuple3f intersect )
    {
        return( sphereIntersectsRay( sphereCenter, sphereRadius, ray.getOrigin(), ray.getDirection(), intersect ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param sphere
     * @param rayOrigin
     * @param rayDirection
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRay( Sphere sphere, Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection )
    {
        return( sphereIntersectsRay( sphere.getCenter(), sphere.getRadius(), rayOrigin, rayDirection, intersection ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param sphere
     * @param rayOrigin
     * @param rayDirection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRay( Sphere sphere, Point3f rayOrigin, Vector3f rayDirection )
    {
        return( sphereIntersectsRay( sphere, rayOrigin, rayDirection, null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param sphere
     * @param ray
     * @param intersect
     * 
     * @return true for an intersection
     */
    public static boolean intersectsRay( Sphere sphere, Ray3f ray, Tuple3f intersect )
    {
        return( sphereIntersectsRay( sphere, ray.getOrigin(), ray.getDirection(), intersect ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere.
     * 
     * @param sphere
     * @param ray
     * 
     * @return true for an intersection
     */
    public static boolean intersectsRay( Sphere sphere, Ray3f ray )
    {
        return( sphereIntersectsRay( sphere, ray.getOrigin(), ray.getDirection(), null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphereCenter
     * @param sphereRadius
     * @param rayOrigin
     * @param rayDirection
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Tuple3f sphereCenter, float sphereRadius, Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection )
    {
        Vector3f sect = VectorHeap.alloc();
        boolean hit = sphereIntersectsRay( sphereCenter, sphereRadius, rayOrigin, rayDirection, sect );
        
        boolean result = false;
        
        if (hit)
        {
            Vector3f dir = VectorHeap.alloc();
            dir.sub( sect, rayOrigin );
            
            float dot = dir.dot( rayDirection );
            
            if ( dot >= 0 )
            { // then it's in front!
                if ( intersection != null )
                {
                    intersection.set( sect );
                }
                
                result = true;
            }
            
            VectorHeap.free( dir );
        }
        
        VectorHeap.free( sect );
        
        return( result );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphereCenter
     * @param sphereRadius
     * @param ray
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Tuple3f sphereCenter, float sphereRadius, Ray3f ray, Tuple3f intersection )
    {
        return( sphereIntersectsRayInFront( sphereCenter, sphereRadius, ray.getOrigin(), ray.getDirection(), intersection ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphereCenter
     * @param sphereRadius
     * @param rayOrigin
     * @param rayDirection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Tuple3f sphereCenter, float sphereRadius, Point3f rayOrigin, Vector3f rayDirection )
    {
        return( sphereIntersectsRayInFront( sphereCenter, sphereRadius, rayOrigin, rayDirection, null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @return true for an intersection
     * 
     * @param sphereCenter
     * @param sphereRadius
     * @param ray
     */
    public static boolean sphereIntersectsRayInFront( Tuple3f sphereCenter, float sphereRadius, Ray3f ray )
    {
        return( sphereIntersectsRayInFront( sphereCenter, sphereRadius, ray, null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphere
     * @param rayOrigin
     * @param rayDirection
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Sphere sphere, Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection )
    {
        return( sphereIntersectsRayInFront( sphere.getCenter(), sphere.getRadius(), rayOrigin, rayDirection, null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphere
     * @param ray
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Sphere sphere, Ray3f ray, Tuple3f intersection )
    {
        return( sphereIntersectsRayInFront( sphere, ray.getOrigin(), ray.getDirection(), intersection ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphere
     * @param rayOrigin
     * @param rayDirection
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Sphere sphere, Point3f rayOrigin, Vector3f rayDirection )
    {
        return( sphereIntersectsRayInFront( sphere, rayOrigin, rayDirection, null ) );
    }
    
    /**
     * Does a ray intersection test with a Sphere and checks,
     * if the intersection is in front.
     * 
     * @param sphere
     * @param ray
     * 
     * @return true for an intersection
     */
    public static boolean sphereIntersectsRayInFront( Sphere sphere, Ray3f ray )
    {
        return( sphereIntersectsRayInFront( sphere, ray, null ) );
    }
    
    private static final float EPSILON_ANGLE = 0.00001f;
    
    // min is inside x, max is inside y
    private static boolean boxIntersectsRayStep( Tuple3f xMin_yMax, float vd, float vn )
    {
        if ( Math.abs( vd ) < EPSILON_ANGLE )
        {
            if ( vn > 0.0f )
                return( false );
            return( true ); // Direction is parallel to the slab (no intersection)
        }
        else
        {
            /* ray not parallel - get distance to plane */
            float t = -vn / vd;
            
            if ( vd < 0.0f )
            {
                /* front face - T is a near point */
                if ( t > xMin_yMax.y ) 
                    return( false );
                if ( t > xMin_yMax.x )
                {
                    /* hit near face */
                    xMin_yMax.x = t ;
                }
            }
            else
            {
                /* back face - T is a far point */
                if ( t < xMin_yMax.x ) 
                    return( false );
                if ( t < xMin_yMax.y )
                {
                    /* hit far face */
                    xMin_yMax.y = t;
                }
            }
        }
        
        return( true );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLowerX
     * @param boxLowerY
     * @param boxLowerZ
     * @param boxUpperX
     * @param boxUpperY
     * @param boxUpperZ
     * @param origin
     * @param dir
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( float boxLowerX, float boxLowerY, float boxLowerZ,
                                            float boxUpperX, float boxUpperY, float boxUpperZ,
                                            Point3f origin, Vector3f dir, Tuple3f intersection )
    {
        Point3f tmp = PointHeap.alloc();
        
        tmp.x = Float.NEGATIVE_INFINITY;
        tmp.y = Float.POSITIVE_INFINITY;
        
        boolean hit = boxIntersectsRayStep( tmp, -dir.x, boxLowerX - origin.x ) &&
                      boxIntersectsRayStep( tmp, -dir.y, boxLowerY - origin.y ) &&
                      boxIntersectsRayStep( tmp, -dir.z, boxLowerZ - origin.z ) &&
                      boxIntersectsRayStep( tmp, +dir.x, origin.x - boxUpperX ) &&
                      boxIntersectsRayStep( tmp, +dir.y, origin.y - boxUpperY ) &&
                      boxIntersectsRayStep( tmp, +dir.z, origin.z - boxUpperZ );
        
        float tnear = tmp.x;
        float tfar = tmp.y;
        PointHeap.free( tmp );
        
        if ( !hit )
            return( false );
        
        float tresult = 0.0f;
        if ( tnear >= 0.0f )
        {
            /* outside, hitting front face */
            tresult = tnear;
        }
        else if ( tfar >= 0.0f )
        {
            /* inside, hitting back face */
            tresult = tfar;
        }
        else
        {
            return( false );
        }
        
        if ( intersection != null )
        {
            intersection.scaleAdd( tresult, dir, origin );
        }
        
        return( true );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLowerX
     * @param boxLowerY
     * @param boxLowerZ
     * @param boxUpperX
     * @param boxUpperY
     * @param boxUpperZ
     * @param ray
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( float boxLowerX, float boxLowerY, float boxLowerZ,
                                            float boxUpperX, float boxUpperY, float boxUpperZ,
                                            Ray3f ray, Tuple3f intersection )
    {
        return( boxIntersectsRay( boxLowerX, boxLowerY, boxLowerZ,
                                  boxUpperX, boxUpperY, boxUpperZ,
                                  ray.getOrigin(), ray.getDirection(), intersection ) );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLower
     * @param boxUpper
     * @param origin
     * @param dir
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( Tuple3f boxLower, Tuple3f boxUpper,
                                            Point3f origin, Vector3f dir, Tuple3f intersection )
    {
        return( boxIntersectsRay( boxLower.x, boxLower.x, boxLower.z,
                                  boxUpper.x, boxUpper.y, boxUpper.z,
                                  origin, dir, intersection ) );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLower
     * @param boxUpper
     * @param ray
     * @param intersection
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( Tuple3f boxLower, Tuple3f boxUpper,
                                            Ray3f ray, Tuple3f intersection )
    {
        return( boxIntersectsRay( boxLower, boxUpper,
                                  ray.getOrigin(), ray.getDirection(), intersection ) );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLowerX
     * @param boxLowerY
     * @param boxLowerZ
     * @param boxUpperX
     * @param boxUpperY
     * @param boxUpperZ
     * @param origin
     * @param dir
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( float boxLowerX, float boxLowerY, float boxLowerZ,
                                            float boxUpperX, float boxUpperY, float boxUpperZ,
                                            Point3f origin, Vector3f dir )
    {
        return( boxIntersectsRay( boxLowerX, boxLowerY, boxLowerZ, boxUpperX, boxUpperY, boxUpperZ, origin, dir, null ) );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLowerX
     * @param boxLowerY
     * @param boxLowerZ
     * @param boxUpperX
     * @param boxUpperY
     * @param boxUpperZ
     * @param ray
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( float boxLowerX, float boxLowerY, float boxLowerZ,
                                            float boxUpperX, float boxUpperY, float boxUpperZ,
                                            Ray3f ray )
    {
        return( boxIntersectsRay( boxLowerX, boxLowerY, boxLowerZ,
                                  boxUpperX, boxUpperY, boxUpperZ,
                                  ray.getOrigin(), ray.getDirection() ) );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLower
     * @param boxUpper
     * @param origin
     * @param dir
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( Tuple3f boxLower, Tuple3f boxUpper,
                                            Point3f origin, Vector3f dir )
    {
        return( boxIntersectsRay( boxLower.x, boxLower.x, boxLower.z,
                                  boxUpper.x, boxUpper.y, boxUpper.z,
                                  origin, dir ) );
    }
    
    /**
     * Tests a Box for intersection with a Ray.
     * 
     * @param boxLower
     * @param boxUpper
     * @param ray
     * 
     * @return true for an intersection
     */
    public static boolean boxIntersectsRay( Tuple3f boxLower, Tuple3f boxUpper, Ray3f ray )
    {
        return( boxIntersectsRay( boxLower, boxUpper,
                                  ray.getOrigin(), ray.getDirection() ) );
    }
    
    public static boolean convexHullIntersectsRay( ConvexHull hull, Point3f origin, Vector3f dir, Tuple3f intersection )
    {
        float tfar = Float.POSITIVE_INFINITY;
        float tnear = Float.NEGATIVE_INFINITY;
        
        for ( int i=0; i< hull.slabs.length; ++i )
        { 
            float vd = hull.slabs[ i ].getNormal().dot( dir );
            float vn = hull.slabs[ i ].distanceTo( origin );
            if ( Math.abs( vd ) < EPSILON_ANGLE )
            {
                if (vn > 0)
                    return( false );
                return( true ); // Direction is parallel to the slab (no intersection)
            }
            else
            {
                /* ray not parallel - get distance to plane */
                float t = -vn/vd;
                
                if ( vd < 0.0f )
                {
                    /* front face - T is a near point */
                    if ( t > tfar ) 
                        return( false );
                    if ( t > tnear )
                    {
                        /* hit near face */
                        tnear = t ;
                    }
                }
                else
                {
                    /* back face - T is a far point */
                    if ( t < tnear ) 
                        return( false );
                    if ( t < tfar )
                    {
                        /* hit far face */
                        tfar = t;
                    }
                }
            }
        }
        
        /* survived all tests */
        
        float tresult = 0;
        if ( tnear >= 0.0f )
        {
            /* outside, hitting front face */
            tresult = tnear;
        }
        else if ( tfar >= 0.0f )
        {
            /* inside, hitting back face */
            tresult = tfar;
        }
        else
        {
            return( false );
        }
        
        if ( intersection != null )
            intersection.scaleAdd( tresult, dir, origin );
        
        return( true );
    }
    
    public static boolean convexHullIntersectsRay( ConvexHull hull, Ray3f ray, Tuple3f intersection )
    {
        return( convexHullIntersectsRay( hull, ray.getOrigin(), ray.getDirection(), intersection ) );
    }
}

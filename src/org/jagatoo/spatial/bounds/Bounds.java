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
package org.jagatoo.spatial.bounds;

import java.util.List;

import org.jagatoo.datatypes.Ray3f;
import org.jagatoo.spatial.VertexContainer;
import org.jagatoo.spatial.bodies.Body;
import org.openmali.vecmath.Matrix4f;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;

/**
 * Interface base for Bounds.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public interface Bounds extends Body
{
    /**
     * Tests for intersection with a ray.
     * 
     * @param rayOrigin
     * @param rayDirection
     * @param intersection
     */
    public abstract boolean intersects( Point3f rayOrigin, Vector3f rayDirection, Tuple3f intersection );
    
    /**
     * Tests for intersection with a ray.
     * 
     * @param ray
     * @param intersection
     */
    public abstract boolean intersects( Ray3f ray, Tuple3f intersection );
    
    /**
     * Tests for intersection with a ray.
     * 
     * @param rayOrigin
     * @param rayDirection
     */
    public abstract boolean intersects( Point3f rayOrigin, Vector3f rayDirection );
    
    /**
     * Tests for intersection with a ray.
     * 
     * @param ray
     */
    public abstract boolean intersects( Ray3f ray );
    
    /**
     * Tests for intersection with another Bounds object.
     * 
     * @param bounds
     */
    public abstract boolean intersects( Bounds bounds );
    
    /**
     * Tests for intersection with an array of Bounds objects.
     * 
     * @param bos
     */
    public abstract boolean intersects( Bounds[] bos );
    
    /**
     * Finds closest bounding object that intersects this bounding object.
     * 
     * @param boundsObjects
     */
    public abstract Bounds closestIntersection( Bounds[] boundsObjects );
    
    /**
     * Transforms the Bounds object by the given transform.
     * 
     * @param trans
     */
    public abstract void transform( Matrix4f trans );
    
    /**
     * Transforms a Bounds object so that it bounds a volume that
     * is the result of transforming the given bounding object by
     * the given transform.
     * 
     * @param bounds
     * @param trans
     */
    public abstract void transform( Bounds bounds, Matrix4f trans );
    
    /**
     * Tests, if the given point is inside of these Bounds.
     * 
     * @param px
     * @param py
     * @param pz
     * 
     * @return true, if it is inside
     */
    public boolean contains( float px, float py, float pz );
    
    /**
     * Tests, if the given point is inside of these Bounds.
     * 
     * @param point
     * 
     * @return true, if it is inside
     */
    public boolean contains( Point3f point );
    
    /**
     * Combines this Body with a point.
     * 
     * @param point
     */
    public abstract void combine( float x, float y, float z );
    
    /**
     * Combines this Body with a point.
     * 
     * @param point
     */
    public abstract void combine( Point3f point );
    
    /**
     * Combine this Body with an array of points.
     * 
     * @param points
     */
    public abstract void combine( Point3f[] points );
    
    /**
     * Sets the the value of this Bounds object to
     * enclode the specified bounding object
     * 
     * @param boundsObject
     */
    public abstract void set( Bounds boundsObject );
    
    /**
     * Sets this bounds to the comnination of all the specified bounds.
     * Any current bounds information is replaced with this new combination
     * 
     * @param bounds
     */
    public abstract void set( Bounds[] bounds );
    
    /**
     * @param source
     */
    public void compute( final VertexContainer source );
    
    /**
     * @param coords
     */
    public void compute( final List<Tuple3f> coords );
    
    /**
     * @param coords
     */
    public void compute( final Tuple3f[] coords );
}

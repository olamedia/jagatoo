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

import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;
import org.openmali.vecmath.Vector3f;

/**
 * Simple ray implementation
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Ray3f
{
    // make this public for performance reasons
    public Point3f origin;
    
    // make this public for performance reasons
    public Vector3f direction;
    
    /**
     * @return the ray's origin
     */
    public Point3f getOrigin()
    {
        return( origin );
    }
    
    /**
     * Sets the ray's origin.
     * 
     * @param origin new origin
     */
    public void setOrigin( Tuple3f origin )
    {
        this.origin.x = origin.x;
        this.origin.y = origin.y;
        this.origin.z = origin.z;
    }
    
    /**
     * Sets the ray's origin.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setOrigin( float x, float y, float z )
    {
        this.origin.x = x;
        this.origin.y = y;
        this.origin.z = z;
    }
    
    /**
     * @return the ray's direction
     */
    public Vector3f getDirection()
    {
        return( direction );
    }
    
    /**
     * Sets the ray's direction.
     * 
     * @param direction new direction
     */
    public void setDirection( Tuple3f direction )
    {
        this.direction.x = direction.x;
        this.direction.y = direction.y;
        this.direction.z = direction.z;
    }
    
    /**
     * Sets the ray's direction.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setDirection( float x, float y, float z )
    {
        this.direction.x = x;
        this.direction.y = y;
        this.direction.z = z;
    }
    
    /**
     * @return the ray's length
     * <i>same as length()</i>
     */
    public float getLength()
    {
        return( direction.length() );
    }
    
    /**
     * @return the ray's length
     * <i>same as getLength()</i>
     */
    public float length()
    {
        return( direction.length() );
    }
    
    /**
     * Creates a clone.
     */
    @Override
    public Ray3f clone()
    {
        return( new Ray3f( this ) );
    }
    
    /**
     * Sets this ray to be equal to the passed one.
     */
    public void set( Ray3f ray )
    {
        origin.set( ray.origin );
        direction.set( ray.direction );
    }
    
    /**
     * Checks if the given ray equals this one.
     * 
     * @param ray the ray to test for equality
     */
    public boolean equals( Ray3f ray )
    {
        return( this.origin.equals( ray.origin ) && this.direction.equals( ray.direction ) );
    }
    
    /**
     * @return a String representation of this ray
     */
    @Override
    public String toString()
    {
        return( this.getClass().getName() + ": " +
                "origin=(" + origin.x + ", " + origin.y + ", " + origin.z + "), " +
                "direction=(" + direction.x + ", " + direction.y + ", " + direction.z + ")");
    }
    
    /**
     * Creates a new Ray3f.
     * 
     * @param origin the new origin point
     * @param direction the new direction vector
     */
    public Ray3f( Point3f origin, Vector3f direction )
    {
        this.origin = origin;
        this.direction = direction;
    }
    
    /**
     * Creates a new Ray3f.
     * 
     * @param origX
     * @param origY
     * @param origZ
     * @param direcX
     * @param direcY
     * @param direcZ
     */
    public Ray3f( float origX, float origY, float origZ, float direcX, float direcY, float direcZ )
    {
        this( new Point3f( origX, origY, origZ ), new Vector3f( direcX, direcY, direcZ ) );
    }
    
    /**
     * Creates a new Ray3f.
     */
    public Ray3f()
    {
        this( 0f, 0f, 0f, 0f, 0f, 0f );
    }
    
    /**
     * Clone constructor.
     */
    public Ray3f( Ray3f template )
    {
        this();
        
        this.set( template );
    }
}

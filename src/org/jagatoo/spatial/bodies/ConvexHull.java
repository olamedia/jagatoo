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

import org.jagatoo.util.errorhandling.UnsupportedFunction;
import org.openmali.vecmath.Point3f;

/**
 * @author YVG
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class ConvexHull implements Body
{
    protected Plane[] slabs;
    
    /**
     * test for intersection with a point
     */
    public boolean intersect( Point3f point )
    {
        for ( int i =0; i < slabs.length; i++ )
        {
            if ( slabs[ i ].distanceTo( point ) > 0 )
                return( false );
        }
        
        return( true );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean contains( float px, float py, float pz )
    {
        //return( Classifier.classifyConvexHullPoint( this, px, py, pz ) == Classifier.Classification.INSIDE );
        throw( new UnsupportedFunction() );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean contains( Point3f point )
    {
        return( contains( point.x, point.y, point.z ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Body body )
    {
        throw( new UnsupportedFunction() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Body[] bos )
    {
        throw( new UnsupportedFunction() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( float px, float py, float pz )
    {
        throw( new UnsupportedFunction() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Point3f point )
    {
        throw( new UnsupportedFunction() );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Point3f[] points )
    {
        throw( new UnsupportedFunction() );
    }
    
    public ConvexHull( Plane[] theSlabs )
    {
        super();
        
        slabs = theSlabs;
    }
}

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

import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;

/**
 * An Axis-Aligned Box.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class Box implements java.io.Serializable, Body
{
	private static final long serialVersionUID = -6277131116550693278L;
    
    /**
     * The lower corner of this bounding box
     */
    private Point3f lower = new Point3f();
    
    /**
     * The upper corner of this bounding box
     */
    private Point3f upper = new Point3f();
    
    // just for temporary use!
    private Point3f center = new Point3f();
    private Point3f size = new Point3f();
    
    /**
     * {@inheritDoc}
     */
    public boolean contains( float px, float py, float pz )
    {
        return( Classifier.classifyBoxPoint( this, px, py, pz ) == Classifier.Classification.INSIDE );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean contains( Point3f point )
    {
        return( contains( point.x, point.y, point.z ) );
    }
    
    private void check()
    {
        /*
        if (upper.x < lower.x) throw( new Error( "Improper Box values" ) );
        if (upper.y < lower.y) throw( new Error("Improper Box values" ) );
        if (upper.z < lower.z) throw( new Error("Improper Box values" ) );
        */
    }
    
    public void set( float lowerX, float lowerY, float lowerZ, float upperX, float upperY, float upperZ )
    {
        this.lower.set( lowerX, lowerY, lowerZ );
        this.upper.set( upperX, upperY, upperZ );
        
        check();
    }
    
    public void set( Tuple3f lower, Tuple3f upper )
    {
        this.lower.set( lower );
        this.upper.set( upper );
        
        check();
    }
    
    public void setLower( float x, float y, float z )
    {
        lower.set( x, y, z );
        
        check();
    }
    
    /**
     * Sets the lower corner of this bounding box
     */
    public void setLower( Tuple3f point )
    {
        setLower( point.x, point.y, point.z );
    }
    
    /**
     * Sets the x coordinate of the lower corner of this Box.
     * 
     * @param x
     */
    public void setLowerX( float x )
    {
        lower.x = x;
    }
    
    /**
     * Sets the y coordinate of the lower corner of this Box.
     * 
     * @param y
     */
    public void setLowerY( float y )
    {
        lower.y = y;
    }
    
    /**
     * Sets the z coordinate of the lower corner of this Box.
     * 
     * @param z
     */
    public void setLowerZ( float z )
    {
        lower.z = z;
    }
    
    /**
     * @return the lower corner of this Box.
     */
    public Point3f getLower()
    {
        return( lower );
    }
    
    public void getLower( Tuple3f point )
    {
        point.x = lower.x;
        point.y = lower.y;
        point.z = lower.z;
    }
    
    /**
     * @return the x coordinate of the lower corner of this Box.
     */
    public float getLowerX()
    {
        return( lower.x );
    }
    
    /**
     * @return the y coordinate of the lower corner of this Box.
     */
    public float getLowerY()
    {
        return( lower.y );
    }
    
    /**
     * @return the z coordinate of the lower corner of this Box.
     */
    public float getLowerZ()
    {
        return( lower.z );
    }
    
    public void setUpper( float x, float y, float z )
    {
        upper.set( x, y, z );
        
        check();
    }
    
    /**
     * Sets the upper corner of this box
     */
    public void setUpper( Tuple3f point )
    {
        setUpper( point.x, point.y, point.z );
    }
    
    /**
     * Sets the x coordinate of the upper corner of this Box.
     * 
     * @param x
     */
    public void setUpperX( float x )
    {
        upper.x = x;
    }
    
    /**
     * Sets the y coordinate of the upper corner of this Box.
     * 
     * @param y
     */
    public void setUpperY( float y )
    {
        upper.y = y;
    }
    
    /**
     * Sets the z coordinate of the upper corner of this Box.
     * 
     * @param z
     */
    public void setUpperZ( float z )
    {
        upper.z = z;
    }
    
    /**
     * @return the upper corner of this box.
     */
    public Point3f getUpper()
    {
        return( upper );
    }
    
    public void getUpper( Tuple3f point )
    {
        point.x = upper.x;
        point.y = upper.y;
        point.z = upper.z;
    }
    
    /**
     * @return the x coordinate of the upper corner of this Box.
     */
    public float getUpperX()
    {
        return( upper.x );
    }
    
    /**
     * @return the y coordinate of the upper corner of this Box.
     */
    public float getUpperY()
    {
        return( upper.y );
    }
    
    /**
     * @return the z coordinate of the upper corner of this Box.
     */
    public float getUpperZ()
    {
        return( upper.z );
    }
    
    public float getXSpan()
    {
        return( upper.x - lower.x );
    }
    
    public float getYSpan()
    {
        return( upper.y - lower.y );
    }
    
    public float getZSpan()
    {
        return( upper.z - lower.z );
    }
    
    /**
     * Moves this Box to a position, where it's center is at the given location.
     */
    public void setCenter( float x, float y, float z )
    {
        final float xSpan = getXSpan();
        final float ySpan = getYSpan();
        final float zSpan = getZSpan();
        
        lower.x = x - ( xSpan / 2.0f );
        lower.y = y - ( ySpan / 2.0f );
        lower.z = z - ( zSpan / 2.0f );
    }
    
    /**
     * Moves this Box to a position, where it's center is at the given location.
     */
    public void setCenter( Tuple3f point )
    {
        setCenter( point.x, point.y, point.z );
    }
    
    /**
     * Computes the xspan of this Box.
     */
    public float getCenterX()
    {
        return( (lower.x + upper.x) / 2.0f );
    }
    
    /**
     * Computes the xspan of this Box.
     */
    public float getCenterY()
    {
        return( (lower.y + upper.y) / 2.0f );
    }
    
    /**
     * Computes the xspan of this Box.
     */
    public float getCenterZ()
    {
        return( (lower.z + upper.z) / 2.0f );
    }
    
    /**
     * Computes the x-, y-, and z-span of this Box and puts the values into <code>out</code>.
     * 
     * @param out
     */
    public Tuple3f getCenter( Tuple3f out )
    {
        out.x = getCenterX();
        out.y = getCenterY();
        out.z = getCenterZ();
        
        return( out );
    }
    
    /**
     * Computes the x-, y-, and z-span of this Box and puts the values into <code>out</code>.
     */
    public Point3f getCenter()
    {
        center.x = getCenterX();
        center.y = getCenterY();
        center.z = getCenterZ();
        
        return( center );
    }
    
    /**
     * Resizes this Box to the given size.
     */
    public void setSize( float xSpan, float ySpan, float zSpan )
    {
        upper.x = lower.x + xSpan;
        upper.y = lower.y + ySpan;
        upper.z = lower.z + zSpan;
    }
    
    /**
     * Resizes this Box to the given size.
     */
    public void setSize( Tuple3f span )
    {
        setSize( span.x, span.y, span.z );
    }
    
    /**
     * Computes the x-, y-, and z-span of this Box and puts the values into <code>out</code>.
     * 
     * @param out
     */
    public void getSize( Tuple3f out )
    {
        out.set( getXSpan(), getYSpan(), getZSpan() );
    }
    
    /**
     * Computes the x-, y-, and z-span of this Box and puts the values into <code>out</code>.
     */
    public Tuple3f getSize()
    {
        size.set( getXSpan(), getYSpan(), getZSpan() );
        
        return( size );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Body body )
    {
        if ( body instanceof Box )
        {
            Box box = (Box)body;
            combine( box.getLower() );
            combine( box.getUpper() );
        }
        else if ( body instanceof Sphere )
        {
            Sphere s = (Sphere)body;
            combine( s.getCenterX() - s.getRadius(), s.getCenterY() - s.getRadius(), s.getCenterZ() - s.getRadius() );
            combine( s.getCenterX() + s.getRadius(), s.getCenterY() + s.getRadius(), s.getCenterZ() + s.getRadius() );
        }
        else if ( body instanceof ConvexHull )
        {
            throw( new Error( "ConvexHull not supported yet" ) );
        } 
        else
        {
            throw( new Error( "Unknown Body type" ) );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Body[] bodies )
    {
        for ( int i = 0; i < bodies.length; i++ )
        {
            combine( bodies[ i ] );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( float x, float y, float z )
    {
        if ( x < lower.x )
            lower.x = x;
        else if ( x > upper.x )
            upper.x = x;
        
        if ( y < lower.y )
            lower.y = y;
        else if ( y > upper.y )
            upper.y = y;
        
        if ( z < lower.z )
            lower.z = z;
        else if ( z > upper.z )
            upper.z = z;
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Point3f p )
    {
        combine( p.x, p.y, p.z );
    }
    
    /**
     * {@inheritDoc}
     */
    public void combine( Point3f[] points )
    {
        for ( int i = 0; i < points.length; i++ )
        {
            combine( points[ i ] );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer( 96 );
        sb.append( "Box[ lower: (" );
        sb.append( lower.x );
        sb.append( ", " );
        sb.append( lower.y );
        sb.append( ", " );
        sb.append( lower.z );
        sb.append( "), upper: (" );
        sb.append( upper.x - lower.x );
        sb.append( ", " );
        sb.append( upper.y - lower.y );
        sb.append( ", " );
        sb.append( upper.z - lower.z );
        sb.append( ") ]" );
        
        return( sb.toString() );
    }
    
    /**
     * Creates a new Box
     */
    public Box( float lowerX, float lowerY, float lowerZ, float upperX, float upperY, float upperZ )
    {
        super();
        
        lower.set( lowerX, lowerY, lowerZ );
        upper.set( upperX, upperY, upperZ );
    }
    
    /**
     * Creates a new Box
     */
    public Box( Tuple3f lower, Tuple3f upper )
    {
        this( lower.x, lower.y, lower.z, upper.x, upper.y, upper.z );
    }
    
    /**
     * Creates a new Box
     */
    public Box()
    {
        this( 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f );
    }
}

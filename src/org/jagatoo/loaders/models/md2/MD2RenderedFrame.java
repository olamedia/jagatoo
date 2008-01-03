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
package org.jagatoo.loaders.models.md2;

import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Vector3f;

/**
 * A single frame rendered from an MD2 Model
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class MD2RenderedFrame
{
    /** The anim name of the frame */
    private String anim;
    /** The index of the frame */
    private String index;
    /** The fans */
    private Point3f[] fans;
    /** The strips */
    private Point3f[] strips;
    /** The fans texture coordinates */
    private TexCoord2f[] fansTex;
    /** The strips texture coordinates */
    private TexCoord2f[] stripsTex;
    /** The fans normals */
    private Vector3f[] fansNorms;
    /** The strips normals */
    private Vector3f[] stripsNorms;
    
    /** The fans counts */
    private int[] fanCounts;
    /** The strip counts */
    private int[] stripCounts;
	
    /** 
     * Creates new MD2RenderedFrame 
     *
     * @param name The name of this frame
     * @param fans The shape defining the triangle fans 
     * @param strips The shape defining the triangle strips
     * @param fansTex
     * @param stripsTex
     * @param fansNorms
     * @param stripsNorms
     * @param fanCounts
     * @param stripCounts
     */
    public MD2RenderedFrame( String name, Point3f[] fans, Point3f[] strips,
                             TexCoord2f[] fansTex, TexCoord2f[] stripsTex,
                             Vector3f[] fansNorms, Vector3f[] stripsNorms,
                             int[] fanCounts, int[] stripCounts )
    {
        this( name, fans, strips, fansTex, stripsTex, fanCounts, stripCounts );
        
        this.fansNorms = fansNorms;
        this.stripsNorms = stripsNorms;
    }
    
    public MD2RenderedFrame( String name, Point3f[] fans, Point3f[] strips,
                             TexCoord2f[] fansTex, TexCoord2f[] stripsTex,
                             int[] fanCounts, int[] stripCounts )
    {
        this.fans = fans;
        this.strips = strips;
        //this.factory = factory;
        this.stripsTex = stripsTex;
        this.fansTex = fansTex;
        this.stripCounts = stripCounts;
        this.fanCounts = fanCounts;
        
        this.fansNorms = null;
        this.stripsNorms = null;
        
        int i;
        
        for ( i = name.length() - 1; i >= 0; i-- )
        {
            if ( ( name.charAt( i ) >= 'a' ) && ( name.charAt( i ) <= 'z' ) )
            {
                break;
            }
        }
        
        anim = name.substring( 0, i + 1 );
        index = name.substring( i + 1 );
    }
    
    /**
     * Get the fan indexs counts 
     */
    public int[] getFanCounts()
    {
        return( fanCounts );
    }
    
    /**
     * Get the strip indexs counts 
     */
    public int[] getStripCounts()
    {
        return( stripCounts );
    }
    
    /**
     * Get the geometry building up the strips of this model
     * 
     * @return The geometry for the triangle strips
     */
    public Point3f[] getStrips()
    {
        return( strips );
    }
    
    /**
     * Get the geometry building up the fans of this model
     * 
     * @return The geometry for the triangle fans
     */
    public Point3f[] getFans()
    {
        return( fans );
    }
    
    /**
     * Get the geometry building up the strips of this model
     * 
     * @return The geometry for the triangle strips
     */
    public TexCoord2f[] getStripsTexCoords()
    {
        return( stripsTex );
    }
    
    /**
     * Get the geometry building up the fans of this model
     * 
     * @return The geometry for the triangle fans
     */
    public TexCoord2f[] getFansTexCoords()
    {
        return( fansTex );
    }
    
    /**
     * Get the vertex normals for the strips of this model
     * 
     * @return The geometry for the triangle strips
     */
    public Vector3f[] getStripsNorms()
    {
        return( stripsNorms );
    }
    
    /**
     * Get the vertex normals for the fans of this model
     * 
     * @return The geometry for the triangle fans
     */
    public Vector3f[] getFansNorms()
    {
        return( fansNorms );
    }
    
    /**
     * Retrieve the index of this frame
     * 
     * @return The index of this frame
     */
    public String getAnimIndex()
    {
        return( index );
    }
    
    /**
     * Retrieve the name of this frame
     * 
     * @return The name of this frame
     */
    public String getAnimName()
    {
        return( anim );
    }
}

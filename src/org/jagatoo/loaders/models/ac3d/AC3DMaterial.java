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
package org.jagatoo.loaders.models.ac3d;

import org.openmali.vecmath2.Colorf;

/**
 * Contains all the information for an ac3d material tag.
 * 
 * @author Jeremy
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 * 
 * @version 1.1
 */
public class AC3DMaterial
{
    /** The index of this material */
    private int      index;
    /** The name of the material */
    private String   name;
    /** The color of the material */
    private Colorf  color;
    /** The color of ambient light reflected */
    private Colorf  amb;
    /** The emited color of this material */
    private Colorf  emis;
    /** The speculative color of the material */
    private Colorf  spec;
    /** The shinyness */
    private float    shininess;
    /** The translucancy of the material */
    private float    translucency;
    
    /**
     * @return The index
     */
    public int getIndex()
    {
        return( index );
    }
    
    /**
     * @return The color
     */
    public Colorf getColor() {
        return( new Colorf( color ) );
    }
    
    /**
     * @return The ambient reflectiveness color
     */
    public Colorf getAmbience()
    {
        return( new Colorf( amb ) );
    }
    
    /**
     * @return The emissive color
     */
    public Colorf getEmissive()
    {
        return( new Colorf( emis ) );
    }
    
    /**
     * @return The specular color
     */
    public Colorf getSpecular()
    {
        return( new Colorf( spec ) );
    }
    
    /**
     * @return The shininess
     */
    public float getShininess()
    {
        return( shininess );
    }
    
    /**
     * @return The translucency
     */
    public float getTranslucency()
    {
        return( translucency );
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return( name );
    }
    
    /**
     * Creates new Material from the ac3d data lump
     * 
     * @param index The material index
     * @param name The name of this material
     * @param color The color
     * @param ambient The ambient color
     * @param emissive The emissive color
     * @param specular The specular color
     * @param shininess The shininess
     * @param translucency The transluncancy
     */
    public AC3DMaterial( int index, String name, Colorf color, Colorf ambient,
                         Colorf emissive, Colorf specular, float shininess, float translucency )
    {
        this.index        = index;
        this.name         = new String( name );
        // take copies of the color objects
        this.color        = new Colorf( color );
        this.amb          = new Colorf( ambient );
        this.emis         = new Colorf( emissive );
        this.spec         = new Colorf( specular );
        this.shininess    = shininess;
        this.translucency = translucency;
        
        /*
        System.out.println( "Created an AC3DMaterial\n" +
                            "Index: " + index + " name: " + name + "\nColour: R: " + color.getRed() +
                            " G: " + color.getGreen() + " B:" + color.getBlue() +
                            "\nAmbient: R: " + amb.getRed() + " G: " + amb.getGreen() +
                            " B: " + amb.getBlue() + "\nEmissive: R: " + emis.getRed() +
                            " G: " + emis.getGreen() + " B: " + emis.getBlue() +
                            "\nSpecular: R: " + spec.getRed() + " G: " + spec.getGreen() +
                            " B: " + spec.getBlue() + "\nShininess: " + shininess +
                            " translucency: " + translucency );
        */
    }
}

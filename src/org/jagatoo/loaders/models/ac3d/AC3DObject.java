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

import java.util.ArrayList;

/**
 * Representation of the AC3D object tag.
 * 
 * @author Jeremy
 * @author Amos Wenger (aka BlueSky) [code cleaning]
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 * 
 * @version 1.1
 */
public class AC3DObject
{
    /** The 'world' tpye */
    public static final int TYPE_WORLD = 0;
    /** The 'poly' tpye */    
    public static final int TYPE_POLY = 1;
    /** The 'group' type */
    public static final int TYPE_GROUP = 2;
    
    /** The type of the object */
    private int type;
    /** The object name */
    private String name;
    /** The texture URL */
    private String textureName;
    /** The rotatino matrix of this object*/
    private float[] rotation;
    /** The location vector of this object*/
    private float[] location;
    /** The objects verticies */
    private float[][] verts;
    /** Texture repeat values*/
    private float textureRepeatX = 1f, textureRepeatY = 1f;
    /** Texture offset values*/
    private float textureOffsetx = 0f, textureOffsety = 0f;
    /** The surfaces of this object*/
    private ArrayList<AC3DSurface> surfaces = new ArrayList<AC3DSurface>();
    /** This objects sub objects*/
    private ArrayList<AC3DObject> kids = new ArrayList<AC3DObject>();
    
    private Object userObject = null;
    
    /**
     * Add a surface
     *
     * @param surface The surface to add
     */
    public void addSurface(AC3DSurface surface)
    {
        surfaces.add( surface );
    }
    
    /**
     * Add a kidd.
     *
     * @param object The object to add
     */
    public void addObject(AC3DObject object)
    {
        kids.add( object );
    }
    
    /**
     * @return all the surfaces from this object.
     */
    public ArrayList<AC3DSurface> getSurfaces()
    {
        return( surfaces );
    }
    
    /**
     * @return the url of the texture of this object
     */
    public String getTextureName()
    {
        if (textureName != null)
        {
            return( new String( textureName ) );
        }
        else
        {
            return( null );
        }
    }
    
    /**
     * @return The child objects
     */
    public ArrayList<AC3DObject> getChildren()
    {
        return( kids );
    }
    
    /**
     * @return The type
     */
    public int getType()
    {
        return( type );
    }
    
    /**
     * @return The name
     */
    public String getName()
    {
        if (name == null)
        {
            return( null );
        }
        else
        {
            return( new String( name ) );
        }
    }
    
    /**
     * @return The rotation
     */
    public float[] getRotation()
    {
        return( rotation );
    }
    
    /**
     * @return The locaiton
     */
    public float[] getLocation()
    {
        return( location );
    }
    
    /**
     * @return The verticies
     */
    public float[][] getVerticies()
    {
        return( verts );
    }
    
    /**
     * @return The texture repeat in the x axis
     */
    public float getTextureRepeatX()
    {
        return( textureRepeatX );
    }
    
    /**
     * @return The texture repeat in the y axis
     */
    public float getTextureRepeatY()
    {
        return( textureRepeatY );
    }
    
    /**
     * @return The texture offset in the x axis
     */
    public float getTextureOffsetX()
    {
        return( textureOffsetx );
    }
    
    /**
     * @return The texture offset in the y axis
     */
    public float getTextureOffsetY()
    {
        return( textureOffsety );
    }
    
    public Object getUserObject()
    {
        return( userObject );
    }
    
    /**
     * Creates new AC3DObject.
     * 
     * @param type The type of object
     * @param name The name of the object
     * @param textureName The texture name of the object
     * @param rotation The objects rotation
     * @param location The objects location
     * @param verts The objects verticies
     * @param textureRepeatX The texture repeat value in the x axis
     * @param textureRepeatY The texture repeat value in the y axis
     * @param textureOffsetx The texture offset in the X axis
     * @param textureOffsety The texture off set in the Y axis
     */
    public AC3DObject(int type, String name, String textureName, 
                      float[] rotation, float[] location, float[][] verts, 
                      float textureRepeatX, float textureRepeatY, 
                      float textureOffsetx, float textureOffsety)
    {
        this.type = type;
        this.name = name;
        this.textureName = textureName;
        this.rotation = rotation;
        this.location = location;
        this.verts = verts;
        this.textureRepeatX = textureRepeatX;
        this.textureRepeatY = textureRepeatY;
        this.textureOffsetx = textureOffsetx;
        this.textureOffsety = textureOffsety;
        
        /*
        System.out.println( "Created Object, type: " + type + " name: " + name +
                            " textureName: " + textureName + " with " + verts.length + " verticies" );
        */
    }
}

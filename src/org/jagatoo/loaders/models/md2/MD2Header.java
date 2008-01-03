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

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.jagatoo.util.streams.LittleEndianDataInputStream;

/**
 * The header of a Quake 2 Model file
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class MD2Header
{
    /** The magic number - must be 844121161 */
    //private int magic;
    /** The version number of this file - must be 8 */
    //private int version;			
    /** The width of the model's skin */
    //private int skinWidth;
    /** The height of the model's skin */
    //private int skinHeight;			
    /** The size of a single frame in bytes */
    //private int frameSize;
    /** The number of skins available for this model */
    //private int numSkins;
    /** The number of vertices in this model */
    private int numVertices;
    /** The number of texture coordinates defined in this model */
    //private int numTexcoords;
    /** The number of triangles in this model */
    //private int numTriangles;		
    /** The number of gl commands in this model */
    private int numGlCommands;
    /** The number of the frames of animation this model has */
    private int numFrames;		
    /** The offset in bytes into the file of the skins information */
    //private int offsetSkins;
    /** The offset in bytes into the file of the texture coordinate information */
    //private int offsetTexcoords;
    /** The offset into the file in bytes of the triangles data */
    //private int offsetTriangles;
    /** The offset into the file in bytes of the frames information */
    private int offsetFrames;		
    /** The offset into the file in bytes of the gl commands list */
    private int offsetGlCommands; 
    /** The offset to the end of the model */
    //private int offsetEnd;
	
    /** 
     * Creates new MD2Header 
     * 
     * @param b The file to read the header from
     */
    public MD2Header( byte[] b ) throws IOException
    {        
        DataInputStream in = new DataInputStream( new ByteArrayInputStream( b ) );
        read( in );
        in.close();
    }
    
    /**
     * Retrieves the offset into the file for gl commands. 
     * 
     * @return The offset into the file for the gl commands
     */
    public int getGLCommandsOffset()
    {
        return( offsetGlCommands );
    }
    
    /**
     * Retrieves a count of the number of gl commandsin this model.
     * 
     * @return The number of gl commands in this model
     */
    public int getGLCommandsCount()
    {
        return( numGlCommands );
    }
    
    /**
     * Retrieves a count of the number of vertex in this model.
     * 
     * @return The number of vertex in this model
     */
    public int getVertexCount()
    {
        return( numVertices );
    }
    
    /**
     * Retrieves the offset into the file for frames information
     * 
     * @return The offset into the file for the frames
     */
    public int getFramesOffset()
    {
        return( offsetFrames );
    }
    
    /**
     * Retrieves a count of the number of frames in this model.
     * 
     * @return The number of frames in this model
     */
    public int getFrameCount()
    {
        return( numFrames );
    }
    
    /**
     * Reads in this header from the specified stream.
     * 
     * @param tin The input stream to read in from
     */
    private void read( DataInputStream din ) throws IOException
    {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream( din );
        
        /*this.magic = */in.readInt();
    	/*this.version = */in.readInt();
        /*this.skinWidth = */in.readInt();
        /*this.skinHeight = */in.readInt();
        /*this.frameSize = */in.readInt();
        /*this.numSkins = */in.readInt();
        this.numVertices = in.readInt();
        /*this.numTexcoords = */in.readInt();
        /*this.numTriangles = */in.readInt();
        this.numGlCommands = in.readInt();
        this.numFrames = in.readInt();
        /*this.offsetSkins = */in.readInt();
        /*this.offsetTexcoords = */in.readInt();
        /*this.offsetTriangles = */in.readInt();
        this.offsetFrames = in.readInt();
        this.offsetGlCommands = in.readInt();
        /*this.offsetEnd = */in.readInt();        
    }
}

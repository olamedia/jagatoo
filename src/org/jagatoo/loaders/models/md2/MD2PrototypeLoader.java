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
package org.jagatoo.loaders.models.md2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jagatoo.loaders.ParsingException;
import org.jagatoo.util.streams.StreamUtils;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Vector3f;

/**
 * A Loader to load quake 2 model files
 * 
 * If the texture filename is not provided, assumes that it is the same as the
 * MD2 filename but with the filename extension ".jpg", e.g., example.md2 and
 * example.jpg.
 * 
 * To load from the classpath, use ClassLoader.getResource().
 * 
 * <pre>
 *  Current limitations:
 *  - ignores flags
 * </pre>
 * 
 * @since 2005-08-23
 * 
 * @author Kevin Glass
 * @author <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
 * @author 5parrowhawk
 * @author Amos Wenger (aka BlueSky)
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD2PrototypeLoader
{
    /**
     * Checks if the specified frame is valid.
     * 
     * @param frameName The name of the frame to check
     * @param filter The list of string to filter the frame against
     * 
     * @return <i>true</i>, if this frame should be rendered
     */
    private static boolean isFrameValid( String frameName, List< String > filter )
    {
        if ( filter == null )
            return( true );
        
        for ( int i = 0; i < filter.size(); i++ )
        {
            final String f = filter.get( i );
            if ( frameName.startsWith( f ) )
                return( true );
        }
        
        return( false );
    }
    
    /**
     * Renders the specified frame with the command provided.
     * 
     * @param frame The frame to render
     * @param commandList The commands to use for the render
     * @param bNormals Whether or not to render normal data
     */
    private static final MD2RenderedFrame createRenderedFrame( MD2Frame frame, MD2GLCommandList commandList, boolean bNormals )
    {
        int stripCount = 0;
        int stripVertexCount = 0;
        int fanCount = 0;
        int fanVertexCount = 0;
        
        // parse one, count the number of vertexs for each type of
        // primitive
        for ( int i = 0; i < commandList.getCommandSetCount(); i++ )
        {
            MD2GLCommands commands = commandList.getCommandSet( i );
            
            if ( commands.getType() == MD2GLCommands.STRIP )
            {
                stripVertexCount += commands.getCount();
                stripCount++;
            }
            if ( commands.getType() == MD2GLCommands.FAN )
            {
                fanVertexCount += commands.getCount();
                fanCount++;
            }
        }
        // parse two, count up the number of vertexs for each strip
        int[] stripCounts = new int[ stripCount ];
        int stripIndex = 0;
        int[] fanCounts = new int[ fanCount ];
        int fanIndex = 0;
        
        for ( int i = 0; i < commandList.getCommandSetCount(); i++ )
        {
            MD2GLCommands commands = commandList.getCommandSet( i );
            
            if ( commands.getType() == MD2GLCommands.STRIP )
            {
                stripCounts[ stripIndex ] = commands.getCount();
                stripIndex++;
            }
            if ( commands.getType() == MD2GLCommands.FAN )
            {
                fanCounts[ fanIndex ] = commands.getCount();
                fanIndex++;
            }
        }
        
        // parse three, actually render
        Point3f[] stripArray = new Point3f[ stripVertexCount ];
        TexCoord2f[] stripTexArray = new TexCoord2f[ stripVertexCount ];
        
        Vector3f[] stripNormArray = new Vector3f[ stripVertexCount ];
        
        // new TriangleStripArray(stripVertexCount,format,stripCounts);
        stripIndex = 0;
        Point3f[] fanArray = new Point3f[ fanVertexCount ];
        TexCoord2f[] fanTexArray = new TexCoord2f[ fanVertexCount ];
        
        Vector3f[] fanNormArray = new Vector3f[ fanVertexCount ];
        
        // new TriangleFanArray(fanVertexCount,format,fanCounts);
        fanIndex = 0;
        
        for ( int i = 0; i < commandList.getCommandSetCount(); i++ )
        {
            MD2GLCommands commands = commandList.getCommandSet( i );
            
            if ( commands.getType() == MD2GLCommands.STRIP )
            {
                for ( int j = 0; j < commands.getCount(); j++ )
                {
                    MD2GLCommand command = commands.getCommand( j );
                    
                    stripArray[ stripIndex ] = frame.getVertex( command.getVertexIndex() ).getFloats();
                    stripTexArray[ stripIndex ] = command.getTextureCoordinates();
                    stripNormArray[ stripIndex ] = frame.getVertex( command.getVertexIndex() ).getNormal();
                    
                    stripIndex++;
                }
                
            }
            
            if ( commands.getType() == MD2GLCommands.FAN )
            {
                for ( int j = 0; j < commands.getCount(); j++ )
                {
                    MD2GLCommand command = commands.getCommand( j );
                    
                    fanArray[ fanIndex ] = frame.getVertex( command.getVertexIndex() ).getFloats();
                    fanTexArray[ fanIndex ] = command.getTextureCoordinates();
                    fanNormArray[ fanIndex ] = frame.getVertex( command.getVertexIndex() ).getNormal();
                    
                    fanIndex++;
                }
            }
        }
        
        return( new MD2RenderedFrame( frame.getName(), fanArray, stripArray, fanTexArray, stripTexArray, bNormals ? fanNormArray : null, bNormals ? stripNormArray : null, fanCounts, stripCounts ) );
    }
    
    /**
     * Renders the specified frame with the command provided.
     * 
     * @param frame The frame to render
     * @param commandList The commands to use for the render
     */
    private static final MD2RenderedFrame createRenderedFrame( MD2Frame frame, MD2GLCommandList commandList )
    {
        return( createRenderedFrame( frame, commandList, true ) );
    }
    
    /**
     * Loads the specified file as an MD2 file with a skin specified as a PCX file.
     * 
     * @param in The file to load
     * @param loadFlags the loading flags
     * @param skin The texture to apply (skin) as PCX
     * @param filter A list of <code>String</code> to filter the frames
     * @param bNormals Whether or not to load vertex-normal data
     */
    public static final MD2ModelPrototype load( InputStream in, int loadFlags, String skin, List< String > filter, boolean bNormals ) throws IOException, ParsingException
    {
        if ( !( in instanceof BufferedInputStream ) )
            in = new BufferedInputStream( in );
        
        final byte[] data = StreamUtils.buildByteArray( in );
        in.close();
        
        MD2Header header = new MD2Header( data );
        MD2Frames frames = new MD2Frames( data, header );
        
        MD2GLCommandList list = new MD2GLCommandList( data, header );
        
        int frameCount = header.getFrameCount();
        MD2RenderedFrame[] rendered = new MD2RenderedFrame[ frameCount ];
        
        for ( int i = 0; i < frameCount; i++ )
        {
            if ( ( isFrameValid( frames.getFrame( i ).getName(), filter )  || ( i == 0 ) ) )
            {
                if ( bNormals )
                {
                    rendered[ i ] = createRenderedFrame( frames.getFrame( i ), list );
                }
                else
                {
                    rendered[ i ] = createRenderedFrame( frames.getFrame( i ), list, false );
                }
            }
        }
        
        return( new MD2ModelPrototype( loadFlags, rendered, skin ) );
    }
    
    /**
     * Loads the specified file as an MD2 file with a skin specified as a PCX file.
     * 
     * @param in The file to load
     * @param loadFlags the loading flags
     * @param skin The texture to apply (skin) as PCX
     */
    public static final MD2ModelPrototype load( InputStream in, int loadFlags, String skin ) throws IOException, ParsingException
    {
        return( load( in, loadFlags, skin, null, true ) );
    }
    
    private MD2PrototypeLoader()
    {
    }
}

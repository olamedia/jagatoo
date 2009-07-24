/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.loaders.models.bsp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.GroupType;
import org.jagatoo.loaders.models._util.LoaderUtils;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models.bsp.BSPEntitiesParser.BSPEntity;
import org.jagatoo.loaders.models.bsp.BSPEntitiesParser.BSPEntity_worldspawn;
import org.jagatoo.loaders.models.bsp.lumps.*;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.opengl.enums.TextureImageFormat;

/**
 * Loads the Quake 3 BSP file according to spec.
 * It is not expected that it would be rendered from this data structure,
 * but used to convert into a better format for rendering via Xith3D.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 * @author Sebastian Thiele (aka SETIssl)
 */
public class BSPPrototypeLoader
{
    public static boolean loadNormals = true;
    
    protected static final Boolean DEBUG = null;
    
    protected static BSPEntity[] readEntities( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kEntities < 0 )
        {
            //JAGTLog.debug( "kEntities not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kEntities );
        int num = file.lumps[ bspDir.kEntities ].length;
        
        byte[] bytes = file.readFully( num );
        
        //System.out.println( new String( bytes ) );
        
        BSPEntity[] entities = BSPEntitiesParser.parseEntites( bytes );
        
        /*
        for ( BSPEntity entity : entities )
        {
            //if ( entity.className2.startsWith( "info_" ) )
            {
                System.out.println( entity );
                System.out.println();
            }
        }
        */
        
        return ( entities );
    }
    
    private static final boolean urlExists( URL url )
    {
        InputStream in = null;
        
        try
        {
            in = url.openStream();
            return ( in != null );
        }
        catch ( Throwable t )
        {
            return ( false );
        }
        finally
        {
            if ( in != null )
            {
                try
                {
                    in.close();
                    
                }
                catch ( Throwable t )
                {
                }
            }
        }
    }
    
    private static final boolean addStandardWAD( URL baseURL, String filenameBase, ArrayList<WADFile> wadFiles ) throws MalformedURLException
    {
        String filename = filenameBase.concat( ".wad" );
        
        URL url = new URL( baseURL, filename );
        if ( ( urlExists( url ) ) )
        {
            for ( int i = 0; i < wadFiles.size(); i++ )
            {
                if ( wadFiles.get( i ).getWADFilename().equals( filename ) )
                    return ( false );
            }
            
            try
            {
                WADFile wadFile = new WADFile( url );
                wadFiles.add( wadFile );
                
                return ( true );
            }
            catch ( IOException e )
            {
                //e.printStackTrace();
                //System.err.println( "WAD file not found \"" + url + "\"." );
            }
        }
        
        return ( false );
    }
    
    protected static WADFile[] readWADFiles( BSPFile file, BSPEntity[] entities ) throws IOException
    {
        if ( entities == null )
        {
            throw new Error( "entities must be read before WADFiles can be read." );
        }
        
        if ( file.getBaseURL() == null )
        {
            return ( new WADFile[ 0 ] );
        }
        
        ArrayList<WADFile> wadFiles = new ArrayList<WADFile>();
        
        for ( int i = 0; i < entities.length; i++ )
        {
            BSPEntity entity = entities[i];
            
            if ( entity.className.equals( "worldspawn" ) )
            {
                BSPEntity_worldspawn worldSpawn = (BSPEntity_worldspawn)entity;
                
                if ( worldSpawn.wads != null )
                {
                    for ( int j = 0; j < worldSpawn.wads.length; j++ )
                    {
                        //System.out.println( "######### wad: " + worldSpawn.wads[j] );
                        URL url = new URL( file.getBaseURL(), worldSpawn.wads[j] );
                        try
                        {
                            WADFile wadFile = new WADFile( url );
                            wadFiles.add( wadFile );
                        }
                        catch ( IOException e )
                        {
                            //e.printStackTrace();
                            System.err.println( "WAD file not found \"" + url + "\"." );
                        }
                    }
                }
            }
        }
        
        addStandardWAD( file.getBaseURL(), file.getName(), wadFiles );
        
        return ( wadFiles.toArray( new WADFile[ wadFiles.size() ] ) );
    }
    
    private static void setupTexture( AbstractTexture texture, String textureName, AppearanceFactory appFactory )
    {
        if ( appFactory.isFallbackTexture( texture ) )
        {
            JAGTLog.printlnEx( "texture not found: ", textureName, " (WAD / .tga / .jpg)" );
            System.err.println( "missing texture: " + textureName );
        }
        else
        {
            //appFactory.setTextureMagFilter( texture, TextureMagFilter.BILINEAR );
            //appFactory.setTextureMinFilter( texture, TextureMinFilter.TRILINEAR );
        }
    }
    
    private static AbstractTexture[] loadTexture( BSPFile file, String textureName, WADFile[] wadFiles, BSPEntity[] entities, AppearanceFactory appFactory )
    {
        if ( ( wadFiles != null ) && ( wadFiles.length > 0 ) )
        {
            for ( int i = 0; i < wadFiles.length; i++ )
            {
                WADFile wadFile = wadFiles[i];
                
                if ( wadFile.containsResource( textureName ) )
                {
                    try
                    {
                        //AbstractTexture test = appFactory.loadTexture( new URL( file.getBaseURL(), textureName + ".tga" ), false, true, true, true, false );
                        
                        AbstractTexture[] textures;
                        if ( ( file.getVersion() == 30 ) && ( textureName.startsWith( "sky" ) ) )
                            textures = wadFile.readSkyTextures( textureName, appFactory, file.getBaseURL(), entities );
                        else
                            textures = wadFile.readTexture( textureName, appFactory );
                        
                        if ( textures != null )
                        {
                            setupTexture( textures[0], textureName, appFactory );
                            
                            return ( textures );
                        }
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        URL baseURL = file.getBaseURL();
        
        if ( baseURL == null )
        {
            AbstractTexture texture = appFactory.loadOrGetTexture( textureName + ".tga", false, true, true, true, false );
            if ( texture == null )
                texture = appFactory.loadOrGetTexture( textureName + ".jpg", false, true, true, true, true );
            
            setupTexture( texture, textureName, appFactory );
            
            return ( new AbstractTexture[] { texture } );
        }
        
        String texNameForURL = textureName;
        if ( texNameForURL.startsWith( "{" ) )
            texNameForURL = texNameForURL.substring( 1 );
        
        AbstractTexture texture = null;
        try
        {
            texture = appFactory.loadTexture( new URL( baseURL, texNameForURL + ".tga" ), false, true, true, true, false );
            if ( texture == null )
                texture = appFactory.loadTexture( new URL( baseURL, texNameForURL + ".jpg" ), false, true, true, true, true );
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
            
            texture = appFactory.getFallbackTexture();
        }
        
        setupTexture( texture, textureName, appFactory );
        
        return ( new AbstractTexture[] { texture } );
    }
    
    protected static AbstractTexture[][] readTextures( BSPFile file, BSPDirectory bspDir, WADFile[] wadFiles, BSPEntity[] entities, AppearanceFactory appFactory ) throws IOException
    {
        if ( bspDir.kTextures < 0 )
        {
            //JAGTLog.debug( "kTextures not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kTextures );
        
        int textureCount = 0;
        AbstractTexture[][] textures = null;
        if ( file.getVersion() == 30 )
        {
            textureCount = file.readInt();
            textures = new AbstractTexture[ textureCount ][];
        }
        else if ( file.getVersion() == 46 )
        {
            textureCount = file.lumps[ bspDir.kTextures ].length / ( 64 + 2 * 4 );
            textures = new AbstractTexture[ textureCount ][ 1 ];
        }
        
        if ( file.getVersion() == 30 )
        {
            for ( int i = 0; i < textureCount; i++ )
            {       
                file.seek( bspDir.kTextures );
                file.skipBytes( ( 4 * i ) + 4 );
                int ofs = file.readInt();
                
                file.skipBytes( ofs - ( ( 4 * i ) + 8 ) );
                
                byte[] texNameBytes = file.readFully( 16 );
                
                String textureName = null;
                for ( int b = 0; b < texNameBytes.length; b++ )
                {
                    if ( texNameBytes[ b ] == 0 )
                    {
                        textureName = new String( texNameBytes, 0, b );
                        break;
                    }
                }
                
                if ( textureName == null )
                {
                    textureName = new String( texNameBytes );
                }
                
                /*int width = */file.readInt();
                /*int height = */file.readInt();
                
                /*int offset1 = */file.readInt();
                /*int offset2 = */file.readInt();
                /*int offset3 = */file.readInt();
                /*int offset4 = */file.readInt();
                
                /*
                System.out.print( textureName );
                System.out.print( " { width: "  + width + ", height: " + height );
                System.out.print( " | ofs1: "   + offset1 +  ", ofs2: " + offset2 );
                System.out.println( ", ofs3: " + offset3 + ", ofs4: "  + offset4 + " }" );
                */
                
                textures[ i ] = loadTexture( file, textureName, wadFiles, entities, appFactory );
            }
        }
        else if ( file.getVersion() == 46 )
        {      
            byte[] ca = new byte[ 64 ];

            for ( int i = 0; i < textureCount; i++ )
            {     
                file.readFully( ca ); // q3 - something...
                /*int flags = */file.readInt();
                /*int content = */file.readInt();
                
                String textureName = new String( ca );
                textureName = textureName.substring( 0, textureName.indexOf( 0 ) );
                
                //System.out.println( textureName + ", " + flags + ", " + content );
                
                textures[ i ] = loadTexture( file, textureName, wadFiles, entities, appFactory );
            }
        }
        
        return ( textures );
    }
    
    private static void changeGamma( AbstractTextureImage img, float factor )
    {
        final ByteBuffer imgData = img.getDataBuffer();
        final int pixelSize = img.getPixelSize();
        
        byte gtable[] = new byte[ 256 ];
        for ( int i = 0; i < 256; i++ )
            gtable[ i ] = (byte)Math.floor( 255.0 * Math.pow( i / 255.0, 1.0 / factor ) + 0.5 );
        
        // Go through every pixel in the lightmap
        final int size = img.getWidth() * img.getHeight();
        for ( int i = 0; i < size; i++ )
        {
            int tmp;
            
            /*
            tmp = pBB.get( i * pixelSize + 0 ) & 0xFF;
            pBB.put( i * pixelSize + 0, gtable[ tmp ] );
            tmp = pBB.get( i * pixelSize + 1 ) & 0xFF;
            pBB.put( i * pixelSize + 1, gtable[ tmp ] );
            tmp = pBB.get( i * pixelSize + 2 ) & 0xFF;
            pBB.put( i * pixelSize + 2, gtable[ tmp ] );
            */
            
            tmp = imgData.get( i * pixelSize + 0 ) & 0xFF;
            imgData.put( i * pixelSize + 0, gtable[ tmp ] );
            tmp = imgData.get( i * pixelSize + 1 ) & 0xFF;
            imgData.put( i * pixelSize + 1, gtable[ tmp ] );
            tmp = imgData.get( i * pixelSize + 2 ) & 0xFF;
            imgData.put( i * pixelSize + 2, gtable[ tmp ] );
        }
    }
    
    protected static AbstractTexture[] readLightmaps( BSPFile file, BSPDirectory bspDir, AppearanceFactory appFactory ) throws IOException
    {
        if ( bspDir.kLightmaps < 0 )
        {
            //JAGTLog.debug( "kLightmaps not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        int w = 128;
        int h = 128;
        
        file.seek( bspDir.kLightmaps );
        final int sizePerImage = w * h * 3;
        final int num = file.lumps[ bspDir.kLightmaps ].length / sizePerImage;
        
        AbstractTexture[] lightMaps = new AbstractTexture[ num ];
        
        byte[] buffer = new byte[ 1024 ];
        
        for ( int i = 0; i < num; i++ )
        {
            AbstractTextureImage texImg0 = appFactory.createTextureImage( TextureImageFormat.RGB, w, h );
            ByteBuffer bb = texImg0.getDataBuffer();
            bb.position( 0 );
            bb.limit( bb.capacity() );
            
            for ( int j = 0; j < sizePerImage; j += buffer.length )
            {
                int rest = sizePerImage - j;
                int length;
                if ( rest >= buffer.length )
                    length = buffer.length;
                else
                    length = rest;
                
                file.readFully( buffer, 0, length );
                
                bb.put( buffer, 0, length );
            }
            
            bb.flip();
            
            int pos = bb.position();
            int limit = bb.limit();
            
            changeGamma( texImg0, 1.2f );
            
            bb.position( pos );
            bb.limit( limit );
            
            lightMaps[ i ] = appFactory.createTexture( texImg0, true );
        }
        
        return ( lightMaps );
    }
    
    protected static byte[] readLightmapData( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kLightmaps < 0 )
        {
            //JAGTLog.debug( "kLightmaps not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kLightmaps );
        
        return ( file.readFully( file.lumps[ bspDir.kLightmaps ].length ) );
    }
    
    /**
     * Half-Life BSP only method, reads out texture structure data from the bsp file.
     * 
     * @param file HL BSP file, the texture info are read from
     * @param bspDir HL BSP Lump Directory
     * @return void add return value!
     * @throws IOException
     */
    protected static BSPTexInfo[] readTexInfos( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        final int kTexInfo = bspDir.kLeafFaces;
        
        if ( kTexInfo < 0 )
        {
            //JAGTLog.debug( "kTexInfo not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( kTexInfo );
        int num = file.lumps[ kTexInfo ].length / ( ( 4 + 4 + 1 + 1 ) * 4 );
        
        BSPTexInfo[] texInfos = new BSPTexInfo[ num ];
        
        for ( int i = 0; i < num; i++ )
        {
            BSPTexInfo texInfo = new BSPTexInfo();
            texInfos[ i ] = texInfo;
            
            texInfo.s[ 0 ] = file.readFloat();
            texInfo.s[ 1 ] = file.readFloat();
            texInfo.s[ 2 ] = file.readFloat();
            texInfo.s[ 3 ] = file.readFloat();
            
            texInfo.t[ 0 ] = file.readFloat();
            texInfo.t[ 1 ] = file.readFloat();
            texInfo.t[ 2 ] = file.readFloat();
            texInfo.t[ 3 ] = file.readFloat();
            //System.out.println( "s: " + texInfo.s[0] + ", " + texInfo.s[1] + ", " + texInfo.s[2] + ", " + texInfo.s[3] );
            //System.out.println( "t: " + texInfo.t[0] + ", " + texInfo.t[1] + ", " + texInfo.t[2] + ", " + texInfo.t[3] );
            
            texInfo.textureID = file.readInt();
            texInfo.flags = file.readInt();
        }
        
        return ( texInfos );
    }
    
    protected static BSPVertex[] readVertices( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kVertices < 0 )
        {
            //JAGTLog.debug( "kVertices not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kVertices );
        int num = 0;
        if ( bspDir.getVersion() == 30 )
            num = file.lumps[ bspDir.kVertices ].length / ( 3 * 4 );
        else if ( bspDir.getVersion() == 46 )
            num = file.lumps[ bspDir.kVertices ].length / ( 11 * 4 );
        
        BSPVertex[] vertices = new BSPVertex[ num ];
        
        for ( int i = 0; i < num; i++ )
        {
            BSPVertex vertex              = new BSPVertex();
            vertices[ i ]                 = vertex;
            
            vertex.position.setX(    file.readFloat() );
            vertex.position.setY(    file.readFloat() );
            vertex.position.setZ(    file.readFloat() );
            
            if ( bspDir.getVersion() == 46 )
            {
                vertex.texCoord.setS(    file.readFloat() );
                vertex.texCoord.setT(    file.readFloat() );
                
                vertex.lightTexCoord.setS( file.readFloat() );
                vertex.lightTexCoord.setT( file.readFloat() );
                
                vertex.normal.setX(      file.readFloat() );
                vertex.normal.setY(      file.readFloat() );
                vertex.normal.setZ(      file.readFloat() );
                
                int r = file.readByte();
                if ( r < 0 )
                    r = -r + 127;
                
                int g = file.readByte();
                if ( g < 0 )
                    g = -g + 127;
                
                int b = file.readByte();
                if ( b < 0 )
                    b = -b + 127;
                
                int a = file.readByte();
                if ( a < 0 )
                    a = -a + 127;
                
                vertex.color.setRed( (float)r / 255f );
                vertex.color.setGreen( (float)g / 255f );
                vertex.color.setBlue( (float)b / 255f );
                vertex.color.setAlpha( (float)a / 255f );
            }
        }
        
        return ( vertices );
    }
    
    protected static int[] readMeshVertices( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kMeshVerts < 0 )
        {
            //JAGTLog.debug( "kMeshVerts not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kMeshVerts );
        int num = file.lumps[ bspDir.kMeshVerts ].length / 4;
        
        int[] meshVertices = new int[ num ];
        for ( int i = 0; i < num; i++ )
        {
            meshVertices[ i ] = file.readInt();
        }
        
        return ( meshVertices );
    }
    
    /**
     * Half-Life BSP only method, reads out Edge data from the bsp file.
     * 
     * @param file HL BSP file, the edges are read from
     * @param bspDir HL BSP Lump Directory
     * @return BSPEdge[] array of edges ( which contains array of  2 vertex indices per edge )
     * @throws IOException
     */
    protected static BSPEdge[] readEdges( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kBrushSides < 0 )
        {
            //JAGTLog.debug( "kBrushSides not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kBrushSides );
        // 4 bytes -> 2 x 2 short values -> 2 vertex index numbers -> 1 edge
        int num = file.lumps[ bspDir.kBrushSides ].length / ( 2 * 2 );

        BSPEdge[] edges = new BSPEdge[ num ];
        
        for ( int i = 0; i < num; i++ )
        {
            BSPEdge edge = new BSPEdge();
            edges[ i ] = edge;
            
            edge.vindices[ 0 ] = file.readUnsignedShort();
            edge.vindices[ 1 ] = file.readUnsignedShort();
        }
        
        return ( edges );
    }
    
    protected static int[] readSurfEdges( BSPFile file, BSPDirectory bspDir) throws IOException
    {
        if ( bspDir.kSurfEdges < 0 )
        {
            //JAGTLog.debug( "kBrushSides not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kSurfEdges );
        int num = file.lumps[ bspDir.kSurfEdges ].length / 4;
        
        int[] surfEdges = new int[ num ];

        for ( int i = 0; i < num; i++ )
        {
            surfEdges[ i ] = file.readInt();
        }
        
        return ( surfEdges );
    }
    
    protected static BSPFace[] readFaces( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kFaces < 0 )
        {
            //JAGTLog.debug( "kFaces not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kFaces );
        
        if ( file.getVersion() == 30 )
        {
            int num = file.lumps[ bspDir.kFaces ].length / ( 2 + 2 + 4 + 2 + 2 + 4 + 4 );

            BSPFace[] faces = new BSPFace[ num ];
            
            for ( int i = 0; i < num; i++ )
            {
                BSPFace face            = new BSPFace();
                faces[ i ]              = face;
                
                face.effect       = -1;
                
                /*int planenum = */file.readUnsignedShort();  // ushort planeNum : Index into planes lump
                /*int side = */file.readUnsignedShort();      // ushort side     : If non-zero, must flip the normal of the given plane to match face orientation
                
                face.vertexIndex = file.readInt();   //int firstedge; : we must support > 64k edges
                face.numOfVerts  = file.readUnsignedShort(); // ushort numedges :
                
                face.textureID   = file.readUnsignedShort();   // ushort texinfo : Index into texinfo lump
                
                // lighting info
                /*byte[] styles = */file.readFully( 4 ); // byte styles[ MAXLIGHTMAPS ] : #define MAXLIGHTMAPS 4
                face.lightmapID = file.readInt();   // int lightofs : start of [ numstyles * surfsize ] samples
            }
            
            return ( faces );
        }
        else if ( file.getVersion() == 46 )
        {
            int num = file.lumps[ bspDir.kFaces ].length / ( 26 * 4 );
    
            BSPFace[] faces = new BSPFace[ num ];
    
            for ( int i = 0; i < num; i++ )
            {
                BSPFace face                     = new BSPFace();
                faces[ i ]                       = face;
                
                face.textureID             = file.readInt();
                face.effect                = file.readInt();
                face.type                  = file.readInt();
                face.vertexIndex           = file.readInt();
                face.numOfVerts            = file.readInt();
                face.meshVertIndex         = file.readInt();
                face.numMeshVerts          = file.readInt();
                face.lightmapID            = file.readInt();
                face.lMapCorner[ 0 ]       = file.readInt();
                face.lMapCorner[ 1 ]       = file.readInt();
                
                face.lMapSize[ 0 ]         = file.readInt();
                face.lMapSize[ 1 ]         = file.readInt();
                
                face.lMapPos[ 0 ]          = file.readFloat();
                face.lMapPos[ 1 ]          = file.readFloat();
                face.lMapPos[ 2 ]          = file.readFloat();
                
                face.lMapBitsets[ 0 ][ 0 ] = file.readFloat();
                face.lMapBitsets[ 0 ][ 1 ] = file.readFloat();
                face.lMapBitsets[ 0 ][ 2 ] = file.readFloat();
                
                face.lMapBitsets[ 1 ][ 0 ] = file.readFloat();
                face.lMapBitsets[ 1 ][ 1 ] = file.readFloat();
                face.lMapBitsets[ 1 ][ 2 ] = file.readFloat();
                
                face.vNormal[ 0 ]          = file.readFloat();
                face.vNormal[ 1 ]          = file.readFloat();
                face.vNormal[ 2 ]          = file.readFloat();
                
                face.size[ 0 ]             = file.readInt();
                face.size[ 1 ]             = file.readInt();
                //System.out.println( "type=" + face.type + ", verts " + face.numOfVerts + ", " + face.numMeshVerts );
            }
            
            return ( faces );
        }
        
        return ( null );
    }
    
    protected static BSPVisData readVisData( BSPFile file, BSPDirectory bspDir, int leafCount ) throws IOException
    {
        if ( bspDir.kVisData < 0 )
        {
            //JAGTLog.debug( "kVisData not currently supported by ", bspDir.getClass().getSimpleName() );
            return ( null );
        }
        
        file.seek( bspDir.kVisData );
        
        BSPVisData visData = null;
        
        if ( file.getVersion() == 30 ) 
        {
            visData = new BSPVisData();
            
            visData.numOfClusters = leafCount;
            visData.bytesPerCluster = ( visData.numOfClusters - 1 ) / 8;
            
            //System.out.println( visData.numOfClusters + ", " + visData.bytesPerCluster );
            
            byte[] compressed = file.readFully( file.lumps[ bspDir.kVisData ].length );
            visData.pBitsets = BSPVersionDataLoader30.decompressVis( visData.numOfClusters, compressed );
        }
        else if ( file.getVersion() == 46 )
        {
            visData = new BSPVisData();
            
            visData.numOfClusters = file.readInt();
            visData.bytesPerCluster = file.readInt();
            
            //System.out.println( visData.numOfClusters + ", " + visData.bytesPerCluster );
            
            visData.pBitsets = file.readFully( visData.bytesPerCluster * visData.numOfClusters );
        }
        
        return ( visData );
    }
    
    protected static BSPPlane[] readPlanes( BSPFile file, BSPDirectory bspDir, float worldScale ) throws IOException
    {
        if ( bspDir.kPlanes < 0 )
        {
            //JAGTLog.debug( "kPlanes not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        int entryLength = 0;
        if ( bspDir.getVersion() == 30 )
        {
            entryLength = 5;
        }
        else if ( bspDir.getVersion() == 46 )
        {
            entryLength = 4;
        }
        
        file.seek( bspDir.kPlanes );
        int num = file.lumps[ bspDir.kPlanes ].length / ( entryLength * 4 );
        BSPPlane[] planes = new BSPPlane[ num ];
        
        for ( int i = 0; i < num; i++ )
        {
            BSPPlane plane = new BSPPlane();
            planes[ i ] = plane;
            
            plane.normal.setX( file.readFloat() );
            plane.normal.setY( file.readFloat() );
            plane.normal.setZ( file.readFloat() );
            plane.d = file.readFloat() * worldScale;
            
            file.skipBytes( ( entryLength - 4 ) * 4 );
            
            //System.out.println( i + ": " + planes[ i ].normal + ", " + planes[ i ].d );
        }
        
        return ( planes );
    }
    
    protected static BSPNode[] readNodes( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kNodes < 0 )
        {
            //JAGTLog.debug( "kNodes not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kNodes );
        
        int length = 0;
        if ( file.getVersion() == 30 )
        {
            length = 24;
        }
        else if ( file.getVersion() == 46 )
        {
            length = ( 9 * 4 );
        }
        
        int num = file.lumps[ bspDir.kNodes ].length / length;
        BSPNode[] nodes = new BSPNode[ num ];
        
        if ( file.getVersion() == 30 ) 
        {
            for (int i = 0; i < num; i++)
            {
                BSPNode node         = new BSPNode();
                nodes[ i ]           = node;
                
                node.plane     = file.readInt(); // index into planes array
                
                // If > 0, then indices into nodes; otherwise, bitwise inverse = indices into leaves.
                short child0 = file.readShort();
                short child1 = file.readShort();
                
                // FIXME: How to use this index for leafs, but not for nodes???
                
                /*
                if ( child0 <= 0 )
                    System.out.println( child0 );
                if ( child1 <= 0 )
                    System.out.println( child1 );
                */
                
                if ( child0 > 0 )
                    node.front = child0;
                else
                    //node.front = -( Integer.reverse( child0 ) >> 16 );
                    node.front = -( child0 + 1 );
                if ( child1 > 0 )
                    node.back = child1;
                else
                    //node.back = -( Integer.reverse( child1 ) >> 16 );
                    node.back = -( child1 + 1 );
                
                node.mins[ 0 ] = file.readShort();
                node.mins[ 1 ] = file.readShort();
                node.mins[ 2 ] = file.readShort();
                
                node.maxs[ 0 ] = file.readShort();
                node.maxs[ 1 ] = file.readShort();
                node.maxs[ 2 ] = file.readShort();
                
                /*int firstFace = */file.readUnsignedShort();
                /*int numFaces = */file.readUnsignedShort();// counting both sides
                
                //System.out.println( "      " + node.plane + " - " + node.front + " - " + node.back );
                //System.out.println( "mins: " + node.mins[ 0 ] + " - " + node.mins[ 1 ] + " - " + node.mins[ 2 ] );
                //System.out.println( "maxs: " + node.maxs[ 0 ] + " - " + node.maxs[ 1 ] + " - " + node.maxs[ 2 ] );
            }
        }
        else if ( file.getVersion() == 46 )
        {
            for (int i = 0; i < num; i++)
            {
                BSPNode node = new BSPNode();
                nodes[ i ]   = node;
                
                node.plane     = file.readInt();
                node.front     = file.readInt();
                node.back      = file.readInt();
                
                node.mins[ 0 ] = file.readInt();
                node.mins[ 1 ] = file.readInt();
                node.mins[ 2 ] = file.readInt();
                
                node.maxs[ 0 ] = file.readInt();
                node.maxs[ 1 ] = file.readInt();
                node.maxs[ 2 ] = file.readInt();
            }
        }
        
        return ( nodes );
    }
    
    protected static BSPLeaf[] readLeafs( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kLeafs < 0 )
        {
            //JAGTLog.debug( "kLeafs not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        int entryLength = 0;
        if ( file.getVersion() == 30 )
        {
            entryLength = 26;
        }
        else if ( file.getVersion() == 46 )
        {
            entryLength = ( 12 * 4 );
        }
        
        file.seek( bspDir.kLeafs );
        int num = file.lumps[ bspDir.kLeafs ].length / entryLength;
        
        BSPLeaf[] leafs = new BSPLeaf[ num ];
        
        //System.out.println( num );
        
        if ( file.getVersion() == 30 )
        {
            for ( int i = 0; i < num; i++ )
            {
                BSPLeaf leaf                = new BSPLeaf();
                leafs[ i ]                  = leaf;
                
                /*
                signed int contents;            // Contents enum
                unsigned int vis_offset;        // Something to do with visibility; -1 = no visibility info
                signed short mins[3], maxs[3];  // Bounding box
                unsigned short firstmarksurface, nummarksurfaces;   // Index and count into marksurfaces lump
                unsigned char ambient_level[4]; // Ambient sound levels, indexed by ambient enum
                */
                
                /*int contents = */file.readInt();
                /*int visOffset = */file.readInt();
                
                //System.out.println( "contents: " + contents );
                //System.out.println( "visOffset: "+ visOffset );
                
                // for frustum culling
                leaf.mins[ 0 ]        = file.readShort();
                leaf.mins[ 1 ]        = file.readShort();
                leaf.mins[ 2 ]        = file.readShort();
                    
                leaf.maxs[ 0 ]        = file.readShort();
                leaf.maxs[ 1 ]        = file.readShort();
                leaf.maxs[ 2 ]        = file.readShort();
                
                /*int firstMarkSurface = */file.readUnsignedShort();
                /*int numMarkSurfaces = */file.readUnsignedShort();
                
                //System.out.println( "firstMarkSurface: " + firstMarkSurface );
                //System.out.println( "numMarkSurfaces: " + numMarkSurfaces );
                
                file.readFully( 4 ); // ambient_level;
            }
        }
        else if ( file.getVersion() == 46 )
        {
            for ( int i = 0; i < num; i++ )
            {
                BSPLeaf leaf                = new BSPLeaf();
                leafs[ i ]                  = leaf;
                
                leaf.cluster          = file.readInt();
                leaf.area             = file.readInt();
                
                leaf.mins[ 0 ]        = file.readInt();
                leaf.mins[ 1 ]        = file.readInt();
                leaf.mins[ 2 ]        = file.readInt();
                
                leaf.maxs[ 0 ]        = file.readInt();
                leaf.maxs[ 1 ]        = file.readInt();
                leaf.maxs[ 2 ]        = file.readInt();
                
                leaf.leafFace         = file.readInt();
                leaf.numOfLeafFaces   = file.readInt();
                
                leaf.leafBrush        = file.readInt();
                leaf.numOfLeafBrushes = file.readInt();
            }
        }
        
        return ( leafs );
    }
    
    protected static int[] readLeafFaces( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kLeafFaces < 0 )
        {
            //JAGTLog.debug( "kLeafFaces not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kLeafFaces );
        int num = file.lumps[ bspDir.kLeafFaces ].length / 4;
        
        int[] leafFaces = new int[ num ];
        for ( int i = 0; i < num; i++ )
        {
            leafFaces[ i ] = file.readInt();
        }
        
        return ( leafFaces );
    }
    
    protected static BSPBrush[] readBrushes( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kBrushes < 0 )
        {
            //JAGTLog.debug( "kBrushes not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kBrushes );
        int num = file.lumps[ bspDir.kBrushes ].length / ( 3 * 4 );
        
        BSPBrush[] brushes = new BSPBrush[ num ];
        for ( int i = 0; i < num; i++ )
        {
            BSPBrush brush = new BSPBrush();
            brushes[i] = brush;
            
            brush.brushSide = file.readInt();
            brush.numBrushSides = file.readInt();
            brush.textureID = file.readInt();
        }
        
        return ( brushes );
    }
    
    protected static BSPBrushSide[] readBrushSides( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kBrushSides < 0 )
        {
            //JAGTLog.debug( "kBrushSides not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kBrushSides );
        int num = file.lumps[ bspDir.kBrushSides ].length / ( 3 * 4 );
        
        BSPBrushSide[] brushSides = new BSPBrushSide[ num ];
        for ( int i = 0; i < num; i++ )
        {
            BSPBrushSide brushSide = new BSPBrushSide();
            brushSides[i] = brushSide;
            
            brushSide.plane = file.readInt();
            brushSide.textureID = file.readInt();
        }
        
        return ( brushSides );
    }
    
    protected static BSPModel[] readModels( BSPFile file, BSPDirectory bspDir ) throws IOException
    {
        if ( bspDir.kModels < 0 )
        {
            //JAGTLog.debug( "kModels not currently supported by ", bspDir.getClass().getSimpleName() );
            
            return ( null );
        }
        
        file.seek( bspDir.kModels );
        
        if ( file.getVersion() == 30 )
        {
            int num = file.lumps[ bspDir.kModels ].length / ( 16 * 4 );
            BSP30Model[] models = new BSP30Model[ num ];
            
            for ( int i = 0; i < num; i++ )
            {
                BSP30Model model = new BSP30Model();
                models[ i ] = model;
                
                model.min[ 0 ]     = file.readFloat();
                model.min[ 1 ]     = file.readFloat();
                model.min[ 2 ]     = file.readFloat();
                
                model.max[ 0 ]     = file.readFloat();
                model.max[ 1 ]     = file.readFloat();
                model.max[ 2 ]     = file.readFloat();
                
                model.origin[ 0 ]     = file.readFloat();
                model.origin[ 1 ]     = file.readFloat();
                model.origin[ 2 ]     = file.readFloat();
                
                model.headNode[ 0 ]     = file.readInt();
                model.headNode[ 1 ]     = file.readInt();
                model.headNode[ 2 ]     = file.readInt();
                model.headNode[ 3 ]     = file.readInt();
                
                model.visLeaves    = file.readInt();
                
                model.faceIndex   = file.readInt();
                model.numOfFaces   = file.readInt();
            }
            
            return ( models );
        }
        else if ( file.getVersion() == 46 )
        {
            int num = file.lumps[ bspDir.kModels ].length / ( 10 * 4 );
            BSP46Model[] models = new BSP46Model[ num ];
            
            for ( int i = 0; i < num; i++ )
            {
                BSP46Model model = new BSP46Model();
                models[ i ] = model;
                
                model.min[0]       = file.readFloat();
                model.min[1]       = file.readFloat();
                model.min[2]       = file.readFloat();
                
                model.max[0]       = file.readFloat();
                model.max[1]       = file.readFloat();
                model.max[2]       = file.readFloat();
                
                model.faceIndex    = file.readInt();
                model.numOfFaces   = file.readInt();
                model.brushIndex   = file.readInt();
                model.numOfBrushes = file.readInt();
            }
            
            return ( models );
        }
        
        return ( null );
    }
    
    /**
     * Loads the BSP scene prototype.
     */
    private static BSPScenePrototype load( BSPFile bspFile, GeometryFactory geomFactory, boolean convertZup2Yup, float worldScale, AppearanceFactory appFactory, NodeFactory nodeFactory, NamedObject sceneGroup, GroupType mainGroupType, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        final BSPDirectory bspDir;
        
        switch ( bspFile.getVersion() )
        {
            case 30:
                bspDir = new BSPDirectory30();
                break;
            case 46:
                bspDir = new BSPDirectory46();
                break;
            default:
                throw new Error( "Cannot find a matching implementation of BSPDirectory for format version " + bspFile.getVersion() + "." );
        }
        
        BSPVersionDataLoader loader = bspDir.getDataLoader();
        
        BSPScenePrototype prototype = loader.loadPrototypeData( bspFile, bspDir, worldScale, appFactory );
        
        loader.convertFacesToGeometries( prototype, appFactory, geomFactory, convertZup2Yup, worldScale );
        
        BSPConverter.convert( prototype, appFactory, nodeFactory, sceneGroup, mainGroupType, worldScale, bspFile.getBaseURL(), siHandler );
        
        return ( prototype );
    }
    
    /**
     * Loads the BSP scene prototype.
     */
    public static BSPScenePrototype load( InputStream in, String filename, URL baseURL, GeometryFactory geomFactory, boolean convertZup2Yup, float worldScale, AppearanceFactory appFactory, NodeFactory nodeFactory, NamedObject sceneGroup, GroupType mainGroupType, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        if ( !( in instanceof BufferedInputStream ) )
            in = new BufferedInputStream( in );
        
        if ( filename.endsWith( ".bsp" ) )
            filename = filename.substring( 0, filename.length() - 4 );
        
        BSPFile bspFile = new BSPFile( in, filename, baseURL );
        
        return ( load( bspFile, geomFactory, convertZup2Yup, worldScale, appFactory, nodeFactory, sceneGroup, mainGroupType, siHandler ) );
    }
    
    /**
     * Loads the BSP scene prototype.
     */
    public static BSPScenePrototype load( URL url, GeometryFactory geomFactory, boolean convertZup2Yup, float worldScale, AppearanceFactory appFactory, NodeFactory nodeFactory, NamedObject sceneGroup, GroupType mainGroupType, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        return ( load( url.openStream(), LoaderUtils.extractFilenameWithoutExt( url ), LoaderUtils.extractBaseURL( url ), geomFactory, convertZup2Yup, worldScale, appFactory, nodeFactory, sceneGroup, mainGroupType, siHandler ) );
    }
}

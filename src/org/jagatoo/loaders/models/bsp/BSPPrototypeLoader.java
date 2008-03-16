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
package org.jagatoo.loaders.models.bsp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;
import org.jagatoo.loaders.models.bsp.lumps.*;
import org.jagatoo.util.image.DirectBufferedImage;

/**
 * Loads the Quake 3 BSP file according to spec.
 * It is not expected that it would be rendered from this data structure,
 * but used to convert into a better format for rendering via Xith3D.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public class BSPPrototypeLoader
{
    protected static final Boolean DEBUG = null;
    
    private static BSPVisData readVisData( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kVisData );
        
        BSPVisData visData = new BSPVisData();
        visData.numOfClusters = file.readInt();
        visData.bytesPerCluster = file.readInt();
        
        visData.pBitsets = file.readFully( visData.bytesPerCluster * visData.numOfClusters );
        
        return( visData );
    }
    
    private static BSPPlane[] readPlanes( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kPlanes );
        int num = file.lumps[ BSPDirectory.kPlanes ].length / ( 4 * 4 );
        BSPPlane[] planes = new BSPPlane[ num ];
        
        for ( int i = 0; i < num; i++ )
        {
            planes[ i ]          = new BSPPlane();
            planes[ i ].normal.setX( file.readFloat() );
            planes[ i ].normal.setY( file.readFloat() );
            planes[ i ].normal.setZ( file.readFloat() );
            planes[ i ].d        = file.readFloat();
        }
        
        return( planes );
    }
    
    private static BSPNode[] readNodes( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kNodes );
        int num = file.lumps[ BSPDirectory.kNodes ].length / ( 4 * 9 );
        BSPNode[] nodes = new BSPNode[ num ];
        
        for (int i = 0; i < num; i++)
        {
            nodes[ i ]           = new BSPNode();
            nodes[ i ].plane     = file.readInt();
            nodes[ i ].front     = file.readInt();
            nodes[ i ].back      = file.readInt();
            
            nodes[ i ].mins[ 0 ] = file.readInt();
            nodes[ i ].mins[ 1 ] = file.readInt();
            nodes[ i ].mins[ 2 ] = file.readInt();
            
            nodes[ i ].maxs[ 0 ] = file.readInt();
            nodes[ i ].maxs[ 1 ] = file.readInt();
            nodes[ i ].maxs[ 2 ] = file.readInt();
        }
        
        return( nodes );
    }
    
    private static BSPSubModel[] readSubModels( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kModels );
        int num = file.lumps[ BSPDirectory.kModels ].length / ( 4 * 10 );
        BSPSubModel[] subModels = new BSPSubModel[ num ];
        
        for ( int i = 0; i < num; i++ )
        {
            subModels[ i ] = new BSPSubModel();
            
            subModels[ i ].min[ 0 ]     = file.readFloat();
            subModels[ i ].min[ 1 ]     = file.readFloat();
            subModels[ i ].min[ 2 ]     = file.readFloat();
            
            subModels[ i ].max[ 0 ]     = file.readFloat();
            subModels[ i ].max[ 1 ]     = file.readFloat();
            subModels[ i ].max[ 2 ]     = file.readFloat();
            
            subModels[ i ].faceIndex    = file.readInt();
            subModels[ i ].numOfFaces   = file.readInt();
            subModels[ i ].brushIndex   = file.readInt();
            subModels[ i ].numOfBrushes = file.readInt();
        }
        
        return( subModels );
    }
    
    private static String readEntities( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kEntities );
        int num = file.lumps[ BSPDirectory.kEntities ].length;
        
        byte[] ca = file.readFully( num );
        String s = new String( ca );
        
        return( s );
    }
    
    private static DirectBufferedImage[] readLightmaps( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kLightmaps );
        int num = file.lumps[ BSPDirectory.kLightmaps ].length / ( 128 * 128 * 3 );
        
        DirectBufferedImage[] lightMaps = new DirectBufferedImage[ num ];
        for ( int i = 0; i < num; i++ )
        {
            lightMaps[ i ] = DirectBufferedImage.getDirectImageRGB( 128, 128 );
            file.readFully( lightMaps[ i ].getBackingStore() );
        }
        
        return( lightMaps );
    }
    
    private static String[] readTextures( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kTextures );
        int num = file.lumps[ BSPDirectory.kTextures ].length / ( 64 + 2 * 4 );
        
        byte[] ca = new byte[ 64 ];
        String[] textures = new String[ num ];
        for ( int i = 0; i < num; i++ )
        {
            file.readFully( ca );
            file.readInt();
            file.readInt();
            String s = new String( ca );
            s = s.substring( 0, s.indexOf( 0 ) );
            textures[ i ] = s;
        }
        
        return( textures );
    }
    
    private static BSPLeaf[] readLeafs( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kLeafs );
        int num = file.lumps[ BSPDirectory.kLeafs ].length / ( 12 * 4 );
        
        BSPLeaf[] leafs = new BSPLeaf[ num ];
        for ( int i = 0; i < num; i++ )
        {
            leafs[ i ]                  = new BSPLeaf();
            leafs[ i ].cluster          = file.readInt();
            leafs[ i ].area             = file.readInt();
            
            leafs[ i ].mins[ 0 ]        = file.readInt();
            leafs[ i ].mins[ 1 ]        = file.readInt();
            leafs[ i ].mins[ 2 ]        = file.readInt();
            
            leafs[ i ].maxs[ 0 ]        = file.readInt();
            leafs[ i ].maxs[ 1 ]        = file.readInt();
            leafs[ i ].maxs[ 2 ]        = file.readInt();
            
            leafs[ i ].leafFace         = file.readInt();
            leafs[ i ].numOfLeafFaces   = file.readInt();
            
            leafs[ i ].leafBrush        = file.readInt();
            leafs[ i ].numOfLeafBrushes = file.readInt();
        }
        
        return( leafs );
    }
    
    private static int[] readLeafFaces( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kLeafFaces );
        int num = file.lumps[ BSPDirectory.kLeafFaces ].length / 4;
        
        int[] leafFaces = new int[ num ];
        for ( int i = 0; i < num; i++ )
        {
            leafFaces[ i ] = file.readInt();
        }
        
        return( leafFaces );
    }
    
    private static int[] readMeshVertices( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kMeshVerts );
        int num = file.lumps[ BSPDirectory.kMeshVerts ].length / 4;
        
        int[] meshVertices = new int[ num ];
        for ( int i = 0; i < num; i++ )
        {
            meshVertices[ i ] = file.readInt();
        }
        
        return( meshVertices );
    }
    
    private static BSPVertex[] readVertices( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kVertices );
        int num = file.lumps[ BSPDirectory.kVertices ].length / ( 11 * 4 );
        
        BSPVertex[] vertices = new BSPVertex[ num ];
        for ( int i = 0; i < num; i++ )
        {
            vertices[ i ]                 = new BSPVertex();
            vertices[ i ].position.setX(    file.readFloat() );
            vertices[ i ].position.setY(    file.readFloat() );
            vertices[ i ].position.setZ(    file.readFloat() );
            
            vertices[ i ].texCoord.setS(    file.readFloat() );
            vertices[ i ].texCoord.setT(    file.readFloat() );
            
            vertices[ i ].lightTexCoord.setS( file.readFloat() );
            vertices[ i ].lightTexCoord.setT( file.readFloat() );
            
            vertices[ i ].normal.setX(      file.readFloat() );
            vertices[ i ].normal.setY(      file.readFloat() );
            vertices[ i ].normal.setZ(      file.readFloat() );
            
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
            
            vertices[ i ].color.setRed( (float)r / 255f );
            vertices[ i ].color.setGreen( (float)g / 255f );
            vertices[ i ].color.setBlue( (float)b / 255f );
            vertices[ i ].color.setAlpha( (float)a / 255f );
        }
        
        return( vertices );
    }
    
    private static BSPFace[] readFaces( BSPFile file ) throws IOException
    {
        file.seek( BSPDirectory.kFaces );
        int num = file.lumps[ BSPDirectory.kFaces ].length / ( 26 * 4 );
        
        BSPFace[] faces = new BSPFace[ num ];
        for ( int i = 0; i < num; i++ )
        {
            faces[ i ]                       = new BSPFace();
            faces[ i ].textureID             = file.readInt();
            faces[ i ].effect                = file.readInt();
            faces[ i ].type                  = file.readInt();
            faces[ i ].vertexIndex           = file.readInt();
            faces[ i ].numOfVerts            = file.readInt();
            faces[ i ].meshVertIndex         = file.readInt();
            faces[ i ].numMeshVerts          = file.readInt();
            faces[ i ].lightmapID            = file.readInt();
            faces[ i ].lMapCorner[ 0 ]       = file.readInt();
            faces[ i ].lMapCorner[ 1 ]       = file.readInt();
            
            faces[ i ].lMapSize[ 0 ]         = file.readInt();
            faces[ i ].lMapSize[ 1 ]         = file.readInt();
            
            faces[ i ].lMapPos[ 0 ]          = file.readFloat();
            faces[ i ].lMapPos[ 1 ]          = file.readFloat();
            faces[ i ].lMapPos[ 2 ]          = file.readFloat();
            
            faces[ i ].lMapBitsets[ 0 ][ 0 ] = file.readFloat();
            faces[ i ].lMapBitsets[ 0 ][ 1 ] = file.readFloat();
            faces[ i ].lMapBitsets[ 0 ][ 2 ] = file.readFloat();
            
            faces[ i ].lMapBitsets[ 1 ][ 0 ] = file.readFloat();
            faces[ i ].lMapBitsets[ 1 ][ 1 ] = file.readFloat();
            faces[ i ].lMapBitsets[ 1 ][ 2 ] = file.readFloat();
            
            faces[ i ].vNormal[ 0 ]          = file.readFloat();
            faces[ i ].vNormal[ 1 ]          = file.readFloat();
            faces[ i ].vNormal[ 2 ]          = file.readFloat();
            
            faces[ i ].size[ 0 ]             = file.readInt();
            faces[ i ].size[ 1 ]             = file.readInt();
            //System.out.println( "type=" + faces[ i ].type + ", verts " + faces[ i ].numOfVerts + ", " + faces[ i ].numMeshVerts );
        }
        
        return( faces );
    }
    
    /**
     * Loads the BSP scene prototype.
     */
    private static BSPScenePrototype load( BSPFile bspFile ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        BSPScenePrototype prototype = new BSPScenePrototype();
        
        try
        {
            prototype.faces = readFaces( bspFile );
            prototype.vertices = readVertices( bspFile );
            prototype.lightMaps = readLightmaps( bspFile );
            prototype.visData = readVisData( bspFile );
            prototype.leafs = readLeafs( bspFile );
            prototype.textureNames = readTextures( bspFile );
            prototype.leafFaces = readLeafFaces( bspFile );
            prototype.meshVertices = readMeshVertices( bspFile );
            prototype.entities = readEntities( bspFile );
            prototype.planes = readPlanes( bspFile );
            prototype.nodes = readNodes( bspFile );
            prototype.subModels = readSubModels( bspFile );
            
            bspFile.close();
        }
        catch (IOException e)
        {
            throw( new ParsingErrorException( e.getMessage() ) );
        }
        
        return( prototype );
    }
    
    /**
     * Loads the BSP scene prototype.
     */
    public static BSPScenePrototype load( InputStream in ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        if ( !( in instanceof BufferedInputStream ) )
            in = new BufferedInputStream( in );
        
        BSPFile bspFile = new BSPResource( in );
        
        return( load( bspFile ) );
    }
    
    /**
     * Loads the BSP scene prototype.
     */
    public static BSPScenePrototype load( String filename ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        BSPFile bspFile = new BSPFile( filename );
        
        return( load( bspFile ) );
    }
}

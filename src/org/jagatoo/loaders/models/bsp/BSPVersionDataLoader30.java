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
package org.jagatoo.loaders.models.bsp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;
import org.jagatoo.loaders.models.bsp.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models.bsp.lumps.BSP30Model;
import org.jagatoo.loaders.models.bsp.lumps.BSPDirectory;
import org.jagatoo.loaders.models.bsp.lumps.BSPEdge;
import org.jagatoo.loaders.models.bsp.lumps.BSPFace;
import org.jagatoo.loaders.models.bsp.lumps.BSPTexInfo;
import org.jagatoo.loaders.models.bsp.lumps.BSPVertex;
import org.jagatoo.loaders.models.bsp.lumps.BSPVisData;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vertex3f;

/**
 * Loads BSP-level-data for Half-Life-maps (version 30).
 * 
 * @author Sebastian Thiele (aka SETIssl)
 */
public class BSPVersionDataLoader30 implements BSPVersionDataLoader
{
    private static void dumpCompressedVisData( byte[] visBytes )
    {
        File f2 = new File( "out.dat" );
        
        try
        {
            BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( f2 ) );
            
            bos.write( visBytes );
            
            bos.flush();
            bos.close();
        }
        catch ( IOException e )
        {
        }
    }
    
    public static BSPVisData decompressVis ( int numLeafs, byte[] visBytes )
    {
        dumpCompressedVisData( visBytes );
        
        BSPVisData visData = new BSPVisData();
        
        int     c;
        byte[]  out;
        int     rows;
        
        byte uInt[] = new byte[ 4 ];
        
        long l = 0;
        l = visBytes[ 0 ] & 0xFF;
        l <<= 8;
        l = visBytes[ 1 ] & 0xFF;
        l <<= 8;
        l = visBytes[ 2 ] & 0xFF;
        l <<= 8;
        l = visBytes[ 3 ] & 0xFF;
        //System.out.println( "l: " + l );
        
        //System.out.println( visBytes );
        rows = ( numLeafs + 7 ) >> 3;   // 10
        
        System.out.println( numLeafs );
        
        for ( int i = 0; i < rows; i++ )
        {
            /*
            if ( VisBytes.length != 0 )
            {
                out++ = visBytes++;
                continue;
            }
            
            c = visBytes[ 1 ];
            visBytes += 2;
            while ( c )
            {
                out++ = 0;
                c--;
            }
            */
        }
        
        /*
        do
        {
            
        }
        while ( out - decompressed < row );
        */
        
        //visData.pBitsets = out;
        
        return( visData );
    }
    
    /**
     * {@inheritDoc}
     */
    public BSPScenePrototype loadPrototypeData( BSPFile bspFile, BSPDirectory bspDir, float worldScale, AppearanceFactory appFactory ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        BSPScenePrototype prototype = new BSPScenePrototype();
        
        try
        {
            prototype.entities = BSPPrototypeLoader.readEntities( bspFile, bspDir );
            prototype.wadFiles = BSPPrototypeLoader.readWADFiles( bspFile, bspDir, prototype.entities );
            prototype.planes = BSPPrototypeLoader.readPlanes( bspFile, bspDir, worldScale );
            prototype.baseTextures = BSPPrototypeLoader.readTextures( bspFile, bspDir, prototype.wadFiles, appFactory );
            prototype.vertices = BSPPrototypeLoader.readVertices( bspFile, bspDir );
            //prototype.meshVertices = BSPPrototypeLoader.readMeshVertices( bspFile, bspDir );
            prototype.leafs = BSPPrototypeLoader.readLeafs( bspFile, bspDir);
            prototype.visData = BSPPrototypeLoader.readVisData( bspFile, bspDir, prototype.leafs.length );
            prototype.nodes = BSPPrototypeLoader.readNodes( bspFile, bspDir );
            //prototype.leafFaces = BSPPrototypeLoader.readLeafFaces( bspFile, bspDir );
            prototype.texInfos = BSPPrototypeLoader.readTexInfos( bspFile, bspDir );
            prototype.faces = BSPPrototypeLoader.readFaces( bspFile, bspDir );
            //prototype.lightMaps = BSPPrototypeLoader.readLightmaps( bspFile, bspDir, appFactory );
            //prototype.leafs = BSPPrototypeLoader.readLeafs( bspFile, bspDir);
            //prototype.leafBrushes = BSPPrototypeLoader.readLeafBrushes( bspFile, bspDir);
            prototype.edges = BSPPrototypeLoader.readEdges( bspFile, bspDir );
            //prototype.brushSides = BSPPrototypeLoader.readBrushSides( bspFile, bspDir);
            prototype.surfEdges = BSPPrototypeLoader.readSurfEdges( bspFile, bspDir );
            prototype.models = BSPPrototypeLoader.readModels( bspFile, bspDir );
            
            bspFile.close();
        }
        catch ( IOException e )
        {
            throw new ParsingErrorException( e );
        }
        
        return( prototype );
    }
    
    
    /**
     * Creates the indexed geometry array for the BSP face.  The lightmap tex coords are stored in
     * unit 1, the regular tex coords are stored in unit 0
     * 
     * @param face 
     * @return 
     */
    private Object convertFaceToGeometry( BSPFace face, int[] surfEdges, BSPEdge[] bspEdges, BSPVertex[] vertices, BSPTexInfo[] texInfos, GeometryFactory geomFactory, float worldScale )
    {
        final int numVertices = face.numOfVerts;
        
        BSPVertex[] control = new BSPVertex[ numVertices ];
        BSPVertex v0;
        //BSPVertex v1;
        
        int j = 0;
        for ( int i = 0; i < face.numOfVerts; i++ )
        {
            int sfIdx = surfEdges[ face.vertexIndex + i ];
            if ( sfIdx >= 0 )
            {
                v0 = vertices[ bspEdges[ sfIdx ].vindices[ 0 ] ];
                //v1 = vertices[ bspEdges[ sfIdx ].vindices[ 1 ] ];
            }
            else
            {
                v0 = vertices[ bspEdges[ -sfIdx ].vindices[ 1 ] ];
                //v1 = vertices[ bspEdges[ -sfIdx ].vindices[ 0 ] ];
            }
            
            control[ j++ ] = v0;
        }
        
        GeometryType geomType = GeometryType.TRIANGLE_FAN_ARRAY;
        
        Object ga = geomFactory.createInterleavedGeometry( geomType, 3,
                                                           numVertices, 0, null,
                                                           Vertex3f.COORDINATES | Vertex3f.TEXTURE_COORDINATES, false, new int[] { 2 }, null
                                                         );
        
        BSPTexInfo texInfo = texInfos[ face.textureID ];
        face.textureID = texInfo.textureID;
        
        //Matrix4f m = Matrix4f.fromPool();
        Point3f p = Point3f.fromPool();
        
        for ( int i = 0; i < numVertices; i++ )
        {
            p.set( control[ i ].position );
            p.mul( worldScale );
            
            geomFactory.setCoordinate( ga, geomType, i, new float[] { p.getX(), p.getZ(), -p.getY() }, 0, 1 );
            
            /*
            m.setIdentity();
            m.setRow( 0, texInfo.s );
            m.setRow( 1, texInfo.t );
            
            m.transform( p );
            */
            
            float u = p.getX() * texInfo.s[0] + p.getZ() * texInfo.s[2] + -p.getY() * texInfo.s[1] + texInfo.s[3];
            float v = p.getX() * texInfo.t[0] + p.getZ() * texInfo.t[2] + -p.getY() * texInfo.t[1] + texInfo.t[3];
            
            geomFactory.setTexCoord( ga, geomType, 0, 2, i, new float[] { u, v }, 0, 1 );
            
            //geomFactory.setTexCoord( ga, geomType, 0, 2, i, new float[] { p.getX(), p.getY() }, 0, 1 );
            //geomFactory.setTexCoord( ga, geomType, 1, 2, i, new float[] { ps.mPoints[ i ].lightTexCoord.getS(), ps.mPoints[ i ].lightTexCoord.getT() }, 0, 1 );
            //geomFactory.setColor( ga, geomType, ps.mPoints[ i ].color.hasAlpha() ? 4 : 3, i, new float[] { ps.mPoints[ i ].color.getRed(), ps.mPoints[ i ].color.getGreen(), ps.mPoints[ i ].color.getBlue(), ps.mPoints[ i ].color.getAlpha() }, 0, 1 );
        }
        
        Point3f.toPool( p );
        //Matrix4f.toPool( m );
        
        geomFactory.finalizeGeometry( ga, geomType, 0, numVertices, 0, 0 );
        
        return( ga );
    }
    
    /**
     * {@inheritDoc}
     */
    public void convertFacesToGeometries( BSPScenePrototype prototype, GeometryFactory geomFactory, float worldScale )
    {
        int numModels = prototype.models.length;
        
        prototype.geometries = new Object[ numModels ][];
        
        for ( int m = 0; m < numModels; m++ )
        {
            final BSP30Model model = (BSP30Model)prototype.models[ m ];
            final int numFaces = model.numOfFaces;
            
            Object[] geometries = new Object[ numFaces ];
            
            for ( int f = 0; f < numFaces; f++ )
            {
                geometries[ f ] = convertFaceToGeometry( prototype.faces[ model.faceIndex + f ], prototype.surfEdges, prototype.edges, prototype.vertices, prototype.texInfos, geomFactory, worldScale );
            }
            
            prototype.geometries[ m ] = geometries;
        }
    }
}

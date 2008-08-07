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

import java.io.IOException;
import java.nio.ByteBuffer;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models.bsp.lumps.BSP30Model;
import org.jagatoo.loaders.models.bsp.lumps.BSPDirectory;
import org.jagatoo.loaders.models.bsp.lumps.BSPEdge;
import org.jagatoo.loaders.models.bsp.lumps.BSPFace;
import org.jagatoo.loaders.models.bsp.lumps.BSPTexInfo;
import org.jagatoo.loaders.models.bsp.lumps.BSPVertex;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.opengl.enums.TextureImageFormat;
import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vertex3f;

/**
 * Loads BSP-level-data for Half-Life-maps (version 30).
 * 
 * @author Sebastian Thiele (aka SETIssl)
 */
public class BSPVersionDataLoader30 implements BSPVersionDataLoader
{
    public static byte[] decompressVis( int numLeafs, byte[] compressed )
    {
        //dumpCompressedVisData( compressed );
        
        //byte[] decompressed = new byte[ numLeafs * ( numLeafs - 1 ) ];
        //Arrays.fill( decompressed, (byte)0xFF );
        byte[] decompressed = null;
        
        return( decompressed );
    }
    
    /**
     * {@inheritDoc}
     */
    public BSPScenePrototype loadPrototypeData( BSPFile bspFile, BSPDirectory bspDir, float worldScale, AppearanceFactory appFactory ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        BSPScenePrototype prototype = new BSPScenePrototype( bspFile.getVersion() );
        
        try
        {
            prototype.entities = BSPPrototypeLoader.readEntities( bspFile, bspDir );
            prototype.wadFiles = BSPPrototypeLoader.readWADFiles( bspFile, bspDir, prototype.entities );
            prototype.planes = BSPPrototypeLoader.readPlanes( bspFile, bspDir, worldScale );
            prototype.baseTextures = BSPPrototypeLoader.readTextures( bspFile, bspDir, prototype.wadFiles, prototype.entities, appFactory );
            prototype.vertices = BSPPrototypeLoader.readVertices( bspFile, bspDir );
            //prototype.meshVertices = BSPPrototypeLoader.readMeshVertices( bspFile, bspDir );
            prototype.leafs = BSPPrototypeLoader.readLeafs( bspFile, bspDir);
            prototype.visData = BSPPrototypeLoader.readVisData( bspFile, bspDir, prototype.leafs.length );
            prototype.nodes = BSPPrototypeLoader.readNodes( bspFile, bspDir );
            //prototype.leafFaces = BSPPrototypeLoader.readLeafFaces( bspFile, bspDir );
            prototype.texInfos = BSPPrototypeLoader.readTexInfos( bspFile, bspDir );
            prototype.faces = BSPPrototypeLoader.readFaces( bspFile, bspDir );
            prototype.lightMaps = new AbstractTexture[ prototype.faces.length ];
            prototype.lightMapData = BSPPrototypeLoader.readLightmapData( bspFile, bspDir );
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
    private NamedObject convertFaceToGeometry( int faceIndex, BSPFace face, int[] surfEdges, BSPEdge[] bspEdges, BSPVertex[] vertices, BSPTexInfo[] texInfos, AbstractTexture[][] baseTextures, byte[] lightMapData, AbstractTexture[] lightMaps, AppearanceFactory appFactory, GeometryFactory geomFactory, float worldScale )
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
        
        /*
         * TODO: ATTENTION!
         * 
         * This must be removed to show lightmaps.
         * But since lightmaps are not currently working, we disable
         * them through this line.
         */
        face.lightmapID = -1;
        
        NamedObject ga = geomFactory.createInterleavedGeometry( "Geometry " + faceIndex,
                                                                geomType, 3,
                                                                numVertices, 0, null,
                                                                Vertex3f.COORDINATES | Vertex3f.TEXTURE_COORDINATES, false, ( face.lightmapID >= 0 ) ? new int[] { 2, 2 } : new int[] { 2 }, null
                                                              );
        
        BSPTexInfo texInfo = texInfos[ face.textureID ];
        face.textureID = texInfo.textureID;
        
        //Matrix4f m = Matrix4f.fromPool();
        Point3f p = Point3f.fromPool();
        
        float min_u = Float.MAX_VALUE;
        float max_u = -Float.MAX_VALUE;
        float min_v = Float.MAX_VALUE;
        float max_v = -Float.MAX_VALUE;
        
        for ( int i = 0; i < numVertices; i++ )
        {
            p.set( control[ i ].position );
            
            //geomFactory.setCoordinate( ga, geomType, i, new float[] { p.getX(), p.getZ(), -p.getY() }, 0, 1 );
            geomFactory.setCoordinate( ga, geomType, i, new float[] { p.getX() * worldScale, p.getZ() * worldScale, -p.getY() * worldScale }, 0, 1 );
            
            //float u = p.getX() * texInfo.s[0] + p.getZ() * texInfo.s[2] + -p.getY() * texInfo.s[1] + texInfo.s[3];
            //float v = p.getX() * texInfo.t[0] + p.getZ() * texInfo.t[2] + -p.getY() * texInfo.t[1] + texInfo.t[3];
            float u = p.getX() * texInfo.s[0] + p.getY() * texInfo.s[1] + p.getZ() * texInfo.s[2] + texInfo.s[3];
            float v = p.getX() * texInfo.t[0] + p.getY() * texInfo.t[1] + p.getZ() * texInfo.t[2] + texInfo.t[3];
            
            if ( face.textureID >= 0 )
            {
                int orgWidth = baseTextures[ face.textureID ][0].getImage( 0 ).getOriginalWidth();
                int orgHeight = baseTextures[ face.textureID ][0].getImage( 0 ).getOriginalHeight();
                //int width = baseTextures[ face.textureID ][0].getWidth();
                //int height = baseTextures[ face.textureID ][0].getHeight();
                
                u /= orgWidth;
                v /= orgHeight;
            }
            
            if ( u < min_u )
                min_u = u;
            if ( u > max_u )
                max_u = u;
            if ( v < min_v )
                min_v = v;
            if ( v > max_v )
                max_v = v;
            
            geomFactory.setTexCoord( ga, geomType, 0, 2, i, new float[] { u, v }, 0, 1 );
        }
        
        Point3f.toPool( p );
        
        if ( face.lightmapID >= 0 )
        {
            int lightMapWidth = (int)FastMath.ceil( max_u / 16f ) - (int)FastMath.floor( min_u / 16f ) + 1;
            int lightMapHeight = (int)FastMath.ceil( max_v / 16f ) - (int)FastMath.floor( min_v / 16f ) + 1;
            
            //System.out.println( lightMapWidth + "x" + lightMapHeight );
            
            int lightMapDataOffset = face.lightmapID;
            face.lightmapID = faceIndex;
            
            AbstractTextureImage texImg0 = appFactory.createTextureImage( TextureImageFormat.RGB, lightMapWidth, lightMapHeight );
            
            ByteBuffer bb = texImg0.getDataBuffer();
            bb.position( 0 );
            bb.put( lightMapData, lightMapDataOffset, lightMapWidth * lightMapHeight * 3 );
            bb.flip();
            
            lightMaps[faceIndex] = appFactory.createTexture( texImg0, true );
            
            for ( int i = 0; i < numVertices; i++ )
            {
                p = control[ i ].position;
                
                float u = p.getX() * texInfo.s[0] + p.getY() * texInfo.s[1] + p.getZ() * texInfo.s[2] + texInfo.s[3];
                float v = p.getX() * texInfo.t[0] + p.getY() * texInfo.t[1] + p.getZ() * texInfo.t[2] + texInfo.t[3];
                
                u /= lightMapWidth;
                v /= lightMapHeight;
                
                geomFactory.setTexCoord( ga, geomType, 1, 2, i, new float[] { u, v }, 0, 1 );
            }
        }
        
        geomFactory.finalizeGeometry( ga, geomType, 0, numVertices, 0, 0 );
        
        return( ga );
    }
    
    /**
     * {@inheritDoc}
     */
    public void convertFacesToGeometries( BSPScenePrototype prototype, AppearanceFactory appFactory, GeometryFactory geomFactory, float worldScale )
    {
        int numModels = prototype.models.length;
        
        prototype.geometries = new NamedObject[ numModels ][];
        
        for ( int m = 0; m < numModels; m++ )
        {
            final BSP30Model model = (BSP30Model)prototype.models[ m ];
            final int numFaces = model.numOfFaces;
            
            NamedObject[] geometries = new NamedObject[ numFaces ];
            
            for ( int f = 0; f < numFaces; f++ )
            {
                geometries[ f ] = convertFaceToGeometry( f, prototype.faces[ model.faceIndex + f ], prototype.surfEdges, prototype.edges, prototype.vertices, prototype.texInfos, prototype.baseTextures, prototype.lightMapData, prototype.lightMaps, appFactory, geomFactory, worldScale );
            }
            
            prototype.geometries[ m ] = geometries;
        }
    }
}

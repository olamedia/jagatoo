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

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;
import org.jagatoo.loaders.models.bsp.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models.bsp.lumps.BSP46Model;
import org.jagatoo.loaders.models.bsp.lumps.BSPDirectory;
import org.jagatoo.loaders.models.bsp.lumps.BSPFace;
import org.jagatoo.loaders.models.bsp.lumps.BSPVertex;
import org.jagatoo.loaders.models.bsp.util.PatchSurface;
import org.openmali.spatial.bounds.BoundingBox;
import org.openmali.vecmath2.Vertex3f;

/**
 * Loads BSP-level-data for Q3-maps (version 46).
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class BSPVersionDataLoader46 implements BSPVersionDataLoader
{
    /**
     * {@inheritDoc}
     */
    public BSPScenePrototype loadPrototypeData( BSPFile bspFile, BSPDirectory bspDir, float worldScale, AppearanceFactory appFactory ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        BSPScenePrototype prototype = new BSPScenePrototype();
        
        try
        {
            prototype.faces = BSPPrototypeLoader.readFaces( bspFile, bspDir );
            prototype.vertices = BSPPrototypeLoader.readVertices( bspFile, bspDir );
            prototype.lightMaps = BSPPrototypeLoader.readLightmaps( bspFile, bspDir, appFactory );
            prototype.leafs = BSPPrototypeLoader.readLeafs( bspFile, bspDir );
            prototype.visData = BSPPrototypeLoader.readVisData( bspFile, bspDir, prototype.leafs.length );
            prototype.baseTextures = BSPPrototypeLoader.readTextures( bspFile, bspDir, appFactory );
            prototype.leafFaces = BSPPrototypeLoader.readLeafFaces( bspFile, bspDir );
            prototype.meshVertices = BSPPrototypeLoader.readMeshVertices( bspFile, bspDir );
            prototype.entities = BSPPrototypeLoader.readEntities( bspFile, bspDir );
            prototype.planes = BSPPrototypeLoader.readPlanes( bspFile, bspDir, worldScale );
            prototype.nodes = BSPPrototypeLoader.readNodes( bspFile, bspDir );
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
    private Object convertFaceToIndexedGeom( BSPFace face, BSPVertex[] vertices, int[] meshVertices, GeometryFactory geomFactory, float worldScale )
    {
        GeometryType geomType = GeometryType.INDEXED_TRIANGLE_ARRAY;
        Object ga = geomFactory.createInterleavedGeometry( geomType, 3,
                                                           face.numOfVerts, face.numMeshVerts, null,
                                                           Vertex3f.COORDINATES | Vertex3f.TEXTURE_COORDINATES, false, new int[] { 2, 2 }, null
                                                         );
        
        for ( int i = 0; i < face.numOfVerts; i++ )
        {
            int j = face.vertexIndex + i;
            geomFactory.setCoordinate( ga, geomType, i, new float[] { vertices[ j ].position.getX() * worldScale, vertices[ j ].position.getZ() * worldScale, -vertices[ j ].position.getY() * worldScale }, 0, 1 );
            geomFactory.setTexCoord( ga, geomType, 0, 2, i, new float[] { vertices[ j ].texCoord.getS(), vertices[ j ].texCoord.getT() }, 0, 1 );
            geomFactory.setTexCoord( ga, geomType, 1, 2, i, new float[] { vertices[ j ].lightTexCoord.getS(), vertices[ j ].lightTexCoord.getT() }, 0, 1 );
            
            /*
            if ( vertices[ j ].color.getRed() < 0f )
                throw new Error( "illegal color + " + vertices[ j ].color );
            
            geomFactory.setColor( ga, geomType, vertices[ j ].color.hasAlpha() ? 4 : 3, i, new float[] { vertices[ j ].color.getRed(), vertices[ j ].color.getGreen(), vertices[ j ].color.getBlue(), vertices[ j ].color.getAlpha() }, 0, 1 );
            */
        }
        
        int[] index = new int[ face.numMeshVerts ];
        System.arraycopy( meshVertices, face.meshVertIndex, index, 0, face.numMeshVerts );
        
        geomFactory.setIndex( ga, geomType, 0, index, 0, index.length );
        
        geomFactory.finalizeGeometry( ga, geomType, 0, face.numOfVerts, 0, index.length );
        
        return( ga );
    }
    
    private Object convertFaceToSurfacePatch( BSPFace face, BSPVertex[] vertices, GeometryFactory geomFactory, float worldScale )
    {
        BSPVertex[] control = new BSPVertex[ face.numOfVerts ];
        
        for ( int i = 0; i < face.numOfVerts; i++ )
            control[ i ] = vertices[ face.vertexIndex + i ];
        
        PatchSurface ps = new PatchSurface( control, face.numOfVerts, face.size[ 0 ], face.size[ 1 ] );
        
        GeometryType geomType = GeometryType.INDEXED_TRIANGLE_ARRAY;
        
        /*
        Object ga = geomFactory.createGeometry( geomType, 3,
                                                ps.mPoints.length, ps.mIndices.length,
                                                null
                                              );
        */
        Object ga = geomFactory.createInterleavedGeometry( geomType, 3,
                                                           ps.mPoints.length, ps.mIndices.length, null,
                                                           Vertex3f.COORDINATES | Vertex3f.TEXTURE_COORDINATES, false, new int[] { 2, 2 }, null
                                                         );
        
        for ( int i = 0; i < ps.mPoints.length; i++ )
        {
            geomFactory.setCoordinate( ga, geomType, i, new float[] { ps.mPoints[ i ].position.getX() * worldScale, ps.mPoints[ i ].position.getZ() * worldScale, -ps.mPoints[ i ].position.getY() * worldScale }, 0, 1 );
            geomFactory.setTexCoord( ga, geomType, 0, 2, i, new float[] { ps.mPoints[ i ].texCoord.getS(), ps.mPoints[ i ].texCoord.getT() }, 0, 1 );
            geomFactory.setTexCoord( ga, geomType, 1, 2, i, new float[] { ps.mPoints[ i ].lightTexCoord.getS(), ps.mPoints[ i ].lightTexCoord.getT() }, 0, 1 );
            //geomFactory.setColor( ga, geomType, ps.mPoints[ i ].color.hasAlpha() ? 4 : 3, i, new float[] { ps.mPoints[ i ].color.getRed(), ps.mPoints[ i ].color.getGreen(), ps.mPoints[ i ].color.getBlue(), ps.mPoints[ i ].color.getAlpha() }, 0, 1 );
        }
        
        geomFactory.setIndex( ga, geomType, 0, ps.mIndices, 0, ps.mIndices.length );
        
        geomFactory.finalizeGeometry( ga, geomType, 0, ps.mPoints.length, 0, ps.mIndices.length );
        
        return( ga );
    }
    
    /**
     * {@inheritDoc}
     */
    public void convertFacesToGeometries( BSPScenePrototype prototype, GeometryFactory geomFactory, float worldScale )
    {
        //int numModels = prototype.models.length;
        int numModels = 1;
        
        prototype.geometries = new Object[ numModels ][];
        
        for ( int m = 0; m < numModels; m++ )
        {
            final BSP46Model model = (BSP46Model)prototype.models[ m ];
            final int numFaces = model.numOfFaces;
            
            Object[] geometries = new Object[ numFaces ];
            
            for ( int f = 0; f < model.numOfFaces; f++ )
            {
                BSPFace face = prototype.faces[ model.faceIndex + f ];
                
                switch ( face.type )
                {
                    case 1:
                        // regular mesh
                        geometries[ f ] = convertFaceToIndexedGeom( face, prototype.vertices, prototype.meshVertices, geomFactory, worldScale );
                        break;
                    
                    case 2:
                        geometries[ f ] = convertFaceToSurfacePatch( face, prototype.vertices, geomFactory, worldScale );
                        break;
                    
                    case 3:
                        geometries[ f ] = convertFaceToIndexedGeom( face, prototype.vertices, prototype.meshVertices, geomFactory, worldScale );
                        break;
                }
            }
            
            prototype.geometries[ m ] = geometries;
            
            prototype.boundingBox = new BoundingBox( model.min[ 0 ] * worldScale, model.min[ 2 ] * worldScale, -model.min[ 1 ] * worldScale,
                                                     model.max[ 0 ] * worldScale, model.max[ 2 ] * worldScale, -model.max[ 1 ] * worldScale
                                                   );
        }
    }
}
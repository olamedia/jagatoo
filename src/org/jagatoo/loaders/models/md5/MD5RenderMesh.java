/**
 * Copyright (c) 2003-2006 KProject
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'KProject' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jagatoo.loaders.models.md5;

import org.jagatoo.loaders.models.md5.animation.MD5Frame;
import org.jagatoo.loaders.models.md5.mesh.MD5Bone;
import org.jagatoo.loaders.models.md5.mesh.MD5Mesh;
import org.jagatoo.loaders.models.md5.mesh.MD5MeshModel;
import org.jagatoo.loaders.models.md5.mesh.MD5Triangle;
import org.jagatoo.loaders.models.md5.mesh.MD5Weight;
import org.jagatoo.loaders.models.md5.reader.MD5MathUtil;
import org.jagatoo.util.nio.BufferUtils;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;

/**
 * @author kman
 */
public class MD5RenderMesh
{
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer normalBuffer;
    protected FloatBuffer texBuffer;
    protected IntBuffer indexBuffer;
    protected MD5MeshModel model;
    protected MD5Mesh subMesh;
    
    public MD5RenderMesh( MD5MeshModel model, MD5Mesh subMesh )
    {
        this.model = model;
        this.subMesh = subMesh;
    }
    
    public void computeTriMesh()
    {
        int[] indices = new int[ subMesh.numtris * 3 ];
        for ( int i = 0; i < subMesh.numtris; i++ )
        {
            MD5Triangle tr = subMesh.triangle.elementAt( i );
            /*
            indexBuffer.put( ( i * 3 ) + 0, tr.triangle[ 0 ] );
            indexBuffer.put( ( i * 3 ) + 1, tr.triangle[ 1 ] );
            indexBuffer.put( ( i * 3 ) + 2, tr.triangle[ 2 ] );
            */
            indices[ ( i * 3 ) + 0 ] = tr.triangle[ 0 ];
            indices[ ( i * 3 ) + 1 ] = tr.triangle[ 1 ];
            indices[ ( i * 3 ) + 2 ] = tr.triangle[ 2 ];
        }
        indexBuffer = BufferUtils.createIntBuffer( indices );
        TexCoord2f[] te = new TexCoord2f[ subMesh.numvert ];
        for ( int i = 0; i < subMesh.numvert; i++ )
        {
            TexCoord2f tt = subMesh.verts.elementAt( i ).texCoord;
            //BufferUtils.setInBuffer(tt,texBuffer,i);
            te[ i ] = tt;
        }
        texBuffer = TexCoord2f.writeToBuffer( te, BufferUtils.createFloatBuffer( te.length * 2 ) );
        
        Point3f[] vertices = new Point3f[ subMesh.numvert ];
        for ( int i = 0; i < subMesh.numvert; i++ )
        {
            Point3f finalVertex = new Point3f();
            /* calculate final vertex to draw with weights */
            for ( int j = 0; j < subMesh.verts.elementAt( i ).weightCount; j++ )
            {
                MD5Weight weight = subMesh.weights.elementAt( subMesh.verts.elementAt( i ).firstWeight + j );
                MD5Bone joint = model.getSkeleton().getBones().elementAt( weight.joint );
                Point3f wv = MD5MathUtil.Quat_rotatePoint( joint.getRotation(), weight.offset );
                finalVertex.addX( ( ( joint.getTranslation().getX() + wv.getX() ) * weight.weight ) );
                finalVertex.addY( ( ( joint.getTranslation().getY() + wv.getY() ) * weight.weight ) );
                finalVertex.addZ( ( ( joint.getTranslation().getZ() + wv.getZ() ) * weight.weight ) );
            }
            vertices[ i ] = finalVertex;
        }
        vertexBuffer = Point3f.writeToBuffer( vertices, BufferUtils.createFloatBuffer( vertices.length * 3 ) );
        normalBuffer = computeNormals( subMesh.numvert, vertexBuffer, subMesh.triangle );
        //return( new TriMesh( subMesh.name, vertexBuffer, normalBuffer, null, texBuffer, indexBuffer );
    }
    
    public void computeTrimesh( MD5Frame frame )
    {
        int[] indices = new int[ subMesh.numtris * 3 ];
        for ( int i = 0; i < subMesh.numtris; i++ )
        {
            MD5Triangle tr = subMesh.triangle.elementAt( i );
            /*indexBuffer.put(i * 3, tr.triangle[0]);
            indexBuffer.put((i * 3) + 1, tr.triangle[1]);
            indexBuffer.put((i * 3) + 2, tr.triangle[2]);*/
            indices[ i * 3 ] = tr.triangle[ 0 ];
            indices[ ( i * 3 ) + 1 ] = tr.triangle[ 1 ];
            indices[ ( i * 3 ) + 2 ] = tr.triangle[ 2 ];
        }
        indexBuffer = BufferUtils.createIntBuffer( indices );
        TexCoord2f[] te = new TexCoord2f[ subMesh.numvert ];
        for ( int i = 0; i < subMesh.numvert; i++ )
        {
            TexCoord2f tt = subMesh.verts.elementAt( i ).texCoord;
            //BufferUtils.setInBuffer(tt,texBuffer,i);
            te[ i ] = tt;
        }
        texBuffer = TexCoord2f.writeToBuffer( te, BufferUtils.createFloatBuffer( te.length * 2 ) );
        
        Point3f[] vertices = new Point3f[ subMesh.numvert ];
        for ( int i = 0; i < subMesh.numvert; i++ )
        {
            Point3f finalVertex = new Point3f();
            /* calculate final vertex to draw with weights */
            for ( int j = 0; j < subMesh.verts.elementAt( i ).weightCount; j++ )
            {
                MD5Weight weight = subMesh.weights.elementAt( subMesh.verts.elementAt( i ).firstWeight + j );
                //MD5Bone skelJoint = model.getSkeleton().getBone( weight.joint );
                MD5Bone joint = frame.bones.elementAt( weight.joint );
                //joint.getTranslation().addLocal( skelJoint.getTranslation() );
                //joint.getRotation().multLocal( skelJoint.getRotation() );
                Point3f wv = MD5MathUtil.Quat_rotatePoint( joint.getRotation(), weight.offset );
                finalVertex.addX( ( ( joint.getTranslation().getX() + wv.getX() ) * weight.weight ) );
                finalVertex.addY( ( ( joint.getTranslation().getY() + wv.getY() ) * weight.weight ) );
                finalVertex.addZ( ( ( joint.getTranslation().getZ() + wv.getZ() ) * weight.weight ) );
            }
            vertices[ i ] = finalVertex;
        }
        vertexBuffer = Point3f.writeToBuffer( vertices, BufferUtils.createFloatBuffer( vertices.length * 3 ) );
        //normalBuffer = computeNormals( subMesh.numvert, vertexBuffer, subMesh.triangle );
    }
    
    private static final void readTupleFromBuffer( Tuple3f tup, FloatBuffer buff, int index )
    {
        tup.setX( buff.get( index * 3 + 0 ) );
        tup.setY( buff.get( index * 3 + 1 ) );
        tup.setZ( buff.get( index * 3 + 2 ) );        
    }
    
    public FloatBuffer computeNormals( int numvert, FloatBuffer vertexBuffer, Vector< MD5Triangle > triangle )
    {
        Point3f coord1 = new Point3f();
        Point3f coord2 = new Point3f();
        Point3f coord3 = new Point3f();
        
        Vector3f tmp1 = new Vector3f();
        Vector3f tmp2 = new Vector3f();
        
        // Get the current object
        
        // Here we allocate all the memory we need to calculate the normals
        Vector3f[] tempNormals = new Vector3f[ triangle.size() ];
        Vector3f[] normals = new Vector3f[ numvert ];
        int numtris = triangle.size();
        // Go though all of the faces of this object
        for ( int i = 0; i < numtris; i++ )
        {
            readTupleFromBuffer( coord1, vertexBuffer, triangle.elementAt( i ).triangle[ 0 ] );
            readTupleFromBuffer( coord2, vertexBuffer, triangle.elementAt( i ).triangle[ 1 ] );
            readTupleFromBuffer( coord3, vertexBuffer, triangle.elementAt( i ).triangle[ 2 ] );
            
            tmp1.sub( coord1, coord3 );
            tmp2.sub( coord3, coord2 );
            
            tmp1.cross( tmp1, tmp2 );
            tmp1.normalize();
            
            tempNormals[ i ] = new Vector3f( tmp1 );
        }
        
        Vector3f sum = new Vector3f();
        int shared = 0;
        
        for ( int i = 0; i < numvert; i++ )
        {
            for ( int j = 0; j < numtris; j++ )
            {
                if ( triangle.elementAt( j ).triangle[ 0 ] == i || triangle.elementAt( j ).triangle[ 1 ] == i || triangle.elementAt( j ).triangle[ 2 ] == i )
                {
                    sum.add( tempNormals[ j ] );
                    
                    shared++;
                }
            }
            
            sum.div( (float)( -shared ) );
            sum.normalize();
            sum.negate();
            normals[ i ] = new Vector3f( sum );
            
            sum.setZero(); // Reset the sum
            shared = 0; // Reset the shared
        }
        
        return( Vector3f.writeToBuffer( normals, BufferUtils.createFloatBuffer( normals.length * 3 ) ) );
    }
    
    public FloatBuffer getVertexBuffer()
    {
        return( vertexBuffer );
    }
    
    public FloatBuffer getNormalBuffer()
    {
        return( normalBuffer );
    }
    
    public FloatBuffer getTexBuffer()
    {
        return( texBuffer );
    }
    
    public IntBuffer getIndexBuffer()
    {
        return( indexBuffer );
    }
    
    public MD5MeshModel getModel()
    {
        return( model );
    }
    
    public MD5Mesh getSubMesh()
    {
        return( subMesh );
    }
}

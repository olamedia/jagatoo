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
package org.jagatoo.loaders.models.obj;

import java.util.List;

import org.jagatoo.util.geometry.Polygon;
import org.openmali.vecmath.TexCoord2f;
import org.openmali.vecmath.Vector3f;

/**
 * An OBJFace.
 * 
 * @author Kevin Glass
 */
public class OBJFace
{
    private OBJFaceList  faceList;
    private int[]        vData;
    private int[]        tData;
    private int[]        nData;
    private int i;
    private OBJMaterial  mat;
    private Polygon      poly = new Polygon();
    
    public OBJMaterial getMaterial()
    {
        return( mat );
    }
    
    public int getCount()
    {
        return( ( vData.length - 2 ) * 3 );
    }
    
    private int findIndex( Vector3f vt )
    {
        for ( int i = 0; i < vData.length; i++ )
        {
            if ( faceList.getVertexList().get( vData[ i ] ) == vt )
            {
                return( i );
            }
        }
        
        return( -1 );
    }
    
    public void configure( Vector3f[] dataVerts, Vector3f[] dataNormals, TexCoord2f[] dataTexs, int index )
    {
        Vector3f v1 = new Vector3f();
        Vector3f v2 = new Vector3f();
        Vector3f faceNormal = new Vector3f();
        
        v1.sub( faceList.getVertexList().get( vData[ 1 ] ), faceList.getVertexList().get( vData[ 0 ] ) );
        v2.sub( faceList.getVertexList().get( vData[ 2 ] ), faceList.getVertexList().get( vData[ 0 ] ) );
        faceNormal.cross( v1, v2 );
        faceNormal.normalize();
        faceNormal.scale( -1 );
        
        for ( int i = 0; i < vData.length; i++ )
        {
            poly.add( faceList.getVertexList().get( vData[ i ] ) );
        }
        
        List<Polygon> list = poly.getAsTriangles();
        for ( int i = 0; i < list.size(); i++ )
        {
            Polygon tri = list.get( i );
            for ( int v = 0; v < 3; v++ )
            {
                int ptIndex = index + ( i * 3 ) + v;
                int vIndex = findIndex( (Vector3f)tri.getVertices().get( v ) );
                
                //array.setCoordinate( index + i, verts.get( vData[ i ] ) );
                dataVerts[ ptIndex ] = faceList.getVertexList().get( vData[ vIndex ] );
                
                if ( ( nData[ i ] != -1 ) && ( faceList.normalsSupported() ) )
                {
                    dataNormals[ ptIndex ] = faceList.getNormalList().get( nData[ vIndex ] );
                }
                else
                {
                    dataNormals[ ptIndex ] = faceNormal;
                }
                
                if ( ( tData[ i ] != -1) && (faceList.texturesSupported() ) )
                {
                    dataTexs[ ptIndex ] = faceList.getTexList().get( tData[ vIndex ] );
                }
            }
        }
    }
    
    protected void add( int v, int n, int t )
    {
        vData[ i ] = v;
        nData[ i ] = n;
        tData[ i ] = t;
        
        i++;
    }
    
    public OBJFace( OBJFaceList faceList, int count, OBJMaterial mat )
    {
        this.faceList = faceList;
        
        this.vData = new int[ count ];
        this.nData = new int[ count ];
        this.tData = new int[ count ];
        
        this.mat = mat;
    }
}

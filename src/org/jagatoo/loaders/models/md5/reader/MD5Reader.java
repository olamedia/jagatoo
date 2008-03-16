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
 * Copyright (c) 2006 KProject
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
package org.jagatoo.loaders.models.md5.reader;

import org.jagatoo.loaders.models.md5.mesh.MD5Bone;
import org.jagatoo.loaders.models.md5.mesh.MD5Mesh;
import org.jagatoo.loaders.models.md5.mesh.MD5MeshModel;
import org.jagatoo.loaders.models.md5.mesh.MD5Skeleton;
import org.jagatoo.loaders.models.md5.mesh.MD5Triangle;
import org.jagatoo.loaders.models.md5.mesh.MD5Vertex;
import org.jagatoo.loaders.models.md5.mesh.MD5Weight;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author kman
 */
public class MD5Reader
{
    public static MD5MeshModel readMeshFile( InputStream in ) throws IOException
    {
        MD5MeshModel result = new MD5MeshModel();
        Vector< String > joints = new Vector< String >();
        
        BufferedReader buff = new BufferedReader( new InputStreamReader( in ) );
        String input;
        while ( ( input = buff.readLine() ) != null )
        {
            input = input.trim();
            if ( input.equals( "" ) )
            {
            }
            else if ( input.startsWith( "MD5Version" ) )
            {
                
            }
            else if ( input.startsWith( "numJoints" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                //result.numJoints = Integer.parseInt( str.nextToken() );
            }
            else if ( input.startsWith( "numMeshes" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                //result.numMeshes = Integer.parseInt( str.nextToken() );
            }
            else if ( input.startsWith( "joints" ) )
            {
                while ( ( input = buff.readLine() ) != null )
                {
                    input = input.trim();
                    if ( input.equals( "" ) )
                    {
                    }
                    else if ( input.trim().equals( "}" ) )
                    {
                        break;
                    }
                    else
                    {
                        joints.addElement( input.trim() );
                    }
                }
                Vector< MD5Bone > bones = compileJoints( joints );
                MD5Skeleton skeleton = new MD5Skeleton();
                skeleton.setBones( bones );
                result.setSkeleton( skeleton );
            }
            else if ( input.startsWith( "mesh" ) )
            {
                Vector< String > mesh = new Vector< String >();
                mesh.addElement( input );
                while ( ( input = buff.readLine() ) != null )
                {
                    input = input.trim();
                    if ( input.equals( "" ) )
                    {
                    }
                    else if ( input.trim().equals( "}" ) )
                    {
                        break;
                    }
                    else
                    {
                        mesh.addElement( input.trim() );
                    }
                }
                result.addMesh( compileMesh( mesh ) );
            }
        }
        
        return( result );
    }
    
    private static MD5Mesh compileMesh( Vector< String > lines )
    {
        MD5Mesh result = new MD5Mesh();
        Vector< MD5Vertex > verts = new Vector< MD5Vertex >();
        Vector< MD5Triangle > tris = new Vector< MD5Triangle >();
        Vector< MD5Weight > ws = new Vector< MD5Weight >();
        
        for ( String line: lines )
        {
            if ( line.trim().equals( "" ) )
            {
            }
            else if ( line.trim().startsWith( "mesh" ) )
            {
                StringTokenizer str = new StringTokenizer( line.trim() );
                str.nextToken();
                result.name = str.nextToken();
                //System.out.println( "NAME:" + result.name );
            }
            else if ( line.trim().startsWith( "shader" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                String shader = str.nextToken().replace( '\"', ' ' ).trim();
                result.shader = shader;
            }
            else if ( line.trim().startsWith( "numverts" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                int numVerts = Integer.parseInt( str.nextToken() );
                result.numvert = numVerts;
            }
            else if ( line.trim().startsWith( "numtris" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                int numtris = Integer.parseInt( str.nextToken() );
                result.numtris = numtris;
            }
            else if ( line.trim().startsWith( "numweights" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                int numweights = Integer.parseInt( str.nextToken() );
                result.numweight = numweights;
            }
            else if ( line.trim().startsWith( "vert" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                //vert 0 ( 0.878716 0.639479 ) 0 1
                MD5Vertex def = new MD5Vertex();
                def.index = Integer.parseInt( str.nextToken() );
                str.nextToken();
                def.texCoord = new TexCoord2f();
                def.texCoord.setS( Float.parseFloat( str.nextToken() ) );
                def.texCoord.setT( 1.0f - Float.parseFloat( str.nextToken() ) );
                str.nextToken();
                def.firstWeight = Integer.parseInt( str.nextToken() );
                def.weightCount = Integer.parseInt( str.nextToken() );
                verts.addElement( def );
            }
            //numtris 1984
            //tri 0 0 2 1
            else if ( line.startsWith( "tri" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                MD5Triangle tri = new MD5Triangle();
                //tri.triangle=new Vector3f();
                tri.index = Integer.parseInt( str.nextToken() );
                tri.triangle[ 0 ] = Integer.parseInt( str.nextToken() );
                tri.triangle[ 1 ] = Integer.parseInt( str.nextToken() );
                tri.triangle[ 2 ] = Integer.parseInt( str.nextToken() );
                tris.addElement( tri );
            }
            //numweights 1375
            //weight 0 4 1.000000 ( -0.003532 1.366476 -0.035214 )
            else if ( line.startsWith( "weight" ) )
            {
                StringTokenizer str = new StringTokenizer( line );
                str.nextToken();
                MD5Weight w = new MD5Weight();
                w.index = Integer.parseInt( str.nextToken() );
                w.joint = Integer.parseInt( str.nextToken() );
                w.weight = Float.parseFloat( str.nextToken() );
                str.nextToken();
                w.offset = new Vector3f();
                w.offset.setX( Float.parseFloat( str.nextToken() ) );
                w.offset.setY( Float.parseFloat( str.nextToken() ) );
                w.offset.setZ( Float.parseFloat( str.nextToken() ) );
                str.nextToken();
                ws.add( w );
            }
        }
        result.triangle = tris;
        result.verts = verts;
        result.weights = ws;
        
        return( result );
    }
    
    private static Vector< MD5Bone > compileJoints( Vector< String > joints )
    {
        Vector< MD5Bone > result = new Vector< MD5Bone >();
        int index = 0;
        for ( String line: joints )
        {
            MD5Bone def = new MD5Bone();
            StringTokenizer str = new StringTokenizer( line );
            String name = str.nextToken().trim();
            def.name = name.replace( '\"', ' ' ).trim();
            def.boneId = index;
            def.parentId = Integer.parseInt( str.nextToken() );
            str.nextToken();
            Vector3f pos = new Vector3f();
            pos.setX( Float.parseFloat( str.nextToken() ) );
            pos.setY( Float.parseFloat( str.nextToken() ) );
            pos.setZ( Float.parseFloat( str.nextToken() ) );
            def.translation = pos;
            str.nextToken();
            str.nextToken();
            Vector3f pos1 = new Vector3f();
            pos1.setX( Float.parseFloat( str.nextToken() ) );
            pos1.setY( Float.parseFloat( str.nextToken() ) );
            pos1.setZ( Float.parseFloat( str.nextToken() ) );
            str.nextToken();
            def.rotation = MD5MathUtil.toQuaternion( pos1 );
            index++;
            result.add( def );
        }
        
        return( result );
    }
}

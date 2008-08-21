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
package org.jagatoo.loaders.models.md5;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingException;
import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.util.strings.SimpleStringTokenizer;
import org.jagatoo.util.strings.StringUtils;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author kman
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD5AnimationReader
{
    private static Object calcFrame( float[] baseFrame, ArrayList<String> boneDefs, float[] values, AnimationFactory animFactory, boolean convertZup2Yup, float scale )
    {
        SimpleStringTokenizer st = new SimpleStringTokenizer( "" );
        
        int numBones = boneDefs.size();
        
        Vector3f[] boneTrs = new Vector3f[ numBones ];
        Quaternion4f[] boneRots = new Quaternion4f[ numBones ];
        
        NamedObject[] bones = animFactory.createBonesArray( numBones );
        for ( int i = 0; i < numBones; i++ )
        {
            Vector3f translation = new Vector3f( baseFrame[i * 6 + 0],
                                                 baseFrame[i * 6 + 1],
                                                 baseFrame[i * 6 + 2]
                                               );
            Quaternion4f rotation = new Quaternion4f( baseFrame[i * 6 + 3],
                                                         baseFrame[i * 6 + 4],
                                                         baseFrame[i * 6 + 5],
                                                         0f
                                                       );
            
            st.setString( boneDefs.get( i ) );
            String name = StringUtils.unquoteString( st.nextToken() );
            int parent = Integer.parseInt( st.nextToken() );
            int flags = Integer.parseInt( st.nextToken() );
            int startIndex = Integer.parseInt( st.nextToken() );
            
            int j = 0;
            if ( ( flags & 1 ) != 0 )
            {
                translation.setX( values[startIndex + j] );
                ++j;
            }
            
            if ( ( flags & 2 ) != 0 )
            {
                translation.setY( values[startIndex + j] );
                ++j;
            }
            
            if ( ( flags & 4 ) != 0 )
            {
                translation.setZ( values[startIndex + j] );
                ++j;
            }
            
            if ( ( flags & 8 ) != 0 )
            {
                rotation.setA( values[startIndex + j] );
                ++j;
            }
            
            if ( ( flags & 16 ) != 0 )
            {
                rotation.setB( values[startIndex + j] );
                ++j;
            }
            
            if ( ( flags & 32 ) != 0 )
            {
                rotation.setC( values[startIndex + j] );
                ++j;
            }
            
            // compute orient quaternion's D value
            rotation.computeD();
            
            boneTrs[i] = translation;
            boneRots[i] = rotation;
            
            if ( parent == -1 )
            {
                if ( convertZup2Yup )
                {
                    rotation.mul( Quaternion4f.Z_UP_TO_Y_UP, rotation );
                }
            }
            else
            {
                Point3f rpos = boneRots[parent].transform( translation, new Point3f() );
                translation.set( rpos.getX() + boneTrs[parent].getX(),
                                 rpos.getY() + boneTrs[parent].getY(),
                                 rpos.getZ() + boneTrs[parent].getZ()
                               );
                
                /// concatenate rotations
                rotation.mul( boneRots[parent], rotation );
                rotation.normalize();
            }
            
            bones[i] = animFactory.createBone( name, translation, rotation );
        }
        
        return( animFactory.createBoneAnimationKeyFrame( bones, null ) );
    }
    
    public static void load( InputStream in, String filename, URL baseURL, AppearanceFactory appFactory, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, NamedObject[] shapes, Object[][][] boneWeights, AnimationFactory animFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        float[] baseFrame = null;
        float frameRate = 25f;
        int numAnimatedComponents = 0;
        ArrayList<String> boneDefs = null;
        ArrayList<float[]> bounds = null;
        ArrayList<Object> frames = null;
        
        BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
        String line;
        SimpleStringTokenizer st = new SimpleStringTokenizer( "" );
        while ( ( line = br.readLine() ) != null )
        {
            line = line.trim();
            
            if ( line.equals( "" ) )
            {
            }
            else if ( line.startsWith( "MD5Version" ) )
            {
                int version = Integer.parseInt( line.substring( 11 ), 10 );
                if ( version != 10 )
                {
                    br.close();
                    
                    throw new IncorrectFormatException( "MD5 version " + version + " is not supported. Expected 10." );
                }
            }
            else if ( line.startsWith( "commandline" ) )
            {
                // ignored!
            }
            else if ( line.startsWith( "numFrames" ) )
            {
                int numFrames = Integer.parseInt( line.substring( 10 ) );
                frames = new ArrayList<Object>( numFrames );
                bounds = new ArrayList<float[]>( numFrames );
            }
            else if ( line.startsWith( "numJoints" ) )
            {
                int numJoints = Integer.parseInt( line.substring( 10 ) );
                boneDefs = new ArrayList<String>( numJoints );
            }
            else if ( line.startsWith( "frameRate" ) )
            {
                frameRate = Float.parseFloat( line.substring( 10 ) );
            }
            else if ( line.startsWith( "numAnimatedComponents" ) )
            {
                numAnimatedComponents = Integer.parseInt( line.substring( 22 ) );
            }
            else if ( line.startsWith( "hierarchy" ) )
            {
                while ( ( line = br.readLine() ) != null )
                {
                    line = line.trim();
                    
                    if ( line.equals( "" ) )
                    {
                    }
                    else if ( line.equals( "}" ) )
                    {
                        break;
                    }
                    else
                    {
                        boneDefs.add( line );
                    }
                }
            }
            else if ( line.startsWith( "bounds" ) )
            {
                while ( ( line = br.readLine() ) != null )
                {
                    line = line.trim();
                    
                    if ( line.equals( "" ) )
                    {
                    }
                    else if ( line.equals( "}" ) )
                    {
                        break;
                    }
                    else
                    {
                        float[] bound = new float[ 6 ];
                        
                        st.setString( line );
                        st.skipToken(); // skip "("
                        
                        bound[0] = Float.parseFloat( st.nextToken() );
                        bound[1] = Float.parseFloat( st.nextToken() );
                        bound[2] = Float.parseFloat( st.nextToken() );
                        
                        st.skipToken(); // skip ")"
                        st.skipToken(); // skip "("
                        
                        bound[3] = Float.parseFloat( st.nextToken() );
                        bound[4] = Float.parseFloat( st.nextToken() );
                        bound[5] = Float.parseFloat( st.nextToken() );
                        
                        st.skipToken(); // skip ")"
                        
                        bounds.add( bound );
                    }
                }
            }
            else if ( line.startsWith( "baseframe" ) )
            {
                baseFrame = new float[ numAnimatedComponents ];
                
                int i = 0;
                while ( ( line = br.readLine() ) != null )
                {
                    line = line.trim();
                    
                    if ( line.equals( "" ) )
                    {
                    }
                    else if ( line.equals( "}" ) )
                    {
                        break;
                    }
                    else
                    {
                        st.setString( line );
                        st.skipToken(); // skip "("
                        
                        baseFrame[i * 6 + 0] = Float.parseFloat( st.nextToken() );
                        baseFrame[i * 6 + 1] = Float.parseFloat( st.nextToken() );
                        baseFrame[i * 6 + 2] = Float.parseFloat( st.nextToken() );
                        
                        st.skipToken(); // skip ")"
                        st.skipToken(); // skip "("
                        
                        baseFrame[i * 6 + 3] = Float.parseFloat( st.nextToken() );
                        baseFrame[i * 6 + 4] = Float.parseFloat( st.nextToken() );
                        baseFrame[i * 6 + 5] = Float.parseFloat( st.nextToken() );
                        
                        //str.skipToken(); // skip ")"
                        
                        i++;
                    }
                }
            }
            else if ( line.startsWith( "frame" ) )
            {
                st.setString( line );
                st.skipToken();
                
                st.skipToken(); // skip frame-index
                float[] frameValues = new float[ numAnimatedComponents ];
                int k = 0;
                while ( ( line = br.readLine() ) != null )
                {
                    line = line.trim();
                    
                    if ( line.equals( "" ) )
                    {
                    }
                    else if ( line.equals( "}" ) )
                    {
                        break;
                    }
                    else
                    {
                        st.setString( line );
                        
                        while ( st.hasMoreTokens() )
                        {
                            frameValues[k++] = Float.parseFloat( st.nextToken() );
                        }
                    }
                }
                
                frames.add( calcFrame( baseFrame, boneDefs, frameValues, animFactory, convertZup2Yup, scale ) );
            }
        }
        
        br.close();
        
        
        
        Object[] keyFrames = frames.toArray( animFactory.createBoneAnimationKeyFrameArray( frames.size() ) );
        
        Object[] controllers = animFactory.createBoneAnimationKeyFrameControllerArray( shapes.length );
        for ( int i = 0; i < shapes.length; i++ )
        {
            NamedObject shape = shapes[i];
            
            controllers[i] = animFactory.createBoneAnimationKeyFrameController( keyFrames, shape, boneWeights[i] );
        }
        
        siHandler.addAnimation( filename, keyFrames.length, frameRate, controllers );
    }
}

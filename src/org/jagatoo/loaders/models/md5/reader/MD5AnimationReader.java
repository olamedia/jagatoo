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

import org.jagatoo.loaders.models.md5.animation.MD5AnimBone;
import org.jagatoo.loaders.models.md5.animation.MD5Animation;
import org.jagatoo.loaders.models.md5.animation.MD5BaseFrame;
import org.jagatoo.loaders.models.md5.animation.MD5Bound;
import org.jagatoo.loaders.models.md5.animation.MD5Frame;
import org.jagatoo.loaders.models.md5.mesh.MD5Bone;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author kman
 */
public class MD5AnimationReader
{
    public static MD5Animation readAnimFile( InputStream in ) throws IOException
    {
        MD5Animation result = new MD5Animation();
        
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
            else if ( input.startsWith( "numFrames" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                result.numFrames = Integer.parseInt( str.nextToken() );
            }
            else if ( input.startsWith( "numJoints" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                result.numJoints = Integer.parseInt( str.nextToken() );
            }
            else if ( input.startsWith( "frameRate" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                result.frameRate = Integer.parseInt( str.nextToken() );
            }
            else if ( input.startsWith( "numAnimatedComponents" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                result.numAnimatedComponents = Integer.parseInt( str.nextToken() );
            }
            else if ( input.startsWith( "hierarchy" ) )
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
                        //hierarchy.addElement(input);
                        StringTokenizer str = new StringTokenizer( input );
                        MD5AnimBone hr = new MD5AnimBone();
                        hr.name = str.nextToken().replace( '\"', ' ' ).trim();
                        hr.parent = Integer.parseInt( str.nextToken() );
                        hr.flags = Integer.parseInt( str.nextToken() );
                        hr.startIndex = Integer.parseInt( str.nextToken() );
                        result.bones.addElement( hr );
                    }
                }
            }
            else if ( input.startsWith( "bounds" ) )
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
                        MD5Bound bound = new MD5Bound();
                        StringTokenizer str = new StringTokenizer( input );
                        str.nextToken();
                        bound.start.setX( Float.parseFloat( str.nextToken() ) );
                        bound.start.setY( Float.parseFloat( str.nextToken() ) );
                        bound.start.setZ( Float.parseFloat( str.nextToken() ) );
                        str.nextToken();
                        str.nextToken();
                        bound.end.setX( Float.parseFloat( str.nextToken() ) );
                        bound.end.setY( Float.parseFloat( str.nextToken() ) );
                        bound.end.setZ( Float.parseFloat( str.nextToken() ) );
                        str.nextToken();
                        result.bounds.addElement( bound );
                    }
                }
            }
            else if ( input.startsWith( "baseframe" ) )
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
                        MD5BaseFrame fr = new MD5BaseFrame();
                        StringTokenizer str = new StringTokenizer( input );
                        str.nextToken();
                        fr.position.setX( Float.parseFloat( str.nextToken() ) );
                        fr.position.setY( Float.parseFloat( str.nextToken() ) );
                        fr.position.setZ( Float.parseFloat( str.nextToken() ) );
                        str.nextToken();
                        str.nextToken();
                        float x = Float.parseFloat( str.nextToken() );
                        float y = Float.parseFloat( str.nextToken() );
                        float z = Float.parseFloat( str.nextToken() );
                        str.nextToken();
                        fr.rotation = MD5MathUtil.toQuaternion( x, y, z );
                        result.baseFrame.addElement( fr );
                    }
                }
            }
            else if ( input.startsWith( "frame" ) )
            {
                StringTokenizer str = new StringTokenizer( input );
                str.nextToken();
                MD5Frame fr = new MD5Frame();
                fr.id = Integer.parseInt( str.nextToken() );
                fr.values = new float[ result.numAnimatedComponents ];
                //System.out.println("[FRAME]-->"+fr.id);
                int k = 0;
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
                        str = new StringTokenizer( input );
                        
                        //System.out.println("[INPUT:]"+input);
                        while ( str.hasMoreTokens() )
                        {
                            fr.values[ k ] = Float.parseFloat( str.nextToken() );
                            //System.out.println("[VAL]"+fr.values[k]);
                            k++;
                        }
                    }
                }
                result.frames.addElement( fr );
            }
        }
        
        for ( MD5Frame frame: result.frames )
            calcFrame( result, frame );
        
        return( result );
    }
    
    public static void calcFrame( MD5Animation animation1, MD5Frame frame )
    {
        frame.bones.removeAllElements();
        for ( int i = 0; i < animation1.bones.size(); ++i )
        {
            MD5BaseFrame baseJoint1 = animation1.baseFrame.elementAt( i );
            Vector3f animatedPos = new Vector3f( baseJoint1.position );
            Quaternion4f animatedOrient = new Quaternion4f( baseJoint1.rotation );
            int j = 0;
            MD5AnimBone baseJoint = animation1.bones.elementAt( i );
            if ( ( baseJoint.flags & 1 ) != 0 )//baseJoint.transX())//if (baseJoint.getBitset().get(0)) /* Tx */
            {
                //System.out.println("TX");
                animatedPos.setX( frame.values[ baseJoint.startIndex + j ] );
                ++j;
            }
            
            if ( ( baseJoint.flags & 2 ) != 0 )//if(baseJoint.transY())//if (baseJoint.getBitset().get(1)) /* Ty */
            {
                //System.out.println("TY");
                animatedPos.setY( frame.values[ baseJoint.startIndex + j ] );
                ++j;
            }
            
            if ( ( baseJoint.flags & 4 ) != 0 )//if(baseJoint.transZ())//if (baseJoint.getBitset().get(2)) /* Tz */
            {
                //System.out.println("TZ");
                animatedPos.setZ( frame.values[ baseJoint.startIndex + j ] );
                ++j;
            }
            
            if ( ( baseJoint.flags & 8 ) != 0 )//if(baseJoint.rotX())//if (baseJoint.getBitset().get(3)) /* Qx */
            {
                //System.out.println("RX");
                animatedOrient.setA( frame.values[ baseJoint.startIndex + j ] );
                ++j;
            }
            
            if ( ( baseJoint.flags & 16 ) != 0 )//if(baseJoint.rotY())//if (baseJoint.getBitset().get(4)) /* Qy */
            {
                //System.out.println("RY");
                animatedOrient.setB( frame.values[ baseJoint.startIndex + j ] );
                ++j;
            }
            
            if ( ( baseJoint.flags & 32 ) != 0 )//if(baseJoint.rotZ())//if (baseJoint.getBitset().get(5)) /* Qz */
            {
                //System.out.println("RZ");
                animatedOrient.setD( frame.values[ baseJoint.startIndex + j ] );
                ++j;
            }
            
            // compute orient quaternion's w value
            MD5MathUtil.Quat_computeD( animatedOrient );
            MD5Bone bone = new MD5Bone();
            bone.setBoneId( i );
            bone.setName( baseJoint.name );
            bone.setParentId( baseJoint.parent );
            if ( bone.isRoot() )
            {
                bone.setTranslation( animatedPos );
                bone.setRotation( animatedOrient );
            }
            else
            {
                MD5Bone parent = frame.bones.elementAt( bone.getParentId() );
                //System.out.println("-->"+bone.getParentId()+"/"+frame.bones.size());
                Point3f rpos = MD5MathUtil.Quat_rotatePoint( parent.getRotation(), animatedPos );
                bone.getTranslation().setX( rpos.getX() + parent.getTranslation().getX() );
                bone.getTranslation().setY( rpos.getY() + parent.getTranslation().getY() );
                bone.getTranslation().setZ( rpos.getZ() + parent.getTranslation().getZ() );
                
                /// concatenate rotations
                bone.setRotation( MD5MathUtil.Quat_multQuat( parent.getRotation(), animatedOrient ) );
                MD5MathUtil.Quat_normalize( bone.getRotation() );
            }
            //bone.getTranslation().addLocal(baseJoint.translation);
            frame.bones.addElement( bone );
        }
    }
}

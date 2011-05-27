/**
 * Copyright (c) 2007-2011, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.loaders.models.collada.stax;

import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.loaders.models.collada.datastructs.animation.Axis;
import org.jagatoo.logging.JAGTLog;

/**
 * A COLLADA Channel.
 *
 * Child of Animation.
 *
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 * @author Joe LaFata (aka qbproger)
 */
public class XMLChannel
{
    /**
     * The type of a channel
     *
     * @author Amos Wenger (aka BlueSky)
     */
    public static enum ChannelType {
        /** A translate channel */
        TRANSLATE,
        /** A rotate channel */
        ROTATE,
        /** A scale channel */
        SCALE,
        /** A matrix channel */
        MATRIX
    }

    public ChannelType type;

    public String source = null;
    public String target = null;

    private String targetNodeId;
    private String transElemSid;
    private int transElemIndexI = -1;
    private int transElemIndexJ = -1;

    //target="Root/rotateX.ANGLE"
    //target="Root/translate"
    //target="Root/scale"

    /*
    <channel target="here/trans.X" />
    <channel target="here/trans.Y" />
    <channel target="here/trans.Z" />
    <channel target="here/rot.ANGLE" />
    <channel target="here/rot(3)" />
    <channel target="here/mat(3)(2)" />
    <node id="here">
        <translate sid="trans"> 1.0 2.0 3.0 </translate>
        <rotate sid="rot"> 1.0 2.0 3.0 4.0 </rotate>
        <matrix sid="mat">1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0 10.0 11.0 12.0 13.0 14.0 15.0 16.0</matrix>
    </node>
     */

    public String getTargetNodeId()
    {
        checkIsParsed();
        return ( targetNodeId );
    }

    public String getTransElemSid()
    {
        checkIsParsed();
        return ( transElemSid );
    }

    public int getTransElemIndexI()
    {
        checkIsParsed();
        return ( transElemIndexI );
    }

    public int getTransElemIndexJ()
    {
        checkIsParsed();
        return ( transElemIndexJ );
    }

    /**
     * Parse the target attribute and gets the joint and the type of movement
     */
    private void checkIsParsed()
    {
        if ( targetNodeId == null )
        {
            StringTokenizer tok = new StringTokenizer( target, "/.()", true );
            targetNodeId = tok.nextToken();
            tok.nextToken(); //skip "/"
            transElemSid = tok.nextToken();
            if ( !tok.hasMoreTokens() ) //full transform. element
            {
                return;
            }
            String token = tok.nextToken();
            if ( ".".equals( token ) )//member access
            {
                token = tok.nextToken();
                if ( "X".equals( token ) )
                {
                    transElemIndexI = 0;
                    return;
                }
                if ( "Y".equals( token ) )
                {
                    transElemIndexI = 1;
                    return;
                }
                if ( "Z".equals( token ) )
                {
                    transElemIndexI = 2;
                    return;
                }
                if ( "ANGLE".equals( token ) || "W".equals( token ) )
                {
                    transElemIndexI = 3;
                    return;
                }
                throw new Error( "Bad member name of transformation element." );
            }
            else if ( "(".equals( token ) )//array access
            {
                int i = Integer.parseInt( tok.nextToken() );
                token = tok.nextToken(); //skip ")"
                if ( !tok.hasMoreTokens() )
                {
                    transElemIndexI = i;
                    return;
                }
                //token = tok.nextToken(); //skip empty token
                if ( "(".equals( tok.nextToken() ) )//2 dim. array access (to matrix)
                {
                    int j = Integer.parseInt( tok.nextToken() );
                    if ( ")".equals( tok.nextToken() ) )
                    {
                        transElemIndexI = i;
                        transElemIndexJ = j;
                        return;
                    }
                    throw new Error( "\')\' expected." );
                }
                else
                {
                    throw new Error( "\'(\' expected." );
                }
            }
        }
    }
    
    public void parse( XMLStreamReader parser ) throws XMLStreamException
    {
        
        for ( int i = 0; i < parser.getAttributeCount(); i++ )
        {
            QName attr = parser.getAttributeName( i );
            if ( attr.getLocalPart().equals( "source" ) )
            {
                source = XMLIDREFUtils.parse( parser.getAttributeValue( i ) );
            }
            else if ( attr.getLocalPart().equals( "target" ) )
            {
                target = XMLIDREFUtils.parse( parser.getAttributeValue( i ) );
            }
            else
            {
                JAGTLog.exception( "Unsupported ", this.getClass().getSimpleName(), " Attr tag: ", attr.getLocalPart() );
            }
        }
        
        for ( int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next() )
        {
            switch ( event )
            {
                case XMLStreamConstants.END_ELEMENT:
                {
                    if ( parser.getLocalName().equals( "channel" ) )
                        return;
                    break;
                }
            }
        }
    }
}

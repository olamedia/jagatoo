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
public class XMLChannel {

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
        SCALE
    }

    public ChannelType type;

    public final static String TRANSLATE_TARGET = "translate";
    public final static String ROTATE_TARGET = "rotate";
    public final static String SCALE_TARGET = "scale";

    public String source = null;
    public String target = null;

    private String targetJoint = null;
    private Axis targetAxis;

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
    
    public String getTargetJoint() {
        checkIsParsed();
        return this.targetJoint;
    }

    public Axis getRotationAxis() {
        checkIsParsed();
        if (type != ChannelType.ROTATE) {
            throw new IncorrectFormatException( "It is not a rotation key frame, it's a " + type );
        }
        
        return this.targetAxis;
    }


    /**
     * Parse the target attribute and gets the joint and the type of movement
     */
    private void checkIsParsed() {

        if (targetJoint == null) {

            StringTokenizer tok = new StringTokenizer(target);
            targetJoint = tok.nextToken("/");
            String trans = tok.nextToken();

            //see if it's a translation or a rotation
            if (trans.startsWith(TRANSLATE_TARGET)) {

                /*
                 * It's a translate channel
                 */
                type = ChannelType.TRANSLATE;

            } else if (trans.startsWith(ROTATE_TARGET)) {

                /*
                 * It's a rotate channel
                 */
                type = ChannelType.ROTATE;

                // Get the axis
                if (trans.contains("rotateX")) {
                    targetAxis = Axis.X;
                } else if (trans.contains("rotateY")) {
                    targetAxis = Axis.Y;
                } else {
                    targetAxis = Axis.Z;
                }

            } else if (trans.startsWith(SCALE_TARGET)) {

                /*
                 * It's a scale channel
                 */

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

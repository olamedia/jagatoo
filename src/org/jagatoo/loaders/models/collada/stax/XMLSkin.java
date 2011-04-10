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

import org.jagatoo.loaders.models.collada.datastructs.animation.DaeJoint;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Influence;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.util.errorhandling.IncorrectFormatException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;

/**
 * A Skin. It defines how skeletal animation should be computed.
 * Child of Controller.
 *
 * @author Amos Wenger (aka BlueSky)
 * @author Joe LaFata (aka qbproger)
 */
public class XMLSkin
{

    /**
     * This source defines a joint/bone for each vertex and each joint
     */
    private static final String SKIN_JOINTS_SOURCE = "-joints"; //"skin-joints"

    /**
     * This source defines a weight for each vertex and each joint
     */
    private static final String SKIN_WEIGHTS_SOURCE = "-weights"; //"skin-weights"

    public String source = null;

    // Here we instantiate it because if not read, it should be identity
    // (so the COLLADA doc says)
    public XMLMatrix4x4 bindShapeMatrix = null;

    public ArrayList<XMLSource> sources = new ArrayList<XMLSource>();
    public ArrayList<XMLInput> jointsInputs = null;
    public XMLVertexWeights vertexWeights = null;

    private Influence[][] influences = null;

    /**
     * Search the "skin-joints" source.
     * Maybe there is a better way get that
     *
     * @return
     */
    public XMLSource getJointsSource()
    {
        for ( XMLSource source : sources )
        {
            if ( source.id.endsWith( SKIN_JOINTS_SOURCE ) )
            {
                return source;
            }
        }
        throw new IncorrectFormatException( "Could not find source " + SKIN_JOINTS_SOURCE + " in library_controllers" );
    }

    /**
     * Search the "skin-weights" source.
     * Maybe there is a better way get that
     *
     * @return
     */
    public XMLSource getWeightsSource()
    {
        for ( XMLSource source : sources )
        {
            if ( source.id.endsWith( SKIN_WEIGHTS_SOURCE ) )
            {
                return source;
            }
        }
        throw new IncorrectFormatException( "Could not find source " + SKIN_WEIGHTS_SOURCE + " in library_controllers" );
    }

    /**
     * Normalize the influences weights
     *
     * @param influences
     */
    private void normalizeInfluences( Influence[][] influences )
    {
        for ( int i = 0; i < influences.length; i++ )
        {
            Influence[] influence = influences[ i ];
            if ( influence == null )
            {
                influences[ i ] = new Influence[1];
                influences[ i ][ 0 ] = new Influence( ( short ) 0, 0f ); //dummy influence
                continue;
            }
            float sum = 0f;
            for ( int j = 0; j < influence.length; j++ )
            {
                sum += influence[ j ].getWeight();
            }
            if ( sum == 0f || sum == 1f )
            {
                continue;
            }
            for ( int j = 0; j < influence.length; j++ )
            {
                influence[ j ].setWeight( influence[ j ].getWeight() / sum );
            }
        }
    }

    public Influence[][] buildInfluences( DaeSkeleton skeleton, int numVertices, int[] vertexIndices )
    {
        if ( influences != null )
        {
            return influences;
        }

        influences = new Influence[numVertices][];

        XMLSource jointsSource = getJointsSource();

        // get the "skin-weights", maybe it could be done only one time, when the sources array is filled.
        XMLSource weightsSource = getWeightsSource();
        assert(numVertices == vertexIndices.length);
        int vIndex = 0;
        for ( int i = 0; i < numVertices; i++ ) //vertexWeights.vcount.ints.length
        {
            int vi = vertexIndices[i];
            final int numJoints = vertexWeights.vcount.ints[ vi ];

            influences[ i ] = new Influence[ numJoints ];

            vIndex = calcInfluenceStartIndex(vi, vertexWeights.vcount.ints);
            for ( int j = 0; j < numJoints; j++ )
            {
                final int jointIndex = vertexWeights.v.ints[ vIndex + j * 2 + 0 ];
                final int weightIndex = vertexWeights.v.ints[ vIndex + j * 2 + 1 ];

                final float weight = weightsSource.floatArray.floats[ weightIndex ];

                if ( jointIndex == -1 )
                {
                    //influences[ i ][ j ] = new Influence( bindShapeMatrix.matrix4f, weight );
                    influences[ i ][ j ] = new Influence( ( short ) 0, 0f/*weight*/ ); //?
                }
                else
                {
                    final String jointSourceId = jointsSource.idrefArray.idrefs[ jointIndex ];
                    final DaeJoint joint = skeleton.getJointBySourceId( jointSourceId );

                    influences[ i ][ j ] = new Influence( joint.getIndex(), weight );
                }
            }

         //   vIndex += numJoints * 2;
        }

        normalizeInfluences( influences );

        return ( influences );
    }

    private int calcInfluenceStartIndex( int vi, int[] numJoints )
    {
        assert(vi < numJoints.length);

        int v = 0;
        for ( int i = 0; i < vi; i++ )
        {
            v += numJoints[ i ] * 2;
        }

        return ( v );
    }

    public void parse( XMLStreamReader parser ) throws XMLStreamException
    {
        doParsing( parser );

        if ( source == null )
        {
            JAGTLog.exception( this.getClass().getSimpleName(), " missing attribute source." );
        }

        if ( jointsInputs == null )
        {
            JAGTLog.exception( this.getClass().getSimpleName(), " missing joint." );
        }

        if ( vertexWeights == null )
        {
            JAGTLog.exception( this.getClass().getSimpleName(), " missing vertex weights." );
        }

        if ( sources.size() < 3 )
        {
            JAGTLog.exception( this.getClass().getSimpleName(), " not enough sources." );
        }
    }

    private void doParsing( XMLStreamReader parser ) throws XMLStreamException
    {
        for ( int i = 0; i < parser.getAttributeCount(); i++ )
        {
            QName attr = parser.getAttributeName( i );
            if ( attr.getLocalPart().equals( "source" ) )
            {
                source = XMLIDREFUtils.parse( parser.getAttributeValue( i ) );
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
                case XMLStreamConstants.START_ELEMENT:
                {
                    String localName = parser.getLocalName();
                    if ( localName.equals( "source" ) )
                    {
                        XMLSource src = new XMLSource();
                        src.parse( parser );
                        sources.add( src );
                    }
                    else if ( localName.equals( "bind_shape_matrix" ) )
                    {
                        if ( bindShapeMatrix != null )
                        {
                            JAGTLog.exception( this.getClass().getSimpleName(), " too many bind_shape_matrix tags." );
                        }

                        bindShapeMatrix = XMLMatrixUtils.readColumnMajor( StAXHelper.parseText( parser ) );
                        //bindShapeMatrix = XMLMatrixUtils.readRowMajor( StAXHelper.parseText( parser ) );
                    }
                    else if ( localName.equals( "joints" ) )
                    {
                        jointsInputs = getJointInputs( parser );
                    }
                    else if ( localName.equals( "vertex_weights" ) )
                    {
                        vertexWeights = new XMLVertexWeights();
                        vertexWeights.parse( parser );
                    }
                    else
                    {
                        JAGTLog.exception( "Unsupported ", this.getClass().getSimpleName(), " Start tag: ", parser.getLocalName() );
                    }
                    break;
                }
                case XMLStreamConstants.END_ELEMENT:
                {
                    if ( parser.getLocalName().equals( "skin" ) )
                    {
                        return;
                    }
                    break;
                }
            }
        }
    }

    public ArrayList<XMLInput> getJointInputs( XMLStreamReader parser ) throws XMLStreamException
    {
        ArrayList<XMLInput> inputs = new ArrayList<XMLInput>();
        for ( int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next() )
        {
            switch ( event )
            {
                case XMLStreamConstants.START_ELEMENT:
                {
                    String localName = parser.getLocalName();
                    if ( localName.equals( "input" ) )
                    {
                        XMLInput input = new XMLInput();
                        input.parse( parser );
                        inputs.add( input );
                    }
                    else
                    {
                        JAGTLog.exception( "Unsupported XMLJoint Start tag: ", parser.getLocalName() );
                    }
                }
                case XMLStreamConstants.END_ELEMENT:
                {
                    if ( parser.getLocalName().equals( "joints" ) )
                    {
                        return inputs;
                    }
                    break;
                }
            }
        }
        return (null);
    }
}

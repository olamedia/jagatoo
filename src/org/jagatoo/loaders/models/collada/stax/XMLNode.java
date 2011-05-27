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

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.COLLADATransform;
import org.openmali.FastMath;
import org.openmali.vecmath2.*;
import org.openmali.vecmath2.util.MatrixUtils;

/**
 * A Node can have a Transform and can instance
 * geometries/controllers (for skeletal animation).
 * Child of VisualScene or Node.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Joe LaFata (aka qbproger)
 */
public class XMLNode
{
    
    public ArrayList< String > layers = null;
    public String sid = null;
    public XMLAsset asset = null;
    
    public static enum Type
    {
        NODE,
        JOINT
    }

    public Type type = Type.NODE;
    public String id = null;
    public String name = null;

    public final COLLADATransform transform = new COLLADATransform();
    public ArrayList< XMLInstanceGeometry > instanceGeometries = new ArrayList< XMLInstanceGeometry >();
    public ArrayList< XMLInstanceController > instanceControllers = new ArrayList< XMLInstanceController >();
    
    public ArrayList< XMLNode > childrenList = new ArrayList< XMLNode >();

    public static ArrayList<String> parseLayerList( String str )
    {
        ArrayList<String> layers = new ArrayList<String>();
        StringTokenizer tknz = new StringTokenizer( str );
        while ( tknz.hasMoreTokens() )
        {
            layers.add( tknz.nextToken() );
        }
        return layers;
    }

    public void applyTranslate( String sid, String s )
    {
        StringTokenizer tknz = new StringTokenizer( s );
        Vector3f translate = new Vector3f(
                Float.parseFloat( tknz.nextToken() ),
                Float.parseFloat( tknz.nextToken() ),
                Float.parseFloat( tknz.nextToken() )
        );

        transform.put( sid, translate );
    }

    public void applyRotate( String sid, String s )
    {
        StringTokenizer tknz = new StringTokenizer( s );
        AxisAngle3f rotate = new AxisAngle3f(
                Float.parseFloat( tknz.nextToken() ),
                Float.parseFloat( tknz.nextToken() ),
                Float.parseFloat( tknz.nextToken() ),
                FastMath.toRad( Float.parseFloat( tknz.nextToken() ) )
        );

        transform.put( sid, rotate );
    }

    public void applyScale( String sid, String s )
    {
        StringTokenizer tknz = new StringTokenizer( s );

        Tuple3f scale = new Tuple3f(
                Float.parseFloat( tknz.nextToken() ),
                Float.parseFloat( tknz.nextToken() ),
                Float.parseFloat( tknz.nextToken() )
        );

        transform.put( sid, scale );
    }

    private void applyMatrix( String sid, String s )
    {
        Matrix4f matrix = XMLMatrixUtils.readColumnMajor( s ).matrix4f;
        transform.put( sid, matrix );
    }
    
    public void parse( XMLStreamReader parser ) throws XMLStreamException
    {
        for ( int i = 0; i < parser.getAttributeCount(); i++ )
        {
            QName attr = parser.getAttributeName( i );
            if ( attr.getLocalPart().equals( "id" ) )
            {
                id = parser.getAttributeValue( i );
            }
            else if ( attr.getLocalPart().equals( "name" ) )
            {
                name = parser.getAttributeValue( i );
            }
            else if ( attr.getLocalPart().equals( "sid" ) )
            {
                sid = parser.getAttributeValue( i );
            }
            else if ( attr.getLocalPart().equals( "type" ) )
            {
                type = Type.valueOf( parser.getAttributeValue( i ).trim() );
            }
            else if ( attr.getLocalPart().equals( "layer" ) )
            {
                layers = parseLayerList( parser.getAttributeValue( i ) );
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
                    if ( localName.equals( "asset" ) )
                    {
                        if ( asset != null )
                            throw new ParsingException( this.getClass().getSimpleName() + " Too MANY: " + parser.getLocalName() );
                        
                        asset = new XMLAsset();
                        asset.parse( parser );
                    }
                    else if ( localName.equals( "node" ) )
                    {
                        XMLNode n = new XMLNode();
                        n.parse( parser );
                        childrenList.add( n );
                    }
                    else if ( localName.equals( "matrix" ) )
                    {
                        String sid = readTransformSid( "matrix", parser );
                        applyMatrix( sid, StAXHelper.parseText( parser ));
                    }
                    else if ( localName.equals( "rotate" ) )
                    {
                        String sid = readTransformSid( "rotate", parser );
                        applyRotate( sid, StAXHelper.parseText( parser ) );
                    }
                    else if ( localName.equals( "translate" ) )
                    {
                        String sid = readTransformSid( "translate", parser );
                        applyTranslate( sid, StAXHelper.parseText( parser ) );
                    }
                    else if ( localName.equals( "scale" ) )
                    {
                        String sid = readTransformSid( "scale", parser );
                        applyScale( sid, StAXHelper.parseText( parser ) );
                    }
                    else if ( localName.equals( "instance_geometry" ) )
                    {
                        XMLInstanceGeometry instGeom = new XMLInstanceGeometry();
                        instGeom.parse( parser );
                        instanceGeometries.add( instGeom );
                    }
                    else if ( localName.equals( "instance_controller" ) )
                    {
                        XMLInstanceController instCont = new XMLInstanceController();
                        instCont.parse( parser );
                        instanceControllers.add( instCont );
                    }
                    else
                    {
                        JAGTLog.exception( "Unsupported ", this.getClass().getSimpleName(), " Start tag: ", parser.getLocalName() );
                    }
                    break;
                }
                case XMLStreamConstants.END_ELEMENT:
                {
                    if ( parser.getLocalName().equals( "node" ) )
                        return;
                    break;
                }
            }
        }
    }

    private String readTransformSid( String name, XMLStreamReader parser )
    {
        String sid = null;
        for ( int i = 0; i < parser.getAttributeCount(); i++ )
        {
            QName attr = parser.getAttributeName( i );
            if ( attr.getLocalPart().equals( "sid" ) )
            {
                sid = parser.getAttributeValue( i );
            }
            else
            {
                JAGTLog.exception( "Unsupported ", this.getClass().getSimpleName(), " Attr tag: ", attr.getLocalPart() );
            }
        }
//        if ( sid == null )
//        {
//            throw new Error( "Missing SID of \'" + name + "\' element of node ID=" + id );
//        }

        return ( sid );
    }
}

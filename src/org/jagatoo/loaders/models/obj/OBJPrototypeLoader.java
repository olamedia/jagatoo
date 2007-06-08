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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.openmali.vecmath.Color3f;
import org.openmali.vecmath.TexCoord2f;
import org.openmali.vecmath.Vector3f;

/**
 * A loader to create abstract java data from a Wavefront OBJ file.
 * 
 * @author Kevin Glass
 * @author <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
 * @author Amos Wenger (aka BlueSky)
 * @author Marvin Froehlich (aka Qudus)
 */
public final class OBJPrototypeLoader
{
    protected static Boolean debug = false;
    
    private static Vector3f parseVector( String line )
    {
        StringTokenizer tokens = new StringTokenizer( line );
        
        tokens.nextToken();
        
        final float x = Float.parseFloat( tokens.nextToken() );
        final float y = Float.parseFloat( tokens.nextToken() );
        final float z = Float.parseFloat( tokens.nextToken() );
        
        return( new Vector3f( x, y, z ) );
    }
    
    private static TexCoord2f parseTexCoord( String line )
    {
        StringTokenizer tokens = new StringTokenizer( line );
        
        tokens.nextToken();
        
        final float u = Float.parseFloat( tokens.nextToken() );
        final float v = Float.parseFloat( tokens.nextToken() );
        
        return( new TexCoord2f( u, v ) );
    }
    
    private static Color3f parseColor( String token )
    {
        StringTokenizer tokens = new StringTokenizer( token );
        
        final float r = Float.parseFloat( tokens.nextToken() );
        final float g = Float.parseFloat( tokens.nextToken() );
        final float b = Float.parseFloat( tokens.nextToken() );
        
        return( new Color3f ( r, g, b ) );
    }
    
    private static List<OBJMaterial> parseMatLib( URL baseURL, String name ) throws IOException
    {
        List<OBJMaterial> matList = new ArrayList<OBJMaterial>( 1 );
        OBJMaterial       mat     = null;
        
        BufferedReader    bufferedReader = null;
        
        try
        {
            try
            {
                bufferedReader = new BufferedReader( new InputStreamReader( new URL( baseURL, name ).openStream() ) );
            }
            catch ( FileNotFoundException f )
            {
                try
                {
                    System.out.println( "Base URL = " + baseURL );
                    System.out.println( "Resource name = " + name );
                    bufferedReader = new BufferedReader( new InputStreamReader( Thread.currentThread().getContextClassLoader().getResourceAsStream( name ) ) );
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
            
            String line;
            while ( ( line = bufferedReader.readLine() ) != null )
            {
                int i;
                
                for ( i = 0; i < line.length ( ); i++ )
                {
                    if ( !Character.isWhitespace( line.charAt( i ) ) )
                    {
                        break;
                    }
                }
                
                line = line.substring( i );
                
                if ( line.startsWith( "#" ) )
                {
                    // ignore comments
                }
                else if ( line.trim().isEmpty() )
                {
                    // ignore blank lines
                }
                else if ( line.startsWith( "newmtl" ) )
                {
                    String matName = line.substring( line.indexOf( " " ) + 1 );
                    
                    mat = new OBJMaterial( matName );
                    
                    matList.add( mat );
                }
                else if ( line.startsWith( "Tf" ) )
                {
                    Color3f col = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setDiffuseColor( col );
                    mat.setAmbientColor( col );
                }
                else if ( line.startsWith( "Ka" ) )
                {
                    Color3f col = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setAmbientColor( col );
                }
                else if ( line.startsWith( "Kd" ) )
                {
                    Color3f col = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setColor( col );
                    mat.setDiffuseColor( col );
                }
                else if ( line.startsWith( "Ks" ) )
                {
                    Color3f col = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setSpecularColor( col );
                }
                else if ( line.startsWith( "Ns" ) )
                {
                    mat.setShininess( Float.parseFloat( line.substring( line.indexOf( " " ) + 1 ) ) );
                }
                else if ( line.startsWith( "map_Kd" ) )
                {
                    String texName = line.substring( line.indexOf( " " ) + 1 );
                    
                    mat.setTextureName( texName );
                }
                else
                {
                    if ( OBJPrototypeLoader.debug != null && OBJPrototypeLoader.debug.booleanValue() )
                    {
                        System.err.println( OBJPrototypeLoader.class.getName() +
                                            ":  ignoring unknown material tag:  \"" + line + "\"" );
                    }
                }
            }
        }
        finally
        {
            if ( bufferedReader != null )
            {
                bufferedReader.close();
            }
        }
        
        return( matList );
    }
    
    public static OBJModelPrototype load( InputStream in, URL baseURL, Vector3f geomOffset ) throws IOException
    {
        BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
        
        Map<String, OBJMaterial> matMap = new HashMap<String, OBJMaterial>();
        
        List<Vector3f> verts = new ArrayList<Vector3f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<TexCoord2f> texs = new ArrayList<TexCoord2f>();
        
        OBJGroup topGroup = OBJGroup.createTopGroup( verts, normals, texs );
        
        try
        {
            OBJMaterial currentMat = null;
            
            OBJGroup currentGroup = topGroup;
            
            String line = null;
            
            while ((line = br.readLine()) != null)
            {
                if (line.startsWith( "#" ))
                {
                    // comment: ignore
                }
                else if (line.isEmpty())
                {
                    // empty line: ignore
                }
                else if (line.startsWith( "vn" ))
                {
                    normals.add( parseVector( line ) );
                }
                else if (line.startsWith( "vt" ))
                {
                    texs.add( parseTexCoord( line ) );
                }
                else if (line.startsWith( "v" ))
                {
                    Vector3f vert = parseVector( line );
                    vert.add( geomOffset );
                    verts.add( vert );
                }
                else if (line.startsWith( "f" ))
                {
                    currentGroup.add( line, currentMat );
                }
                else if (line.startsWith( "g" ))
                {
                    String name = (line.trim().length() >= 3) ? line.substring( 2 ) : null;
                    OBJGroup g = new OBJGroup( name, verts, normals, texs );
                    topGroup.addChild( g );
                    currentGroup = g;
                }
                else if (line.startsWith( "o" ))
                {
                    String name = (line.trim().length() >= 3) ? line.substring( 2 ) : null;
                    OBJGroup g = new OBJGroup( name, verts, normals, texs );
                    topGroup.addChild( g );
                    currentGroup = g;
                }
                else if (line.startsWith( "mtllib" ))
                {
                    StringTokenizer tokens = new StringTokenizer( line );
                    tokens.nextToken();
                    String name = tokens.nextToken();
                    List<OBJMaterial> matList = parseMatLib( baseURL, name );
                    for (OBJMaterial mat: matList)
                    {
                        if (mat != null)
                            matMap.put( mat.getName(), mat );
                    }
                }
                else if (line.startsWith( "usemtl" ))
                {
                    String name = line.substring( line.indexOf( " " ) + 1 );
                    currentMat = matMap.get( name );
                }
                else if (line.startsWith( "s" ))
                {
                    if (debug != null && debug.booleanValue())
                    {
                        System.err.println( OBJPrototypeLoader.class.getName() + ":  smoothing groups not currently supported:  \"" + line + "\"" );
                    }
                }
                else
                {
                    if (debug != null && debug.booleanValue())
                    {
                        System.err.println( OBJPrototypeLoader.class.getName() + ":  ignoring unknown OBJ tag:  \"" + line + "\"" );
                    }
                }
            }
        }
        finally
        {
            br.close();
        }
        
        return( new OBJModelPrototype( matMap, topGroup ) );
    }
    
    public static OBJModelPrototype load( InputStream in, URL baseURL ) throws IOException
    {
        return( load( in, baseURL, new Vector3f( 0.0f, 0.0f, 0.0f ) ) );
    }
    
    private OBJPrototypeLoader()
    {
    }
}

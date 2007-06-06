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
package org.jagatoo.loaders.models.ac3d;

import org.openmali.vecmath.Color3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Parses the AC3D data file into a java model.
 * 
 * @author Jeremy
 * @author Amos Wenger (aka BlueSky) [code cleaning]
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 * 
 * @version 1.1
 */
public class AC3DPrototypeLoader
{
    /**
     * Reads the header block.
     */
    private static AC3DHeader loadHeader( BufferedReader reader ) throws IOException
    {
        String header = reader.readLine();
        String filetype = header.substring( 0, 4 );
        String versionText = header.substring( 4 );
        //int formatVersion = header.charAt( 4 ) - 87;
        int formatVersion = Integer.parseInt( versionText, 16 );
        if ( !( header.substring( 0, 4 ).equals( "AC3D" ) ) )
        {
            System.out.println( "File is not an AC3D file" );
            System.out.println( "Header read: " + header );
            
            throw( new IOException( "File is not an AC3D file" ) );
        }
        
        //System.out.println( "Found AC3D file of format version " + formatVersion );
        
        return( new AC3DHeader( filetype, formatVersion ) );
    }
    
    /**
     * Loads an AC3DMaterial from the data.
     * 
     * @param data The data from the file
     * @param materialIndex The index of this material
     * 
     * @return The <CODE>AC3DMaterial</CODE>
     */
    private static AC3DMaterial loadMaterial( String data, int materialIndex )
    {
        // The name of the material
        String name;
        // The colour of the material
        Color3f color;
        // The color of ambient light reflected
        Color3f amb;
        // The emited color of this material
        Color3f emis;
        // The speculative color of the material
        Color3f spec;
        // The shinyness
        float shininess;
        // The translucancy of the material
        float translucency;
        
        float r, g, b;
        StringTokenizer tokenizer = new StringTokenizer( data, " " );
        
        tokenizer.nextToken(); // Material
        
        name = tokenizer.nextToken();
        tokenizer.nextToken(); // rgb
        
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        color = new Color3f( r, g, b );
        
        tokenizer.nextToken();// amb
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        amb = new Color3f( r, g, b );
        
        tokenizer.nextToken();// emis
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        emis = new Color3f( r, g, b );
        
        tokenizer.nextToken();// spec
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        spec = new Color3f( r, g, b );
        
        tokenizer.nextToken();// shi
        shininess = Float.parseFloat( tokenizer.nextToken() );
        
        tokenizer.nextToken();// trans
        translucency = Float.parseFloat( tokenizer.nextToken() );
        
        //System.out.println( "Read material: " + name + " " + color + " " + amb + " " + emis + " " + spec + " " + shinyness + " " + translucency);
        
        return( new AC3DMaterial( materialIndex, name, color, amb, emis, spec, shininess, translucency ) );
    }
    
    /**
     * Loads an AC3DSurface from the file
     * 
     * @param reader The reader to use
     * 
     * @return The <CODE>AC3DSurface</CODE>
     * 
     * @throws IOException Thrown if an IO error happens
     * @throws FileFormatException Thrown if the file does not match the AC3D specification
     */
    private static AC3DSurface loadSurface( BufferedReader reader ) throws IOException
    {
        // The type of this surface
        int type;
        // is two sided?
        boolean twoSided;
        // is shaded
        boolean shaded;
        // materials index
        int material = -1;
        // The vertecies on this surface
        int[] surfVerts;
        // The texture coordiantes for each vertex
        float[][] textCoords;
        
        String token;
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer( line, " " );
        
        //System.out.println( "Surface: " + line );
        
        tokenizer.nextToken();
        int flags = Integer.parseInt( tokenizer.nextToken().substring( 2 ), 16 );
        type = ( flags & 0x0f );
        //System.out.println( "Type is: " + type );
        shaded = ( ( flags >> 4 ) & 1 ) == 1;
        // System.out.println( "Shaded: " + shaded );
        twoSided = ( ( flags >> 5 ) & 1 ) == 1;
        //System.out.println( "Two sided: " + twoSided );
        
        // read next token
        line = reader.readLine();
        tokenizer = new StringTokenizer( line, " " );
        token = tokenizer.nextToken();
        if ( token.equals( "mat" ) )
        {
            material = Integer.parseInt( tokenizer.nextToken() );
            // read next token
            line = reader.readLine();
            tokenizer = new StringTokenizer( line, " " );
            token = tokenizer.nextToken();
        }
        int numRefs = Integer.parseInt( tokenizer.nextToken() );
        
        surfVerts = new int[ numRefs ];
        textCoords = new float[ numRefs ][ 2 ];
        for ( int i = 0; i < numRefs; i++ )
        {
            int vertRef;
            line = reader.readLine();
            tokenizer = new StringTokenizer( line, " " );
            token = tokenizer.nextToken();
            vertRef = Integer.parseInt( token );
            surfVerts[i] = vertRef;
            textCoords[ i ][ 0 ] = Float.parseFloat( tokenizer.nextToken() );
            textCoords[ i ][ 1 ] = Float.parseFloat( tokenizer.nextToken() );
        }
        
        return( new AC3DSurface( type, twoSided, shaded, material, surfVerts, textCoords ) );
    }
    
    /**
     * Loads an AC3DObject from the file
     * 
     * @param line The first line of this tag
     * @param reader The reader to read from
     * 
     * @return The <CODE>AC3DObject</CODE>
     * 
     * @throws IOException Thrown if there is an IO Error
     * @throws FileFormatException Thrown if the file does not match the AC3D specification
     */
    private static AC3DObject loadObject( String line, BufferedReader reader ) throws IOException
    {
        // The type of the object
        int type;
        // The object name
        String name = null;
        // The texture name
        String textureName = null;
        // The rotation matrix of this object
        float[] rotation = { 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
        // The location vector of this object
        float[] location = new float[3];
        // The objects verticies
        float[][] verts = new float[0][0];
        // Texture repeat values
        float textureRepeatX = 1, textureRepeatY = 1;
        // Texture offset values
        float textureOffsetx = 0f, textureOffsety = 0f;
        // The object we are creating
        AC3DObject object;
        // temporary stor of surfaces
        ArrayList<AC3DSurface> tempSurfaces = new ArrayList<AC3DSurface>();
        
        StringTokenizer tokenizer = new StringTokenizer( line, " " );
        String token, stringType;
        
        tokenizer.nextToken();
        stringType = token = tokenizer.nextToken();
        if ( stringType.equals( "world" ) )
        {
            type = AC3DObject.TYPE_WORLD;
        }
        else if ( stringType.equals( "poly" ) )
        {
            type = AC3DObject.TYPE_POLY;
        }
        else if ( stringType.equals( "group" ) )
        {
            type = AC3DObject.TYPE_GROUP;
        }
        else
        {
            throw( new IOException( "Object type \"" + stringType + "\" is not valid" ) );
        }
        //System.out.println( "Object type: " + type );
        
        while ( !( token.equals( "kids" ) ) )
        {
            line = reader.readLine();
            tokenizer = new StringTokenizer( line );
            token = tokenizer.nextToken();
            if ( token.equals( "name" ) )
            {
                name = tokenizer.nextToken();
                //System.out.println( "name: " + name );
            }
            else if ( token.equals( "data" ) )
            {
                // I think this is just one line, the data block is a single line (maybe)
                line = reader.readLine();
                //System.out.println( "data tags unsupported" );
            }
            else if ( token.equals( "texture" ) )
            {
                // Read the first quote
                tokenizer.nextToken( "\"" );
                // read up to the second quote
                textureName = tokenizer.nextToken( "\"" );
            }
            else if ( token.equals( "texoff" ) )
            {
                textureOffsetx = Float.parseFloat( tokenizer.nextToken() );
                textureOffsety = Float.parseFloat( tokenizer.nextToken() );
            }
            else if ( token.equals( "texrep" ) )
            {
                textureRepeatX = Float.parseFloat( tokenizer.nextToken() );
                textureRepeatY = Float.parseFloat( tokenizer.nextToken() );
                //System.out.println( "repy: " + repy + " repx: " + repx );
            }
            else if ( token.equals( "rot" ) )
            {
                //System.out.println( "rot tag" );
                for ( int i = 0; i < 9; i++ )
                {
                    rotation[ i ] = Float.parseFloat( tokenizer.nextToken() );
                }
            }
            else if ( token.equals( "loc" ) )
            {
                // System.out.println( "loc tag" );
                for ( int i = 0; i < 3; i++ )
                {
                    location[ i ] = Float.parseFloat( tokenizer.nextToken() );
                }
            }
            else if ( token.equals( "url" ) )
            {
                System.out.println( "url tag unsuported" );
            }
            else if ( token.equals( "numvert" ) )
            {
                int numvert = Integer.parseInt( tokenizer.nextToken() );
                verts = new float[ numvert ][ 3 ];
                
                for ( int i = 0; i < numvert; i++ )
                {
                    line = reader.readLine();
                    tokenizer = new StringTokenizer( line, " " );
                    verts[ i ][ 0 ] = Float.parseFloat( tokenizer.nextToken() );
                    verts[ i ][ 1 ] = Float.parseFloat( tokenizer.nextToken() );
                    verts[ i ][ 2 ] = Float.parseFloat( tokenizer.nextToken() );
                }
            }
            else if ( token.equals( "numsurf" ) )
            {
                int numsurf = Integer.parseInt( tokenizer.nextToken() );
                //System.out.println( "Reading " + numsurf + " surfaces" );
                for ( int i = 0; i < numsurf; i++ )
                {
                    //System.out.println( "Reading surface " + i );
                    AC3DSurface surface = loadSurface( reader );
                    
                    // check we are a line, or that we have at least 3 vertecies
                    // as a poly with 3 vertecies is broked
                    if ( ( surface.isLine() ) || ( surface.getVertexReferenceCount() >= 3 ) )
                    {
                        tempSurfaces.add( surface );
                    }
                    else
                    {
                        //System.out.println( "Read broken surface" );
                        numsurf--;
                        i--;
                    }
                    //System.out.println( "Loaded surface " + i );
                }
            }
        }
        
        // at this point we should have all we need to create the object, we can
        // add the kids later
        object = new AC3DObject( type, name, textureName, rotation, location,
                                 verts, textureRepeatX, textureRepeatY, textureOffsetx,
                                 textureOffsety );
        for ( AC3DSurface surf: tempSurfaces )
        {
            object.addSurface( surf );
        }
        
        //System.out.println( "token is " + token );
        
        // there is always one, and only onw kids token
        if ( token.equals( "kids" ) )
        {
            int numKids = Integer.parseInt( tokenizer.nextToken() );
            
            //System.out.println( "Adding " + numKids + " to " + object.getName() + object );
            for ( int i = 0; i < numKids; i++ )
            {
                object.addObject( loadObject( reader ) );
            }
        }
        else
        {
            // Something is wrong with the file, the file spec says the the
            // surfaces and kids are the last tags, we have found something else
            throw( new IOException( "\"" + token.toString() + "\"" +
                                     " found where only a 'kids' should be" ) );
        }
        
        return( object );
    }
    
    /**
     * Loads an AC3DObject from the file
     * 
     * @param reader The reader to read from
     * 
     * @return The <CODE>AC3DObject</CODE>
     * 
     * @throws IOException Thrown if there is an IO Error
     * @throws FileFormatException Thrown if the file does not match the AC3D specification
     */
    private static AC3DObject loadObject(BufferedReader reader) throws IOException
    {
        String line = reader.readLine();
        
        return( loadObject( line, reader ) );
    }
    
    /**
     * The real loading method
     * 
     * @return The model containing the fully textured and materialed object
     */
    public static AC3DModelPrototype load( InputStream in, URL baseURL ) throws IOException
    {
        //long startTime = System.currentTimeMillis();
        
        BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
        AC3DModelPrototype model = new AC3DModelPrototype( loadHeader( reader ) );
        int materialCount = 0;
        
        String line;
        while ( ( line = reader.readLine() ) != null )
        {
            //System.out.println( "Read: " + line );
            String token = new StringTokenizer( line, " " ).nextToken();
            //System.out.println( "Token is: " + token );
            if ( token.equals( "MATERIAL" ) )
            {
                model.addMaterial( loadMaterial( line, materialCount++ ) );
            }
            else if ( token.equals( "OBJECT" ) )
            {
                model.addObject( loadObject( line, reader ) );
            }
        }
        //System.out.println( "Done" );
        
        //System.out.println( modelURL + " parsed in " + (System.currentTimeMillis() - startTime) + " milliseconds" );
        
        return( model );
    }
    
    /*
    public static void main(String[] args)
    {
        System.out.println( "Trying to load " + args[ 0 ] );
        
        try
        {
            if ( args[ 0 ].equals( "URL" ) )
            {
                load( new URL( args[ 1 ] ) );
            }
            else
            {
                load( args[ 0 ] );
            }
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
        }
    }
    */
}

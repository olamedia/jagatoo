/**
 * Copyright (c) 2007-2010, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.test.util.opengl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class OGLGenerator
{
    private static final String LWJGL_OPENGL_FOLDER = "/media/sda6/lwjgl-co/lwjgl/src/generated/org/lwjgl/opengl/";
    
    private static void writeClassHeader( BufferedWriter bw ) throws IOException
    {
        bw.write( "package org.jagatoo.opengl;" ); bw.newLine();
        bw.newLine();
        bw.write( "/**" ); bw.newLine();
        bw.write( " * A collection of OpenGL constants." ); bw.newLine();
        bw.write( " * " ); bw.newLine();
        bw.write( " * @author Marvin Froehilch (aka Qudus)" ); bw.newLine();
        bw.write( " */" ); bw.newLine();
        bw.write( "public class OGL" ); bw.newLine();
        bw.write( "{" ); bw.newLine();
    }
    
    private static void writeClassFooter( BufferedWriter bw ) throws IOException
    {
        bw.write( "}" ); bw.newLine();
    }
    
    public static void writeConstants( String filename, String since, BufferedWriter bw ) throws IOException
    {
        BufferedReader br = new BufferedReader( new FileReader( LWJGL_OPENGL_FOLDER + filename ) );
        
        String line;
        while ( ( line = br.readLine() ) != null )
        {
            line = line.trim();
            
            if ( line.startsWith( "public static final int " ) )
            {
                int equalsPos = line.indexOf( '=' );
                String declaration0 = line.substring( 0, equalsPos + 4 );
                String valueStr = line.substring( equalsPos + 2, line.length() - 1 );
                String hexValue = valueStr.substring( 2 ).toUpperCase();
                long intValue = Long.parseLong( hexValue, 16 );
                
                bw.write( "    /**" ); bw.newLine();
                bw.write( "     * int-value = " ); bw.write( String.valueOf( intValue ) ); bw.write( "<br>" ); bw.newLine();
                bw.write( "     * hex-value = 0x" ); bw.write( hexValue ); bw.newLine();
                bw.write( "     * " ); bw.newLine();
                bw.write( "     * @since " ); bw.write( since ); bw.newLine();
                bw.write( "     */" ); bw.newLine();
                bw.write( "    " ); bw.write( declaration0 ); bw.write( hexValue ); bw.write( ';' ); bw.newLine();
                bw.write( "    \n" );
            }
        }
        
        br.close();
    }
    
    public static void main( String[] args ) throws Throwable
    {
        BufferedWriter bw = new BufferedWriter( new FileWriter( "/space/workspace/jagatoo/src/org/jagatoo/opengl/OGL.java" ) );
        
        writeClassHeader( bw );
        
        writeConstants( "GL11.java", "OpenGL 1.1", bw );
        writeConstants( "GL12.java", "OpenGL 1.2", bw );
        writeConstants( "GL13.java", "OpenGL 1.3", bw );
        writeConstants( "GL14.java", "OpenGL 1.4", bw );
        writeConstants( "GL15.java", "OpenGL 1.5", bw );
        writeConstants( "GL20.java", "OpenGL 2.0", bw );
        writeConstants( "GL21.java", "OpenGL 2.1", bw );
        writeConstants( "EXTTextureCompressionS3TC.java", "EXTTextureCompressionS3TC", bw );
        writeConstants( "EXTTextureEnvCombine.java", "EXTTextureEnvCombine", bw );
        writeConstants( "ARBTextureEnvCombine.java", "ARBTextureEnvCombine", bw );
        writeConstants( "ARBShadows.java", "ARBTextureEnvCombine", bw );
        
        writeClassFooter( bw );
        
        bw.close();
    }
}

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
package org.jagatoo.loaders.models.bsp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.models.bsp.lumps.BSPLump;

/**
 * Represents a BSP source file.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
class BSPFile
{
    private byte[]            byteBuffer   = null;
    private FloatBuffer       floatBuffer  = null;
    private ByteBuffer        bBuffer      = null;
    private IntBuffer         intBuffer    = null;
    private ShortBuffer       shortBuffer  = null;
    
    private RandomAccessFile  in;
    
    private char[]            ID;
    private int               version;
    
    /**
     * An array of all lumps in this directory.
     */
    protected BSPLump[] lumps;
    
    public RandomAccessFile getInputFile()
    {
        return( in );
    }
    
    public void resetPointer() throws IOException
    {
        in.seek( 0 );
    }
    
    public void seek(int lump) throws IOException
    {
        in.seek( lumps[ lump ].offset );
    }
    
    public void skipBytes( int numBytes ) throws IOException
    {
        in.skipBytes( numBytes );
    }
    
    public byte readByte() throws IOException
    {
        return( in.readByte() );
    }
    
    public char readChar() throws IOException
    {
        return( (char)readByte() );
    }
    
    public int readInt() throws IOException
    {
        in.read( byteBuffer );
        
        return( intBuffer.get( 0 ) );
    }
    
    public short readShort() throws IOException
    {
        in.read( byteBuffer );
        
        return( shortBuffer.get( 0 ) );
    }
    
    public int readUnsignedShort() throws IOException
    {
        int low = in.read();
        int high = in.read();
        
        int ushort = ( ( high & 0xFF ) << 8 ) | ( low & 0xFF );
        
        return( ushort );
    }
    
    public float readFloat() throws IOException
    {
        in.read(byteBuffer);
        
        return( floatBuffer.get( 0 ) );
    }
    
    public void readFully( byte[] data ) throws IOException
    {
        in.readFully( data );
    }
    
    public byte[] readFully( int count ) throws IOException
    {
        byte[] data = new byte[ count ];
        
        in.readFully( data );
        
        return( data );
    }
    
    public void readFully( char[] data, int count ) throws IOException
    {
        for ( int i = 0; i < count; i++ )
        {
            data[ i ] = readChar();
        }
    }
    
    protected void readDirectory() throws IOException
    {
    	final int lumpCount;
    	switch ( version )
    	{
    	    case 46:
                lumpCount = 17;
    	        break;
            default:
                lumpCount = 15;
                break;
    	}
    	
        this.lumps = new BSPLump[ lumpCount ];
        
        for ( int i = 0; i < lumpCount; i++ )
        {
            lumps[ i ] = new BSPLump();
            lumps[ i ].offset = readInt();
            lumps[ i ].length = readInt();
            
            //Output Lump Offset and Length
            //System.err.println( i + " - " + lumps[ i ].offset + " - " + lumps[ i ].length);
        }
    }
    
    public void close() throws IOException
    {
        in.close();
    }
    
    public char[] getID()
    {
        return( ID );
    }
    
    public int getVersion()
    {
        return( version );
    }
    
    protected void checkVersion( int version ) throws IncorrectFormatException
    {
        /*
        if ( version != 0x2e )
            throw( new IncorrectFormatException( "Invalid Quake 3 BSP file" ) );
        */
        
        if ( ( version != 46 ) && ( version != 30 ) )
        {
            String errorCodeString = "Unable to read ";
            
            switch ( version ) 
            {
                case 29:
                    errorCodeString += "Quake1 BSP file. version = " + version;
                    break;
                case 38:
                    errorCodeString += "Quake2 BSP file. ID = " + new String( ID ) +" - version = " + version;
                    break;
                /*
                case 30:
                    errorCodeString += "Half-Life1 BSP file. version = " + version;
                    break;
                */
                case 19:   
                case 20:
                    errorCodeString += "Half-Life2 BSP file. ID = " + new String( ID ) + " - version = " + version;
                    break;
                default:
                    errorCodeString = "Unknown BSP version number or BSP format.";
                    break;
            }
            
            throw new IncorrectFormatException( errorCodeString );
        }
    }
    
    protected void checkHeader() throws IOException, IncorrectFormatException
    {
        // read bsp "magic number" ( "IBSP" or "VBSP" )
        this.ID = new char[ 4 ];
        this.ID[ 0 ] = readChar();
        this.ID[ 1 ] = readChar();
        this.ID[ 2 ] = readChar();
        this.ID[ 3 ] = readChar();
        
        if ( !new String( ID ).substring( 1 ).equals( "BSP" ) )
        {
            //throw new IncorrectFormatException( "The read file is not a valid BSP level file." );
            
            /*
             * We need to return the file-pointer to the beginning of the file
             * to load the version, since HalfLife files don't have the
             * "magic number"!
             */
            resetPointer();
        }
        
        // read bsp version
        this.version = readInt();
        
        checkVersion( version );
    }
    
    public BSPFile( File file ) throws IOException, IncorrectFormatException
    {
        super();
        
        this.byteBuffer = new byte[ 4 ];
        this.bBuffer = ByteBuffer.wrap( byteBuffer );
        this.bBuffer.order( ByteOrder.LITTLE_ENDIAN );
        this.floatBuffer = bBuffer.asFloatBuffer();
        this.intBuffer = bBuffer.asIntBuffer();
        this.shortBuffer = bBuffer.asShortBuffer();
        
        this.in = new RandomAccessFile( file, "r" );
        
        checkHeader();
   	 	
        readDirectory();
    }
    
    public BSPFile(String filename) throws IOException, IncorrectFormatException
    {
        this( new File( filename ) );
    }
    
    protected BSPFile() {}
}

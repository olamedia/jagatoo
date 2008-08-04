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
package org.jagatoo.loaders.models.bsp;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingErrorException;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.util.streams.StreamUtils;
import org.openmali.FastMath;

/**
 * Represents a Half-Life WAD source file.
 * 
 * @author Sebastian Thiele (aka SETIssl)
 * @author Marvin Froehlich (aka Qudus)
 */
public class WADFile
{
    private static final int MAGIC_NUMBER_WAD3 = 0x57414433;
    
    // wad directory, contains info for integrated files
    private static class WADDirectoryEntry 
    {
        public long    offset          = 0;    // - Offset 
        public int     compFileSize    = 0;    // - Compressed File Size 
        public int     uncompFileSize  = 0;    // - Uncompressed File Size 
        public byte    fileType        = 0;    // - File Type 
        public byte    compType        = 0;    // - Compression Type 
        public byte[]  padding         = null; // - Padding 
        public String  fileName        = null; // - Filename (null-terminated)
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
            return( this.getClass().getSimpleName() + " { " +
                    "name: \"" + fileName + "\"" +
                    "\t | offset: " + offset +
                    "\t | compFileSize: " + compFileSize +
                    "\t | uncompFileSize: " + uncompFileSize +
                    "\t | fileType: " + fileType +
                    "\t | compType: " + compType +
                    "\t | pad1: " + padding[0] + "\t | pad2: " + padding[1] +
                    " }"
                  );
        }
    }
    
    private final URL     wadResource;
    private final String  wadFilename;
    private int           magicNumber;
    private String        wadType;
    
    private final HashMap<String, WADDirectoryEntry> wadDir;
    
    public final String getWADFilename()
    {
        return( wadFilename );
    }
    
    public final String getWadType()
    {
        return( wadType );
    }
    
    public final int getLumpCount()
    {
        return( wadDir.size() );
    }
    
    public final String[] getWADResources()
    {
        String[] result = new String[ wadDir.size() ];
        
        int i = 0;
        for ( String s : wadDir.keySet() )
        {
            result[ i ] = s;
        }
        
        java.util.Arrays.sort( result );
        
        return( result );
    }
    
    public final boolean containsResource( String resName )
    {
        return( wadDir.containsKey( resName.toLowerCase() ) );
    }
    
    public final BufferedInputStream getResourceAsStream( String resName ) throws IOException
    {
        WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
        
        if ( entry == null )
        {
            return( null );
        }
        
        InputStream in = wadResource.openStream();
        if ( !( in instanceof BufferedInputStream ) )
        {
            in = new BufferedInputStream( in );
        }
        
        in.skip( entry.offset );
        
        return( (BufferedInputStream)in );
    }
    
    public final void exportResource( String resName, String filename ) throws IOException
    {
        WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
        
        if ( entry == null )
        {
            throw new IOException( "The resource was not found in this WAD file." );
        }
        
        InputStream in = wadResource.openStream();
        if ( !( in instanceof BufferedInputStream ) )
        {
            in = new BufferedInputStream( in );
        }
        
        in.skip( entry.offset );
        
        BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( filename ) );
        for ( int i = 0; i < entry.compFileSize; i++ )
        {
            out.write( (byte)in.read() );
        }
        out.close();
        
        in.close();
    }
    
    private void readTransparentTexture( DataInputStream din, byte[][] palette, int width, int height, ByteBuffer bb ) throws IOException
    {
        byte r, g, b, a;
        
        // convert the mip palette based bitmap to RGB format...
        int size = width * height;
        for ( int i = 0; i < size; i++ )
        {
            int palIdx = din.read();
            
            r = palette[palIdx][0];
            g = palette[palIdx][1];
            b = palette[palIdx][2];
            a = (byte)255;
            
            if ( ( r == (byte)0 ) && ( g == (byte)0 ) && ( b == (byte)255 ) )
            {
                r = (byte)0;
                b = (byte)0;
                g = (byte)0;
                a = (byte)0;
            }
            
            bb.put( r );
            bb.put( g );
            bb.put( b );
            bb.put( a );
        }
    }
    
    private void readSkyTexture( DataInputStream din, byte[][] palette, int width, int height, ByteBuffer bb ) throws IOException
    {
        /*
        String skyname;
        String skyfilename;
        String skypathname;
        byte[] tga_data;
        int sky_index;
        
        // convert the mip palette based bitmap to RGB format...
        pos = 0;
        for (i = 0; i < size; i++)
        {
            rgba[pos++] = palette[indices[i]*3];
            rgba[pos++] = palette[indices[i]*3 + 1];
            rgba[pos++] = palette[indices[i]*3 + 2];
        }
        
        textures[texture_index] = new Texture(rgba, width, height, GL_RGB, nearest);
        
        textures[texture_index]->flags = TEXTURE_SKY;
        
        bool skyname_found = FALSE;
        
        int ent_index = -1;
        
        if ((ent_index = FindEntityByClassname(ent_index, "worldspawn")) != -1)
        {
            char *value;
            
            // look for a worldspawn "skyname" key...
            value = ValueForKey(&entities[ent_index], "skyname");
            if (value[0])
            {
                strncpy(skyname, value, 64);
                skyname_found = TRUE;
            }
            
            // didn't find it?  then look for a "message" key and use that as skyname
            if (!skyname_found)
            {
                value = ValueForKey(&entities[ent_index], "message");
                if (value[0])
                {
                    strncpy(skyname, value, 64);
                    skyname_found = TRUE;
                }
            }
        }
        
        if (skyname_found)
        {
            for (sky_index = 0; sky_index < 6; sky_index++)
            {
                tga_data = NULL;
                
                strcpy(skyfilename, "gfx/env/");
                strcat(skyfilename, skyname);
                strcat(skyfilename, skys[sky_index]);
                strcat(skyfilename, ".tga");
                
                // see if sky texture is in a MOD directory first...
                for (int mod_index = 0; mod_index < config.num_mods; mod_index++)
                {
                    strcpy(skypathname, config.mods[mod_index].dir);
                    strcat(skypathname, skyfilename);
                    
                    if (FileTime(skypathname) != -1)
                    {
                        tga_data = TGA_Load(skypathname, &width, &height);
                        break;  // break out of for loop
                    }
                }
                
                if (tga_data == NULL)
                {
                    // load the sky texture from PAK file...
                    tga_data = TGA_LoadPak(skyfilename, &width, &height);
                }
                
                if (!tga_data)
                    Error("Error loading sky texture: %s\n", skyfilename);
                
                sky_textures[sky_index] = new Texture(tga_data, width, height, GL_RGB, nearest);
                
                sky_textures[sky_index]->flags = TEXTURE_SKY;
                
                free(tga_data);
            }
        }
        else
        {
            for (sky_index = 0; sky_index < 6; sky_index++)
                sky_textures[sky_index] = textures[texture_index];
        }
        */
    }
    
    private void readSpecialTexture( DataInputStream din, byte[][] palette, int width, int height, ByteBuffer bb ) throws IOException
    {
        final byte special_texture_transparency = (byte)0;
        
        // convert the mip palette based bitmap to RGB format...
        int size = width * height;
        for ( int i = 0; i < size; i++ )
        {
            int palIdx = din.read();
            
            bb.put( palette[palIdx][0] );
            bb.put( palette[palIdx][1] );
            bb.put( palette[palIdx][2] );
            
            bb.put( special_texture_transparency );
        }
    }
    
    private void readRegularTexture( DataInputStream din, byte[][] palette, int width, int height, ByteBuffer bb ) throws IOException
    {
        float gamma = 1.0f;
        float f, inf;
        
        for ( int i = 0; i < palette.length; i++ )
        {
            for ( int j = 0; j < 3; j++ )
            {
                f = (float)FastMath.pow( ( ( palette[i][j] & 0xFF ) + 1 ) / 256f, gamma );
                inf = f * 255.0f + 0.5f;
                if ( inf < 0 )
                    inf = 0f;
                if ( inf > 255f )
                    inf = 255f;
                palette[i][j] = (byte)inf;
            }
        }
        
        // convert the mip palette based bitmap to RGB format...
        int size = width * height;
        for ( int j = 0; j < size; j++ )
        {
            int palIdx = din.read();
            
            bb.put( palette[palIdx][0] );
            bb.put( palette[palIdx][1] );
            bb.put( palette[palIdx][2] );
        }
    }
    
    public final AbstractTexture readTexture( String resName, AppearanceFactory appFactory ) throws IOException
    {
        if ( magicNumber == MAGIC_NUMBER_WAD3 )
        {
            WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
            
            if ( entry == null )
            {
                throw new IOException( "The resource was not found in this WAD file." );
            }
            
            InputStream in = wadResource.openStream();
            if ( !( in instanceof BufferedInputStream ) )
            {
                in = new BufferedInputStream( in );
            }
            
            DataInputStream din = new DataInputStream( in );
            
            din.skip( entry.offset );
            
            byte[] nameBytes = new byte[ 16 ];
            din.read( nameBytes );
            //System.out.println( new String( name ).trim() );
            
            int width = Integer.reverseBytes( din.readInt() );
            int height = Integer.reverseBytes( din.readInt() );
            
            //System.out.println( width );
            //System.out.println( height );
            
            int[] offsets = new int[ 4 ];
            for ( int i = 0; i < 4; i++ )
            {
                offsets[i] = Integer.reverseBytes( din.readInt() );
                //System.out.println( offsets[i] );
            }
            
            int imgDataSize0 = offsets[1] - offsets[0];
            int imgDataSize = imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            
            byte[] imgData = new byte[ imgDataSize ];
            din.read( imgData );
            
            /*
             * Read the palette first...
             */
            int paletteSize = StreamUtils.readSwappedShort( din );
            byte[][] palette = new byte[paletteSize][];
            for ( int i = 0; i < paletteSize; i++ )
            {
                palette[i] = new byte[] { (byte)in.read(), (byte)in.read(), (byte)in.read() };
            }
            din.close();
            
            boolean isTransparentTexture = resName.startsWith( "{" );
            boolean isSkyTexture = resName.startsWith( "sky" );
            boolean isSpecialTexture = resName.startsWith( "clip" ) || resName.startsWith( "origin" ) || resName.startsWith( "aatrigger" );
            
            din = new DataInputStream( new ByteArrayInputStream( imgData ) );
            
            AbstractTextureImage[] mipmaps = new AbstractTextureImage[ 4 ];
            
            for ( int i = 0; i < 4; i++ )
            {
                //System.out.println( width + ", " + height );
                
                if ( isTransparentTexture || isSpecialTexture )
                    mipmaps[i] = appFactory.createTextureImage( AbstractTextureImage.Format.RGBA, width, height );
                else
                    mipmaps[i] = appFactory.createTextureImage( AbstractTextureImage.Format.RGB, width, height );
                ByteBuffer bb = mipmaps[i].getDataBuffer();
                
                if ( isTransparentTexture )
                    readTransparentTexture( din, palette, width, height, bb );
                else if ( isSkyTexture )
                    readSkyTexture( din, palette, width, height, bb );
                else if ( isSpecialTexture )
                    readSpecialTexture( din, palette, width, height, bb );
                else
                    readRegularTexture( din, palette, width, height, bb );
                
                bb.position( 0 );
                if ( mipmaps[i].getFormat().hasAlpha() )
                    bb.limit( width * height * 4 );
                else
                    bb.limit( width * height * 3 );
                
                width = width >> 1;
                height = height >> 1;
            }
            
            din.close();
            
            AbstractTexture texture = appFactory.createTexture( mipmaps[0], true && !isSkyTexture );
            if ( !isSkyTexture )
            {
                //texture.setImage( 1, mipmaps[1] );
                //texture.setImage( 2, mipmaps[2] );
                //texture.setImage( 3, mipmaps[3] );
            }
            
            String name = new String( nameBytes ).trim();
            texture.setName( name );
            
            // Xith-specific name-setter!
            try
            {
                String resName2 = this.getWadType() + "://" + this.getWADFilename() + "/" + name;
                
                Method m = texture.getClass().getMethod( "setResourceName", String.class );
                m.invoke( texture, resName2 );
            }
            catch ( Throwable t )
            {
                t.printStackTrace();
            }
            
            return( texture );
        }
        
        return( null );
    }
    
    private HashMap<String, WADDirectoryEntry> readWADDirectory( URL wadFile ) throws IOException, IncorrectFormatException, ParsingErrorException
    {      
        try
        {
            DataInputStream in = new DataInputStream( new BufferedInputStream( wadFile.openStream() ) );
            
            // read WAD header
            int magicNumber = in.readInt();
            if ( magicNumber != MAGIC_NUMBER_WAD3 )
            {
                throw new IncorrectFormatException( "This is not a WAD3 file!" );
            }
            
            this.magicNumber = magicNumber;
            this.wadType = "WAD3";
            
            int lumpCount = Integer.reverseBytes( in.readInt() ); // - Number of files
            int dirOffset = Integer.reverseBytes( in.readInt() ); // - Directory offset
            
            /*
            System.out.println( "WadFile: " + wadFile );
            System.out.print( " | lumps: " + lumpCount ); 
            System.out.println( " | offset: " + dirOffset );
            */
            
            HashMap<String, WADDirectoryEntry> wadDir = new HashMap<String, WADDirectoryEntry>( lumpCount );
            
            // read Lump Dir
            in.skipBytes( dirOffset - ( 3 * 4 ) );
            
            byte[] bytes16 = new byte[ 16 ];
            
            for ( int i = 0; i < lumpCount; i++ ) 
            {
                WADDirectoryEntry entry = new WADDirectoryEntry();
                
                entry.offset = Integer.reverseBytes( in.readInt() );
                entry.compFileSize = Integer.reverseBytes( in.readInt() );
                entry.uncompFileSize = Integer.reverseBytes( in.readInt() );
                
                entry.fileType = in.readByte();
                entry.compType = in.readByte();
                entry.padding = new byte[] { in.readByte(), in.readByte() };
                
                in.read( bytes16, 0, 16 );
                entry.fileName = new String( bytes16 ).trim();
                
                wadDir.put( entry.fileName.toLowerCase(), entry );
                
                //if ( getWADFilename().equals( "cs_office.wad" ) )
                //    System.out.println( entry.fileName );
                
                //System.out.println( entry );
            }
            
            in.close();
            
            return( wadDir );
        }
        catch ( IOException ioe )
        {
            throw ioe;
        }
        catch ( Throwable t )
        {
            throw new ParsingErrorException( t );
        }
    }
    
    private static final String getWADFileSimpleName( URL wadResource )
    {
        String filePath = wadResource.getFile();
        
        int lastSlashPos = filePath.lastIndexOf( '/' );
        
        if ( lastSlashPos == -1 )
            return( filePath );
        else
            return( filePath.substring( lastSlashPos + 1 ) );
    }
    
    public WADFile( URL wadResource ) throws IOException, IncorrectFormatException, ParsingErrorException
    {
        super();
        
        this.wadResource = wadResource;
        this.wadFilename = getWADFileSimpleName( wadResource );
        
        this.wadDir = readWADDirectory( wadResource );
    }
}

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
package org.jagatoo.loaders.textures.locators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Locates a textures from a java.io.File object
 * 
 * @author Matthias Mann
 */
public class TextureStreamLocatorFile implements TextureStreamLocator
{
    protected File baseDir;
    
    public String getBaseDirName()
    {
        return ( baseDir.getAbsolutePath() );
    }
    
    /**
     * Creates a new instance of FileTextureStreamLocator
     * 
     * @param baseDir
     */
    public TextureStreamLocatorFile( File baseDir )
    {
        this.baseDir = baseDir;
    }
    
    /**
     * Creates a new instance of FileTextureStreamLocator
     * 
     * @param baseDir
     */
    public TextureStreamLocatorFile( String baseDir )
    {
        this.baseDir = new File( baseDir );
    }
    
    public File getBaseDir()
    {
        return ( baseDir );
    }
    
    public void setBaseDir( File baseDir )
    {
        this.baseDir = baseDir;
    }
    
    public InputStream openTextureStream( String name )
    {
        try
        {
            File f = new File( baseDir, name );
            return ( new FileInputStream( f ) );
        }
        catch ( IOException ex )
        {
            return ( null );
        }
    }
}

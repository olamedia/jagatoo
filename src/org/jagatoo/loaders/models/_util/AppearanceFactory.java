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
package org.jagatoo.loaders.models._util;

import java.io.InputStream;
import java.net.URL;

import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class AppearanceFactory
{
    public static final int APP_TEXTURE_MODE_MODULATE = 0;
    public static final int APP_TEXTURE_MODE_REPLACE = 1;
    public static final int APP_TEXTURE_MODE_COMBINE = 2;
    
    private static final void generateColorBytes( float[] color, byte[] bytes, int b0 )
    {
        if ( color == null )
            return;
        
        bytes[b0 + 0] = (byte)( (int)( color[0] * 255f ) & 0xFF );
        bytes[b0 + 1] = (byte)( (int)( color[1] * 255f ) & 0xFF );
        bytes[b0 + 2] = (byte)( (int)( color[2] * 255f ) & 0xFF );
        
        if ( color.length > 3 )
            bytes[b0 + 3] = (byte)( (int)( color[3] * 255f ) & 0xFF );
    }
    
    private static final void generateFloatBytes( float f, byte[] bytes, int b0 )
    {
        int i = Float.floatToIntBits( f );
        
        bytes[b0 + 0] = (byte)( ( i & 0xFF000000 ) >> 24 );
        bytes[b0 + 1] = (byte)( ( i & 0x00FF0000 ) >> 16 );
        bytes[b0 + 2] = (byte)( ( i & 0x0000FF00 ) >> 8 );
        bytes[b0 + 3] = (byte)( ( i & 0x000000FF ) >> 0 );
    }
    
    private static final String getCompressedString( byte[] bytes )
    {
        char[] chars;
        if ( ( bytes.length % 2 ) == 0 )
            chars = new char[ bytes.length / 2 ];
        else
            chars = new char[ ( bytes.length + 1 ) / 2 ];
        
        int j = 0;
        for ( int i = 0; i < bytes.length; i += 2 )
        {
            chars[j] = (char)( ( bytes[i + 0] & 0xFF ) << 8 );
            chars[j] |= (char)( bytes[i + 1] & 0xFF );
            
            j++;
        }
        
        return( new String( chars ) );
    }
    
    /**
     * Generates a unique ID-String (of minimum length), that identifies
     * an Appearance globally.<br>
     * This String can be used to cache instances of Appearance to avoid
     * recreation and unnecessary compares.
     * 
     * @return an ID-String for an Appearance.
     */
    public static synchronized String generateAppearanceID( // TransparencyAttributes...
                                                            int blendingMode,
                                                            int blendFunction,
                                                            float transparancy,
                                                            // Material...
                                                            int colorTarget,
                                                            float[] ambient,
                                                            float[] emissive,
                                                            float[] diffuse,
                                                            float[] specular,
                                                            float shininess,
                                                            Boolean normalizeNormals,
                                                            // ColoringAttributes...
                                                            int shadeModel,
                                                            float[] color,
                                                            // RenderingAttributes...
                                                            Boolean depthBufferEnabled,
                                                            Boolean depthBufferWriteEnabled,
                                                            float alphaTestValue,
                                                            int alphaTestFunction,
                                                            int stencilFuncSep,
                                                            int stencilOpSep,
                                                            int stencilMaskSep,
                                                            int depthTestFunction,
                                                            Boolean ignoreVertexColors,
                                                            Boolean stencilEnabled,
                                                            int stencilOpFail,
                                                            int stencilOpZFail,
                                                            int stencilOpZPass,
                                                            int stencilTestFunction,
                                                            int stencilRef,
                                                            int stencilMask,
                                                            int colorWriteMask,
                                                            // PolygonAttributes...
                                                            int faceCullMode,
                                                            int drawMode,
                                                            float polygonOffset,
                                                            float polygonOffsetFactor,
                                                            Boolean backfaceNormalFlip,
                                                            Boolean anitaliasing,
                                                            Boolean sortEnabled,
                                                            // Line- and PointAttributes are not important!
                                                            // Texture-units...
                                                            String[] textureName,
                                                            int[] boundaryModeS,
                                                            int[] boundaryModeT,
                                                            int[] minFilter,
                                                            int[] magFilter,
                                                            int[] textureMode,
                                                            int[] perspCorrMode,
                                                            float[][] texBlendColor,
                                                            int[] texTransformID, // some ID for the texture transform matrix
                                                            int[] combineRGBMode,
                                                            int[] combineAlphaMode,
                                                            int[][] combineRGBSource,
                                                            int[][] combineAlphaSource,
                                                            int[][] combineRGBFunction,
                                                            int[][] combineAlphaFunction,
                                                            int[] combineRGBScale,
                                                            int[] combineAlphaScale,
                                                            int[] compareMode,
                                                            int[] compareFunc,
                                                            float[] anisotropicLevel,
                                                            int[] tcGenMode,
                                                            int[] tcGenTexCoordMode,
                                                            float[][] tcGenPlaneS,
                                                            float[][] tcGenPlaneT,
                                                            float[][] tcGenPlaneR,
                                                            float[][] tcGenPlaneQ
                                             )
    {
        /*
         * This implementation only honors the attributes,
         * that are currently used by loaded shapes.
         * If more attributes are used, this implementation
         * should be replenished.
         */
        
        byte[] bytes = new byte[ 64 ];
        byte byt;
        int b0;
        
        // TransparencyAttributes...
        b0 = 0;
        bytes[b0 + 0] = (byte)( blendingMode & 0xFF );
        bytes[b0 + 1] = (byte)( blendFunction & 0xFF );
        bytes[b0 + 2] = (byte)( (int)( transparancy * 255f ) & 0xFF );
        
        // Material...
        b0 = 3;
        bytes[b0 + 0] = (byte)( colorTarget & 0xFF );
        generateColorBytes( ambient, bytes, b0 + 1 );
        generateColorBytes( emissive, bytes, b0 + 4 );
        generateColorBytes( diffuse, bytes, b0 + 7 );
        generateColorBytes( specular, bytes, b0 + 10 );
        bytes[b0 + 13] = (byte)( (int)( shininess * 255f / 128f ) & 0xFF ); // normalize values 1..128 to a byte
        if ( normalizeNormals != null )
            bytes[b0 + 14] = ( normalizeNormals.booleanValue() ? (byte)1 : (byte)2 );
        
        // ColoringAttributes...
        b0 = 15;
        bytes[b0 + 0] = (byte)( shadeModel & 0xFF );
        generateColorBytes( color, bytes, b0 + 1 );
        
        // RenderingAttributes...
        b0 = 20;
        byt = (byte)0;
        if ( depthBufferEnabled != null )
            byt |= ( depthBufferEnabled.booleanValue() ? ( 1 << 6 ) : ( 2 << 6 ) );
        if ( depthBufferWriteEnabled != null )
            byt |= ( depthBufferWriteEnabled.booleanValue() ? ( 1 << 4 ) : ( 2 << 4 ) );
        if ( ignoreVertexColors != null )
            byt |= ( ignoreVertexColors.booleanValue() ? ( 1 << 2 ) : ( 2 << 2 ) );
        if ( stencilEnabled != null )
            byt |= ( stencilEnabled.booleanValue() ? ( 1 << 0 ) : ( 2 << 0 ) );
        bytes[b0 + 0] = byt;
        generateFloatBytes( alphaTestValue, bytes, b0 + 1 );
        bytes[b0 + 5] = (byte)( alphaTestFunction & 0xFF );
        bytes[b0 + 6] = (byte)( stencilFuncSep & 0xFF );
        bytes[b0 + 7] = (byte)( stencilOpSep & 0xFF );
        bytes[b0 + 8] = (byte)( stencilMaskSep & 0xFF );
        bytes[b0 + 9] = (byte)( depthTestFunction & 0xFF );
        bytes[b0 + 10] = (byte)( stencilOpFail & 0xFF );
        bytes[b0 + 11] = (byte)( stencilOpZFail & 0xFF );
        bytes[b0 + 12] = (byte)( stencilOpZPass & 0xFF );
        bytes[b0 + 13] = (byte)( stencilTestFunction & 0xFF );
        bytes[b0 + 14] = (byte)( stencilRef & 0xFF );
        bytes[b0 + 15] = (byte)( stencilMask & 0xFF );
        bytes[b0 + 16] = (byte)( colorWriteMask & 0xFF );
        
        // PolygonAttributes...
        b0 = 37;
        bytes[b0 + 0] = (byte)( faceCullMode & 0xFF );
        bytes[b0 + 1] = (byte)( drawMode & 0xFF );
        bytes[b0 + 2] = (byte)( (int)polygonOffset & 0xFF );
        bytes[b0 + 3] = (byte)( (int)polygonOffsetFactor & 0xFF );
        byt = (byte)0;
        if ( backfaceNormalFlip != null )
            byt |= ( backfaceNormalFlip.booleanValue() ? ( 1 << 6 ) : ( 2 << 6 ) );
        if ( anitaliasing != null )
            byt |= ( anitaliasing.booleanValue() ? ( 1 << 4 ) : ( 2 << 4 ) );
        if ( sortEnabled != null )
            byt |= ( sortEnabled.booleanValue() ? ( 1 << 2 ) : ( 2 << 2 ) );
        bytes[b0 + 4] = byt;
        
        /*
        // Texture-units...
        String[] textureName,
        int[] boundaryModeS,
        int[] boundaryModeT,
        int[] minFilter,
        int[] magFilter,
        int[] textureMode,
        int[] perspCorrMode,
        float[][] texBlendColor,
        int[] texTransformID, // some ID for the texture transform matrix
        int[] combineRGBMode,
        int[] combineAlphaMode,
        int[][] combineRGBSource,
        int[][] combineAlphaSource,
        int[][] combineRGBFunction,
        int[][] combineAlphaFunction,
        int[] combineRGBScale,
        int[] combineAlphaScale,
        int[] compareMode,
        int[] compareFunc,
        float[] anisotropicLevel,
        int[] tcGenMode,
        int[] tcGenTexCoordMode,
        float[][] tcGenPlaneS,
        float[][] tcGenPlaneT,
        float[][] tcGenPlaneR,
        float[][] tcGenPlaneQ
        */
        
        return( getCompressedString( bytes ) );
    }
    
    public abstract Object createAppearance( String appID );
    
    public abstract void setTexture( Object appearance, String appID, int textureUnit, AbstractTexture texture );
    
    public abstract void setTextureMode( Object appearance, String appID, int textureUnit, int textureMode );
    
    public abstract void applyAppearance( Object appearance, String appID, Object geometry );
    
    public abstract AbstractTexture loadTexture( InputStream in, String texName, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTexture loadTexture( URL url, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTexture loadOrGetTexture( String texName, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTextureImage createTextureImage( AbstractTextureImage.Format format, int width, int height );
    
    public abstract AbstractTexture createTexture( AbstractTextureImage texImage0, boolean generateMipmaps );
}

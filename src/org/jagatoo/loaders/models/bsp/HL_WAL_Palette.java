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

import java.io.IOException;
import java.io.InputStream;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class HL_WAL_Palette
{
    private static final byte[][] allocatePalette()
    {
        byte[][] palette = new byte[][]
        {
            { (byte)0, (byte)0, (byte)0 },
            { (byte)15, (byte)15, (byte)15 },
            { (byte)31, (byte)31, (byte)31 },
            { (byte)47, (byte)47, (byte)47 },
            { (byte)63, (byte)63, (byte)63 },
            { (byte)75, (byte)75, (byte)75 },
            { (byte)91, (byte)91, (byte)91 },
            { (byte)107, (byte)107, (byte)107 },
            { (byte)123, (byte)123, (byte)123 },
            { (byte)139, (byte)139, (byte)139 },
            { (byte)155, (byte)155, (byte)155 },
            { (byte)171, (byte)171, (byte)171 },
            { (byte)187, (byte)187, (byte)187 },
            { (byte)203, (byte)203, (byte)203 },
            { (byte)219, (byte)219, (byte)219 },
            { (byte)235, (byte)235, (byte)235 },
            { (byte)15, (byte)11, (byte)7 },
            { (byte)23, (byte)15, (byte)11 },
            { (byte)31, (byte)23, (byte)11 },
            { (byte)39, (byte)27, (byte)15 },
            { (byte)47, (byte)35, (byte)19 },
            { (byte)55, (byte)43, (byte)23 },
            { (byte)63, (byte)47, (byte)23 },
            { (byte)75, (byte)55, (byte)27 },
            { (byte)83, (byte)59, (byte)27 },
            { (byte)91, (byte)67, (byte)31 },
            { (byte)99, (byte)75, (byte)31 },
            { (byte)107, (byte)83, (byte)31 },
            { (byte)115, (byte)87, (byte)31 },
            { (byte)123, (byte)95, (byte)35 },
            { (byte)131, (byte)103, (byte)35 },
            { (byte)143, (byte)111, (byte)35 },
            { (byte)11, (byte)11, (byte)15 },
            { (byte)19, (byte)19, (byte)27 },
            { (byte)27, (byte)27, (byte)39 },
            { (byte)39, (byte)39, (byte)51 },
            { (byte)47, (byte)47, (byte)63 },
            { (byte)55, (byte)55, (byte)75 },
            { (byte)63, (byte)63, (byte)87 },
            { (byte)71, (byte)71, (byte)103 },
            { (byte)79, (byte)79, (byte)115 },
            { (byte)91, (byte)91, (byte)127 },
            { (byte)99, (byte)99, (byte)139 },
            { (byte)107, (byte)107, (byte)151 },
            { (byte)115, (byte)115, (byte)163 },
            { (byte)123, (byte)123, (byte)175 },
            { (byte)131, (byte)131, (byte)187 },
            { (byte)139, (byte)139, (byte)203 },
            { (byte)0, (byte)0, (byte)0 },
            { (byte)7, (byte)7, (byte)0 },
            { (byte)11, (byte)11, (byte)0 },
            { (byte)19, (byte)19, (byte)0 },
            { (byte)27, (byte)27, (byte)0 },
            { (byte)35, (byte)35, (byte)0 },
            { (byte)43, (byte)43, (byte)7 },
            { (byte)47, (byte)47, (byte)7 },
            { (byte)55, (byte)55, (byte)7 },
            { (byte)63, (byte)63, (byte)7 },
            { (byte)71, (byte)71, (byte)7 },
            { (byte)75, (byte)75, (byte)11 },
            { (byte)83, (byte)83, (byte)11 },
            { (byte)91, (byte)91, (byte)11 },
            { (byte)99, (byte)99, (byte)11 },
            { (byte)107, (byte)107, (byte)15 },
            { (byte)7, (byte)0, (byte)0 },
            { (byte)15, (byte)0, (byte)0 },
            { (byte)23, (byte)0, (byte)0 },
            { (byte)31, (byte)0, (byte)0 },
            { (byte)39, (byte)0, (byte)0 },
            { (byte)47, (byte)0, (byte)0 },
            { (byte)55, (byte)0, (byte)0 },
            { (byte)63, (byte)0, (byte)0 },
            { (byte)71, (byte)0, (byte)0 },
            { (byte)79, (byte)0, (byte)0 },
            { (byte)87, (byte)0, (byte)0 },
            { (byte)95, (byte)0, (byte)0 },
            { (byte)103, (byte)0, (byte)0 },
            { (byte)111, (byte)0, (byte)0 },
            { (byte)119, (byte)0, (byte)0 },
            { (byte)127, (byte)0, (byte)0 },
            { (byte)19, (byte)19, (byte)0 },
            { (byte)27, (byte)27, (byte)0 },
            { (byte)35, (byte)35, (byte)0 },
            { (byte)47, (byte)43, (byte)0 },
            { (byte)55, (byte)47, (byte)0 },
            { (byte)67, (byte)55, (byte)0 },
            { (byte)75, (byte)59, (byte)7 },
            { (byte)87, (byte)67, (byte)7 },
            { (byte)95, (byte)71, (byte)7 },
            { (byte)107, (byte)75, (byte)11 },
            { (byte)119, (byte)83, (byte)15 },
            { (byte)131, (byte)87, (byte)19 },
            { (byte)139, (byte)91, (byte)19 },
            { (byte)151, (byte)95, (byte)27 },
            { (byte)163, (byte)99, (byte)31 },
            { (byte)175, (byte)103, (byte)35 },
            { (byte)35, (byte)19, (byte)7 },
            { (byte)47, (byte)23, (byte)11 },
            { (byte)59, (byte)31, (byte)15 },
            { (byte)75, (byte)35, (byte)19 },
            { (byte)87, (byte)43, (byte)23 },
            { (byte)99, (byte)47, (byte)31 },
            { (byte)115, (byte)55, (byte)35 },
            { (byte)127, (byte)59, (byte)43 },
            { (byte)143, (byte)67, (byte)51 },
            { (byte)159, (byte)79, (byte)51 },
            { (byte)175, (byte)99, (byte)47 },
            { (byte)191, (byte)119, (byte)47 },
            { (byte)207, (byte)143, (byte)43 },
            { (byte)223, (byte)171, (byte)39 },
            { (byte)239, (byte)203, (byte)31 },
            { (byte)255, (byte)243, (byte)27 },
            { (byte)11, (byte)7, (byte)0 },
            { (byte)27, (byte)19, (byte)0 },
            { (byte)43, (byte)35, (byte)15 },
            { (byte)55, (byte)43, (byte)19 },
            { (byte)71, (byte)51, (byte)27 },
            { (byte)83, (byte)55, (byte)35 },
            { (byte)99, (byte)63, (byte)43 },
            { (byte)111, (byte)71, (byte)51 },
            { (byte)127, (byte)83, (byte)63 },
            { (byte)139, (byte)95, (byte)71 },
            { (byte)155, (byte)107, (byte)83 },
            { (byte)167, (byte)123, (byte)95 },
            { (byte)183, (byte)135, (byte)107 },
            { (byte)195, (byte)147, (byte)123 },
            { (byte)211, (byte)163, (byte)139 },
            { (byte)227, (byte)179, (byte)151 },
            { (byte)171, (byte)139, (byte)163 },
            { (byte)159, (byte)127, (byte)151 },
            { (byte)147, (byte)115, (byte)135 },
            { (byte)139, (byte)103, (byte)123 },
            { (byte)127, (byte)91, (byte)111 },
            { (byte)119, (byte)83, (byte)99 },
            { (byte)107, (byte)75, (byte)87 },
            { (byte)95, (byte)63, (byte)75 },
            { (byte)87, (byte)55, (byte)67 },
            { (byte)75, (byte)47, (byte)55 },
            { (byte)67, (byte)39, (byte)47 },
            { (byte)55, (byte)31, (byte)35 },
            { (byte)43, (byte)23, (byte)27 },
            { (byte)35, (byte)19, (byte)19 },
            { (byte)23, (byte)11, (byte)11 },
            { (byte)15, (byte)7, (byte)7 },
            { (byte)187, (byte)115, (byte)159 },
            { (byte)175, (byte)107, (byte)143 },
            { (byte)163, (byte)95, (byte)131 },
            { (byte)151, (byte)87, (byte)119 },
            { (byte)139, (byte)79, (byte)107 },
            { (byte)127, (byte)75, (byte)95 },
            { (byte)115, (byte)67, (byte)83 },
            { (byte)107, (byte)59, (byte)75 },
            { (byte)95, (byte)51, (byte)63 },
            { (byte)83, (byte)43, (byte)55 },
            { (byte)71, (byte)35, (byte)43 },
            { (byte)59, (byte)31, (byte)35 },
            { (byte)47, (byte)23, (byte)27 },
            { (byte)35, (byte)19, (byte)19 },
            { (byte)23, (byte)11, (byte)11 },
            { (byte)15, (byte)7, (byte)7 },
            { (byte)219, (byte)195, (byte)187 },
            { (byte)203, (byte)179, (byte)167 },
            { (byte)191, (byte)163, (byte)155 },
            { (byte)175, (byte)151, (byte)139 },
            { (byte)163, (byte)135, (byte)123 },
            { (byte)151, (byte)123, (byte)111 },
            { (byte)135, (byte)111, (byte)95 },
            { (byte)123, (byte)99, (byte)83 },
            { (byte)107, (byte)87, (byte)71 },
            { (byte)95, (byte)75, (byte)59 },
            { (byte)83, (byte)63, (byte)51 },
            { (byte)67, (byte)51, (byte)39 },
            { (byte)55, (byte)43, (byte)31 },
            { (byte)39, (byte)31, (byte)23 },
            { (byte)27, (byte)19, (byte)15 },
            { (byte)15, (byte)11, (byte)7 },
            { (byte)111, (byte)131, (byte)123 },
            { (byte)103, (byte)123, (byte)111 },
            { (byte)95, (byte)115, (byte)103 },
            { (byte)87, (byte)107, (byte)95 },
            { (byte)79, (byte)99, (byte)87 },
            { (byte)71, (byte)91, (byte)79 },
            { (byte)63, (byte)83, (byte)71 },
            { (byte)55, (byte)75, (byte)63 },
            { (byte)47, (byte)67, (byte)55 },
            { (byte)43, (byte)59, (byte)47 },
            { (byte)35, (byte)51, (byte)39 },
            { (byte)31, (byte)43, (byte)31 },
            { (byte)23, (byte)35, (byte)23 },
            { (byte)15, (byte)27, (byte)19 },
            { (byte)11, (byte)19, (byte)11 },
            { (byte)7, (byte)11, (byte)7 },
            { (byte)255, (byte)243, (byte)27 },
            { (byte)239, (byte)223, (byte)23 },
            { (byte)219, (byte)203, (byte)19 },
            { (byte)203, (byte)183, (byte)15 },
            { (byte)187, (byte)167, (byte)15 },
            { (byte)171, (byte)151, (byte)11 },
            { (byte)155, (byte)131, (byte)7 },
            { (byte)139, (byte)115, (byte)7 },
            { (byte)123, (byte)99, (byte)7 },
            { (byte)107, (byte)83, (byte)0 },
            { (byte)91, (byte)71, (byte)0 },
            { (byte)75, (byte)55, (byte)0 },
            { (byte)59, (byte)43, (byte)0 },
            { (byte)43, (byte)31, (byte)0 },
            { (byte)27, (byte)15, (byte)0 },
            { (byte)11, (byte)7, (byte)0 },
            { (byte)0, (byte)0, (byte)255 },
            { (byte)11, (byte)11, (byte)239 },
            { (byte)19, (byte)19, (byte)223 },
            { (byte)27, (byte)27, (byte)207 },
            { (byte)35, (byte)35, (byte)191 },
            { (byte)43, (byte)43, (byte)175 },
            { (byte)47, (byte)47, (byte)159 },
            { (byte)47, (byte)47, (byte)143 },
            { (byte)47, (byte)47, (byte)127 },
            { (byte)47, (byte)47, (byte)111 },
            { (byte)47, (byte)47, (byte)95 },
            { (byte)43, (byte)43, (byte)79 },
            { (byte)35, (byte)35, (byte)63 },
            { (byte)27, (byte)27, (byte)47 },
            { (byte)19, (byte)19, (byte)31 },
            { (byte)11, (byte)11, (byte)15 },
            { (byte)43, (byte)0, (byte)0 },
            { (byte)59, (byte)0, (byte)0 },
            { (byte)75, (byte)7, (byte)0 },
            { (byte)95, (byte)7, (byte)0 },
            { (byte)111, (byte)15, (byte)0 },
            { (byte)127, (byte)23, (byte)7 },
            { (byte)147, (byte)31, (byte)7 },
            { (byte)163, (byte)39, (byte)11 },
            { (byte)183, (byte)51, (byte)15 },
            { (byte)195, (byte)75, (byte)27 },
            { (byte)207, (byte)99, (byte)43 },
            { (byte)219, (byte)127, (byte)59 },
            { (byte)227, (byte)151, (byte)79 },
            { (byte)231, (byte)171, (byte)95 },
            { (byte)239, (byte)191, (byte)119 },
            { (byte)247, (byte)211, (byte)139 },
            { (byte)167, (byte)123, (byte)59 },
            { (byte)183, (byte)155, (byte)55 },
            { (byte)199, (byte)195, (byte)55 },
            { (byte)231, (byte)227, (byte)87 },
            { (byte)0, (byte)255, (byte)0 },
            { (byte)171, (byte)231, (byte)255 },
            { (byte)215, (byte)255, (byte)255 },
            { (byte)71, (byte)68, (byte)24 },
            { (byte)97, (byte)94, (byte)39 },
            { (byte)124, (byte)119, (byte)55 },
            { (byte)150, (byte)145, (byte)70 },
            { (byte)176, (byte)170, (byte)85 },
            { (byte)255, (byte)243, (byte)147 },
            { (byte)255, (byte)247, (byte)199 },
            { (byte)255, (byte)255, (byte)255 },
            { (byte)159, (byte)91, (byte)83 },
        };
        
        return( palette );
    }
    
    public static final byte[][] palette = allocatePalette();
    
    public static void readPalette( InputStream in ) throws IOException
    {
        System.out.println( "byte[][] palette = new byte[][]" );
        System.out.println( "{" );
        
        for ( int i = 0; i < 768; i += 3 )
        {
            int r = in.read();
            int g = in.read();
            int b = in.read();
            
            System.out.println( "    { (byte)" + r + ", (byte)" + g + ", (byte)" + b + " }," );
        }
        
        System.out.println( "};" );
        
        in.close();
    }
}

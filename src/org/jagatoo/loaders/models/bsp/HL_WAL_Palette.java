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
            { (byte)111, (byte)121, (byte)124 },
            { (byte)113, (byte)123, (byte)124 },
            { (byte)90 , (byte)99 , (byte)102 },
            { (byte)123, (byte)129, (byte)131 },
            { (byte)59 , (byte)68 , (byte)69  },
            { (byte)56 , (byte)68 , (byte)67  },
            { (byte)39 , (byte)45 , (byte)47  },
            { (byte)99 , (byte)108, (byte)113 },
            { (byte)50 , (byte)54 , (byte)58  },
            { (byte)115, (byte)122, (byte)125 },
            { (byte)56 , (byte)64 , (byte)67  },
            { (byte)146, (byte)154, (byte)156 },
            { (byte)121, (byte)127, (byte)133 },
            { (byte)115, (byte)125, (byte)124 },
            { (byte)110, (byte)121, (byte)127 },
            { (byte)102, (byte)112, (byte)116 },
            { (byte)128, (byte)136, (byte)139 },
            { (byte)83 , (byte)95 , (byte)99  },
            { (byte)122, (byte)131, (byte)137 },
            { (byte)110, (byte)125, (byte)130 },
            { (byte)112, (byte)119, (byte)126 },
            { (byte)57 , (byte)67 , (byte)69  },
            { (byte)118, (byte)127, (byte)130 },
            { (byte)113, (byte)124, (byte)122 },
            { (byte)114, (byte)129, (byte)132 },
            { (byte)121, (byte)131, (byte)135 },
            { (byte)119, (byte)125, (byte)128 },
            { (byte)55 , (byte)63 , (byte)65  },
            { (byte)111, (byte)121, (byte)128 },
            { (byte)54 , (byte)60 , (byte)63  },
            { (byte)121, (byte)130, (byte)133 },
            { (byte)123, (byte)130, (byte)133 },
            { (byte)47 , (byte)54 , (byte)58  },
            { (byte)127, (byte)137, (byte)141 },
            { (byte)122, (byte)130, (byte)133 },
            { (byte)46 , (byte)53 , (byte)55  },
            { (byte)77 , (byte)85 , (byte)90  },
            { (byte)110, (byte)119, (byte)120 },
            { (byte)84 , (byte)95 , (byte)99  },
            { (byte)117, (byte)127, (byte)130 },
            { (byte)115, (byte)124, (byte)128 },
            { (byte)105, (byte)115, (byte)116 },
            { (byte)102, (byte)115, (byte)121 },
            { (byte)116, (byte)122, (byte)125 },
            { (byte)85 , (byte)97 , (byte)99  },
            { (byte)114, (byte)124, (byte)129 },
            { (byte)124, (byte)134, (byte)134 },
            { (byte)80 , (byte)87 , (byte)94  },
            { (byte)121, (byte)131, (byte)132 },
            { (byte)95 , (byte)102, (byte)109 },
            { (byte)90 , (byte)97 , (byte)105 },
            { (byte)115, (byte)124, (byte)127 },
            { (byte)102, (byte)109, (byte)115 },
            { (byte)97 , (byte)106, (byte)109 },
            { (byte)138, (byte)146, (byte)148 },
            { (byte)107, (byte)118, (byte)118 },
            { (byte)64 , (byte)72 , (byte)76  },
            { (byte)118, (byte)127, (byte)130 },
            { (byte)119, (byte)129, (byte)132 },
            { (byte)99 , (byte)105, (byte)108 },
            { (byte)122, (byte)130, (byte)135 },
            { (byte)118, (byte)125, (byte)129 },
            { (byte)106, (byte)112, (byte)115 },
            { (byte)104, (byte)112, (byte)116 },
            { (byte)100, (byte)114, (byte)120 },
            { (byte)112, (byte)126, (byte)127 },
            { (byte)111, (byte)119, (byte)120 },
            { (byte)78 , (byte)89 , (byte)93  },
            { (byte)117, (byte)128, (byte)127 },
            { (byte)112, (byte)121, (byte)124 },
            { (byte)108, (byte)113, (byte)119 },
            { (byte)101, (byte)108, (byte)111 },
            { (byte)110, (byte)123, (byte)127 },
            { (byte)94 , (byte)103, (byte)108 },
            { (byte)98 , (byte)105, (byte)111 },
            { (byte)92 , (byte)99 , (byte)102 },
            { (byte)84 , (byte)96 , (byte)98  },
            { (byte)123, (byte)135, (byte)133 },
            { (byte)121, (byte)131, (byte)133 },
            { (byte)93 , (byte)105, (byte)109 },
            { (byte)119, (byte)129, (byte)131 },
            { (byte)125, (byte)133, (byte)136 },
            { (byte)55 , (byte)65 , (byte)67  },
            { (byte)123, (byte)133, (byte)135 },
            { (byte)94 , (byte)102, (byte)105 },
            { (byte)117, (byte)127, (byte)126 },
            { (byte)109, (byte)114, (byte)121 },
            { (byte)106, (byte)113, (byte)116 },
            { (byte)103, (byte)110, (byte)114 },
            { (byte)99 , (byte)107, (byte)108 },
            { (byte)118, (byte)125, (byte)130 },
            { (byte)116, (byte)124, (byte)125 },
            { (byte)130, (byte)138, (byte)141 },
            { (byte)121, (byte)130, (byte)132 },
            { (byte)108, (byte)118, (byte)121 },
            { (byte)104, (byte)114, (byte)117 },
            { (byte)79 , (byte)91 , (byte)94  },
            { (byte)113, (byte)123, (byte)122 },
            { (byte)69 , (byte)78 , (byte)81  },
            { (byte)113, (byte)122, (byte)125 },
            { (byte)109, (byte)121, (byte)122 },
            { (byte)127, (byte)135, (byte)138 },
            { (byte)109, (byte)118, (byte)122 },
            { (byte)129, (byte)137, (byte)139 },
            { (byte)126, (byte)134, (byte)137 },
            { (byte)93 , (byte)99 , (byte)102 },
            { (byte)77 , (byte)88 , (byte)93  },
            { (byte)112, (byte)118, (byte)125 },
            { (byte)60 , (byte)68 , (byte)71  },
            { (byte)68 , (byte)78 , (byte)81  },
            { (byte)100, (byte)112, (byte)117 },
            { (byte)120, (byte)130, (byte)132 },
            { (byte)121, (byte)128, (byte)130 },
            { (byte)118, (byte)128, (byte)128 },
            { (byte)93 , (byte)100, (byte)105 },
            { (byte)101, (byte)112, (byte)118 },
            { (byte)119, (byte)127, (byte)129 },
            { (byte)112, (byte)125, (byte)127 },
            { (byte)120, (byte)129, (byte)132 },
            { (byte)114, (byte)124, (byte)125 },
            { (byte)76 , (byte)86 , (byte)88  },
            { (byte)118, (byte)127, (byte)128 },
            { (byte)94 , (byte)103, (byte)107 },
            { (byte)99 , (byte)107, (byte)109 },
            { (byte)117, (byte)129, (byte)128 },
            { (byte)113, (byte)125, (byte)125 },
            { (byte)123, (byte)136, (byte)134 },
            { (byte)106, (byte)121, (byte)126 },
            { (byte)101, (byte)112, (byte)114 },
            { (byte)113, (byte)117, (byte)122 },
            { (byte)112, (byte)118, (byte)125 },
            { (byte)107, (byte)115, (byte)120 },
            { (byte)112, (byte)117, (byte)118 },
            { (byte)116, (byte)124, (byte)126 },
            { (byte)108, (byte)119, (byte)121 },
            { (byte)117, (byte)131, (byte)133 },
            { (byte)114, (byte)126, (byte)130 },
            { (byte)116, (byte)124, (byte)128 },
            { (byte)80 , (byte)89 , (byte)92  },
            { (byte)113, (byte)122, (byte)122 },
            { (byte)123, (byte)135, (byte)133 },
            { (byte)92 , (byte)102, (byte)104 },
            { (byte)111, (byte)120, (byte)121 },
            { (byte)115, (byte)126, (byte)123 },
            { (byte)115, (byte)121, (byte)126 },
            { (byte)104, (byte)111, (byte)114 },
            { (byte)110, (byte)122, (byte)125 },
            { (byte)111, (byte)116, (byte)120 },
            { (byte)120, (byte)128, (byte)132 },
            { (byte)74 , (byte)85 , (byte)88  },
            { (byte)115, (byte)118, (byte)124 },
            { (byte)47 , (byte)52 , (byte)55  },
            { (byte)115, (byte)125, (byte)123 },
            { (byte)102, (byte)111, (byte)114 },
            { (byte)116, (byte)121, (byte)123 },
            { (byte)99 , (byte)111, (byte)116 },
            { (byte)42 , (byte)48 , (byte)51  },
            { (byte)121, (byte)129, (byte)131 },
            { (byte)115, (byte)124, (byte)128 },
            { (byte)126, (byte)134, (byte)137 },
            { (byte)77 , (byte)87 , (byte)90  },
            { (byte)121, (byte)130, (byte)131 },
            { (byte)124, (byte)135, (byte)134 },
            { (byte)81 , (byte)90 , (byte)94  },
            { (byte)108, (byte)117, (byte)121 },
            { (byte)101, (byte)113, (byte)116 },
            { (byte)114, (byte)123, (byte)123 },
            { (byte)100, (byte)107, (byte)112 },
            { (byte)112, (byte)124, (byte)127 },
            { (byte)54 , (byte)60 , (byte)62  },
            { (byte)86 , (byte)95 , (byte)98  },
            { (byte)98 , (byte)109, (byte)112 },
            { (byte)101, (byte)112, (byte)114 },
            { (byte)118, (byte)125, (byte)128 },
            { (byte)73 , (byte)83 , (byte)89  },
            { (byte)107, (byte)113, (byte)118 },
            { (byte)73 , (byte)82 , (byte)85  },
            { (byte)88 , (byte)97 , (byte)101 },
            { (byte)107, (byte)116, (byte)118 },
            { (byte)104, (byte)114, (byte)117 },
            { (byte)119, (byte)130, (byte)133 },
            { (byte)118, (byte)127, (byte)127 },
            { (byte)100, (byte)108, (byte)110 },
            { (byte)117, (byte)125, (byte)127 },
            { (byte)121, (byte)128, (byte)133 },
            { (byte)113, (byte)121, (byte)124 },
            { (byte)84 , (byte)96 , (byte)98  },
            { (byte)102, (byte)114, (byte)120 },
            { (byte)117, (byte)124, (byte)132 },
            { (byte)102, (byte)109, (byte)111 },
            { (byte)119, (byte)127, (byte)134 },
            { (byte)67 , (byte)78 , (byte)79  },
            { (byte)114, (byte)127, (byte)127 },
            { (byte)87 , (byte)98 , (byte)103 },
            { (byte)122, (byte)130, (byte)131 },
            { (byte)89 , (byte)96 , (byte)99  },
            { (byte)86 , (byte)97 , (byte)100 },
            { (byte)115, (byte)126, (byte)127 },
            { (byte)107, (byte)115, (byte)117 },
            { (byte)83 , (byte)89 , (byte)94  },
            { (byte)116, (byte)127, (byte)132 },
            { (byte)127, (byte)133, (byte)135 },
            { (byte)76 , (byte)84 , (byte)88  },
            { (byte)113, (byte)121, (byte)124 },
            { (byte)71 , (byte)81 , (byte)84  },
            { (byte)94 , (byte)101, (byte)105 },
            { (byte)102, (byte)112, (byte)115 },
            { (byte)130, (byte)135, (byte)138 },
            { (byte)73 , (byte)82 , (byte)84  },
            { (byte)76 , (byte)87 , (byte)91  },
            { (byte)57 , (byte)65 , (byte)69  },
            { (byte)124, (byte)132, (byte)137 },
            { (byte)72 , (byte)76 , (byte)81  },
            { (byte)107, (byte)117, (byte)121 },
            { (byte)105, (byte)113, (byte)116 },
            { (byte)103, (byte)115, (byte)118 },
            { (byte)118, (byte)126, (byte)132 },
            { (byte)100, (byte)103, (byte)110 },
            { (byte)124, (byte)130, (byte)133 },
            { (byte)109, (byte)118, (byte)122 },
            { (byte)66 , (byte)74 , (byte)79  },
            { (byte)131, (byte)138, (byte)140 },
            { (byte)122, (byte)130, (byte)133 },
            { (byte)111, (byte)123, (byte)124 },
            { (byte)100, (byte)107, (byte)109 },
            { (byte)96 , (byte)104, (byte)111 },
            { (byte)105, (byte)115, (byte)119 },
            { (byte)88 , (byte)99 , (byte)103 },
            { (byte)121, (byte)126, (byte)130 },
            { (byte)120, (byte)127, (byte)131 },
            { (byte)118, (byte)128, (byte)133 },
            { (byte)113, (byte)123, (byte)125 },
            { (byte)108, (byte)117, (byte)122 },
            { (byte)45 , (byte)53 , (byte)56  },
            { (byte)115, (byte)125, (byte)130 },
            { (byte)92 , (byte)101, (byte)104 },
            { (byte)114, (byte)121, (byte)124 },
            { (byte)61 , (byte)70 , (byte)73  },
            { (byte)117, (byte)124, (byte)132 },
            { (byte)96 , (byte)106, (byte)108 },
            { (byte)120, (byte)126, (byte)132 },
            { (byte)116, (byte)122, (byte)125 },
            { (byte)128, (byte)135, (byte)138 },
            { (byte)80 , (byte)88 , (byte)93  },
            { (byte)81 , (byte)91 , (byte)94  },
            { (byte)102, (byte)109, (byte)112 },
            { (byte)81 , (byte)94 , (byte)97  },
            { (byte)116, (byte)125, (byte)130 },
            { (byte)67 , (byte)77 , (byte)83  },
            { (byte)87 , (byte)95 , (byte)98  },
            { (byte)122, (byte)127, (byte)133 },
            { (byte)102, (byte)110, (byte)113 },
            { (byte)99 , (byte)109, (byte)111 },
            { (byte)119, (byte)128, (byte)134 },
            { (byte)61 , (byte)70 , (byte)75  },
            { (byte)114, (byte)121, (byte)125 },
        };
        
        return( palette );
    }
    
    public static final byte[][] palette = allocatePalette();
    
    private static final String bs( byte b )
    {
        int i = b & 0xFF;
        
        if ( i < 10 )
        {
            return( "(byte)" + i + "  " );
        }
        else if ( i < 100 )
        {
            return( "(byte)" + i + " " );
        }
        else
        {
            return( "(byte)" + i );
        }
    }
    
    public static void readPalette( InputStream in ) throws IOException
    {
        System.out.println( "byte[][] palette = new byte[][]" );
        System.out.println( "{" );
        
        for ( int i = 0; i < 768; i += 3 )
        {
            byte r = (byte)in.read();
            byte g = (byte)in.read();
            byte b = (byte)in.read();
            
            System.out.println( "    { " + bs( r ) + ", " + bs( g ) + ", " + bs( b ) + " }," );
        }
        
        System.out.println( "};" );
        
        in.close();
    }
    
    public static void dump()
    {
        System.out.println( "byte[][] palette = new byte[][]" );
        System.out.println( "{" );
        
        for ( int i = 0; i < 256; i++ )
        {
            System.out.println( "    { " + bs( palette[i][0] ) + ", " + bs( palette[i][1] ) + ", " + bs( palette[i][2] ) + " }," );
        }
        
        System.out.println( "};" );
    }
}

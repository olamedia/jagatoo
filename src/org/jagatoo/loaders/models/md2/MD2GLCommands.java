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
package org.jagatoo.loaders.models.md2;

import java.io.IOException;

import org.jagatoo.util.streams.LittleEndianDataInputStream;

/**
 * The set of GL commands in this MD2 File
 * 
 * @author Kevin Glass
 */
public class MD2GLCommands
{
    /** The STRIP type */
    public static final int STRIP = 1;
    /** The FAN type */
    public static final int FAN = 2;
    
    /** The command indicator */
    private int indicator;
    /** The type of this command */
    private int type;
    /** The commands */
    private MD2GLCommand[] commands;
    
    /**
     * Retrieves the type of this command.
     * 
     * @return The type of this command
     */
    public int getType()
    {
        return( type );
    }
    
    /**
     * Retrieves a command at a specified index.
     */
    public MD2GLCommand getCommand( int i )
    {
        return( commands[ i ] );
    }
    
    /**
     * Retrieves the count of the commands in this set.
     */
    public final int getCount()
    {
        return( indicator );
    }
    
    /** 
     * Creates new MD2GlCommands 
     *
     * @param in The input stream to read this list 
     * @param indicator The indicator
     */
    public MD2GLCommands( LittleEndianDataInputStream in, int indicator ) throws IOException
    {
        this.indicator = Math.abs( indicator );
        if ( indicator < 0 )
        {
            this.type = FAN;
        }
        else
        {
            this.type = STRIP;
        }
        this.commands = new MD2GLCommand[ getCount() ];
        
        for ( int i = 0; i < getCount(); i++ ) 
            commands[ i ] = new MD2GLCommand( in );
    }
}

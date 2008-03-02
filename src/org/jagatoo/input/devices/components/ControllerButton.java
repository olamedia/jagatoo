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
package org.jagatoo.input.devices.components;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ControllerButton extends DigitalDeviceComponent
{
    private final int index;
    private final String name;
    
    private DigiState state = DigiState.UP;
    
    /**
     * @return the Button's index.
     */
    public final int getIndex()
    {
        return( index );
    }
    
    /**
     * @return the button's name.
     */
    public final String getName()
    {
        return( name );
    }
    
    public void setState( DigiState state )
    {
        this.state = state;
    }
    
    public final void setState( boolean state )
    {
        if ( state )
            setState( DigiState.DOWN );
        else
            setState( DigiState.UP );
    }
    
    public final DigiState getState()
    {
        return( state );
    }
    
    public final boolean getBooleanState()
    {
        return( state.getBooleanValue() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( this.getClass().getSimpleName() + " { name = \"" + getName() + "\", index = " + getIndex() + ", state = " + getState() + " }" );
    }
    
    public ControllerButton( int index, String name )
    {
        super( Type.CONTROLLER_BUTTON );
        
        this.index = index;
        this.name = name;
    }
}

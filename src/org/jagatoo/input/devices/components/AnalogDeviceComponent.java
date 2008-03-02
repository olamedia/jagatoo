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
public class AnalogDeviceComponent extends DeviceComponent
{
    private int intValue = 0;
    private float floatValue = 0f;
    private float scale = 1000.0f;
    
    public void setValue( int intValue, float floatValue )
    {
        this.intValue = intValue;
        this.floatValue = floatValue;
    }
    
    public final void setValue( int value )
    {
        setValue( value, (float)intValue / scale );
    }
    
    public final void setValue( float value )
    {
        setValue( (int)( floatValue * scale ), value );
    }
    
    public final void addValue( int deltaValue )
    {
        setValue( intValue + deltaValue );
    }
    
    public final void addValue( float deltaValue )
    {
        setValue( floatValue + deltaValue );
    }
    
    public final int getIntValue()
    {
        return( intValue );
    }
    
    public final float getFloatValue()
    {
        return( floatValue );
    }
    
    public void setScale( float scale )
    {
        this.scale = scale;
    }
    
    public final float getScale()
    {
        return( scale );
    }
    
    protected AnalogDeviceComponent( Type type )
    {
        super( type );
    }
}

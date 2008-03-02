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
 * This class provides public static access to all Mouse buttons.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseButtons
{
    static final MouseButton[] buttonsMap = new MouseButton[ 12 ];
    
    public static final MouseButton LEFT_BUTTON = new MouseButton( "Left Button" );
    public static final MouseButton MIDDLE_BUTTON = new MouseButton( "Middle Button" );
    public static final MouseButton RIGHT_BUTTON = new MouseButton( "Right Button" );
    
    public static final MouseButton EXT_BUTTON_1 = new MouseButton( "Extension Button 1" );
    public static final MouseButton EXT_BUTTON_2 = new MouseButton( "Extension Button 2" );
    public static final MouseButton EXT_BUTTON_3 = new MouseButton( "Extension Button 3" );
    public static final MouseButton EXT_BUTTON_4 = new MouseButton( "Extension Button 4" );
    public static final MouseButton EXT_BUTTON_5 = new MouseButton( "Extension Button 5" );
    public static final MouseButton EXT_BUTTON_6 = new MouseButton( "Extension Button 6" );
    public static final MouseButton EXT_BUTTON_7 = new MouseButton( "Extension Button 7" );
    public static final MouseButton EXT_BUTTON_8 = new MouseButton( "Extension Button 8" );
    public static final MouseButton EXT_BUTTON_9 = new MouseButton( "Extension Button 9" );
    
    public static final MouseButton getByIndex( int index )
    {
        return( buttonsMap[ index ] );
    }
}

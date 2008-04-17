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
package org.jagatoo.input.events;

import org.jagatoo.input.InputSystem;

/**
 * This is a simple synchronized event queue for JAGaTOO input events.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class EventQueue
{
    public static final Object LOCK = new Object();
    
    private InputEvent[] events = new InputEvent[ 128 ];
    private int numEvents = 0;
    
    public final int getNumEvents()
    {
        return( numEvents );
    }
    
    public final void enqueue( InputEvent event )
    {
        synchronized ( LOCK )
        {
            if ( numEvents == events.length - 1 )
            {
                InputEvent[] tmp = new InputEvent[ (int)( numEvents * 1.5 ) + 1 ];
                System.arraycopy( events, 0, tmp, 0, numEvents );
                events = tmp;
            }
            
            events[ numEvents++ ] = event;
        }
    }
    
    public final void dequeueAndFire( InputSystem inputSystem )
    {
        if ( numEvents > 0 )
        {
            synchronized ( LOCK )
            {
                for ( int i = 0; i < numEvents; i++ )
                {
                    final InputEvent event = events[ i ];
                    
                    switch ( event.getType() )
                    {
                        case KEYBOARD_EVENT:
                            final KeyboardEvent kbEvent = (KeyboardEvent)event;
                            kbEvent.getKeyboard().fireKeyboardEvent( kbEvent, true );
                            break;
                            
                        case MOUSE_EVENT:
                            final MouseEvent moEvent = (MouseEvent)event;
                            moEvent.getMouse().fireMouseEvent( moEvent, true );
                            break;
                            
                        case CONTROLLER_EVENT:
                            final ControllerEvent ctEvent = (ControllerEvent)event;
                            ctEvent.getController().fireControllerEvent( ctEvent, true );
                            break;
                    }
                }
                
                numEvents = 0;
            }
        }
    }
}
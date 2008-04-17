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

import java.util.ArrayList;

import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseWheel;

/**
 * A pool for MouseEvent instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class MouseEventPool
{
    private static ArrayList< MouseMovedEvent > instances_moved = new ArrayList< MouseMovedEvent >( 64 );
    private static ArrayList< MouseButtonPressedEvent > instances_pressed = new ArrayList< MouseButtonPressedEvent >( 64 );
    private static ArrayList< MouseButtonReleasedEvent > instances_released = new ArrayList< MouseButtonReleasedEvent >( 64 );
    private static ArrayList< MouseWheelEvent > instances_wheel = new ArrayList< MouseWheelEvent >( 64 );
    private static ArrayList< MouseStoppedEvent > instances_stopped = new ArrayList< MouseStoppedEvent >( 64 );
    private static int n_moved = 0;
    private static int n_pressed = 0;
    private static int n_released = 0;
    private static int n_stopped = 0;
    private static int n_wheel = 0;
    
    private static final Object LOCK_moved = new Object();
    private static final Object LOCK_pressed = new Object();
    private static final Object LOCK_released = new Object();
    private static final Object LOCK_stopped = new Object();
    private static final Object LOCK_wheel = new Object();
    
    public static MouseMovedEvent allocMoved()
    {
        synchronized ( LOCK_moved )
        {
            if ( n_moved > 0 )
            {
                MouseMovedEvent e = instances_moved.remove( --n_moved );
                
                return( e );
            }
            else
            {
                return( new MouseMovedEvent() );
            }
        }
    }
    
    public static MouseMovedEvent allocMoved( Mouse mouse, int x, int y, int dx, int dy, long when, long lastWhen )
    {
        final MouseMovedEvent e = allocMoved();
        
        e.set( mouse, x, y, dx, dy, when, lastWhen );
        
        return( e );
    }
    
    public static void freeMoved( MouseMovedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_moved )
        {
            instances_moved.add( e );
            n_moved++;
        }
    }
    
    public static MouseButtonPressedEvent allocPressed()
    {
        synchronized ( LOCK_pressed )
        {
            if ( n_pressed > 0 )
            {
                MouseButtonPressedEvent e = instances_pressed.remove( --n_pressed );
                
                return( e );
            }
            else
            {
                return( new MouseButtonPressedEvent() );
            }
        }
    }
    
    public static MouseButtonPressedEvent allocPressed( Mouse mouse, MouseButton button, long when, long lastWhen )
    {
        final MouseButtonPressedEvent e = allocPressed();
        
        e.set( mouse, button, when, lastWhen );
        
        return( e );
    }
    
    public static void freePressed( MouseButtonPressedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_pressed )
        {
            instances_pressed.add( e );
            n_pressed++;
        }
    }
    
    public static MouseButtonReleasedEvent allocReleased()
    {
        synchronized ( LOCK_released )
        {
            if ( n_released > 0 )
            {
                MouseButtonReleasedEvent e = instances_released.remove( --n_released );
                
                return( e );
            }
            else
            {
                return( new MouseButtonReleasedEvent() );
            }
        }
    }
    
    public static MouseButtonReleasedEvent allocReleased( Mouse mouse, MouseButton button, long when, long lastWhen )
    {
        final MouseButtonReleasedEvent e = allocReleased();
        
        e.set( mouse, button, when, lastWhen );
        
        return( e );
    }
    
    public static void freeReleased( MouseButtonReleasedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_released )
        {
            instances_released.add( e );
            n_released++;
        }
    }
    
    public static MouseWheelEvent allocWheel()
    {
        synchronized ( LOCK_wheel )
        {
            if ( n_wheel > 0 )
            {
                MouseWheelEvent e = instances_wheel.remove( --n_wheel );
                
                return( e );
            }
            else
            {
                return( new MouseWheelEvent() );
            }
        }
    }
    
    public static MouseWheelEvent allocWheel( Mouse mouse, MouseWheel wheel, int wheelDelta, boolean isPageMove, long when, long lastWhen )
    {
        final MouseWheelEvent e = allocWheel();
        
        e.set( mouse, wheel, wheelDelta, isPageMove, when, lastWhen );
        
        return( e );
    }
    
    public static void freeWheel( MouseWheelEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_wheel )
        {
            instances_wheel.add( e );
            n_wheel++;
        }
    }
    
    public static MouseStoppedEvent allocStopped()
    {
        synchronized ( LOCK_stopped )
        {
            if ( n_stopped > 0 )
            {
                MouseStoppedEvent e = instances_stopped.remove( --n_stopped );
                
                return( e );
            }
            else
            {
                return( new MouseStoppedEvent() );
            }
        }
    }
    
    public static MouseStoppedEvent allocStopped( Mouse mouse, int x, int y, long when, long lastWhen )
    {
        final MouseStoppedEvent e = allocStopped();
        
        e.set( mouse, x, y, when, lastWhen );
        
        return( e );
    }
    
    public static void freeStopped( MouseStoppedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_stopped )
        {
            instances_stopped.add( e );
            n_stopped++;
        }
    }
    
    private MouseEventPool()
    {
    }
}
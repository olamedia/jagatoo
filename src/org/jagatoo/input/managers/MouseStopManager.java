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
package org.jagatoo.input.managers;

import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Mouse;

/**
 * Looks for the mouse beeing stopped for a specified amount of milliseconds.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseStopManager implements Runnable
{
    private final Mouse mouse;
    
    private long dt = 500L * 100000L;
    
    private long lastKnownGameTime = -1L;
    private long lastMovedTime = Long.MAX_VALUE - dt * 100L;
    
    private boolean isSearching = false;
    private boolean isThreadRunning = false;
    
    /**
     * Sets the amount of nanoseconds after the last mouse moving
     * for the onMouseStopped method to be called.
     */
    public void setDelay( long delay )
    {
        this.dt = delay;
    }
    
    /**
     * Returns the amount of nanoseconds after the last mouse moving
     * for the onMouseStopped method to be called.
     */
    public final long getDelay()
    {
        return( dt );
    }
    
    public final boolean isSearching()
    {
        return( isSearching );
    }
    
    /**
     * {@inheritDoc}
     */
    public void run()
    {
        isThreadRunning = true;
        
        long t;
        
        isSearching = true;
        
        while ( isSearching )
        {
            t = System.nanoTime();
            
            if ( t >= lastMovedTime + dt )
            {
                lastMovedTime = Long.MAX_VALUE - dt * 100L;
                mouse.fireOnMouseStopped( mouse.prepareMouseStoppedEvent( lastKnownGameTime ), true );
            }
            
            try { Thread.sleep( 10L ); } catch ( InterruptedException e ) { /*e.printStackTrace();*/ }
        }
        
        isThreadRunning = false;
    }
    
    /**
     * {@inheritDoc}
     */
    public void start() throws InputSystemException
    {
        if ( isThreadRunning )
            throw( new InputSystemException( "The MouseStopManager is already running!" ) );
        
        lastMovedTime = Long.MAX_VALUE - dt * 100L;
        
        new Thread( this ).start();
    }
    
    public void stopMe() throws InputSystemException
    {
        if ( !isThreadRunning )
            throw( new InputSystemException( "The MouseStopManager is not running!" ) );
        
        this.isSearching = false;
    }
    
    public final void notifyMouseMoved( long nanoTime )
    {
        this.lastMovedTime = System.nanoTime();
        this.lastKnownGameTime = nanoTime;
    }
    
    public MouseStopManager( Mouse mouse )
    {
        this.mouse = mouse;
    }
}

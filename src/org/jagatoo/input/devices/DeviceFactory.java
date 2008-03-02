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
package org.jagatoo.input.devices;

import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.misc.Canvas;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class DeviceFactory
{
    private final EventQueue eveneQueue;
    private final Canvas canvas;
    
    public final Canvas getCanvas()
    {
        return( canvas );
    }
    
    private Keyboard[] cachedKeyboards = null;
    private Mouse[] cachedMouses = null;
    private Controller[] cachedControllers = null;
    
    protected EventQueue getEveneQueue()
    {
        return( eveneQueue );
    }
    
    private synchronized final void setCachedKeyboards( Keyboard[] keyboards )
    {
        this.cachedKeyboards = keyboards;
    }
    
    private synchronized final Keyboard[] getCachedKeyboards()
    {
        return( cachedKeyboards );
    }
    
    private synchronized final void setCachedMouses( Mouse[] mouses )
    {
        this.cachedMouses = mouses;
    }
    
    private synchronized final Mouse[] getCachedMouses()
    {
        return( cachedMouses );
    }
    
    private synchronized final void setCachedControllers( Controller[] controllers )
    {
        this.cachedControllers = controllers;
    }
    
    private synchronized final Controller[] getCachedControllers()
    {
        return( cachedControllers );
    }
    
    protected synchronized final void flushCache( boolean keyboards, boolean mouses, boolean controllers )
    {
        if ( keyboards )
            this.cachedKeyboards = null;
        
        if ( mouses )
            this.cachedMouses = null;
        
        if ( controllers )
            this.cachedControllers = null;
    }
    
    public abstract Class< ? extends Keyboard > getExpectedKeyboardClass();
    
    /**
     * @return an array of all the installed Keyboards in the system.
     */
    protected abstract Keyboard[] initKeyboards() throws InputSystemException;
    
    /**
     * @return an array of all the installed Keyboards in the system.
     */
    public Keyboard[] getKeyboards() throws InputSystemException
    {
        if ( getCachedKeyboards() == null )
        {
            setCachedKeyboards(  initKeyboards() );
        }
        
        return( getCachedKeyboards() );
    }
    
    public abstract Class< ? extends Mouse > getExpectedMouseClass();
    
    /**
     * @return an array of all the installed Mouses in the system.
     */
    protected abstract Mouse[] initMouses() throws InputSystemException;
    
    /**
     * @return an array of all the installed Mouses in the system.
     */
    public Mouse[] getMouses() throws InputSystemException
    {
        if ( getCachedMouses() == null )
        {
            setCachedMouses( initMouses() );
        }
        
        return( getCachedMouses() );
    }
    
    public abstract Class< ? extends Controller > getExpectedControllerClass();
    
    /**
     * @return an array of all the installed Controllers in the system.
     */
    protected abstract Controller[] initControllers() throws InputSystemException;
    
    /**
     * @return an array of all the installed Controllers in the system.
     */
    public Controller[] getControllers() throws InputSystemException
    {
        if ( getCachedControllers() == null )
        {
            setCachedControllers( initControllers() );
        }
        
        return( getCachedControllers() );
    }
    
    public DeviceFactory( Canvas canvas, EventQueue eveneQueue )
    {
        this.canvas = canvas;
        this.eveneQueue = eveneQueue;
    }
}

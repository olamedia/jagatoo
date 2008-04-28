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

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * A DeviceFactory is a simple factory to access all input devices
 * available on the system.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class InputDeviceFactory implements KeyboardFactory, MouseFactory, ControllerFactory
{
    private final EventQueue eventQueue;
    private final InputSourceWindow sourceWindow;
    
    public final InputSourceWindow getSourceWindow()
    {
        return( sourceWindow );
    }
    
    private static Keyboard[] staticCachedKeyboards = null;
    private static Mouse[] staticCachedMouses = null;
    private static Controller[] staticCachedControllers = null;
    
    private Keyboard[] cachedKeyboards = null;
    private Mouse[] cachedMouses = null;
    private Controller[] cachedControllers = null;
    
    private final boolean useStaticArrays;
    
    protected EventQueue getEveneQueue()
    {
        return( eventQueue );
    }
    
    private synchronized final void setCachedKeyboards( Keyboard[] keyboards )
    {
        if ( useStaticArrays )
            staticCachedKeyboards = keyboards;
        else
            this.cachedKeyboards = keyboards;
    }
    
    protected synchronized final Keyboard[] getCachedKeyboards()
    {
        if ( useStaticArrays )
            return( staticCachedKeyboards );
        else
            return( cachedKeyboards );
    }
    
    private synchronized final void setCachedMouses( Mouse[] mouses )
    {
        if ( useStaticArrays )
            staticCachedMouses = mouses;
        else
            this.cachedMouses = mouses;
    }
    
    protected synchronized final Mouse[] getCachedMouses()
    {
        if ( useStaticArrays )
            return( staticCachedMouses );
        else
            return( cachedMouses );
    }
    
    private synchronized final void setCachedControllers( Controller[] controllers )
    {
        if ( useStaticArrays )
            staticCachedControllers = controllers;
        else
            this.cachedControllers = controllers;
    }
    
    protected synchronized final Controller[] getCachedControllers()
    {
        if ( useStaticArrays )
            return( staticCachedControllers );
        else
            return( cachedControllers );
    }
    
    protected synchronized void flushCache( boolean keyboards, boolean mouses, boolean controllers )
    {
        if ( useStaticArrays )
        {
            if ( keyboards )
                staticCachedKeyboards = null;
            
            if ( mouses )
                staticCachedMouses = null;
            
            if ( controllers )
                staticCachedControllers = null;
        }
        else
        {
            if ( keyboards )
                this.cachedKeyboards = null;
            
            if ( mouses )
                this.cachedMouses = null;
            
            if ( controllers )
                this.cachedControllers = null;
        }
    }
    
    protected static final void flushCache( InputDeviceFactory factory, boolean keyboards, boolean mouses, boolean controllers )
    {
        factory.flushCache( keyboards, mouses, controllers );
    }
    
    /**
     * @return an array of all the installed Keyboards in the system.
     */
    protected abstract Keyboard[] initKeyboards() throws InputSystemException;
    
    /**
     * @return an array of all the installed Keyboards in the system.
     * 
     * @param factory the factory to call {@link #initKeyboards()} on
     */
    protected static final Keyboard[] initKeyboards( InputDeviceFactory factory ) throws InputSystemException
    {
        return( factory.initKeyboards() );
    }
    
    /**
     * {@inheritDoc}
     */
    public Keyboard[] getKeyboards( boolean forceRefresh ) throws InputSystemException
    {
        if ( ( getCachedKeyboards() == null ) || forceRefresh )
        {
            setCachedKeyboards( initKeyboards() );
        }
        
        return( getCachedKeyboards() );
    }
    
    /**
     * {@inheritDoc}
     */
    public final Keyboard[] getKeyboards() throws InputSystemException
    {
        return( getKeyboards( false ) );
    }
    
    /**
     * @return an array of all the installed Mouses in the system.
     */
    protected abstract Mouse[] initMouses() throws InputSystemException;
    
    /**
     * @return an array of all the installed Mouses in the system.
     * 
     * @param factory the factory to call {@link #initMouses()} on
     */
    protected static final Mouse[] initMouses( InputDeviceFactory factory ) throws InputSystemException
    {
        return( factory.initMouses() );
    }
    
    /**
     * {@inheritDoc}
     */
    public Mouse[] getMouses( boolean forceRefresh ) throws InputSystemException
    {
        if ( ( getCachedMouses() == null ) || forceRefresh )
        {
            setCachedMouses( initMouses() );
        }
        
        return( getCachedMouses() );
    }
    
    /**
     * {@inheritDoc}
     */
    public final Mouse[] getMouses() throws InputSystemException
    {
        return( getMouses( false ) );
    }
    
    /**
     * @return an array of all the installed Controllers in the system.
     */
    protected abstract Controller[] initControllers() throws InputSystemException;
    
    /**
     * @return an array of all the installed Controllers in the system.
     * 
     * @param factory the factory to call {@link #initControllers()} on
     */
    protected static final Controller[] initControllers( InputDeviceFactory factory ) throws InputSystemException
    {
        return( factory.initControllers() );
    }
    
    /**
     * {@inheritDoc}
     */
    public Controller[] getControllers( boolean forceRefresh ) throws InputSystemException
    {
        if ( ( getCachedControllers() == null ) || forceRefresh )
        {
            setCachedControllers( initControllers() );
        }
        
        return( getCachedControllers() );
    }
    
    /**
     * {@inheritDoc}
     */
    public final Controller[] getControllers() throws InputSystemException
    {
        return( getControllers( false ) );
    }
    
    /**
     * This method is called by the InputSystem when it gets destroyed.
     * 
     * @param inputSystem
     * 
     * @throws InputSystemException
     */
    public abstract void destroy( InputSystem inputSystem ) throws InputSystemException;
    
    public InputDeviceFactory( boolean useStaticArrays, InputSourceWindow sourceWindow, EventQueue eventQueue )
    {
        this.useStaticArrays = useStaticArrays;
        
        this.sourceWindow = sourceWindow;
        this.eventQueue = eventQueue;
    }
}

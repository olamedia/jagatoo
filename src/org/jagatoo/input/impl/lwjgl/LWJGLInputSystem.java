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
package org.jagatoo.input.impl.lwjgl;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.misc.InputSourceWindow;

/**
 * LWJGL implementation of the {@link InputSystem}.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLInputSystem extends InputSystem
{
    private int[] indexMap = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerController( Controller controller ) throws InputSystemException
    {
        super.registerController( controller );
        
        final LWJGLController lwjglController = (LWJGLController)controller;
        
        if ( indexMap == null )
        {
            indexMap = new int[ org.lwjgl.input.Controllers.getControllerCount() ];
            java.util.Arrays.fill( indexMap, -1 );
        }
        
        indexMap[ lwjglController.getIndex() ] = getControllersCount() - 1;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deregisterController( Controller controller ) throws InputSystemException
    {
        super.deregisterController( controller );
        
        final LWJGLController lwjglController = (LWJGLController)controller;
        
        indexMap[ lwjglController.getIndex() ] = -1;
    }
    
    protected void processMessages()
    {
        org.lwjgl.opengl.Display.processMessages();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateControllers( EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        if ( !hasController() )
            return;
        
        try
        {
            org.lwjgl.input.Controllers.poll();
            
            while ( org.lwjgl.input.Controllers.next() )
            {
                final int controllerIndex = org.lwjgl.input.Controllers.getEventSource().getIndex();
                final int realIndex = indexMap[ controllerIndex ];
                
                if ( realIndex != -1 )
                {
                    LWJGLController ctrl = (LWJGLController)getController( realIndex );
                    
                    ctrl.update( this, eventQueue, nanoTime );
                }
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw( (InputSystemException)t );
            
            if ( t instanceof Error )
                throw( (Error)t );
            
            if ( t instanceof RuntimeException )
                throw( (RuntimeException)t );
            
            throw( new InputSystemException( t ) );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( long nanoTime ) throws InputSystemException
    {
        processMessages();
        
        super.update( nanoTime );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() throws InputSystemException
    {
        super.destroy();
        
        try
        {
            if ( org.lwjgl.input.Controllers.isCreated() )
                org.lwjgl.input.Controllers.destroy();
        }
        catch ( Throwable t )
        {
            throw( new InputSystemException( t ) );
        }
    }
    
    @Override
    protected LWJGLDeviceFactory createDeviceFactory( InputSourceWindow sourceWindow, EventQueue eventQueue )
    {
        return( new LWJGLDeviceFactory( sourceWindow, eventQueue ) );
    }
    
    public LWJGLInputSystem( InputSourceWindow sourceWindow )
    {
        super( sourceWindow );
    }
}

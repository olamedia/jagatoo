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

import java.util.ArrayList;
import java.util.HashMap;

import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.listeners.InputStateListener;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class StringInputActionMapper implements InputStateListener
{
    private final HashMap< DeviceComponent, String > actionMap = new HashMap< DeviceComponent, String >();
    
    private final ArrayList< StringInputActionListener > listeners = new ArrayList< StringInputActionListener >();
    
    public void addActionListener( StringInputActionListener l )
    {
        if ( listeners.contains( l ) )
            return;
        
        listeners.add( l );
    }
    
    public void removeActionListener( StringInputActionListener l )
    {
        listeners.remove( l );
    }
    
    public final int getNumMappedActions()
    {
        return( actionMap.size() );
    }
    
    public void mapAction( DeviceComponent comp, String action )
    {
        if ( comp == null )
            throw( new IllegalArgumentException( "comp must not be null" ) );
        
        if ( action == null )
            throw( new IllegalArgumentException( "action must not be null" ) );
        
        actionMap.put( comp, action );
    }
    
    public void unmapAction( DeviceComponent comp )
    {
        if ( comp == null )
            throw( new IllegalArgumentException( "comp must not be null" ) );
        
        actionMap.remove( comp );
    }
    
    public void unmapAction( String action )
    {
        if ( action == null )
            throw( new IllegalArgumentException( "action must not be null" ) );
        
        DeviceComponent mappedComp = null;
        
        for ( DeviceComponent comp: actionMap.keySet() )
        {
            String mappedAction = actionMap.get( comp );
            
            if ( mappedAction != null )
            {
                mappedComp = comp;
                break;
            }
        }
        
        if ( mappedComp != null )
        {
            unmapAction( mappedComp );
        }
    }
    
    public final String getMappedAction( DeviceComponent comp )
    {
        return( actionMap.get( comp ) );
    }
    
    private final String checkMouseWheel( DeviceComponent comp, int delta )
    {
        final MouseWheel wheel = (MouseWheel)comp;
        
        String action = getMappedAction( MouseWheel.GLOBAL_WHEEL );
        
        if ( action == null )
        {
            if ( delta > 0 )
            {
                action = getMappedAction( MouseWheel.GLOBAL_WHEEL.getUp() );
                
                if ( action == null )
                    action = getMappedAction( wheel.getUp() );
            }
            else
            {
                action = getMappedAction( MouseWheel.GLOBAL_WHEEL.getDown() );
                
                if ( action == null )
                    action = getMappedAction( wheel.getDown() );
            }
        }
        
        return( action );
    }
    
    public void onInputStateChanged( InputEvent e, DeviceComponent comp, int delta, int state )
    {
        if ( comp == null )
            return;
        
        String action = getMappedAction( comp );
        
        if ( ( action == null ) && ( comp != null ) && ( comp.getType() == DeviceComponent.Type.MOUSE_WHEEL ) )
        {
            action = checkMouseWheel( comp, delta );
        }
        
        if ( action != null )
        {
            for ( int i = 0; i < listeners.size(); i++ )
            {
                listeners.get( i ).onActionInvoked( action, delta, state );
            }
        }
    }
}

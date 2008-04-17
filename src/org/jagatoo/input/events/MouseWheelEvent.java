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

import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.MouseWheel;

/**
 * Stores the details associated with a mouse event.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseWheelEvent extends MouseEvent
{
    /**
     * The button (if one was pressed).
     */
    protected int wheelDelta;
    
    /**
     * This is true, if the mouse-wheel move was a page-move.
     */
    protected boolean isPageMove;
    
    /**
     * The Mouse-Wheel delta movement.
     */
    public final int getWheelDelta()
    {
        return( wheelDelta );
    }
    
    /**
     * This is true, if the mouse-wheel move was a page-move.
     */
    public final boolean isPageMove()
    {
        return( isPageMove );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return( "MouseWheelEvent( pos = (" + getMouse().getCurrentX() + ", " + getMouse().getCurrentY() + "), " +
                "buttonsState = " + getMouse().getButtonsState() + ", " +
                "wheel = " + getWheelDelta() + (isPageMove() ? " (PAGE)" : "") + ", " +
                "when = " + getWhen() +
                " )"
              );
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected void set( Mouse mouse, MouseWheel wheel, int wheelDelta, boolean isPageMove, long when, long lastWhen )
    {
        super.set( mouse, SubType.WHEEL_MOVED, wheel, when, lastWhen );
        
        this.wheelDelta = wheelDelta;
        this.isPageMove = isPageMove;
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected MouseWheelEvent( Mouse mouse, MouseWheel wheel, int wheelDelta, boolean isPageMove, long when, long lastWhen )
    {
        super( mouse, SubType.WHEEL_MOVED, wheel, when, lastWhen );
        
        this.wheelDelta = wheelDelta;
        this.isPageMove = isPageMove;
    }
    
    /**
     * Creates a MouseEvent with default values.
     */
    protected MouseWheelEvent()
    {
        super( SubType.WHEEL_MOVED );
    }
}

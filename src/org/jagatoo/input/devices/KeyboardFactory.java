/**
 * Copyright (c) 2007-2011, JAGaToo Project Group all rights reserved.
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

/**
 * A KeyboardFactory is a simple factory to access all {@link Keyboard}s
 * available on the system.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface KeyboardFactory
{
    /**
     * @return an array of all the installed Keyboards in the system.
     * 
     * @param forceRefresh if true, the list of available devices is refreshed from the system.
     * 
     * @throws InputSystemException
     */
    public Keyboard[] getKeyboards( boolean forceRefresh ) throws InputSystemException;
    
    /**
     * @return an array of all the installed Keyboards in the system. (This method doesn't force a refresh!)
     * 
     * @throws InputSystemException
     */
    public Keyboard[] getKeyboards() throws InputSystemException;
    
    /**
     * This method is called by the InputSystem when it gets destroyed.
     * 
     * @param inputSystem
     * 
     * @throws InputSystemException
     */
    public void destroy( InputSystem inputSystem ) throws InputSystemException;
}

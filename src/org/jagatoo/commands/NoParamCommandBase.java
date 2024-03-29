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
package org.jagatoo.commands;

/**
 * This abstract base class for the Command interface correctly overrides
 * the {@link #hashCode()} and {@link #equals(Object)} methods.
 * This implementation is especially menat for Commands without any
 * parameters.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class NoParamCommandBase extends CommandBase implements NoParamCommand
{
    /**
     * {@inheritDoc}
     */
    public String[] getParameterTypes()
    {
        return ( null );
    }
    
    /**
     * {@inheritDoc}
     */
    public String execute( Boolean inputInfo, Object[] parameters ) throws CommandException
    {
        return ( execute( inputInfo ) );
    }
    
    protected NoParamCommandBase( final String key, final String text, int numParams )
    {
        super( key, text, numParams );
    }
    
    protected NoParamCommandBase( final String key, int numParams )
    {
        this( key, null, numParams );
    }
    
    public NoParamCommandBase( final String key, final String text )
    {
        this( key, text, 0 );
    }
    
    public NoParamCommandBase( final String key )
    {
        this( key, 0 );
    }
}

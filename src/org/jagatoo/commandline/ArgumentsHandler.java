/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
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
package org.jagatoo.commandline;

/**
 * Handles arguments from the command line.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class ArgumentsHandler
{
    /**
     * This method is called when the parser has identified an argument with or without a value.
     * 
     * @param arg the detected argument (null for loose values)
     * @param value the value (null for no-value-arguments)
     */
    protected abstract void handleArgument( Argument arg, Object value );
    
    /**
     * This method is called when a serious error is detected byy the parser.
     * 
     * @param chunk the command line chunk number
     * @param message the error message
     * 
     * @throws CommandLineParsingException
     */
    protected void onError( int chunk, String message ) throws CommandLineParsingException
    {
        throw new CommandLineParsingException( chunk, message );
    }
    
    /**
     * This method is called after the whole command line has been parsed.
     * 
     * @throws CommandLineParsingException
     */
    protected abstract void validate() throws CommandLineParsingException;
}

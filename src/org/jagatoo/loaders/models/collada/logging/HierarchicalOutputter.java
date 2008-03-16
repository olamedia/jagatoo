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
package org.jagatoo.loaders.models.collada.logging;

import java.io.PrintStream;

/**
 * This class allow to output debug informations (as you would
 * do normally with System.out.println() ) but in a "hierarchical"
 * fashion. That means you can increase/decrease the number of spaces
 * which are printed before each message, thus permitting to have
 * a kind of a tree in your output. This is especially useful if you're
 * loading a tree-like file format (used for COLLADA here).
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class HierarchicalOutputter {
    
    private String totalSpaces = "";
    private final String spacesPerTab;
    
    private PrintStream outputStream;
    private PrintStream errorStream;
    
    private boolean printEnabled = true;
    
    /**
     * Create a new HierarchicalOutputter
     */
    public HierarchicalOutputter() {
        
        this(2);
        
    }
    
    /**
     * Create a new HierarchicalOutputter
     * @param spacesPerTab outputStream.print(message);
     */
    public HierarchicalOutputter(int spacesPerTab) {
        
        this(System.out, System.err, spacesPerTab);
        
    }
    
    /**
     * Create a new HierarchicalOutputter
     * @param outputStream The stream used to output the messages
     * @param errorStream The stream used to output the stacktraces
     * @param spacesPerTab {@inheritDoc}
     */
    public HierarchicalOutputter(PrintStream outputStream, PrintStream errorStream, int spacesPerTab) {
        
        this.outputStream = outputStream;
        this.errorStream = errorStream;
        StringBuffer buff = new StringBuffer();
        for(int i = 0; i < spacesPerTab; i++) {
            buff.append(" ");
        }
        this.spacesPerTab = buff.toString();
        
    }
    
    /**
     * Increase the number of spaces before
     * every message printed by this Outputter
     */
    public void increaseTabbing() {
        totalSpaces = totalSpaces + spacesPerTab;
    }
    
    /**
     * Decrease the number of spaces before
     * every message printed by this Outputter
     */
    public void decreaseTabbing() {
        totalSpaces = totalSpaces.substring(spacesPerTab.length());
    }
    
    /**
     * Print a message to the output stream, with the right
     * tabbing.
     * @param message The message to print
     */
    public void print(String message) {
        if(printEnabled) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            errorStream.print(totalSpaces);
            errorStream.println(stackTraceElement);
            errorStream.flush();
            outputStream.print(totalSpaces);
            outputStream.println(message);
            outputStream.print("\n");
            outputStream.flush();
        }
    }
    
    /**
     * @return the printEnabled
     */
    public boolean isPrintEnabled() {
        return printEnabled;
    }
    
    /**
     * @param printEnabled the printEnabled to set
     */
    public void setPrintEnabled(boolean printEnabled) {
        this.printEnabled = printEnabled;
    }
    
}

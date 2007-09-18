/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
/**
 * :Id: LogManager.java,v 1.9 2003/02/24 00:13:53 wurp Exp $
 * 
 * :Log: LogManager.java,v $
 * Revision 1.9  2003/02/24 00:13:53  wurp
 * Formatted all java code for cvs (strictSunConvention.xml)
 * 
 * Revision 1.8  2002/02/12 02:22:27  dilvish
 * Bunch of bug fixes
 * 
 * Revision 1.7  2002/01/15 03:39:00  dilvish
 * Added the ability to dump exception stacks to log
 * 
 * Revision 1.6  2001/06/20 04:05:42  wurp
 * added log4j.
 * 
 * Revision 1.5  2001/04/04 01:06:29  wizofid
 * New framerate window, new animation system
 * 
 * Revision 1.4  2001/03/13 01:43:34  wizofid
 * Added the portal
 * 
 * Revision 1.3  2001/01/28 07:52:20  wurp
 * Removed <dollar> from Id and Log in log comments.
 * Added several new commands to AdminApp
 * Unfortunately, several other changes that I have lost track of.  Try diffing this
 * version with the previous one.
 * 
 * Revision 1.2  2000/12/16 22:07:33  wurp
 * Added Id and Log to almost all of the files that didn't have it.  It's
 * possible that the script screwed something up.  I did a commit and an update
 * right before I ran the script, so if a file is screwed up you should be able
 * to fix it by just going to the version before this one.
 */
package org.jagatoo.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This object manages multiple logs.  This provides a single point
 * at which the application can write to logs, but allows log control
 * to be handled centrally.  Multiple LogInterface objects are registered
 * with the logger.  The LogManager will step through them when a logging
 * message comes in and give each LogInterface an opportunity to consume
 * the log message.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class LogManager
{
    /**
     * Special writer for sending strings to the various logs
     */
    private class LogWriter extends Writer
    {
        private final LogChannel channel;
        private final int type;
        private final StringBuffer buf = new StringBuffer();
        
        public LogWriter( LogChannel channel, int type )
        {
            super();
            
            this.channel = channel;
            this.type = type;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void close() throws IOException
        {
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() throws IOException
        {
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void write( char[] cbuf, int off, int len ) throws IOException
        {
            for ( int i = off; i < (off + len); i++ )
            {
                char ch = cbuf[ i ];
                
                if ( ch == 13 )
                {
                    String s = buf.toString();
                    println( channel, type, s );
                    buf.setLength( 0 );
                }
                else if ( ch >= 32 )
                {
                    buf.append( ch );
                }
            }
        }
    }
    
    private ArrayList<LogInterface> logs;
    private int minRegisteredLogLevel = -Integer.MAX_VALUE;
    private int registeredChannels = 0;
    private long startTime;
    private boolean timestampsEnabled = false;
    private boolean channelsVisible = false;
    private boolean lastNewLine = true;
    private HashSet<String> debugPackageFilter = new HashSet<String>();
    
    private String indentationString = "    ";
    private int indentation = 0;
    private final StringBuffer strBuff = new StringBuffer();
    
    private static LogManager instance = null;
    
    /**
     * Sets the String to be prefixed to the actualy logging output n times.
     * 
     * @param indentationString
     */
    public final void setIndentationString( String indentationString )
    {
        this.indentationString = indentationString;
    }
    
    /**
     * @return the String to be prefixed to the actualy logging output n times.
     */
    public final String getIndentationString()
    {
        return( indentationString );
    }
    
    /**
     * Sets the indentation level to use for the following log outputs.
     * 
     * @param indentation
     */
    public final void setIndentation( int indentation )
    {
        this.indentation = Math.max( 0, indentation );
    }
    
    /**
     * @return the indentation level to use for the following log outputs.
     */
    public final int getIndentation()
    {
        return( indentation );
    }
    
    public final void setTimestampingEnabled( boolean enabled )
    {
        this.timestampsEnabled = enabled;
    }
    
    public final boolean isTimestampingEnabled()
    {
        return( timestampsEnabled );
    }
    
    public final void setChannelsVisible( boolean visible )
    {
        this.channelsVisible = visible;
    }
    
    public final boolean areChannelsVisible()
    {
        return( channelsVisible );
    }
    
    public final void addDebuggingPackage( String pkg )
    {
        debugPackageFilter.add( pkg );
    }
    
    public final void removeDebuggingPackage( String pkg )
    {
        debugPackageFilter.remove( pkg );
    }
    
    public final HashSet<String> getDebuggingPackageFiler()
    {
        return( debugPackageFilter );
    }
    
    private String getTimeString()
    {
        final long delta = System.currentTimeMillis() - startTime;
        
        return( LogFormatter.formatTime( delta ) );
    }
    
    private static String getMemory()
    {
        Runtime runtime = Runtime.getRuntime();
        final long mem = runtime.totalMemory();
        final long free = runtime.freeMemory();
        
        return( LogFormatter.formatMemory( mem - free ) + "/" + LogFormatter.formatMemory( mem ) );
    }
    
    /**
     * Must be called, if the logLevel of a registeredLogInterface has been changed.
     */
    public final void refreshLogInterfaces()
    {
        minRegisteredLogLevel = -Integer.MAX_VALUE;
        registeredChannels = 0;
        
        for ( int i = 0; i < logs.size(); i++ )
        {
            final LogInterface log = logs.get( i );
            
            minRegisteredLogLevel = Math.max( minRegisteredLogLevel, log.getLogLevel() );
            registeredChannels |= log.getChannelFilter();
        }
    }
    
    /**
     * This method allows you to register a class that implements the
     * LogInterface. Every log so registered will get a copy of every
     * log message, along with its mask.
     */
    public final void registerLog( LogInterface log )
    {
        logs.add( log );
        
        refreshLogInterfaces();
    }
    
    /**
     * This method allows you to deregister a class that implements the
     * LogInterface. Every log so deregistered won't get a copy of every
     * log message anymore.
     */
    public final void deregisterLog( LogInterface log )
    {
        logs.remove( log );
        
        refreshLogInterfaces();
    }
    
    public final boolean isAnyLogInterfaceRegistered( LogChannel channel, int logLevel )
    {
        if ( logs.size() == 0 )
            return( false );
        
        if ( minRegisteredLogLevel < logLevel )
            return( false );
        
        if ( ( registeredChannels & channel.getID() ) == 0 )
            return( false );
        
        return( true );
    }
    
    private static final String getCallerPackage()
    {
        final StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        
        String callerClass = null;
        for ( int i = 3; i < stes.length; i++ )
        {
            if ( !stes[ i ].getClassName().startsWith( LogManager.class.getPackage().getName() ) )
            {
                callerClass = stes[ i ].getClassName();
                break;
            }
        }
        
        if ( ( callerClass == null ) || ( callerClass.length() == 0 ) )
            return( "" );
        
        final int lastDot = callerClass.lastIndexOf( '.' );
        final String callerPackage;
        if ( lastDot >= 0 )
            callerPackage = callerClass.substring( 0, lastDot );
        else
            callerPackage = "";
        
        return( callerPackage );
    }
    
    private final String getIndentPrefix()
    {
        strBuff.setLength( 0 );
        for ( int i = 0; i < indentation; i++ )
        {
            strBuff.append( indentationString );
        }
        
        return( strBuff.toString() );
    }
    
    private final synchronized void internalPrintln( LogChannel channel, int logLevel, String message )
    {
        if ( !isAnyLogInterfaceRegistered( channel, logLevel ) )
            return;
        
        if ( logLevel >= LogLevel.DEBUG )
        {
            if ( !debugPackageFilter.isEmpty() )
            {
                final String callerPackage = getCallerPackage();
                if ( !debugPackageFilter.contains( callerPackage  ) )
                    return;
            }
        }
        
        final String prefix;
        if ( lastNewLine && isTimestampingEnabled() )
        {
            prefix = "[" + getTimeString() + ", " + getMemory() + "] ";
        }
        else
        {
            prefix = "";
        }
        
        for ( int i = 0; i < logs.size(); i++ )
        {
            logs.get( i ).println( channel, logLevel, prefix + getIndentPrefix() + message );
        }
        
        this.lastNewLine = true;
    }
    
    /**
     * This method will call all the log objects to store the message,
     * if they want to.
     * 
     * @param logLevel the logLevel of this message
     * @param message the string message to be printed to the log
     */
    final void println( LogChannel channel, int logLevel, String message )
    {
        internalPrintln( channel, logLevel, message );
    }
    
    synchronized final void print( LogChannel channel, int logLevel, String message )
    {
        if ( !isAnyLogInterfaceRegistered( channel, logLevel ) )
            return;
        
        if ( logLevel >= LogLevel.DEBUG )
        {
            if ( !debugPackageFilter.isEmpty() )
            {
                final String callerPackage = getCallerPackage();
                if ( !debugPackageFilter.contains( callerPackage  ) )
                    return;
            }
        }
        
        final String prefix1;
        if ( lastNewLine && areChannelsVisible() )
        {
            prefix1 = channel.getLogString() + " ";
        }
        else
        {
            prefix1 = "";
        }
        
        final String prefix2;
        if ( lastNewLine && isTimestampingEnabled() )
        {
            prefix2 = "[" + getTimeString() + ", " + getMemory() + "] ";
        }
        else
        {
            prefix2 = "";
        }
        
        for ( int i = 0; i < logs.size(); i++ )
        {
            logs.get( i ).print( channel, logLevel, prefix1 + prefix2 + getIndentPrefix() + message );
        }
        
        this.lastNewLine = false;
    }
    
    final void print( LogChannel channel, Throwable e )
    {
        final PrintWriter p;
        if ( e instanceof Error )
            p = new PrintWriter( new LogWriter( channel, LogLevel.ERROR ), true );
        else
            p = new PrintWriter( new LogWriter( channel, LogLevel.EXCEPTION ), true );
        
        e.printStackTrace( p );
    }
    
    /**
     * Steps through the logs and flushes all of them. Necessary since they
     * could be implemented using files with buffers.
     */
    final void flush()
    {
        for ( int i = 0; i < logs.size(); i++ )
        {
            logs.get( i ).flush();
        }
    }
    
    /**
     * Steps through the logs and closes all of them. Necessary since they
     * could be implemented using files with buffers.
     */
    final void close()
    {
        for ( int i = 0; i < logs.size(); i++ )
        {
            logs.get( i ).close();
        }
    }
    
    private LogManager()
    {
        this.logs = new ArrayList<LogInterface>( 2 );
        this.startTime = System.currentTimeMillis();
    }
    
    /**
     * @return the LogManager's singleton instance.
     */
    public static final LogManager getInstance()
    {
        if ( instance == null )
            instance = new LogManager();
        
        return( instance );
    }
}

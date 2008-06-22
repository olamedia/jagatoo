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
package org.jagatoo.util.versioning;

/**
 * General Version information.<br>
 * Please note, that the version information is not guaranteed to be updated
 * with each SVN commit. So the information might be outdated for
 * development versions. Especially the revision-number will be outdated most
 * of the time. Only releases are guaranteed to carry valid and correct
 * version information.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Version
{
    private int major;
    private int minor;
    private int revision;
    private String attributes;
    private int build;
    
    /**
     * @return the major version number
     */
    public int getMajor()
    {
        return( major );
    }
    
    /**
     * @return the minor version number
     */
    public int getMinor()
    {
        return( minor );
    }
    
    /**
     * @return the revision version number
     */
    public int getRevision()
    {
        return( revision );
    }
    
    /**
     * @return the version attributes (like "beta1" or "RC2") If no attributes
     *         are present, this is <i>null</i>.
     */
    public String getAttributes()
    {
        return( attributes );
    }
    
    /**
     * @return the version-control-revision number. It is the revision, that was
     *         last checked in before this release. If this is an unreleased,
     *         the returned value is not guaranteed to be correct.
     */
    public int getBuild()
    {
        return( build );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final String base = getMajor() + "." + getMinor() + "." + getRevision();
        
        if (getAttributes() != null)
            return( base + "-" + getAttributes() + " (build " + getBuild() + ")" );
        else
            return( base + " (build " + getBuild() + ")" );
    }
    
    /**
     * Creates a new instance of Version information class.
     * 
     * @param major the major version number
     * @param minor the minor version number
     * @param revision the revision version number
     * @param attributes the version attributes (like "beta1" or "RC2")
     * @param build the version-control-revision number.
     */
    public Version(int major, int minor, int revision, String attributes, int build)
    {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
        this.attributes = attributes;
        this.build = build;
    }
}

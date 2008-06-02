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
package org.jagatoo.loaders.models.collada.jibx;

import java.util.Date;

import org.openmali.vecmath2.Vector3f;

/**
 * Asset information about a COLLADA file, e.g.
 * the author, the contributor, the creation/modification
 * dates, units, etc..
 * Child of Camera, COLLADA, Light, Material, Technique,
 * Source, Geometry, Image, Animation, AnimationClip,
 * Controller, Extra, Node, VisualScene, Library_*, Effect,
 * ForceField, PhysicsMaterial, PhysicsScene, PhysicsModel
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLAsset {
    
    public XMLContributor contributor = null;
    public Date created = null;
    public Date modified = null;
    public XMLUnit unit = null;
    public static enum UpAxis {
        X_UP,
        Y_UP,
        Z_UP
    }
    public UpAxis upAxis = null;
    
    public final Vector3f getUpVector() {
        if (upAxis == null) {
            return Vector3f.POSITIVE_Y_AXIS; // COLLADA-default!
        }
        
        switch (upAxis) {
            case X_UP:
                return Vector3f.POSITIVE_X_AXIS;
            case Y_UP:
                return Vector3f.POSITIVE_Y_AXIS;
            case Z_UP:
                return Vector3f.POSITIVE_Z_AXIS;
        }
        
        return null;
    }
    
}

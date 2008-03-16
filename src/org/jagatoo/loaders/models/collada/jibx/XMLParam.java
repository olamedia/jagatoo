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

/**
 * A param is instruction on how to interpret
 * a part of a Source. It has a type and a name.
 * The name contains the "use" of the param, e.g.
 * "TIME", "ANGLE", "X", "Y", "Z"
 * Child of Accessor.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLParam {
    
    public static enum Type {
        /** Boolean */ _bool,
        /** Float */ _float,
        /** Integer */ _int,
        /** Name (String) */ _Name,
        /** IDREF (String) */ _IDREF,
        /** 4x4 float matrix */ _float4x4
    }
    
    public XMLParam.Type type;
    
    public static enum Name {
        /** X coordinate */ X,
        /** Y coordinate */ Y,
        /** T coordinate */ Z,
        /** U coordinate for texture mapping */ S,
        /** V coordinate for texture mapping */ T,
        /** Red component for color */ R,
        /** Green component for color */ G,
        /** Blue component for color */ B,
        /** Alpha component for color */ A,
        /** Time. Used for anims */ TIME,
        /** Angle. In degrees. */ ANGLE,
        /** Weight */ WEIGHT,
        /** Joint */ JOINT
    }
    
    public Name name;
    
    public static Type readTypeString(String typeString) {
        return Type.valueOf("_"+typeString);
    }
    
}

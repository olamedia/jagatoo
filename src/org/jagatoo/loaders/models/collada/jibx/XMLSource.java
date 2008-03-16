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
 * Declares a data repository that provides values
 * according to the semantics of an <input> element
 * that refers to it.
 * Child of Morph, Animation, Mesh, ConvexMesh, Skin, Spline
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSource {
    
    public XMLAsset asset = null;
    
    public String id = null;
    
    /*
     * From the COLLADA doc : No more than one of
     * the array elements (<bool_array>, <float_array>,
     * <int_array>, <Name_array>, or <IDREF_array>) may occur.
     * They are mutually exclusive.
     */
    public XMLBoolArray boolArray = null;
    public XMLFloatArray floatArray = null;
    public XMLIntArray intArray = null;
    public XMLNameArray nameArray = null;
    public XMLIDREFArray idrefArray = null;
    
    
    /**
     * TechniqueCommon, as a child of Source, contains an acessor,
     * which contains the information needed to read a Source.
     * (in fact, it's the meaning of the data in the Source)
     * Child of Source.
     * 
     * @author Amos Wenger (aka BlueSky)
     */
    public static class TechniqueCommon {
        
        public XMLAccessor accessor;
        
    }
    
    public XMLSource.TechniqueCommon techniqueCommon = null;
    
}

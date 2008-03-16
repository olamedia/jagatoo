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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Library of Materials.
 * Child of COLLADA.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLLibraryMaterials {
    
    /**
     * This field is written by JiBX and then parsed by the
     * readMaterials() method and then the materialMap HashMap
     * is written.
     */
    private ArrayList<XMLMaterial> materialsList = null;
    
    /**
     * A map of all materials, which is filled by the readMaterials()
     * method just after the materials ArrayList has been written.
     * key = ID
     * value = Material
     */
    public HashMap<String, XMLMaterial> materials = null;
    
    /**
     * Called just after materials has been read, fill
     * the materialMap.
     */
    public void readMaterials() {
        materials = new HashMap<String, XMLMaterial>();
        for (XMLMaterial material : materialsList) {
            materials.put(material.id, material);
        }
        materialsList = null;
    }
    
}

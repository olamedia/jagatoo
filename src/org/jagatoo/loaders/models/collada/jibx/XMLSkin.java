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

import org.jagatoo.loaders.models.collada.datastructs.controllers.Influence;
import org.jagatoo.loaders.models.collada.exceptions.ColladaLoaderException;

/**
 * A Skin. It defines how skeletal animation should be computed.
 * Child of Controller.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSkin {
    
	/**
	 * This source defines a weight for each vertex and each joint
	 */
    private static final String SKING_WEIGHT_SOURCE = "skin-weights";

	public String source = null;
    
    // Here we instantiate it because if not read, it should be identity
    // (so the COLLADA doc says)
    public XMLMatrix4x4 bindShapeMatrix = new XMLMatrix4x4();
    
    public ArrayList<XMLSource> sources = null;
    public ArrayList<XMLInput> jointsInputs = null;
    public XMLVertexWeights vertexWeights = new XMLVertexWeights();
    
    /**
     * Build an array of BoneWeight for easy skinning manipulation
     */
    public Influence[] buildInfluencesForVertex( int vertexIndex ) {
    	//get the number of influences (bone-weight) for the vertex
    	int influences = vertexWeights.vcount.ints[ vertexIndex ];
    	
    	//get the "skin-weights", maybe it could be done only one time, when the sources array is filled.
    	XMLSource weightSources = getWeightSources();
    	
    	//fill the array "skin-weights" source
    	Influence[] boneWeights = new Influence[ influences ];
    	for (int i = 0; i < boneWeights.length; i++) {
    		boneWeights[i] = new Influence();
		}
    	//FIXME I don`t know how to use well the offset attribute:
    	/*
    	 * Example:
    	 * <input semantic="JOINT" source="#pCylinderShape1-skin-joints" offset="0"></input>
    	 * <input semantic="WEIGHT" source="#pCylinderShape1-skin-weights" offset="1"></input>
    	 */
    	for (int i = 0; i < boneWeights.length; i++) {
    		boneWeights[ i ].bone = vertexWeights.v.ints[ vertexIndex + i ];
    		boneWeights[ i ].weight = weightSources.floatArray.floats[ vertexIndex + i ];
		}
    	
    	return boneWeights;
    }

    /**
     * Search the "skin-weights" source.
     * Maybe there is a better way get that
     */
	private XMLSource getWeightSources() {
		for (XMLSource source : sources) {
			if ( source.id.contains( SKING_WEIGHT_SOURCE ) ) {
				return source;
			}
		}
		throw new ColladaLoaderException( "Could not found source " + 
				SKING_WEIGHT_SOURCE + " in library_controllers" );
	}
    
}

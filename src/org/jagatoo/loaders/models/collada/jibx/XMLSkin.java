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

import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Influence;

/**
 * A Skin. It defines how skeletal animation should be computed.
 * Child of Controller.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSkin {
    
    /**
     * This source defines a joint/bone for each vertex and each joint
     */
    private static final String SKIN_JOINTS_SOURCE = "skin-joints";
    
	/**
	 * This source defines a weight for each vertex and each joint
	 */
    private static final String SKIN_WEIGHTS_SOURCE = "skin-weights";

	public String source = null;
    
    // Here we instantiate it because if not read, it should be identity
    // (so the COLLADA doc says)
    public XMLMatrix4x4 bindShapeMatrix = new XMLMatrix4x4();
    
    public ArrayList<XMLSource> sources = null;
    public ArrayList<XMLInput> jointsInputs = null;
    public XMLVertexWeights vertexWeights = new XMLVertexWeights();
    
    private Influence[][] influences = null;
    
    /**
     * Search the "skin-joints" source.
     * Maybe there is a better way get that
     */
    public XMLSource getJointsSource() {
        for (XMLSource source : sources) {
            if ( source.id.endsWith( SKIN_JOINTS_SOURCE ) ) {
                return source;
            }
        }
        throw new IncorrectFormatException( "Could not find source " + 
                SKIN_JOINTS_SOURCE + " in library_controllers" );
    }
    
    /**
     * Search the "skin-weights" source.
     * Maybe there is a better way get that
     */
    public XMLSource getWeightsSource() {
        for (XMLSource source : sources) {
            if ( source.id.endsWith( SKIN_WEIGHTS_SOURCE ) ) {
                return source;
            }
        }
        throw new IncorrectFormatException( "Could not find source " + 
                SKIN_WEIGHTS_SOURCE + " in library_controllers" );
    }
    
    /**
     * Normalize the influences weights
     */
    private void normalizeInfluences()
    {
        // TODO: not yet implemented!
    }
    
    public void buildInfluences( Skeleton skeleton, int numVertices )
    {
        if ( influences != null )
            return;
        
        influences = new Influence[ numVertices ][];
        
        XMLSource jointsSource = getJointsSource();
        
        // get the "skin-weights", maybe it could be done only one time, when the sources array is filled.
        XMLSource weightsSource = getWeightsSource();
        
        int vIndex = 0;
        for ( int i = 0; i < vertexWeights.vcount.ints.length; i++ )
        {
            final int numBones = vertexWeights.vcount.ints[i];
            
            influences[i] = new Influence[numBones];
            
            for ( int j = 0; j < numBones; j++ )
            {
                final int boneIndex = vertexWeights.v.ints[vIndex + j * 2 + 0];
                final int weightIndex = vertexWeights.v.ints[vIndex + j * 2 + 1];
                
                final float weight = weightsSource.floatArray.floats[weightIndex];
                
                if ( boneIndex == -1 )
                {
                    //influences[i][j] = new Influence( skeleton.binShapeMatrix, weight );
                }
                else
                {
                    final String boneSourceId = jointsSource.idrefArray.idrefs[boneIndex];
                    final Bone bone = skeleton.getBoneBySourceId( boneSourceId );
                    
                    influences[i][j] = new Influence( bone.getAbsoluteRotation(), weight );
                }
            }
            
            vIndex += numBones * 2;
        }
        
        normalizeInfluences();
    }
    
    /**
     * Build an array of BoneWeight for easy skinning manipulation
     */
    public Influence[] getInfluencesForVertex( int vertexIndex )
    {
    	return( influences[vertexIndex] );
    }
}

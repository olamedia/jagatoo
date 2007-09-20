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
    
    // Here we instanciate it because if not read, it should be identity
    // (so the COLLADA doc says)
    public XMLMatrix4x4 bindShapeMatrix = new XMLMatrix4x4();
    
    public ArrayList<XMLSource> sources = null;
    public ArrayList<XMLInput> jointsInputs = null;
    public XMLVertexWeights vertexWeights = null;
    
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
			if ( source.id.equals( SKING_WEIGHT_SOURCE ) ) {
				return source;
			}
		}
		throw new ColladaLoaderException( "Could not found source " + 
				SKING_WEIGHT_SOURCE + " in library_controllers" );
	}
    
}

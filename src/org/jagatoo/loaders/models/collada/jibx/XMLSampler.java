package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

import org.jagatoo.loaders.models.collada.exceptions.ColladaLoaderException;

/**
 * A COLLADA Sampler.
 *
 * Child of Animation
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLSampler {

    public String id = null;

    public ArrayList<XMLInput> inputs = null;

    /**
     * Search the input with the specified semantic
     * @param semantic
     * @return the XML input
     */
    public XMLInput getInput( String semantic ) {
    	for (XMLInput input : inputs) {
    		if( input.semantic.equals( semantic ) ) {
    			return input;
    		}
		}
    	throw new ColladaLoaderException( "Could not found input with semantic " + semantic );
    }

}

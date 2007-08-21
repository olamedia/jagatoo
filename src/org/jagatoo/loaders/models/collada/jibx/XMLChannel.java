package org.jagatoo.loaders.models.collada.jibx;

import java.util.StringTokenizer;

import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.exceptions.ColladaLoaderException;

/**
 * A COLLADA Channel.
 *
 * Child of Animation.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLChannel {

	public final static String TRANSLATE_TARGET = "translate";
	public final static String ROTATION_TARGET = "rotate";

    public String source = null;
    public String target = null;

    private String targetBone = null;
    private byte targetAxis;
    private boolean isTranslate = false;


    //target="Root/rotateX.ANGLE"
    //target="Root/translate"


    public String getTargetBone() {
    	checkIsParsed();
    	return this.targetBone;
    }

    public boolean hasTranslationKeyFrame() {
    	checkIsParsed();
    	return this.isTranslate;
    }

    public byte getRotationAxis() {
    	checkIsParsed();
    	if( isTranslate ) {
    		throw new ColladaLoaderException( "It is not a rotation key frame" );
    	}
    	return this.targetAxis;
    }


    /**
     * Parse the target attribute and gets the bone and the type of movement
     */
    private void checkIsParsed() {
    	if( targetBone == null ) {

    		StringTokenizer tok = new StringTokenizer( target );
    		targetBone = tok.nextToken( "/" );
    		String trans =  tok.nextToken();

    		//see if it´s a translation or a rotation
    		if( trans.equals( TRANSLATE_TARGET ) ) {
    			isTranslate = true;

    		//if it´s a rotation, get the axis
    		} else {
    			isTranslate = false;
    			if( trans.indexOf( "X" ) != -1 ) {
    				targetAxis = KeyFrame.X_AXIS;
    			} else if( trans.indexOf( "Y" ) != -1 ) {
    				targetAxis = KeyFrame.Y_AXIS;
    			} else {
    				targetAxis = KeyFrame.Z_AXIS;
    			}
    		}
    	}
    }

}







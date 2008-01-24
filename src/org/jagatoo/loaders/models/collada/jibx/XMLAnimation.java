package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.exceptions.ColladaLoaderException;
import org.jagatoo.loaders.models.collada.jibx.XMLChannel.ChannelType;

/**
 * A COLLADA animation.
 *
 * Child of LibraryAnimations.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLAnimation {

    public String id = null;
    public String name = null;

    public XMLAsset asset = null;

    public ArrayList<XMLSource> sources = new ArrayList<XMLSource>();
    public ArrayList<XMLSampler> samplers = new ArrayList<XMLSampler>();
    public ArrayList<XMLChannel> channels = new ArrayList<XMLChannel>();


    /**
     * Search a source with the specified id
     * @param id
     * @return the XML source
     */
    public XMLSource getSource( String id ) {
        for (XMLSource source : sources) {
            if( source.id.equals( id ) ) {
                return source;
            }
        }
        throw new ColladaLoaderException( "Could not found source with id " + id );
    }


    /**
     * @return the target bone name for this animation
     */
    public String getTargetBone() {
        return channels.get( 0 ).getTargetBone();
    }

    /**
     * @return a float array with all the times of the key frames
     */
    public float[] getInput() {
        return getSource( samplers.get( 0 ).getInput( "INPUT" ).source ).floatArray.floats;
    }

    /**
     * @return a float array with the values of all the key frames
     */
    public float[] getOutput() {
        return getSource( samplers.get( 0 ).getInput( "OUTPUT" ).source ).floatArray.floats;
    }

    /**
     * Tells if the animation contains transformation key frames or rotation key frames
     */
    public ChannelType getType() {
        return channels.get( 0 ).type;
    }


    /**
     * @return the rotation axis of the animation.
     * It only works if the animation if for rotation
     */
    public KeyFrame.Axis getRotationAxis() {
        return channels.get( 0 ).getRotationAxis();
    }

}

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
 * @author Matias Leone (aka Maguila)
 */
public class XMLChannel {

    /**
     * The type of a channel
     *
     * @author Amos Wenger (aka BlueSky)
     */
    public static enum ChannelType {
        /** A translate channel */
        TRANSLATE,
        /** A rotate channel */
        ROTATE,
        /** A scale channel */
        SCALE
    }

    public ChannelType type;

    public final static String TRANSLATE_TARGET = "translate";
    public final static String ROTATE_TARGET = "rotate";
    public final static String SCALE_TARGET = "scale";

    public String source = null;
    public String target = null;

    private String targetBone = null;
    private KeyFrame.Axis targetAxis;

    //target="Root/rotateX.ANGLE"
    //target="Root/translate"
    //target="Root/scale"

    public String getTargetBone() {
        checkIsParsed();
        return this.targetBone;
    }

    public KeyFrame.Axis getRotationAxis() {
        checkIsParsed();
        if (type != ChannelType.ROTATE) {
            throw new ColladaLoaderException("It is not a rotation key frame, it's a "+type);
        }
        return this.targetAxis;
    }


    /**
     * Parse the target attribute and gets the bone and the type of movement
     */
    private void checkIsParsed() {

        if (targetBone == null) {

            StringTokenizer tok = new StringTokenizer(target);
            targetBone = tok.nextToken("/");
            String trans = tok.nextToken();

            //see if it's a translation or a rotation
            if (trans.startsWith(TRANSLATE_TARGET)) {

                /*
                 * It's a translate channel
                 */
                type = ChannelType.TRANSLATE;

            } else if (trans.startsWith(ROTATE_TARGET)) {

                /*
                 * It's a rotate channel
                 */
                type = ChannelType.ROTATE;

                // Get the axis
                if (trans.contains("rotateX")) {
                    targetAxis = KeyFrame.Axis.X;
                } else if (trans.contains("rotateY")) {
                    targetAxis = KeyFrame.Axis.Y;
                } else {
                    targetAxis = KeyFrame.Axis.Z;
                }

            } else if (trans.startsWith(SCALE_TARGET)) {

                /*
                 * It's a scale channel
                 */

            }
        }

    }

}

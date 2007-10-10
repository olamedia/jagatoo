package org.jagatoo.loaders.models.collada.datastructs.animation;

import java.util.Iterator;

import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Point3f;

/**
 * A Skeleton. It contains a root bone, which can have several children
 *
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class Skeleton implements Iterable<Bone> {

    /** The root bone : there's only one (for simple implementation) */
    public final Bone rootBone;

    // GC-friendly hacks
    private final static Matrix3f tempMatrix = new Matrix3f();
    private final static Point3f tempPoint = new Point3f();

    /**
     * The position of our Skeleton. The root bone is transformed by this
     * position (and thus, the children bones are transformed too).
     */
    public final Point3f relativeTranslation;

    /** The keyframes of position */
    public KeyFramePoint3f[] transKeyFrames;

    /**
     * Iterator for easy managment of the bones
     */
    private SkeletonIterator iterator;


    /**
     * Create a new Skeleton
     *
     * @param rootBone
     *                The root bone
     * @param relativeTranslation
     *                The position of the Skeleton
     */
    public Skeleton(Bone rootBone, Point3f relativeTranslation) {

        this.rootBone = rootBone;
        this.relativeTranslation = relativeTranslation;

    }

    /**
     * @return the rootBone
     */
    public Bone getRootBone() {

        return rootBone;

    }

    /**
     * Selects the current translation key frame, based on the current time
     *
     * @param currentTime
     *                beetween 0 and the end of the animation, in miliseconds
     * @return frame index selected
     */
    public int selectCurrentTransFrame(long currentTime) {
        return KeyFrame.searchNextFrame(transKeyFrames, currentTime);
    }

    /**
     * Update the skeleton. It recomputes the transform matrix of each bone,
     * starting with the root bone and then each child recursively.
     */
    public void updateAbsolutes() {

        updateBone(null, rootBone);

    }

    /**
     * Update a bone, e.g. compute its absolute rotation/translation from its
     * relative ones.
     *
     * @param parentTrans
     * @param bone
     */
    private void updateBone(Bone parentBone, Bone bone) {

        if (parentBone == null) {

            /*
             * Root bone
             */

            bone.absoluteRotation.set(bone.getBindRotation());
            bone.absoluteRotation.mul(bone.relativeRotation);

            bone.absoluteTranslation.set(relativeTranslation);
            bone.absoluteTranslation.addZ( bone.getLength() );

            bone.absoluteScaling.set(bone.relativeScaling);

        } else {

            /*
             * Child bone
             */

            bone.absoluteRotation.set(parentBone.absoluteRotation);
            bone.absoluteRotation.mul(bone.getBindRotation());
            bone.absoluteRotation.mul(bone.relativeRotation);

            bone.absoluteTranslation.set(parentBone.absoluteTranslation);
            tempMatrix.set(bone.absoluteRotation);
            tempPoint.set(0f, 0f, bone.getLength());
            tempMatrix.transform(tempPoint);
            bone.absoluteTranslation.add(tempPoint);

            bone.absoluteScaling.set(parentBone.absoluteScaling);
            bone.absoluteScaling.set(bone.absoluteScaling.getX()
                    * bone.relativeScaling.getX(), bone.absoluteScaling.getY()
                    * bone.relativeScaling.getY(), bone.absoluteScaling.getZ()
                    * bone.relativeScaling.getZ());

        }

        // Both for root and children nodes
        bone.absoluteTranslation.set(bone.absoluteTranslation.getX()
                * bone.absoluteScaling.getX(), bone.absoluteTranslation.getY()
                * bone.absoluteScaling.getY(), bone.absoluteTranslation.getZ()
                * bone.absoluteScaling.getZ());

        for (int i = 0; i < bone.numChildren(); i++) {

            updateBone(bone, bone.getChild(i));

        }

    }

    public Iterator<Bone> iterator() {
        if (this.iterator == null) {
            this.iterator = new SkeletonIterator(this);
        }
        return this.iterator;
    }

}

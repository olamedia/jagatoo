package org.jagatoo.loaders.models.collada.datastructs.animation;

import java.util.ArrayList;
import java.util.List;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;

/**
 * A Bone
 *
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class Bone {

    /** Children (optional) */
    private List<Bone> children;

    /** The name of this bone */
    String name;

    /** The bind rotation */
    private final Quaternion4f bindRotation;

    /** The length of this bone */
    private float length;

    /** The bind matrix */
    final Matrix4f bindMatrix;

    /** The inverse bind matrix */
    final Matrix4f invBindMatrix;

    /** The rotation of this bone */
    public Quaternion4f relativeRotation;

    /**
     * The scaling of this bone, along the three axis : X, Y, and Z.
     */
    public Point3f relativeScaling;

    /**
     * The absolute rotation of this bone, ie, a multiplication of all the
     * velocities from the rootBone to this bone, passing by its parents.
     *
     * Stupid question #1 : Why isn't it automatically updated when I
     * setRotation() ? Intelligent answer #1 : For efficiency. If you set the
     * rotation of several bones in a skeleton, you want it only to recompute
     * the transform matrix for each bone once per frame, not once per
     * setRotation()
     */
    final Quaternion4f absoluteRotation;

    /**
     * The absolute translation of this bone, ie, the position of this bone...
     *
     * Stupid question #1 bis : Why isn't it automatically updated when I
     * setRotation() ? Intelligent answer #1 bis : For efficiency. If you set
     * the rotation of several bones in a skeleton, you want it only to
     * recompute the transform matrix for each bone once per frame, not once per
     * setRotation()
     */
    final Point3f absoluteTranslation;

    /**
     * The absolute scaling of this bone, along the three axis : X, Y, and Z.
     */
    public Point3f absoluteScaling;


    /**
     * Temporal key frames for the bone. They are reference extracted from the
     * current animation and they will change every time you play a different
     * animation. They are here to simplify the animation algorithm.
     * These are ROTATION key frames.
     */
    public List<KeyFrameQuat4f> rotKeyFrames = new ArrayList<KeyFrameQuat4f>();
    /**
     * Temporal key frames for the bone. They are reference extracted from the
     * current animation and they will change every time you play a different
     * animation. They are here to simplify the animation algorithm.
     * These are SCALE key frames.
     */
    public List<KeyFramePoint3f> scaleKeyFrames = new ArrayList<KeyFramePoint3f>();


    /**
     * Create a new Bone
     *
     * @param name
     *                The name of this bone
     * @param length
     *                The length of this bone
     * @param bindRotation
     *                The bind rotation of this bone
     */
    public Bone(String name, float length, Quaternion4f bindRotation) {

        this.name = name;

        this.length = length;
        this.bindRotation = bindRotation;

        this.bindMatrix = new Matrix4f();
        this.bindMatrix.set(bindRotation);
        this.bindMatrix.m23( length ); // Set the Z translational component of
                                        // the matrix to length
        this.invBindMatrix = new Matrix4f();
        invBindMatrix.invert(bindMatrix);

        // Init relative rot/scaling
        this.relativeRotation = new Quaternion4f(0f, 0f, 0f, 1f);
        this.relativeScaling = new Point3f(1f, 1f, 1f);

        // Init absolute rot/trans/scaling
        this.absoluteRotation = new Quaternion4f(0f, 0f, 0f, 1f);
        this.absoluteTranslation = new Point3f(0f, 0f, 0f);
        this.absoluteScaling = new Point3f(1f, 1f, 1f);

    }

    /**
     * Add a child bone
     *
     * @param bone
     */
    public void addChild(Bone bone) {

        if (children == null) {
            children = new ArrayList<Bone>();
        }
        children.add(bone);

    }

    /**
     * Remove a child bone
     *
     * @param bone
     */
    public void remove(Bone bone) {

        if (children != null) {
            children.remove(bone);
        }

    }

    /**
     * @return the number of children of this bone
     */
    public int numChildren() {

        return (children == null) ? 0 : children.size();

    }

    /**
     * Get a bone per index.
     *
     * @param i
     *                The index of the bone you want to get.
     * @return The bone :)
     */
    public Bone getChild(int i) {

        return (children == null) ? null : children.get(i);

    }

    /**
     * @return the bindRotation
     */
    public Quaternion4f getBindRotation() {
        return bindRotation;
    }

    /**
     * Set the bindRotation
     *
     * @param quat
     */
    public void setBindRotation(Quaternion4f quat) {
        bindRotation.set(quat);
        bindMatrix.set(bindRotation);
    }

    /**
     * @return the length
     */
    public float getLength() {
        return length;
    }

    /**
     * Set the length
     *
     * @param length
     */
    public void setLength(float length) {
        this.length = length;
        bindMatrix.m23( length );
        invBindMatrix.invert(bindMatrix);
    }

    @Override
    public String toString() {

        return name + "] Bind rotation : " + bindRotation + ", length : "
                + length;

    }

    /**
     * @return the name of this bone
     */
    public String getName() {

        return name;

    }

    /**
     * Set the name of this bone
     *
     * @param name
     */
    public void setName(String name) {

        this.name = name;

    }

    /**
     * Get the absolute rotation of this bone. Absolute translation/rotation are
     * updated by the Skeleton.updateAbsolutes() method.
     *
     * @return a quaternion containing the absolute rotation
     */
    public Quaternion4f getAbsoluteRotation() {
        return absoluteRotation;
    }

    /**
     * Get the absolute translation of this bone. Absolute translation/rotation
     * are updated by the Skeleton.updateAbsolutes() method.
     *
     * @return a point3f containing the absolute translation
     */
    public Point3f getAbsoluteTranslation() {
        return absoluteTranslation;
    }


    /**
     * @return true if the bone has at least one key frame of any kind
     */
    public boolean hasKeyFrames() {
    	int total = 0;
    	if(rotKeyFrames != null)
    		total += rotKeyFrames.size();
    	if(scaleKeyFrames != null)
            total += scaleKeyFrames.size();
        if(total > 0) return true;
        return false;
    }

    /**
     * Completes the relativeTranslation and relativeRotation all with 0.
     */
    public void setNoRelativeMovement() {
        this.relativeRotation.set(0f, 0f, 0f, 1f);
        this.relativeScaling.set(0f, 0f, 0f);
    }

    /**
     * Selects the current translation key frame, based on the current time
     *
     * @param currentTime
     *                beetween 0 and the end of the animation, in miliseconds
     * @return frame index selected
     */
    public int selectCurrentRotFrame(long currentTime) {
        return KeyFrame.searchNextFrame(rotKeyFrames, currentTime);
    }

    /**
     * Selects the current scaling key frame, based on the current time
     *
     * @param currentTime
     *                beetween 0 and the end of the animation, in miliseconds
     * @return frame index selected
     */
    public int selectCurrentScaleFrame(long currentTime) {
        return KeyFrame.searchNextFrame(scaleKeyFrames, currentTime);
    }

}

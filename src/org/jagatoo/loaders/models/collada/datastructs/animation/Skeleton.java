package org.jagatoo.loaders.models.collada.datastructs.animation;

import org.openmali.vecmath.Matrix3f;
import org.openmali.vecmath.Point3f;

/**
 * A Skeleton.
 * It contains a root bone, which can have several children
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Skeleton {
    
    /** The root bone : there's only one (for simple implementation) */
    public final Bone rootBone;
    
    // GC-friendly hacks
    private final static Matrix3f tempMatrix = new Matrix3f();
    private final static Point3f tempPoint = new Point3f();
    
    /** The position of our Skeleton */
    private final Point3f skeletonPos;
    
    /**
     * Create a new Skeleton
     * @param rootBone The root bone
     * @param skeletonPos TODO
     * @param pos The position of the Skeleton
     * @param rotation The rotation of the Skeleton
     * @param scale The scale of the Skeleton
     */
    public Skeleton(Bone rootBone, Point3f skeletonPos) {
        
        this.rootBone = rootBone;
        this.skeletonPos = skeletonPos;
        
    }
    
    /**
     * @return the rootBone
     */
    public Bone getRootBone() {
        
        return rootBone;
        
    }
    
    /**
     * Update the skeleton.
     * It recomputes the transform matrix of each bone,
     * starting with the root bone and then each child
     * recursively.
     */
    public void updateAbsolutes() {
        
        updateBone(null, rootBone);
        
    }
    
    /**
     * Update a bone, e.g. compute its absolute rotation/translation
     * from its relative ones.
     * @param parentTrans
     * @param bone
     */
    private void updateBone(Bone parentBone, Bone bone) {
        
        if(parentBone == null) {
            
            /*
             * Root bone
             */
            
            bone.absoluteRotation.set(bone.getBindRotation());
            bone.absoluteRotation.mul(bone.relativeRotation);
            
            bone.absoluteTranslation.set(skeletonPos);
            bone.absoluteTranslation.z += bone.getLength();
            
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
            
        }
        
        for(int i = 0; i < bone.numChildren(); i++) {
            
            updateBone(bone, bone.getChild(i));
            
        }
        
    }
    
}

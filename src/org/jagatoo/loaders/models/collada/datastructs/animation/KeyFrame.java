package org.jagatoo.loaders.models.collada.datastructs.animation;

import org.jagatoo.loaders.models.collada.Rotations;
import org.openmali.vecmath2.Point3f;

/**
 * A KeyFrame contains information for the animation of a Bone. It can contain
 * translation, rotation or scale information.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public abstract class KeyFrame {
    
    /**
     * An Axis.
     * 
     * @author Amos Wenger (aka BlueSky)
     */
    public static enum Axis {
        /** X Axis : (1, 0, 0) */
        X,
        /** Y Axis : (0, 1, 0) */
        Y,
        /** Z Axis : (0, 0, 1) */
        Z
    }
    
    /**
     * Key frame time
     */
    public long time;
    
    /**
     * Creates a translation key frame
     * 
     * @param bone
     * @param time
     *                frame time
     * @param values
     *                float values of the translation
     * @param valueIndex
     *                first value index
     * @return a new key frame
     */
    public static KeyFrame buildPoint3fKeyFrame(float time, float[] values, int valueIndex) {
        
        KeyFramePoint3f frame = new KeyFramePoint3f();
        frame.time = (long) (time * 1000);
        
        frame.value = new Point3f(
                values[valueIndex],
                values[valueIndex + 1],
                values[valueIndex + 2]
        );
        
        return frame;
        
    }
    
    
    /**
     * Creates a rotation key frame
     * 
     * @param time
     *                frame time
     * @param angle
     *                rotation angle in degrees
     * @param axis
     *                the axis of the rotation
     * @return a new key frame
     */
    public static KeyFrame buildQuaternion4fKeyFrame(float time, float angle, Axis axis) {
        
        KeyFrameQuat4f frame = new KeyFrameQuat4f();
        frame.time = (long) (time * 1000);
        float radians = (float) Math.toRadians(angle);
        
        Point3f euler = new Point3f(0f, 0f, 0f);
        
        switch (axis) {
        
        case X:
            euler = new Point3f(radians, 0f, 0f);
            break;
            
        case Y:
            euler = new Point3f(0f, radians, 0f);
            break;
            
        case Z:
            euler = new Point3f(0f, 0f, radians);
            break;
            
        }
        
        frame.value = Rotations.toQuaternion(euler);
        
        return frame;
    }
    
    /**
     * Search the next key frame according to the current time
     * 
     * @param frames
     * @param currentTime
     *                in milliseconds
     * @return selected key frame index
     */
    public static int searchNextFrame(KeyFrame[] frames, long currentTime) {
        
        int frame = 0;
        
        while (frame < frames.length && frames[frame].time < currentTime) {
            frame++;
        }
        
        return frame;
        
    }
    
}

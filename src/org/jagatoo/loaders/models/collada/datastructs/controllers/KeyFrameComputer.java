package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFramePoint3f;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameQuat4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.util.Interpolation;

/**
 * This class is used to interpolate Tuple3f from
 * keyframe data.
 * 
 * @author Matias Leone (aka Maguila)
 * @author Amos Wenger (aka BlueSky)
 */
public class KeyFrameComputer {
    
    /**
     * Interpolate a Tuple3f between two keyframes
     * @param currentTime The time in the animation
     * @param keyFrames The keyframes of the animation (translation or scaling)
     * @param toInterpolate
     */
    public static void computeTuple3f(long currentTime, List<KeyFramePoint3f> keyFrames, Point3f toInterpolate) {
        int frame = 0;
        if(keyFrames == null || frame >= keyFrames.size()) return;
        frame = KeyFrame.searchNextFrame(keyFrames, currentTime);
        
        if (frame == 0) {
            
            /*
             * Case 1 : we're before the first keyframe
             * Solution : take the first keyframe
             */
            toInterpolate.set(keyFrames.get(frame).value);
            
        } else if (frame == keyFrames.size()) {
            
            /*
             * Case 2 : we're after the last keyframe
             * Solution : take the last keyframe
             */
            toInterpolate.set(keyFrames.get(frame-1).value);
            
        } else {
            
            /*
             * Case 3 : we're between two keyframes
             * Solution : interpolate
             */
        	System.out.println("Interpolating...");
            KeyFramePoint3f prevFrame = keyFrames.get(frame - 1);
            KeyFramePoint3f nextFrame = keyFrames.get(frame);
            
            // time distance beetween both frames
            long timeDist = nextFrame.time - prevFrame.time;
            // compute the "delta" = value in the [0-1] range that
            // represents our "position" between the two frames.
            float delta = (currentTime - prevFrame.time) / timeDist;
            
            // Finally, interpolate
            Interpolation.interpolate(toInterpolate, prevFrame.value, nextFrame.value, delta);
            
        }
        
    }
    

    /**
     * Interpolate a Quaternion4f between two keyframes
     * @param currentTime The time in the animation
     * @param keyFrames The keyframes of the animation (translation or scaling)
     * @param toInterpolate
     */
    public static void computeQuaternion4f(long currentTime, List<KeyFrameQuat4f> keyFrames, Quaternion4f toInterpolate) {
        
        int frame = KeyFrame.searchNextFrame(keyFrames, currentTime);
        System.out.println("Frame = " + frame);
        if (frame == 0) {
            
            /*
             * Case 1 : we're before the first keyframe
             * Solution : take the first keyframe
             */
            toInterpolate.set(keyFrames.get(frame).value);
            
        } else if (frame == keyFrames.size()) {
            
            /*
             * Case 2 : we're after the last keyframe
             * Solution : take the last keyframe
             */
            toInterpolate.set(keyFrames.get(frame - 1).value);
            
        } else {
            
            /*
             * Case 3 : we're between two keyframes
             * Solution : interpolate
             */
            KeyFrameQuat4f prevFrame = keyFrames.get(frame - 1);
            KeyFrameQuat4f nextFrame = keyFrames.get(frame);
            
            // time distance beetween both frames
            long timeDist = nextFrame.time - prevFrame.time;
            // compute the "delta" = value in the [0-1] range that
            // represents our "position" between the two frames.
            float delta = (currentTime - prevFrame.time) / timeDist;
            
            // Finally, interpolate
            Interpolation.nlerp(toInterpolate, prevFrame.value, nextFrame.value, delta);
            
        }
        
    }
    
}

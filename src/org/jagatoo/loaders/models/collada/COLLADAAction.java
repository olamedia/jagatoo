/**
 * Copyright (c) 2007-2008, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFramePoint3f;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameQuat4f;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;

/**
 * A COLLADA "Action", or "Animation" or "Animation clip" or "Animation strip",
 * whatever you call it : it's a "piece of movement" you can play on your model.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class COLLADAAction {
    
    /**
     * The identifier of this animation. It should be unique in the same model,
     * ex. you cannot have two animations that have the same ID for the same
     * model
     */
    private final String id;
    
    /**
     * Translation key frames : if any, define the movement of the
     * Skeleton itself (e.g. moves the whole model)
     */
    public final List<KeyFramePoint3f> transKeyFrames = new ArrayList<KeyFramePoint3f> ();
    /**
     * Rotation key frames, per bone.
     */
    public final Map<Bone, List<KeyFrameQuat4f>> rotKeyFrames = new HashMap<Bone, List<KeyFrameQuat4f>>();
    /**
     * Scale key frames, per bone.
     */
    public final Map<Bone, List<KeyFramePoint3f>> scaleKeyFrames = new HashMap<Bone, List<KeyFramePoint3f>>();
    
    /**
     * Skeleton for this action
     */
    private Skeleton skeleton;
    
    
    /**
     * Creates a new COLLADAAction.
     * 
     * @param id the id of this COLLADA Action
     */
    public COLLADAAction(String id) {
        
        this.id = id;
        
    }
        
    /**
     * @return the ID String of this COLLADA Action.
     */
    public String getId() {
    
        return id;
    
    }
    
    /**
     * @return the skeleton
     */
    public Skeleton getSkeleton() {
        return skeleton;
    }
    
    /**
     * @param skeleton
     *                the skeleton to set
     */
    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }
    
    
    /**
     * Loop through each bone of the skeleton and complete their temp key frames
     * arrays
     */
    public void prepareBones() {
        skeleton.resetIterator();
        for (Iterator<Bone> iterator = this.skeleton.iterator(); iterator.hasNext();) {
        	Bone bone = iterator.next();
        	System.out.println(rotKeyFrames.get(bone));
            bone.rotKeyFrames = rotKeyFrames.get(bone);
            bone.scaleKeyFrames = scaleKeyFrames.get(bone);
            System.out.println(bone.rotKeyFrames.equals(rotKeyFrames.get(bone)));
        }
    }
    
}

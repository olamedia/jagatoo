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
package org.jagatoo.loaders.models._util;

import org.jagatoo.datatypes.NamedObject;
import org.openmali.vecmath2.AxisAngle3f;
import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface AnimationFactory
{
    public static enum AnimationType
    {
        MESH_DEFORMATION_KEY_FRAMES,
        TRANSFORM_KEY_FRAMES,
        SKELETAL,
        WEIGHTED_SKELETAL,
    }
    
    public Object createMeshDeformationKeyFrame( float[] coords, float[] normals, Matrix4f[] mountTransforms );
    
    public Object createTransformKeyFrame( float time, Vector3f translation, Quaternion4f rotation, Tuple3f scale );
    
    public Object createTransformKeyFrame( float time, Vector3f translation, AxisAngle3f rotation, Tuple3f scale );
    
    public Object createTransformKeyFrame( float time, Vector3f translation, Matrix3f rotation, Tuple3f scale );
    
    public Object createTransformKeyFrame( float time, Matrix4f transform );
    
    public void transformTransformKeyFrame( Matrix4f transform, Object frame );
    
    public void transformTransformKeyFrames( Matrix4f transform, Object[] frames );
    
    public Object createAnimationController( AnimationType animType, Object[] keyFrames, NamedObject target );
}

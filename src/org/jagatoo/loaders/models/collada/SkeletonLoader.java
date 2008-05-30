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

import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.jibx.XMLNode;
import org.jagatoo.logging.JAGTLog;
import org.openmali.FastMath;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;

/**
 * A loader for skeletons.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class SkeletonLoader
{
    /**
     * The up point (0, 0, 1).
     * Note : depends on the COLLADA assets : this should be taken into account!
     */
    private final static Vector3f UP = Vector3f.newReadOnly( 0f, 0f, 1f );
    
    /**
     * Loads a Joint, and its children of course.
     * 
     * @param localToWorld
     * @param node
     * @param parentRoot
     * @param parentTip
     * @param bone
     */
    private static void loadJoint( Matrix4f localToWorld, XMLNode node, Point3f parentRoot, Point3f parentTip, Bone bone )
    {
        if ( node.childrenList == null )
        {
            JAGTLog.debug( "=====================================" );
            JAGTLog.debug( "[[Bone]] " + bone.getName() );
        }
        else
        {
            /*
             * Get the node tip
             */
            Matrix4f colMatrix = node.childrenList.get( 0 ).matrix.matrix4f;
            JAGTLog.debug( "ColMatrix = ", colMatrix );
            Point3f nodeTip = new Point3f(
                    parentTip.getX() + colMatrix.get( 0, 3 ),
                    parentTip.getY() + colMatrix.get( 1, 3 ),
                    parentTip.getZ() + colMatrix.get( 2, 3 )
            );
            
            /*
             * Transform all into world coordinates
             */
            Point3f parentRootW = new Point3f( parentRoot );
            localToWorld.transform( parentRootW );
            
            Point3f parentTipW = new Point3f( parentTip );
            localToWorld.transform( parentTipW );
            
            Point3f nodeTipW = new Point3f( nodeTip );
            localToWorld.transform( nodeTipW );
            
            /*
             * Compute the vectors
             */
            Vector3f nodeVecW = new Vector3f();
            nodeVecW.sub( nodeTipW, parentTipW );
            Vector3f parentVecW = new Vector3f();
            parentVecW.sub( parentTipW, parentRootW );
            
            JAGTLog.debug( "=====================================" );
            JAGTLog.debug( "[[Bone]] ", bone.getName() );
            JAGTLog.debug( "parentRoot = ", parentRoot );
            JAGTLog.debug( "parentTip = ", parentTip );
            JAGTLog.debug( "nodeTip = ", nodeTip );
            JAGTLog.debug( "---" );
            JAGTLog.debug( "parentRootW = ", parentRootW );
            JAGTLog.debug( "parentTipW = ", parentTipW );
            JAGTLog.debug( "nodeTipW = ", nodeTipW );
            JAGTLog.debug( "---" );
            JAGTLog.debug( "parentVecW = ", parentVecW );
            JAGTLog.debug( "nodeVecW = ", nodeVecW );
            
            // Retrieve length now, we'll normalize just after
            float length = nodeVecW.length();
            
            // Normalize both vecs for calculations
            parentVecW.normalize();
            nodeVecW.normalize();
            
            // Compute the angle
            float angle = FastMath.acos( parentVecW.dot( nodeVecW ) );
            if ( Float.isNaN( angle ) )
            {
                // Singularity : if a vector is the 0-vector, the angle will be NaN so set it to 0.
                angle = 0f;
            }
            
            Vector3f axis = new Vector3f();
            axis.cross( parentVecW, nodeVecW );
            if ( Float.isNaN( axis.getX() ) | Float.isNaN( axis.getY() ) | Float.isNaN( axis.getZ() ) )
            {
                // Singularity : Angle = 0. The axis found is (NaN, NaN, NaN)
                // In this case we reset it to UP.
                axis.set( UP );
            }
            
            if ( axis.lengthSquared() == 0f )
            {
                /*
                 * Singularity : Angle = 180, there is no single axis.
                 * In this case, we take an axis which is perpendicular
                 * to one of the two vectors. This avoid NaNs.
                 */
                axis.set( parentVecW.getZ(), parentVecW.getX(), parentVecW.getY() );
                // For quaternion conversion
                axis.normalize();
                // Workaround
                axis.negate();
            }
            else
            {
                // For quaternion conversion
                axis.normalize();
                axis.negate();
            }
            
            
            Quaternion4f quat = Rotations.toQuaternion( axis, angle );
            bone.setBindRotation( quat );
            bone.setLength( length );
            
            JAGTLog.debug( "---" );
            JAGTLog.debug( "angle = ", FastMath.toDeg( angle ) );
            JAGTLog.debug( "axis = ", axis );
            JAGTLog.debug( "length = ", length );
            JAGTLog.debug( "quat = ", quat );
            
            for ( XMLNode child : node.childrenList )
            {
                Bone newBone = new Bone( child.id, 0.2f, new Quaternion4f( 0f, 0f, 0f, 1f ) );
                bone.addChild( newBone );
                loadJoint( localToWorld, child, parentTip, nodeTip, newBone );
            }
        }
    }
    
    /**
     * Loads a whole Skeleton.
     * 
     * @param rootNode
     * 
     * @return the skeleton
     */
    public static Skeleton loadSkeleton( XMLNode rootNode )
    {
        Point3f skeletonPos;
        Matrix4f localToWorld;
        // Get the localToWorld matrix
        localToWorld = rootNode.matrix.matrix4f;
        JAGTLog.debug( "LocalToWorld = ", localToWorld );
        
        // Get the root bone root position
        skeletonPos = new Point3f( localToWorld.m03(), localToWorld.m13(), localToWorld.m23() );
        
        // Create the root bone
        Bone rootBone = new Bone( rootNode.id, 0f, new Quaternion4f( 0f, 0f, 0f, 1f ) );
        
        loadJoint( localToWorld, rootNode, Point3f.ZERO, Point3f.ZERO, rootBone );
        
        return( new Skeleton( rootBone, skeletonPos ) );
    }
}

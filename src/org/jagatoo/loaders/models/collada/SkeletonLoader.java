package org.jagatoo.loaders.models.collada;

import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.jibx.XMLNode;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;

/**
 * A loader for skeletons.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class SkeletonLoader {
    
    /** The zero-point (0, 0, 0) */
    final static Point3f ZERO_POINT = new Point3f(0f, 0f, 0f);
    
    /** The up point (0, 0, 1) Note : depends on the COLLADA assets : this should be taken into account */
    final static Vector3f UP = new Vector3f(0f, 0f, 1f);
    
    /**
     * Loads a whole Skeleton.
     * @param rootNode
     * @return the skeleton
     */
    public static Skeleton loadSkeleton(XMLNode rootNode) {
        
        Point3f skeletonPos;
        Matrix4f localToWorld;
        // Get the localToWorld matrix
        localToWorld = rootNode.matrix.matrix4f;
        System.out.println("LocalToWorld = "+localToWorld);
        
        // Get the root bone root position
        skeletonPos = new Point3f(localToWorld.m03(), localToWorld.m13(), localToWorld.m23());
        
        // Create the root bone
        Bone rootBone = new Bone(rootNode.id, 0f, new Quaternion4f(0f, 0f, 0f, 1f));
        
        loadJoint(localToWorld, rootBone, rootNode, ZERO_POINT, ZERO_POINT);
        
        return new Skeleton(rootBone, skeletonPos);
        
    }
    
    /**
     * Loads a Joint, and its children of course.
     * @param localToWorld
     * @param bone
     * @param node
     * @param parentRoot
     * @param parentTip
     */
    private static void loadJoint(Matrix4f localToWorld, Bone bone, XMLNode node, Point3f parentRoot, Point3f parentTip) {
        
        if(node.childrenList == null) {
            
            System.out.println("=====================================");
            System.out.println("[[Bone]] "+bone.getName());
            
        } else {
            
            /*
             * Get the node tip
             */
            Matrix4f colMatrix = node.childrenList.get(0).matrix.matrix4f;
            System.out.println("ColMatrix = "+colMatrix);
            Point3f nodeTip = new Point3f(
                    parentTip.getX() + colMatrix.get(0, 3),
                    parentTip.getY() + colMatrix.get(1, 3),
                    parentTip.getZ() + colMatrix.get(2, 3)
            );
            
            /*
             * Transform all into world coordinates
             */
            Point3f parentRootW = new Point3f(parentRoot);
            localToWorld.transform(parentRootW);
            
            Point3f parentTipW = new Point3f(parentTip);
            localToWorld.transform(parentTipW);
            
            Point3f nodeTipW = new Point3f(nodeTip);
            localToWorld.transform(nodeTipW);
            
            /*
             * Compute the vectors
             */
            Vector3f nodeVecW = new Vector3f();
            nodeVecW.sub(nodeTipW, parentTipW);
            Vector3f parentVecW = new Vector3f();
            parentVecW.sub(parentTipW, parentRootW);
            
            System.out.println("=====================================");
            System.out.println("[[Bone]] "+bone.getName());
            System.out.println("parentRoot = "+parentRoot);
            System.out.println("parentTip = "+parentTip);
            System.out.println("nodeTip = "+nodeTip);
            System.out.println("---");
            System.out.println("parentRootW = "+parentRootW);
            System.out.println("parentTipW = "+parentTipW);
            System.out.println("nodeTipW = "+nodeTipW);
            System.out.println("---");
            System.out.println("parentVecW = "+parentVecW);
            System.out.println("nodeVecW = "+nodeVecW);
            
            // Retrieve length now, we'll normalize just after
            float length = nodeVecW.length();
            
            // Normalize both vecs for calculations
            parentVecW.normalize();
            nodeVecW.normalize();
            
            // Compute the angle
            double angle = Math.acos(parentVecW.dot(nodeVecW));
            if(Double.isNaN(angle)) {
                // Singularity : if a vector is the 0-vector, the angle will be NaN
                // so set it to 0
                angle = 0;
            }
            Vector3f axis = new Vector3f();
            axis.cross(parentVecW, nodeVecW);
            if(Float.isNaN(axis.getX()) | Float.isNaN(axis.getY()) | Float.isNaN(axis.getZ())) {
                // Singularity : Angle = 0. The axis found is (NaN,NaN,NaN)
                // In this case we reset it to UP.
                axis.set(UP);
            }
            if(axis.lengthSquared() == 0) {
                // Singularity : Angle = 180, there is no single axis
                // In this case, we take an axis which is perpendicular
                // to one of the two vectors. This avoid NaNs
                axis.set(parentVecW.getZ(), parentVecW.getX(), parentVecW.getY());
                // For quaternion conversion
                axis.normalize();
                // Workaround
                axis.negate();
            } else {
                // For quaternion conversion
                axis.normalize();
                axis.negate();
            }
            
            
            Quaternion4f quat = Rotations.toQuaternion(axis, angle);
            bone.setBindRotation(quat);
            bone.setLength(length);
            
            System.out.println("---");
            System.out.println("angle = "+Math.toDegrees(angle));
            System.out.println("axis = "+axis);
            System.out.println("length = "+length);
            System.out.println("quat = "+quat);
            
            for (XMLNode child : node.childrenList) {
                Bone newBone = new Bone(child.id, .2f, new Quaternion4f(0f, 0f, 0f, 1f));
                bone.addChild(newBone);
                loadJoint(localToWorld, newBone, child, parentTip, nodeTip);
            }
            
        }
        
    }
    
}

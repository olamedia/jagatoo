package org.jagatoo.loaders.models.collada;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.jagatoo.util.math.MatrixUtils;
import org.openmali.vecmath.Matrix3f;
import org.openmali.vecmath.Quat4f;
import org.openmali.vecmath.Vector3f;

/**
 * Rotations utility functions.
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Martin Baker (code borrowed from euclideanspace.com)
 */
public class Rotations {
    
    private Rotations() {}
    
    /**
     * Convert Euler angles (in degrees) to a (normalized) Quaternion.
     * @param rotX Rotation about the X axis, in degrees
     * @param rotY Rotation about the Y axis, in degrees
     * @param rotZ Rotation about the Z axis, in degrees
     * @return The Quaternion representing the same rotation.
     * @author Martin Baker (euclideanspace.com)
     */
    public static Quat4f toQuaternion(float rotX, float rotY, float rotZ) {
        
        float x = (float) Math.toRadians(rotX);
        float y = (float) Math.toRadians(rotY);
        float z = (float) Math.toRadians(rotZ);
        Matrix3f matrix = MatrixUtils.eulerToMatrix3f(x, y, z);
        Quat4f quat = new Quat4f();
        quat.set(matrix);
        quat.normalize();
        return quat;
        
    }
    
    /**
     * Convert a Quaternion to Euler angles (in degrees)
     * Note : the Quaternion can be non-normalized.
     * @param quaternion The quaternion
     * @return The euler angles (in degrees)
     * @author Martin Baker (euclideanspace.com)
     */
    public static Vector3f toEuler(Quat4f quaternion) {
        
        Vector3f euler = new Vector3f();
        Matrix3f matrix = new Matrix3f();
        matrix.set(quaternion);
        euler = MatrixUtils.matrixToEuler(matrix);
        euler.x = (float) (Math.toDegrees(euler.x));
        euler.y = (float) (Math.toDegrees(euler.y));
        euler.z = (float) (Math.toDegrees(euler.z));
        return euler;
        
    }
    
    /**
     * Test the conversions
     * @param argv
     */
    public static void main(String argv[]) {
        
        StringTokenizer tknz = new StringTokenizer(JOptionPane.showInputDialog("Enter a rotation of the form X, Y, Z"), ",");
        Vector3f vec = new Vector3f(Float.parseFloat(tknz.nextToken()), Float.parseFloat(tknz.nextToken()), Float.parseFloat(tknz.nextToken()));
        Quat4f quat = toQuaternion(vec.x, vec.y, vec.z);
        quat.normalize();
        Vector3f vec2 = toEuler(quat);
        JOptionPane.showMessageDialog(null, "Original vec : "+vec+"\n Converted quaternion : "+quat+"\n Converted again vec : "+vec2);
        
    }
    
    /**
     * Convert an AxisAngle representation to a Quaternion representation.
     * Credits goes to euclideanspace.com author.
     * @param axis The axis. Must be unit-length
     * @param angle The angle, in degrees
     * @return
     */
    public static Quat4f toQuaternion(Vector3f axis, double angle) {
        
        double sinHalfHangle = Math.sin(angle / 2d);
        double cosHalfHangle = Math.cos(angle / 2d);
        Quat4f quat = new Quat4f(
          (float) (axis.x * sinHalfHangle),
          (float) (axis.y * sinHalfHangle),
          (float) (axis.z * sinHalfHangle),
          (float) (cosHalfHangle)
        );
        
        return quat;
        
    }
    
}

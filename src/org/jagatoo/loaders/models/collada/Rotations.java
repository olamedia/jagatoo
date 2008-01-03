package org.jagatoo.loaders.models.collada;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.openmali.FastMath;
import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import org.openmali.vecmath2.util.MatrixUtils;

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
     */
    public static Quaternion4f toQuaternion(float rotX, float rotY, float rotZ) {
        return toQuaternion( new Quaternion4f(), rotX, rotY, rotZ );
        
    }
    
    /**
     * Convert Euler angles (in degrees) to a (normalized) Quaternion.
     * @param tup rotation values
     * @return The Quaternion representing the same rotation.
     */
    public static Quaternion4f toQuaternion(Tuple3f tup) {
        return toQuaternion( new Quaternion4f(), tup.getX(), tup.getY(), tup.getZ() );
        
    }
    
    
    /**
     * Convert Euler angles (in degrees) to a (normalized) Quaternion.
     * @param quat destiny quaternion
     * @param rotX Rotation about the X axis, in degrees
     * @param rotY Rotation about the Y axis, in degrees
     * @param rotZ Rotation about the Z axis, in degrees
     * @return the given quaternion with complete with the values
     */
    public static Quaternion4f toQuaternion( Quaternion4f quat, float rotX, float rotY, float rotZ ) {
    	float x = FastMath.toRad(rotX);
        float y = FastMath.toRad(rotY);
        float z = FastMath.toRad(rotZ);
        Matrix3f matrix = MatrixUtils.eulerToMatrix3f(x, y, z);
        quat.set(matrix);
        quat.normalize();
        return quat;
    }
    
    /**
     * Convert Euler angles (in degrees) to a (normalized) Quaternion.
     * @param quat destiny quaternion
     * @param tup rotation values
     * @return the given quaternion with complete with the values
     */
    public static Quaternion4f toQuaternion( Quaternion4f quat, Tuple3f tup ) {
    	return toQuaternion( quat, tup.getX(), tup.getY(), tup.getZ() );
    }
    
    /**
     * Convert a Quaternion to Euler angles (in degrees)
     * Note : the Quaternion can be non-normalized.
     * @param quaternion The quaternion
     * @return The euler angles (in degrees)
     */
    public static Tuple3f toEuler(Quaternion4f quaternion) {
        
        Matrix3f matrix = new Matrix3f();
        matrix.set( quaternion );
        
        Tuple3f euler = MatrixUtils.matrixToEuler( matrix );
        euler.setX( FastMath.toDeg( euler.getX() ) );
        euler.setY( FastMath.toDeg( euler.getY() ) );
        euler.setZ( FastMath.toDeg( euler.getZ() ) );
        
        return euler;
        
    }
    
    /**
     * Test the conversions
     * @param argv
     */
    public static void main(String argv[]) {
        
        StringTokenizer tknz = new StringTokenizer(JOptionPane.showInputDialog("Enter a rotation of the form X, Y, Z"), ",");
        Vector3f vec = new Vector3f(Float.parseFloat(tknz.nextToken()), Float.parseFloat(tknz.nextToken()), Float.parseFloat(tknz.nextToken()));
        Quaternion4f quat = toQuaternion(vec.getX(), vec.getY(), vec.getZ());
        quat.normalize();
        Tuple3f vec2 = toEuler(quat);
        JOptionPane.showMessageDialog(null, "Original vec : "+vec+"\n Converted quaternion : "+quat+"\n Converted again vec : "+vec2);
        
    }
    
    /**
     * Convert an AxisAngle representation to a Quaternion representation.
     * Credits goes to euclideanspace.com author.
     * @param axis The axis. Must be unit-length
     * @param angle The angle, in degrees
     * @return the rotation quaternion
     */
    public static Quaternion4f toQuaternion(Vector3f axis, double angle) {
        
        double sinHalfHangle = Math.sin(angle / 2d);
        double cosHalfHangle = Math.cos(angle / 2d);
        Quaternion4f quat = new Quaternion4f(
          (float) (axis.getX() * sinHalfHangle),
          (float) (axis.getY() * sinHalfHangle),
          (float) (axis.getZ() * sinHalfHangle),
          (float) (cosHalfHangle)
        );
        
        return quat;
        
    }
    
}

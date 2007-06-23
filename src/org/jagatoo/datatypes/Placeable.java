package org.jagatoo.datatypes;

import org.openmali.vecmath.Matrix3f;
import org.openmali.vecmath.Tuple3f;

/**
 * Placeable object : you can get/set Position and Rotation
 *
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public interface Placeable {

    /**
     * @return The position of this Placeable object
     */
    public Tuple3f getPosition();

    /**
     * Write the position of this Placeable object to the
     * given Tuple3f
     * @param pos The Tuple3f to put the pos into
     */
    public void getPosition(Tuple3f pos);

    /**
     * Set the position of this Placeable object
     * @param pos
     */
    public void setPosition(Tuple3f pos);

    /**
     * Set the position of this Placeable object.
     */
    public void setPosition(float posX, float posY, float posZ);

    /**
     * @return The rotation, in Euler angles (degrees) of
     * this Placeable object
     */
    public Tuple3f getRotation();

    /**
     * Write the rotation, in Euler angles (degrees) of
     * this Placeable object in the given Tuple3f
     */
    public void getRotation(Tuple3f rot);

    /**
     * @return The rotation, as a 3x3 rotation Matrix,
     * of this Placeable object
     */
    public Matrix3f getRotationMatrix();

    /**
     * Write the rotation, as a 3x3 rotation Matrix,
     * of this Placeable object in the given Matrix3f
     */
    public void getRotationMatrix(Matrix3f rot);

    /**
     * Set the rotation of this object, in Euler angles
     * (degrees)
     * @param rot The rotation, in Euler angles (degrees) of
     * this Placeable object
     */
    public void setRotation(Tuple3f rot);

    /**
     * Set the rotation Matrix of this object
     * @param rot The rotation, as a 3x3 rotation Matrix,
     * of this Placeable object
     */
    public void setRotationMatrix(Matrix3f rot);

    /**
     * Set the rotation parameter
     * @param rotX The x-rotation of the Collideable
     * @param rotY The y-rotation of the Collideable
     * @param rotZ The z-rotation of the Collideable
     */
    public void setRotation(float rotX, float rotY, float rotZ);

}

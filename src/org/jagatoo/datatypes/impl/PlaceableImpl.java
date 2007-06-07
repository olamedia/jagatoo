/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.datatypes.impl;

import org.jagatoo.datatypes.Placeable;
import org.jagatoo.util.math.MatrixUtils;
import org.openmali.vecmath.Matrix3f;
import org.openmali.vecmath.Point3f;
import org.openmali.vecmath.Tuple3f;

/**
 * A small, GC-friendly implementation of the Placeable
 * interface. Extend it when you need Placeable without
 * much hassle and you don't already have a superclass.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class PlaceableImpl implements Placeable {
    
    protected Point3f position;
    protected Matrix3f rotation;
    
    // GC-friendliness
    private static Tuple3f tmpTuple3f = new Point3f();
    
    /**
     * Creation a new PlaceableImpl with pos (0,0,0)
     * and no rotation.
     */
    public PlaceableImpl() {
        
        this.position = new Point3f();
        this.rotation = new Matrix3f();
        this.rotation.setIdentity();
        
    }
    
    public Tuple3f getPosition() {

        return this.position;
        
    }
    
    public void getPosition(Tuple3f pos) {
        
        pos.set(this.position);
        
    }
    
    public Tuple3f getRotation() {
        
        MatrixUtils.matrixToEuler(this.rotation, tmpTuple3f);
        return tmpTuple3f;
        
    }
    
    public void getRotation(Tuple3f rot) {

        MatrixUtils.matrixToEuler(this.rotation, rot);
        
    }
    
    public Matrix3f getRotationMatrix() {
        
        return this.rotation;
        
    }
    
    public void getRotationMatrix(Matrix3f rot) {

        rot.set(this.rotation);
        
    }
    
    public void setPosition(Tuple3f pos) {

        this.position.set(pos);
        onPositionChanged();
        
    }
    
    public void setPosition(float posX, float posY, float posZ) {

        this.position.set(posX, posY, posZ);
        onPositionChanged();
        
    }
    
    public void setRotation(Tuple3f rot) {
        
        MatrixUtils.eulerToMatrix3f(rot, this.rotation);
        onRotationChanged();
        
    }
    
    public void setRotation(float rotX, float rotY, float rotZ) {

        MatrixUtils.eulerToMatrix3f(rotX, rotY, rotZ, this.rotation);
        onRotationChanged();
        
    }
    
    public void setRotationMatrix(Matrix3f rot) {

        this.rotation.set(rot);
        onRotationChanged();
        
    }
 
    /*
     * Additionnal methods (e.g. for those wanting to connect
     * Interpolators to only one coordinate
     */
    
    public float getPositionX() {return this.position.x;}
    public float getPositionY() {return this.position.y;}
    public float getPositionZ() {return this.position.y;}
    
    public float getRotationX() {return getRotation().x;}
    public float getRotationY() {return getRotation().y;}
    public float getRotationZ() {return getRotation().z;}
    
    public void setPositionX(float v) {this.position.x = v; onPositionChanged();}
    public void setPositionY(float v) {this.position.y = v; onPositionChanged();}
    public void setPositionZ(float v) {this.position.z = v; onPositionChanged();}
    
    public void setRotationX(float v) {Tuple3f rot = getRotation(); MatrixUtils.eulerToMatrix3f(v, rot.y, rot.z, this.rotation);}
    public void setRotationY(float v) {Tuple3f rot = getRotation(); MatrixUtils.eulerToMatrix3f(rot.x, v, rot.z, this.rotation);}
    public void setRotationZ(float v) {Tuple3f rot = getRotation(); MatrixUtils.eulerToMatrix3f(rot.x, rot.y, v, this.rotation);}
    
    public abstract void onPositionChanged();
    public abstract void onRotationChanged();
    
}

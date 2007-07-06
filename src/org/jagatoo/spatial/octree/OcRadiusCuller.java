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
package org.jagatoo.spatial.octree;

import org.openmali.FastMath;
import org.openmali.vecmath.Tuple3f;

import org.jagatoo.spatial.Visibility;
import org.jagatoo.spatial.bodies.Sphere;


/**
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class OcRadiusCuller implements OcCuller
{
    private Sphere s;
    private Sphere cellSphere;
    
    public OcRadiusCuller( Tuple3f center, float radius )
    {
        s = new Sphere( center, radius );
        cellSphere = new Sphere();
    }
    
    /**
     * Checks the cell and returns whether it should culled or not.  In general this
     * is a box test performed against the cell.
     * 
     * @param cell
     * 
     * @return the Visibility value
     */
    public Visibility checkCell( OcCell cell )
    {
        //if (OcTree.profile) ProfileTimer.startProfile("OcRadiusCuller::checkCell");
        cellSphere.setCenter( cell.cx, cell.cy, cell.cz );
        
        float radius = FastMath.sqrt( cell.halfSize * cell.halfSize * 3.0f );
        cellSphere.setRadius( radius );
        
        //if (s.inSphere(cellSphere.getCenter(),cellSphere.getRadius())) return this.NO_CLIP;
        Visibility val = ( cellSphere.containsPlus( s.getCenterX(), s.getCenterY(), s.getCenterZ(), s.getRadius() ) )
                         ? Visibility.FULLY_VISIBLE : Visibility.NOT_VISIBLE;
        
        //if (OcTree.profile) ProfileTimer.endProfile();
        return( val );
    }
    
    /**
     * Checks the node and returns whether it should be culled or not.  In general
     * this is a sphere test applied to the node.
     * 
     * @param node
     * 
     * @return the Visibility value
     */
    public Visibility checkNode( OcNode node )
    {
        Visibility val = ( node.containsPlus( s.getCenterX(), s.getCenterY(), s.getCenterZ(), s.getRadius() ) )
                         ? Visibility.FULLY_VISIBLE : Visibility.NOT_VISIBLE;
        
        //if (OcTree.profile) ProfileTimer.endProfile();
        return( val );
    }
}

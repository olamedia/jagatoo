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

import org.openmali.vecmath.Point3f;

import org.jagatoo.spatial.SpatialCallback;
import org.jagatoo.spatial.SpatialHandle;

/**
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class OcTest
{
    class TestObject
    {
    }
    
    /**
     * Simple callback to see if we can find a specific node.
     * 
     * @author David Yazel
     */
    private class TestCallback implements SpatialCallback
    {
        TestObject o;
        int hits = 0;
        boolean found = false;
        
        public TestCallback( TestObject object )
        {
            this.o = object;
            found = false;
        }
        
        public void hit( SpatialHandle handle )
        {
            //Log.print( "got a hit " + ((OcNode)handle).getCenter() );
            if ( handle.getObject() == o )
            {
                found = true;
            }
            
            hits++;
        }
    }
    
    private OcTree<TestObject> tree;
    
    public OcTest( OcTree<TestObject> tree )
    {
        this.tree = tree;
    }
    
    /**
     * Inserts a node into the OcTree and then attempts to find it again.
     * 
     * @param x
     * @param y
     * @param z
     */
    public TestCallback test( float x, float y, float z, float radius, float cx,
                              float cy, float cz, float cradius )
    {
        TestObject o = new TestObject();
        tree.insert( x, y, z, radius, o );
        TestCallback callback = new TestCallback( o );
        tree.findWithinSphere( new Point3f( cx, cy, cz ), cradius, callback );
        
        return( callback );
    }
    
    /**
     * Testing the OcTree.
     * 
     * @param args
     */
    /*
    static public void main( String[] args )
    {
        Log.log.registerLog( new ConsoleLog( LogType.PROFILE | LogType.ALL ) );
        Log.log.registerLog( new FileLog( LogType.PROFILE | LogType.ALL, "octree.log" ) );
        
        OcTree<TestObject> tree = new OcTree<TestObject>( 16000, 6 );
        OcTest test = new OcTest( tree );
        Random r = new Random( System.nanoTime() );
        int ok = 0;
        long totalHits = 0;
        Log.print( "Inserting and checking 100000 nodes..." );
        
        final int total = 100000;
        
        for ( int i = 0; i < total; i++ )
        {
            float x = r.nextFloat() * 8000;
            float y = r.nextFloat() * 8000;
            float z = r.nextFloat() * 8000;
            float radius = 1f + (r.nextFloat() * 3);
            
            float cradius = radius * 3f;
            float tradius = radius * 2.3f;
            
            float cx = x + (r.nextBoolean() ? (-tradius) : tradius);
            float cy = y + (r.nextBoolean() ? (-tradius) : tradius);
            float cz = z + (r.nextBoolean() ? (-tradius) : tradius);
            
            TestCallback tcb = test.test( x, y, z, radius, cx, cy, cz, cradius );
            
            if ( tcb.found )
            {
                ok++;
                totalHits += tcb.hits;
            }
            else
            {
                Log.print( "--------------------------------" );
                Log.print( "------------ ERROR -------------" );
                Log.print( "--------------------------------" );
                Log.print( "" );
                Log.print( "object inserted to " + x + "," + y + "," + z + " radius " + radius );
                Log.print( "culler checked : " + cx + "," + cy + "," + cz + " radius " + cradius );
                
                OcTree.debug = new Boolean( true );
                test.test( x, y, z, radius, cx, cy, cz, cradius );
                
                Log.print( "--------------------------------" );
                Log.print( "------- RERUN ON EMPTY TREE ----" );
                Log.print( "--------------------------------" );
                
                tree = new OcTree<TestObject>( 16000, 6 );
                test = new OcTest( tree );
                test.test( x, y, z, radius, cx, cy, cz, cradius );
                
                break;
            }
        }
        
        Log.print( "We performed " + ok + " successful and " + (100000 - ok) + " unsuccessful tests" );
        Log.print( "Average number of hits per check was " + (totalHits / total) );
        
        //Log.print( "Testing octree : " + test.test( 12, 67, 99, 1, 20, 20, 20, 300 ) );
        //Log.print( "Testing octree : " + test.test( 12, 67, 99, 1, 0, 0, 0, 11 ) );
        ProfileTimer.printLogs();
    }
    */
}

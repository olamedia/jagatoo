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
package org.jagatoo.loaders.models.bsp;

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jagatoo.loaders.models.bsp.lumps.BSPFace;
import org.jagatoo.loaders.models.bsp.lumps.BSPVisData;
import org.openmali.vecmath.Vector3f;

/**
 * This class is used to manage the visibility of all the BSP clusters.
 * 
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public class BSPClusterManager
{
    private BSPVisData     bspVisData;
    private BSPFace[]      bspFaces;
    
    private BitSet         faceBitset;
    private List<int[]>[]  clusterLeafs;
    private int[]          leafToCluster;
    private float[]        planes;
    private int[]          nodes;
    
    private Vector3f       normal = new Vector3f();
    
    private boolean        usePVS = true;
    private boolean        lastUsePVS = true;
    
    /**
     * Calculates which cluster the camera position is in
     * 
     * @param camPos the position of the camera (View)
     */
    private int getCluster( Vector3f camPos )
    {
        int index = 0;
        
        while ( index >= 0 )
        {
            final int node = index * 3;
            final int planeIndex = nodes[ node + 0 ] * 4;
            normal.x = planes[ planeIndex + 0 ];
            normal.y = planes[ planeIndex + 1 ];
            normal.z = planes[ planeIndex + 2 ];
            float d = planes[ planeIndex + 3 ];
            
            // Distance from point to a plane
            final float distance = normal.dot( camPos ) - d;
            
            if ( distance > 0.0001f )
                index = nodes[ node + 1 ];
            else
                index = nodes[ node + 2 ];
        }
        
        return( leafToCluster[ -( index + 1 ) ] );
    }
    
    boolean isClusterVisible( int visCluster, int testCluster )
    {
        if ( ( bspVisData.pBitsets == null ) || ( visCluster < 0 ) )
            return( true );
        
        int i = ( visCluster * bspVisData.bytesPerCluster ) + ( testCluster / 8 );
        
        return( ( bspVisData.pBitsets[ i ] & ( 1 << ( testCluster & 0x07 ) ) ) != 0 );
    }
    
    /**
     * Enables or disables PVS usage.
     * 
     * @param enabled if true the PVS is used to disable hidden geometry, false and everything is enabled
     */
    public void setPVSUsage( boolean enabled )
    {
        this.usePVS = enabled;
    }
    
    /**
     * @return <b>true</b>, if the PVS is used to disable hidden geometry. <b>false</b>, and everything is enabled
     */
    public boolean isPVSUsed()
    {
        return( usePVS );
    }
    
    private int lastCluster = -2;
    private Set<Integer> set = new HashSet<Integer>();
    
    /**
     * Disables geometry that is invisible according to the PVS
     * 
     * @param camPos the position of the camera
     */
    public void setVisibility( Vector3f camPos )
    {
        final boolean usePVSChanged = ( usePVS != lastUsePVS );
        
        lastUsePVS = usePVS;
        
        if ( !usePVS )
        {
            faceBitset.set( 0, faceBitset.size() - 1 );
            //faceSwitch.setChildMask( faceBitset );
            
            return;
        }
        
        int camCluster = getCluster( camPos );
        if ( lastCluster == camCluster && !usePVSChanged )
        {
            return;
        }
        
        final boolean debug = false;
            
        if ( debug )
        {
            System.out.println( "new cluster is " + camCluster );
        }
        lastCluster = camCluster;
        
        //faceBitset.clear( 0, faceBitset.size() - 1 );
        faceBitset.clear();
        int numVis = 0;
        int numFacesVis = 0;
        int numClusters = 0;
        int numLeaves = 0;
        if ( debug )
            set.clear();
        
        for ( int i = 0; i < bspVisData.numOfClusters; i++ )
        {
            final boolean isVisible = isClusterVisible( camCluster, i );
            
            if ( clusterLeafs[ i ] != null )
            {
                numClusters++;
            }
            
            if ( ( isVisible ) && ( clusterLeafs[ i ] != null ) )
            {
                boolean hasFaces = false;
                
                for ( int j = 0; j < clusterLeafs[ i ].size(); j++ )
                {
                    final int[] clusterLeaf = clusterLeafs[ i ].get( j );
                    if ( clusterLeaf.length > 0 )
                        numLeaves++;
                    
                    for ( int k = 0; k < clusterLeaf.length; k++ )
                    {
                        if ( !faceBitset.get( clusterLeaf[ k ] ) )
                        {
                            faceBitset.set( clusterLeaf[ k ] );
                            
                            if ( debug )
                            {
                                int key = ( i << 24 ) | (bspFaces[ clusterLeaf[ k ] ].textureID << 8 ) | ( bspFaces[ clusterLeaf[ k ] ].lightmapID );
                                
                                if ( !set.contains( key ) )
                                    set.add( key );
                            }
                            
                            numFacesVis++;
                            hasFaces = true;
                        }
                    }
                }
                
                if ( hasFaces )
                    numVis++;
            }
        }
        
        //faceSwitch.setChildMask( faceBitset );
        
        if ( debug )
        {
            System.out.println( "num cluster is visible is " + numVis + " out of " + numClusters );
            //System.out.println( "num leaves visible is " + numLeaves + " out of " + leafs.length );
            //System.out.println( "num faces visible is " + numFacesVis + " out of " + faces.size() );
            System.out.println( "num unique shapes is " + set.size() );
        }
    }
    
    public BSPClusterManager( BSPVisData bspVisData, BSPFace[] bspFaces, BitSet faceBitset, List<int[]>[] clusterLeafs, int[] leafToCluster, float[] planes, int[] nodes )
    {
        this.bspVisData     = bspVisData;
        this.bspFaces       = bspFaces;
        this.faceBitset     = faceBitset;
        this.clusterLeafs   = clusterLeafs;
        this.leafToCluster  = leafToCluster;
        this.planes         = planes;
        this.nodes          = nodes;
    }
}

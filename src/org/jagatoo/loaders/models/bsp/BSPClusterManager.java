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

import org.jagatoo.loaders.models.bsp.lumps.BSPFace;
import org.jagatoo.loaders.models.bsp.lumps.BSPVisData;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.util.FloatUtils;

/**
 * This class is used to manage the visibility of all the BSP clusters.
 * 
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public class BSPClusterManager
{
    private static final boolean DEBUG = false;
    
    protected BSPVisData     bspVisData;
    protected BSPFace[]      bspFaces;
    
    protected BitSet         faceBitset;
    protected int[][][]      clusterLeafs;
    protected int[]          leafToCluster;
    protected float[]        planes;
    protected int[]          nodes;
    
    private   boolean        usePVS = true;
    private   boolean        lastUsePVS = true;
    
    /**
     * Calculates which cluster the camera position is in
     * 
     * @param camPos the position of the camera (View)
     */
    private int getCluster( Tuple3f camPos )
    {
        int index = 0;
        
        while ( index >= 0 )
        {
            int node = index * 3;
            int planeIndex = nodes[ node + 0 ] * 4;
            float nx = planes[ planeIndex + 0 ];
            float ny = planes[ planeIndex + 1 ];
            float nz = planes[ planeIndex + 2 ];
            float d = planes[ planeIndex + 3 ];
            
            // Distance from point to a plane
            float dot = FloatUtils.dot( nx, ny, nz, camPos.getX(), camPos.getY(), camPos.getZ() );
            float distance = dot - d;
            
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
    
    /*
     * For debugging...
     */
    private HashSet<Integer> set = new HashSet<Integer>();
    private int numVis = 0;
    private int numFacesVis = 0;
    private int numClusters = 0;
    private int numLeaves = 0;
    
    /**
     * Disables geometry that is invisible according to the PVS
     * 
     * @param camPos the position of the camera
     */
    public void setVisibility( Tuple3f camPos )
    {
        boolean usePVSChanged = ( usePVS != lastUsePVS );
        
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
        
        if ( DEBUG )
        {
            System.out.println( "new cluster is " + camCluster );
        }
        
        lastCluster = camCluster;
        
        //faceBitset.clear( 0, faceBitset.size() - 1 );
        faceBitset.clear();
        
        if ( DEBUG )
        {
            numVis = 0;
            numFacesVis = 0;
            numClusters = 0;
            numLeaves = 0;
            
            set.clear();
        }
        
        for ( int i = 0; i < bspVisData.numOfClusters; i++ )
        {
            int[][] clusterLeafs_i = clusterLeafs[ i ];
            
            if ( DEBUG && ( clusterLeafs_i != null ) )
            {
                numClusters++;
            }
            
            if ( ( clusterLeafs_i != null ) && isClusterVisible( camCluster, i ) )
            {
                boolean hasFaces = false;
                
                for ( int j = 0; j < clusterLeafs_i.length; j++ )
                {
                    int[] clusterLeafs_i_j = clusterLeafs_i[ j ];
                    
                    if ( clusterLeafs_i_j.length > 0 )
                    {
                        if ( DEBUG )
                        {
                            numLeaves++;
                        }
                        
                        for ( int k = 0; k < clusterLeafs_i_j.length; k++ )
                        {
                            int clusterLeaf = clusterLeafs_i_j[ k ];
                            
                            if ( !faceBitset.get( clusterLeaf ) )
                            {
                                faceBitset.set( clusterLeaf );
                                
                                if ( DEBUG )
                                {
                                    BSPFace bspFace = bspFaces[ clusterLeaf ];
                                    int key = ( i << 24 ) | ( bspFace.textureID << 8 ) | ( bspFace.lightmapID );
                                    
                                    if ( !set.contains( key ) )
                                        set.add( key );
                                    
                                    numFacesVis++;
                                }
                                
                                hasFaces = true;
                            }
                        }
                    }
                }
                
                if ( DEBUG && hasFaces )
                {
                    numVis++;
                }
            }
        }
        
        //faceSwitch.setChildMask( faceBitset );
        
        if ( DEBUG )
        {
            System.out.println( "num cluster is visible is " + numVis + " out of " + numClusters );
            //System.out.println( "num leaves visible is " + numLeaves + " out of " + leafs.length );
            //System.out.println( "num faces visible is " + numFacesVis + " out of " + faces.size() );
            System.out.println( "num unique shapes is " + set.size() );
        }
    }
    
    public BSPClusterManager( BSPVisData bspVisData, BSPFace[] bspFaces, BitSet faceBitset, int[][][] clusterLeafs, int[] leafToCluster, float[] planes, int[] nodes )
    {
        this.bspVisData     = bspVisData;
        this.bspFaces       = bspFaces;
        this.faceBitset     = faceBitset;
        this.clusterLeafs   = clusterLeafs;
        this.leafToCluster  = leafToCluster;
        this.planes         = planes;
        this.nodes          = nodes;
    }
    
    public BSPClusterManager( BSPClusterManager template )
    {
        this.bspVisData     = template.bspVisData;
        this.bspFaces       = template.bspFaces;
        this.faceBitset     = template.faceBitset;
        this.clusterLeafs   = template.clusterLeafs;
        this.leafToCluster  = template.leafToCluster;
        this.planes         = template.planes;
        this.nodes          = template.nodes;
    }
}

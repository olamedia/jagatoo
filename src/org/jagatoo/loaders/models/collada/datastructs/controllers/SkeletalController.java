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
package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jagatoo.loaders.models.collada.AnimatableModel;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.LibraryAnimations;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Mesh;
import org.jagatoo.loaders.models.collada.datastructs.geometries.MeshSources;
import org.jagatoo.loaders.models.collada.datastructs.geometries.TrianglesGeometry;
import org.jagatoo.loaders.models.collada.jibx.XMLController;
import org.jagatoo.loaders.models.collada.jibx.XMLSkin;
import org.jagatoo.logging.JAGTLog;

/**
 * A COLLADA Skeletal Controller. It computes mesh
 * deformation from the keyframe information.
 * 
 * @see GeometryProvider
 * 
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 */
public class SkeletalController extends Controller implements AnimatableModel
{
    /** The ID of the source mesh */
    private final String sourceMeshId;
    
    /** COLLADA Geometry */
    private Geometry sourceGeom;
    
    /** The skeleton we're using to compute */
    private Skeleton skeleton;
    
    /**
     * Creates a new COLLADASkeletalController
     * 
     * @param libGeoms
     *                The {@link LibraryGeometries} we need to get our sourceMesh from
     * @param sourceMeshId
     *                The source mesh ID
     * @param controller
     * 	              The XML Controller instance
     * @param libAnims 
     *                The library of animations to load actions from
     */
    public SkeletalController( LibraryGeometries libGeoms, String sourceMeshId, XMLController controller, LibraryAnimations libAnims, Skeleton skel )
    {
        super( libGeoms, controller );
        
        this.sourceMeshId = sourceMeshId;
        this.skeleton = skel;
        
        this.libAnims = libAnims;
        
        this.sourceGeom = libGeoms.getGeometries().get( sourceMeshId );
    }
    
    /**
     * @return the sourceMeshId
     */
    public String getSourceMeshId()
    {
        return( sourceMeshId );
    }
    
    // // // // // // ANIMATION PART - BEGINS// // // // // //
    
    /**
     * List of animations that exist 
     * for this SkeletalController's skeleton.
     */
    private HashMap<String, COLLADAAction> actions = new HashMap<String, COLLADAAction>();
    
    /**
     * Library Of Animations
     */
    public LibraryAnimations libAnims;
    
    /**
     * The current action that is being used
     */
    private COLLADAAction currentAction;
    
    /**
     * For animation looping
     */
    private boolean loop = false;
    
    /**
     * Last animated time
     */
    private long currentTime = -1;
    
    /**
     * Is the animation playing?
     */
    private boolean playing = false;
    
    /**
     * Tells if have bones been prepared for current action
     */
    private boolean bonesPrepared = false;
    
    /**
     * Iterator through skeleton bones
     */
    private Iterable<Bone> boneIt = this.skeleton;
    
    /**
     * Starts playing the selected animation
     */
    public void play( COLLADAAction action, boolean loop )
    {
        this.currentAction = action;
        
        // complete the temps array reference in each bone of the skeleton
        currentAction.prepareBones();
        bonesPrepared = true;
        
        // we use the skeleton of the action
        this.skeleton = currentAction.getSkeleton();
        this.loop = loop;
        this.skeleton.resetIterator();
        
        boneIt = this.skeleton;
        
        currentTime = 0;
        playing = true;
    }
    
    /**
     * Normalize the influences weights
     */
    private void normalizeInfluences( Influence[] influences )
    {
        // TODO: not yet implemented!
    }
    
    @Override
    public Geometry updateDestinationGeometry( long currTime )
    {
        // TODO: once this method works well, we should optimize all the variables declarations
    	
        if ( sourceGeom == null )
        {
            sourceGeom = getLibraryGeometries().getGeometries().get( sourceMeshId );
            
            if ( !( sourceGeom instanceof TrianglesGeometry ) )
            {
                throw new Error( "Only TrianglesGeometry is supported for now ! Make" +
                                 " sure your model is triangulated when exporting from your modeling tool." );
            }
            else
            {
                this.destinationGeometry = sourceGeom.copy();
            }
        }
        
    	if ( !playing )
    	    return( this.destinationGeometry );
    	
        if ( skeleton == null )
        {
            JAGTLog.debug( "Hey ! We haven't been initialized yet... Darn." );
        }
        else
        {
            JAGTLog.debug( "Our skeleton root bone is named \"",
                           skeleton.getRootBone().getName(), "\"" );
            
            // deltaT to animate
            currentTime = currTime;
            
            // Translation : only for the skeleton
            
            KeyFrameComputer.computeTuple3f( currentTime, skeleton.transKeyFrames, skeleton.relativeTranslation );
            
            // Loop through all bones...
            if ( currentAction == null )
                return( destinationGeometry );
            
            if ( !bonesPrepared )
            {
            	currentAction.prepareBones();
                this.skeleton = currentAction.getSkeleton();
            }
            
            boneIt = this.skeleton;
            for ( Bone bone : boneIt )
            {
                // if there is no keyframes, don't do any transformations
                if ( !bone.hasKeyFrames() )
                {
                    JAGTLog.debug( "no keyframes!" );
                    bone.setNoRelativeMovement();
                    continue;
                }
                
                JAGTLog.debug( "Keyframes" );
                // Scaling
                KeyFrameComputer.computeTuple3f( currentTime, bone.scaleKeyFrames, bone.relativeScaling );
                
                // Rotation
                KeyFrameComputer.computeQuaternion4f( currentTime, bone.rotKeyFrames, bone.relativeRotation );
            }
            
            // Update absolutes transitions for all bones.
            skeleton.updateAbsolutes();
        }
        
        /*
         * Now I need to to apply transitions to every vertex. I need the
         * following information and I don't know where
         *     1) The mesh I am animating is
         *     2) The triangles of that mesh are
         *     3) The vertices, 3 for each triangle, are
         *     4) The normals 3 for each triangle are.
         * 
         * I suppose, there should be something in Geometry. The only place,
         * I could find something is Mesh. It has the vertices and normals
         * indices, but not the triangles.
         */
        
        /*
         * Apply the transitions to every vertex using the bones' weights.
         */
        TrianglesGeometry triMesh = (TrianglesGeometry)sourceGeom;
        
        // current mesh
        Mesh mesh = triMesh.getMesh();
        
        // vertices and normals sources
        MeshSources sources = mesh.getSources();
        
        // skin info
        XMLSkin skin = this.getController().skin;
        
        // iterate over vertices
        Influence[] influences;
        
        // Check if there is any influence!
        
        /*
         * TODO:
         * use the destinationGeometry
         * 1. transform the normals, only rotation
         * 2. transform the vertex, rotation and translation
         */
        
        // This is a big test, still under construction.
        for ( int j = 0; j < sources.getVertices().length; j += 3 )
        {
            /*
             * FIXME: I have no idea, how to fix the fact that "i" must be
             * divided by 6.  It has to do with the relative array sizes
             * (see XMLSkin).
             */
        	influences = skin.buildInfluencesForVertex( j / 6 );
        	if ( influences.length <= 0 )
        	    //return( destinationGeometry );
        	    continue;
        	
        	normalizeInfluences( influences );
        	/*
        	Point3f vertex = new Point3f(
        	        sources.vertices[sourceGeom.getGeometry().mesh.triangles.p[j]],
        	        sources.vertices[sourceGeom.getGeometry().mesh.triangles.p[j+1]],
        	        sources.vertices[sourceGeom.getGeometry().mesh.triangles.p[j+2]]);
            */
        	/*
            JAGTLog.debug( "old by mesh: ", sourceGeom.getMesh().getVertexIndices()[j] );
            sourceGeom.getMesh().getVertexIndices()[j] = sourceGeom.getMesh().getVertexIndices()[j];
            JAGTLog.debug( "new by mesh: ", sourceGeom.getMesh().getVertexIndices()[j] );
            
            JAGTLog.debug( "old by mesh tris: ", sourceGeom.getGeometry().mesh.triangles.p[j] );
            sourceGeom.getGeometry().mesh.triangles.p[j] = sourceGeom.getGeometry().mesh.triangles.p[j] + 1;
            JAGTLog.debug( "old by mesh tris: ", sourceGeom.getGeometry().mesh.triangles.p[j] );
            
            JAGTLog.debug( "old by sources: ", sources.getVertices()[j] );
            sources.getVertices()[j] = sources.getVertices()[j];
            JAGTLog.debug( "new by sources: ", sources.getVertices()[j] );
            */
        	
            //System.out.println( skeleton.getRootBone().getAbsoluteRotation() );
            
        	for ( int i = 0; i < influences.length; i++ )
        	{
        	    final Influence influence = influences[i];
        	    
        	}
        }
        
        destinationGeometry = sourceGeom.copy();
        
        return( destinationGeometry );
    }
    
    // // // // // // ANIMATION PART - ENDS// // // // // //
    
    /**
     * Set the skeleton of this controller
     *
     * @param skeleton
     *                The skeleton to use
     */
    public void setSkeleton( Skeleton skeleton )
    {
        this.skeleton = skeleton;
    }
    
    public final COLLADAAction getAction( String id )
    {
        return( actions.get( id ) );
    }
    
    public final HashMap<String, COLLADAAction> getActions()
    {
        return( actions );
    }
    
    public ColladaProtoypeModel getPrototypeModel()
    {
        // TODO Auto-generated method stub
        return( null );
    }
    
    public final int numActions()
    {
        return( actions.values().size() );
    }
    
    public final boolean hasActions()
    {
        return( numActions() > 0 );
    }
    
    public final boolean isLooping()
    {
        return( loop );
    }
    
    public final boolean isPlaying()
    {
        return( playing );
    }
    
    public void calcActions( String actionId )
    {
        for ( Iterator<Entry<String, COLLADAAction>> i = libAnims.getAnimations().entrySet().iterator(); i.hasNext(); )
        {
            Entry<String, COLLADAAction> entry = i.next();
            
            if ( entry.getKey().equals( actionId ) )
            {
                JAGTLog.debug( "Action found" );
                actions.put( entry.getKey(), entry.getValue() );
            }
        }
    }
    
    public void play( String actionId, boolean loop )
    {
    	calcActions( actionId );
        this.play( actions.get( actionId ), loop );
    }
    
    public void pause()
    {
        playing = false;
    }
    
    public void resume()
    {
        playing = true;
    }
}

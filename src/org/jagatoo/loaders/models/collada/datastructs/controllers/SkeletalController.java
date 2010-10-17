/**
 * Copyright (c) 2007-2010, JAGaToo Project Group all rights reserved.
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

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models.collada.COLLADAAction;
import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.LibraryAnimations;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.datastructs.geometries.GeometryProvider;
import org.jagatoo.loaders.models.collada.stax.XMLController;
import org.jagatoo.logging.JAGTLog;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A COLLADA Skeletal Controller.
 *
 * @author Amos Wenger (aka BlueSky)
 * @author Matias Leone (aka Maguila)
 * @see GeometryProvider
 */
public class SkeletalController extends Controller //implements AnimatableModel
{
    /**
     * The ID of the source mesh
     */
    private final String sourceMeshId;

    /**
     * Game engine Geometry
     */
    private NamedObject sourceGeom;

    /**
     * The skeleton we're using to compute
     */
    private DaeSkeleton skeleton;
    private AssetFolder colladaFile;

    /**
     * Creates a new SkeletalController
     *
     * @param colladaFile  The collada file to add them to
     * @param sourceMeshId The source mesh ID
     * @param controller   The XML Controller instance
     */
    public SkeletalController( AssetFolder colladaFile, String sourceMeshId, XMLController controller )
    {
        super( colladaFile.getLibraryGeometries(), controller );

        this.sourceMeshId = sourceMeshId;
        this.skeleton = colladaFile.getLibraryVisualsScenes().getSkeletons().get( controller.id.replace( "-skin", "" ) );
        this.colladaFile = colladaFile;
        this.libAnims = colladaFile.getLibraryAnimations();

        this.sourceGeom = colladaFile.getLibraryGeometries().getGeometries().get( sourceMeshId );
    }

    /**
     * @return the sourceMeshId
     */
    public String getSourceMeshId()
    {
        return ( sourceMeshId );
    }

    public DaeSkeleton getSkeleton()
    {
        if( skeleton == null)
        {
            skeleton = colladaFile.getLibraryVisualsScenes().getSkeletons().get( getController().id.replace( "-skin", "" ) );    
        }

        return ( skeleton );
    }

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
     * Set the skeleton of this controller
     *
     * @param skeleton The skeleton to use
     */
    public void setSkeleton( DaeSkeleton skeleton )
    {
        this.skeleton = skeleton;
    }

    public final COLLADAAction getAction( String id )
    {
        return ( actions.get( id ) );
    }

    public final HashMap<String, COLLADAAction> getActions()
    {
        return ( actions );
    }

    public final int numActions()
    {
        return ( actions.values().size() );
    }

    public final boolean hasActions()
    {
        return ( numActions() > 0 );
    }

    public void calcActions( String actionId )
    {
        for ( Entry<String, COLLADAAction> entry : libAnims.getAnimations().entrySet() )
        {
            if ( entry.getKey().equals( actionId ) )
            {
                JAGTLog.debug( "Action found" );
                actions.put( entry.getKey(), entry.getValue() );
            }
        }
    }
}

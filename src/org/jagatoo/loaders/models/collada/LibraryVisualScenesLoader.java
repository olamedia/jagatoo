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
package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.ControllerInstanceNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.GeometryInstanceNode;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.LibraryVisualScenes;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.MatrixTransform;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Node;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Scene;
import org.jagatoo.loaders.models.collada.jibx.XMLBindMaterial;
import org.jagatoo.loaders.models.collada.jibx.XMLInstanceController;
import org.jagatoo.loaders.models.collada.jibx.XMLInstanceGeometry;
import org.jagatoo.loaders.models.collada.jibx.XMLInstanceMaterial;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryVisualScenes;
import org.jagatoo.loaders.models.collada.jibx.XMLNode;
import org.jagatoo.loaders.models.collada.jibx.XMLVisualScene;
import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Vector3f;

/**
 * Class used to load LibraryVisualScenes.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryVisualScenesLoader
{
    /**
     * Load LibraryVisualScenes
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libScenes
     *            The JAXB data to load from
     */
    static void loadLibraryVisualScenes( AssetFolder colladaFile, XMLLibraryVisualScenes libScenes, Vector3f upVector )
    {
        LibraryVisualScenes colLibVisualScenes = colladaFile.getLibraryVisualsScenes();
        HashMap<String, Scene> scenes = colLibVisualScenes.getScenes();
        
        Collection<XMLVisualScene> visualScenes = libScenes.scenes.values();
        
        JAGTLog.increaseIndentation();
        for ( XMLVisualScene visualScene : visualScenes )
        {
            Scene colScene = new Scene( visualScene.id, visualScene.name );
            scenes.put( colScene.getId(), colScene );
            
            JAGTLog.debug( "TT] Found scene [", colScene.getId(), ":", colScene.getName(), "]" );
            JAGTLog.increaseIndentation();
            for ( XMLNode node : visualScene.nodes.values() )
            {
                JAGTLog.debug( "TT] Found node [", node.id, ":", node.name, "]" );
                JAGTLog.increaseIndentation();
                
                Node colNode = null;
                
                if ( node.type == XMLNode.Type.NODE )
                {
                    JAGTLog.debug( "TT] Alright, it's a basic node" );
                    
                    MatrixTransform transform = new MatrixTransform( node.matrix.matrix4f );
                    
                    if ( node.instanceGeometries != null )
                    {
                    	JAGTLog.debug( "TT] A geometry node!" );
                    	
                        for ( XMLInstanceGeometry instanceGeometry : node.instanceGeometries )
                        {
                            colNode = newCOLLADAGeometryInstanceNode( colladaFile, node, transform, instanceGeometry.url, instanceGeometry.bindMaterial );
                        }
                    }
                    else if ( node.instanceControllers != null )
                    {
                    	JAGTLog.debug( "TT] A controller node!" );
                    	
                        for ( XMLInstanceController instanceController : node.instanceControllers )
                        {
                            colNode = newCOLLADAControllerInstanceNode( colladaFile, node, transform, instanceController.url, instanceController.bindMaterial );
                            Controller controller = colladaFile.getLibraryControllers().getControllers().get( instanceController.url );
                            
                            if ( controller instanceof SkeletalController )
                            {
                                final SkeletalController skelController = (SkeletalController)controller;
                                
                            	JAGTLog.debug( "Wow! It's a Skeletal Controller Node!" );
                            	skelController.setSkeleton( colLibVisualScenes.getSkeletons().get( instanceController.skeleton ) );
                            	skelController.setDestinationMesh( colladaFile.getLibraryGeometries().getGeometries().get( skelController.getSourceMeshId() ) );
                            }
                        }
                    }
                }
                else if ( node.type == XMLNode.Type.JOINT )
                {
                    JAGTLog.debug( "TT] Alright, it's a skeleton node" );
                    
                    Skeleton skeleton = SkeletonLoader.loadSkeleton( node, upVector );
                    colLibVisualScenes.getSkeletons().put( node.id, skeleton );
                    Collection<Controller> controllers = colladaFile.getLibraryControllers().getControllers().values();
                    for ( Controller controller : controllers )
                    {
                        if ( controller instanceof SkeletalController )
                        {
                            final SkeletalController skelController = (SkeletalController)controller;
                            
                            if ( node.id.equals( skelController.getController().skin.source ) )
                            {
                                skelController.setSkeleton( skeleton );
                            }
                        }
                    }
                }
                else
                {
                    JAGTLog.debug( "TT] Node is of type : ", node.type, " we don't support specific nodes yet..." );
                }
                
                JAGTLog.decreaseIndentation();
                
                if ( colNode != null )
                {
                	JAGTLog.debug( "TT] Successfully adding colNode ", colNode.getId() );
                    colScene.getNodes().put( colNode.getId(), colNode );
                }
                else if ( node.type != XMLNode.Type.JOINT )
                {
                    JAGTLog.debug( "TT] NULL node! Something went wrong..." );
                }
            }
            
            JAGTLog.decreaseIndentation();
        }
        
        JAGTLog.decreaseIndentation();
    }
    
    /**
     * Creates a new COLLADA node (type : geometry instance) from the informations given.
     * 
     * @param colladaFile
     * @param node
     * @param transform
     * @param geometryUrl
     * @param bindMaterial
     * 
     * @return
     */
    static GeometryInstanceNode newCOLLADAGeometryInstanceNode( AssetFolder colladaFile, XMLNode node, MatrixTransform transform, String geometryUrl, XMLBindMaterial bindMaterial )
    {
        GeometryInstanceNode colNode;
        String materialUrl = null;
        XMLBindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
        List<XMLInstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
        for ( XMLInstanceMaterial instanceMaterial : instanceMaterialList )
        {
            if ( materialUrl == null )
            {
                materialUrl = instanceMaterial.target;
            }
            else
            {
                JAGTLog.debug( "TT] Several materials for the same geometry instance ! Skipping...." );
            }
        }
        
        colNode = new GeometryInstanceNode(
                colladaFile,
                node.id,
                node.name,
                transform,
                geometryUrl,
                materialUrl
        );
        
        return( colNode );
    }
    
    /**
     * Creates a new COLLADA node (type : controller instance) from the informations given.
     * 
     * @param colladaFile
     * @param node
     * @param transform
     * @param geometryUrl
     * @param bindMaterial
     * 
     * @return
     */
    static ControllerInstanceNode newCOLLADAControllerInstanceNode( AssetFolder colladaFile, XMLNode node, MatrixTransform transform, String controllerUrl, XMLBindMaterial bindMaterial )
    {
        ControllerInstanceNode colNode;
        String materialUrl = null;
        XMLBindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
        List<XMLInstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
        for ( XMLInstanceMaterial instanceMaterial : instanceMaterialList )
        {
            if ( materialUrl == null )
            {
                materialUrl = instanceMaterial.target;
            }
            else
            {
                JAGTLog.debug( "TT] Several materials for the same controller instance ! Skipping...." );
            }
        }
        
        colNode = new ControllerInstanceNode(
                colladaFile,
                node.id,
                node.name,
                transform,
                controllerUrl,
                materialUrl
        );
        
        return( colNode );
    }
}

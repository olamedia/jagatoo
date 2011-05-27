/**
 * Copyright (c) 2007-2011, JAGaToo Project Group all rights reserved.
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

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models._util.*;
import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeJoint;
import org.jagatoo.loaders.models.collada.datastructs.animation.DaeSkeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Influence;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.datastructs.effects.Effect;
import org.jagatoo.loaders.models.collada.datastructs.effects.Profile;
import org.jagatoo.loaders.models.collada.datastructs.effects.ProfileCommon;
import org.jagatoo.loaders.models.collada.datastructs.images.Surface;
import org.jagatoo.loaders.models.collada.datastructs.materials.Material;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.AbstractInstance;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.ControllerInstance;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.GeometryInstance;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.DaeNode;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.opengl.enums.BlendMode;
import org.jagatoo.opengl.enums.ColorTarget;
import org.openmali.spatial.bounds.BoundsType;
import org.openmali.types.primitives.MutableFloat;
import org.openmali.vecmath2.Matrix4f;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to retrieve a game engine models from a File
 * object, which corresponds to the data loaded from an XML object.
 *
 * @author Amos Wenger (aka BlueSky)
 */
class DaeConverter
{
    private final AssetFolder file;
    private final SpecialItemsHandler siHandler;
    private final AppearanceFactory appFactory;
    private final GeometryFactory geomFactory;
    private final NodeFactory nodeFactory;
    private final AnimationFactory animFactory;

    private final HashMap<AbstractInstance, NamedObject> instanceMap = new HashMap<AbstractInstance, NamedObject>();
    private final HashMap<String, NamedObject> geomIdToShape = new HashMap<String, NamedObject>();
    private final HashSet<NamedObject> staticGeoms = new HashSet<NamedObject>();

    public Set<NamedObject> getStaticGeoms()
    {
        return ( staticGeoms );
    }

    /**
     * Creates a game engine Appearance from informations.
     *
     * @param material collada Material
     * @return the Appearance as NamedObject
     */
    private NamedObject convertToAppearance( Material material )
    {
        if ( material == null )
        {
            return null;
        }

        AssetFolder file = material.getFile();

        NamedObject app = appFactory.createAppearance( material.getId(), 0 );
        NamedObject mat = appFactory.createMaterial( material.getId() );
        appFactory.setMaterialLightingEnabled( mat, true );
        appFactory.setMaterialColorTarget( mat, ColorTarget.AMBIENT );
        appFactory.applyMaterial( mat, app );
        //  app.setMaterial( new Material( true ) );
        //  app.getMaterial().setColorTarget( Material.AMBIENT );

        String texture = null;
        String effectUrl = material.getEffect();
        Effect effect = ( effectUrl.length() == 0 ) ?
                file.getLibraryEffects().getEffects().values().iterator().next() :
                file.getLibraryEffects().getEffects().get( material.getEffect() );
        for ( Profile profile : effect.profiles )
        {
            if ( profile instanceof ProfileCommon )
            {
                ProfileCommon profileCommon = ( ProfileCommon ) profile;

                for ( Surface surface : profileCommon.getSurfaces().values() )
                {
                    for ( String imageId : surface.imageIds )
                    {
                        if ( texture == null )
                        {
                            texture = file.getLibraryImages().getImages().get( imageId );
                            //System.out.println( "Found texture : " + texture );
                        }
                        else
                        {
                            System.err.println( "Ignoring extra texture : " + file.getLibraryImages().getImages().get( imageId ) );
                        }
                    }
                }
            }
            else
            {
                System.err.println( "Ignoring profile type : " + profile.getClass().getName() );
            }
        }
        AbstractTexture t = null;
        if ( texture != null )
        {
            try
            {
                t = appFactory.loadTexture( new URL( file.getBasePath().toExternalForm() + texture ), true, true, true, true, true );
                //app.setTexture(TextureLoader.getInstance().loadTexture(new URL(file.getBasePath().toExternalForm() + texture)));
                appFactory.applyTexture( t, 0, app );
            }
            catch ( MalformedURLException e )
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println( "No texture found" );
        }

        if ( ( t != null ) && ( t.getFormat().hasAlpha() ) )
        {
            NamedObject ta = appFactory.createTransparencyAttributes( "" );
            appFactory.setTransparencyAttribsBlendMode( ta, BlendMode.BLENDED );
            appFactory.applyTransparancyAttributes( ta, app );
        }

        return ( app );
    }

    public NamedObject convertNode( DaeNode node )
    {
        NamedObject grp = null;

        if ( node.getCOLLADATransform().toTransform().getMatrix4f(null).equals( Matrix4f.IDENTITY ) )
        {
            grp = nodeFactory.createSimpleGroup( node.getId(), BoundsType.SPHERE );
        }
        else
        {
            grp = nodeFactory.createTransformGroup( node.getId(),
                    node.getCOLLADATransform().toTransform().getMatrix4f(null),
                    BoundsType.SPHERE );
            siHandler.addSpecialItem( SpecialItemsHandler.SpecialItemType.NESTED_TRANSFORM, grp.getName(), grp );
        }

        for ( DaeNode n : node.getChildren() )
        {
            nodeFactory.addNodeToGroup( convertNode( n ), grp );
        }

        for ( AbstractInstance ai : node.getGeometryInstances() )
        {
            nodeFactory.addNodeToGroup( convertToShape( ai ), grp );
        }

        for ( AbstractInstance ai : node.getControllerInstances() )
        {
            nodeFactory.addNodeToGroup( convertToShape( ai ), grp );
        }

        return ( grp );
    }

    private NamedObject convertToShape( AbstractInstance instance )
    {
        NamedObject geom = null;
        NamedObject app = null;
        String name = instance.getName();
        Matrix4f bsm = null;

        if ( instance instanceof GeometryInstance )
        {
            GeometryInstance colladaGINode = ( GeometryInstance ) instance;
            geom = file.getLibraryGeometries().getGeometries().get( colladaGINode.getGeometry().getName() );
            app = convertToAppearance( colladaGINode.getMaterial() );
            instanceMap.put( colladaGINode, geom );
        }
        else if ( instance instanceof ControllerInstance )
        {
            ControllerInstance colladaCINode = ( ControllerInstance ) instance;
            geom = colladaCINode.getController().getDestinationGeometry();
            app = convertToAppearance( colladaCINode.getMaterial() );
            bsm = colladaCINode.getController().getController().skin.bindShapeMatrix.matrix4f;
            instanceMap.put( colladaCINode, geom );
        }
        else
        {
            throw new Error( "Type " + instance.getClass().getSimpleName() + " not implemented yet." );
        }

        if ( bsm != null )
        {
            this.nodeFactory.transformGeometryWithNormals( geom, bsm );
        }
        NamedObject shape = nodeFactory.createShape( name, geom, app, BoundsType.SPHERE );
        geomIdToShape.put( geom.getName(), shape );
        staticGeoms.add( geom );
        siHandler.addSpecialItem( SpecialItemsHandler.SpecialItemType.SHAPE, name, shape );

        return ( shape );
    }

    public Object convertToModelAnimation( SkeletalController c, COLLADAAction action )
    {
        action.prepareJoints();
        MutableFloat startTime = new MutableFloat( 0f );
        MutableFloat endTime = new MutableFloat( 0f );
        calcKeyFrames( action, startTime, endTime );

        NamedObject geom = file.getLibraryGeometries().getGeometries().get( c.getSourceMeshId() );
        Object skeleton = convertSkeleton( c.getSkeleton() );

        int vertexCount = geomFactory.getVertexCount( geom );
        int[] indices = file.getLibraryGeometries().getMeshes().get( geom.getName() ).getVertexIndices();
        Influence[][] influences = c.getController().skin.buildInfluences( c.getSkeleton(), vertexCount, indices );
        int influencesPerVertex = calcInfluencesPerVertex( influences );

        float[] weights = new float[influences.length * influencesPerVertex];
        short[] jointIndices = new short[influences.length * influencesPerVertex];
        convertInfluences( influences, influencesPerVertex, weights, jointIndices );

        NamedObject target = geomIdToShape.get( c.getSourceMeshId() );
        Object kfc = animFactory.createSkeletalKeyFrameController(
                skeleton,
                startTime.floatValue(),
                endTime.floatValue(),
                influencesPerVertex,
                weights,
                jointIndices,
                target
        );
        Object[] kfcs = ( Object[] ) Array.newInstance( kfc.getClass(), 1 );
        kfcs[ 0 ] = kfc;
        Object ma = animFactory.createAnimation(
                action.getId(),
                2,
                0.5f / ( endTime.floatValue() - startTime.floatValue() ),
                kfcs,
                null
        );

        this.staticGeoms.remove( geom );

        return ( ma );
    }

    private static void convertInfluences( Influence[][] influences, int influencesPerVertex, float[] weights, short[] jointIndices )
    {
        for ( int i = 0; i < influences.length; i++ )
        {
            for ( int j = 0; j < influencesPerVertex; j++ )
            {
                float weight = 0f;
                short jointIndex = 0;
                if ( j < influences[ i ].length )
                {
                    jointIndex = influences[ i ][ j ].getJointIndex();
                    weight = influences[ i ][ j ].getWeight();
                }

                weights[ i * influencesPerVertex + j ] = weight;
                jointIndices[ i * influencesPerVertex + j ] = jointIndex;
            }
        }
    }

    private static int calcInfluencesPerVertex( Influence[][] influences )
    {
        int influencesPerVertex = 0;
        for ( int i = 0; i < influences.length; i++ )
        {
            influencesPerVertex = Math.max( influencesPerVertex, influences[ i ].length );
        }

        return ( influencesPerVertex );
    }

    private Object convertSkeleton( DaeSkeleton skeleton )
    {
        short index = 0;
        final ArrayList<NamedObject> joints = new ArrayList<NamedObject>();
        skeleton.getRootJoint().setIndex( index );
        traverseSkeleton( -1, null, 0, skeleton.getRootJoint(), joints );
        return animFactory.createSkeleton( joints.toArray( ( NamedObject[] ) Array.newInstance( joints.get( 0 ).getClass(), joints.size() ) ) );
    }

    private int traverseSkeleton( int parentIndex, NamedObject parent, int index, DaeJoint daeJoint, ArrayList<NamedObject> joints )
    {
        daeJoint.setIndex( ( short ) index );
        NamedObject joint = daeJoint.getCOLLADATransform().isMatrixTransform() ?
             animFactory.createJoint(
                ( short ) index,
                daeJoint.getId(),
                parent,
                calcBindTransform( daeJoint ),
                daeJoint.getMatrices()
        ) : animFactory.createJoint(
                ( short ) index,
                daeJoint.getId(),
                parent,
                calcBindTransform( daeJoint ),
                daeJoint.getTranslations(),
                daeJoint.getRotations(),
                daeJoint.getScales()
        );
        joints.add( joint );
        parentIndex = index++;
        if ( daeJoint.numChildren() > 0 )
        {
            for ( int i = 0; i < daeJoint.numChildren(); i++ )
            {
                index = traverseSkeleton( parentIndex, joint, index, daeJoint.getChild( i ), joints );
            }
        }

        return index;
    }

    private Transform calcBindTransform( DaeJoint joint )
    {
        if ( !DaeJoint.isRoot( joint ) )
        {
            return ( joint.getCOLLADATransform().toTransform() );
        }
        DaeNode parent = joint.getParentNode();
        Transform t = joint.getCOLLADATransform().toTransform();
        for (; parent != null; parent = parent.getParentNode() )
        {
            t.mul( parent.getCOLLADATransform().toTransform(), t );
        }

        return ( t );
    }


    private static void calcKeyFrames( COLLADAAction action, MutableFloat st, MutableFloat et )
    {
        float startTime = Float.MAX_VALUE;
        float endTime = Float.MIN_VALUE;
        for ( DaeJoint joint : action.getSkeleton() )
        {
            float[] tl = joint.getTranslations().getTimeline();
            if ( tl != null && tl.length > 0 )
            {
                startTime = Math.min( startTime, tl[ 0 ] );
                endTime = Math.max( endTime, tl[ tl.length - 1 ] );
            }

            tl = joint.getRotations().getTimeline();
            if ( tl != null && tl.length > 0 )
            {
                startTime = Math.min( startTime, tl[ 0 ] );
                endTime = Math.max( endTime, tl[ tl.length - 1 ] );
            }

            tl = joint.getScales().getTimeline();
            if ( tl != null && tl.length > 0 )
            {
                startTime = Math.min( startTime, tl[ 0 ] );
                endTime = Math.max( endTime, tl[ tl.length - 1 ] );
            }
        }

        st.setValue( startTime );
        et.setValue( endTime );
    }

    /**
     * @param file
     * @param siHandler
     * @param appFactory
     * @param geomFactory
     * @param nodeFactory
     * @param animFactory
     */
    DaeConverter( AssetFolder file, SpecialItemsHandler siHandler, AppearanceFactory appFactory, GeometryFactory geomFactory, NodeFactory nodeFactory, AnimationFactory animFactory )
    {
        this.file = file;
        this.siHandler = siHandler;
        this.appFactory = appFactory;
        this.geomFactory = geomFactory;
        this.nodeFactory = nodeFactory;
        this.animFactory = animFactory;
    }
}

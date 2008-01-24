package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFramePoint3f;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameQuat4f;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.jibx.XMLAnimation;
import org.jagatoo.loaders.models.collada.jibx.XMLChannel;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryAnimations;
import org.jagatoo.loaders.models.collada.jibx.XMLChannel.ChannelType;

/**
 * Library animations loader.
 *
 * @author Matias Leone (aka Maguila)
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryAnimationsLoader {

    private static final int MAX_ANIMATIONS_PER_BONE = 4;

    /**
     * Load the library of animations.
     * @param colladaFile
     * @param libAnim
     */
    public static void loadLibraryAnimations(AssetFolder colladaFile,
            XMLLibraryAnimations libAnim ) {

        Collection<XMLAnimation> anims = libAnim.animations.values();
        COLLADALoader.logger.print("There " + (anims.size() > 1 ? "are" : "is") + " "
                + anims.size() + " animation" + (anims.size() > 1 ? "s" : "")
                + " in this file.");



        //FIXME we must know generate COLLADAACtion objects, each one with a AnimationGroup
        // fill with key frames
        //but I don't where to save all this COLLADAACtion.

        HashMap<String, COLLADAAction> colAnims = colladaFile.getLibraryAnimations().getAnimations();



        /*
         * for( all Skeletons ) {
         *
         * But we must know what skeleton belongs each bone
         *
         */

        for(Skeleton skeleton : colladaFile.getLibraryVisualsScenes().getSkeletons().values()) {

        	//create new Action
            COLLADALoader.logger.print("Creating new COLLADAAction with ID of " + skeleton.getRootBone().getName() + "-action.");
            COLLADAAction currAction = new COLLADAAction(skeleton.getRootBone().getName() + "-action");
            currAction.setSkeleton(skeleton);
        	
            // loop through each bone
            int animCount;
            KeyFrame keyFrame;
            Iterator<Bone> it = skeleton.iterator();
            while(it.hasNext()) {
                Bone bone = it.next();

                COLLADALoader.logger.print( "Loading animations for bone " + bone.getName() );

                COLLADALoader.logger.increaseTabbing();

                // search the animations for each bone, max 4 ( 3 rots and 1 trans )
                animCount = 0;
                for (XMLAnimation animation : anims) {
                    if( animCount < MAX_ANIMATIONS_PER_BONE &&
                            animation.getTargetBone().equals( bone.getName() )) {

                        COLLADALoader.logger.print("Loading animation "+animation.name+" of type "+animation.getType()+
                                (animation.getType() == ChannelType.ROTATE ? (" and of axis "+animation.getRotationAxis()):""));
                        if(animation.getType() == null) animation.channels.get(0).type = XMLChannel.ChannelType.SCALE;
                        switch(animation.getType()) {

                        case TRANSLATE :

                            // it's a translation key frame
                            for (int j = 0, k = 0; j < animation.getInput().length; j++, k+=3) {
                                keyFrame = KeyFrame.buildPoint3fKeyFrame(
                                        animation.getInput()[ j ],
                                        animation.getOutput(),
                                        k );
                                currAction.getSkeleton().transKeyFrames.add((KeyFramePoint3f)keyFrame);
                            }

                             
                            break;

                        case ROTATE :

                            // it's a rotation key frame
                            for (int j = 0; j < animation.getInput().length; j++) {
                                keyFrame = KeyFrame.buildQuaternion4fKeyFrame(
                                        animation.getInput()[ j ],
                                        animation.getOutput()[ j ],
                                        animation.getRotationAxis() );
                                /*
                                 * FIXME : It's no longer a list, but an array, now
                                 * And anyway this should be added to a COLLADAAction,
                                 * not to the bones
                                 */
                                bone.rotKeyFrames.add( (KeyFrameQuat4f)keyFrame );
                            }

                            break;

                        case SCALE :

                            // it's a scale key frame
                            for (int j = 0, k = 0; j < animation.getInput().length; j++, k+=3) {
                                keyFrame = KeyFrame.buildPoint3fKeyFrame(
                                        animation.getInput()[ j ],
                                        animation.getOutput(),
                                        k );
                                /*
                                 * FIXME : It's no longer a list but an array, now
                                 * And anyway this should be added to a COLLADAAction,
                                 * not to the bones
                                 */
                                bone.scaleKeyFrames.add( (KeyFramePoint3f)keyFrame );
                            }

                            break;

                        }
                        //add current Action
                        colAnims.put(currAction.getId(), currAction);
                        
                        //update total anims for the bone
                        animCount++;
                    }
                }

                COLLADALoader.logger.decreaseTabbing();

                compressRotationKeyFrames( bone );
            }

        }
    }

    /**
     * Joins all the rotation key frames which has the same frame time
     * @param bone
     */
    private static void compressRotationKeyFrames(Bone bone) {
        Map<Float, List<KeyFrame>> framesMap = new HashMap<Float, List<KeyFrame>>();

        //loop through each frame
        float currentTime;
        List<KeyFrame> frames;
        KeyFrame frame;
        String name;



        //Comming soon
//      for (int i = 0; i < bone.rotKeyFrames.size(); i++) {
//
//      name = bone.getName();
//      //see if it's a new frame time
//      if( !framesMap.containsKey( name ) ) {
//      frames = new ArrayList<KeyFrame>();
//      framesMap.put( name, frames );
//      currentTime = bone.rotKeyFrames.get( i ).getTime();
//
//      //search other two key frames with the same time, and add them to the map list
//      for (int j = i + 1; j < bone.rotKeyFrames.size() ; j++) {
//      frame = bone.rotKeyFrames.get( j );
//      if( frame.getTime() == currentTime ) {
//      frames.add( frame );
//      }
//      }
//      }
//      }


        //build the new compress frames



    }



}

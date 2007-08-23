package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.jibx.XMLAnimation;
import org.jagatoo.loaders.models.collada.jibx.XMLInput;
import org.jagatoo.loaders.models.collada.jibx.XMLLibraryAnimations;
import org.jagatoo.loaders.models.collada.jibx.XMLSampler;

public class LibraryAnimationsLoader {

	private static final int MAX_ANIMATIONS_PER_BONE = 4;

	public static void loadLibraryAnimations(AssetFolder colladaFile,
			XMLLibraryAnimations libAnim ) {

		Collection<XMLAnimation> anims = libAnim.animations.values();
		COLLADALoader.logger.print("There " + (anims.size() > 1 ? "are" : "is") + " "
                + anims.size() + " animation" + (anims.size() > 1 ? "s" : "")
                + " in this file.");

		
		
		//FIXME we must know generate COLLADAACtion objects, each one with a AnimationGroup
		// fill with key frames
		//but I don't where to save all this COLLADAACtion.
		
		
		
		
		
		/*
		 * for( all Skeletons ) {
		 * 
		 * But we must know what skeleton belongs each bone
		 * 
		 */
		
//		Skeleton skeleton = colladaFile.getLibraryVisualsScenes()
//		.getSkeletons().get( "SHOULD BE THE CURRENT SKELETON" );
//
//		//loop through each bone
//		int animCount;
//		KeyFrame keyFrame;
//		for (Bone bone : skeleton ) {
//
//			COLLADALoader.logger.print( "Loading animations for bone" + bone.getName() );
//
//			//search the animations for each bone, max 4 ( 3 rots and 1 trans )
//			animCount = 0;
//			for (XMLAnimation animation : anims) {
//				if( animCount < MAX_ANIMATIONS_PER_BONE &&
//						animation.getTargetBone().equals( bone.getName() ) ) {
//
//					//it´s a translation key frame
//					if ( animation.hasTranslationKeyFrames() ) {
//						for (int j = 0, k = 0; j < animation.getInput().length; j++, k+=3) {
//							keyFrame = KeyFrame.buildTranslationKeyFrame(
//												animation.getInput()[ j ],
//												animation.getOutput(),
//												k );
//							bone.transKeyFrames.add( keyFrame );
//						}
//
//						//it´s a rotation key frame
//					} else {
//						for (int j = 0; j < animation.getInput().length; j++) {
//							keyFrame = KeyFrame.buildRotationKeyFrame(
//											animation.getInput()[ j ],
//											animation.getOutput()[ j ],
//											animation.getRotationAxis() );
//							bone.rotKeyFrames.add( keyFrame );
//						}
//					}
//
//					//update total anims for the bone
//					animCount++;
//				}
//			}
//			compressRotationKeyFrames( bone );
//		}
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
//		for (int i = 0; i < bone.rotKeyFrames.size(); i++) {
//
//			name = bone.getName();
//			//see if it´s a new frame time
//			if( !framesMap.containsKey( name ) ) {
//				frames = new ArrayList<KeyFrame>();
//				framesMap.put( name, frames );
//				currentTime = bone.rotKeyFrames.get( i ).getTime();
//
//				//search other two key frames with the same time, and add them to the map list
//				for (int j = i + 1; j < bone.rotKeyFrames.size() ; j++) {
//					frame = bone.rotKeyFrames.get( j );
//					if( frame.getTime() == currentTime ) {
//						frames.add( frame );
//					}
//				}
//			}
//		}


		//build the new compress frames



	}



}

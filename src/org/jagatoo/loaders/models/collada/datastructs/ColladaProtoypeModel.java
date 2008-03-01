package org.jagatoo.loaders.models.collada.datastructs;

import java.util.ArrayList;
import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;

public class ColladaProtoypeModel {
	AssetFolder colladaFile;
	HashMap<String, Skeleton> skeletons = new HashMap<String, Skeleton>();
	
	public ColladaProtoypeModel(AssetFolder colladaFile) {
		this.colladaFile = colladaFile;
		this.skeletons = colladaFile.getLibraryVisualsScenes().getSkeletons();
	}

	public void initAnimation( String animationName, long time ) {
		
	}

	public void animate( long time ) {
		
	}

}

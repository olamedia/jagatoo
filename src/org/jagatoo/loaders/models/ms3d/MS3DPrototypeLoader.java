package org.jagatoo.loaders.models.ms3d;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MS3DPrototypeLoader {

	public static MS3DPrototypeModel load( InputStream inputStream ) {

		try {
			BufferedInputStream in = new BufferedInputStream( inputStream );

			System.out.println( "Loading MS3D model..." );

			MS3DPrototypeModel model = new MS3DPrototypeModel( in );

	        System.out.println( "Model successfully  loaded" );

	        return model;

		} catch (FileNotFoundException e) {
			throw new RuntimeException( "No existe el archivo " );
		} catch (IOException e) {
			System.out.println( e.getMessage() );
			throw new RuntimeException( e );
		}
	}

}

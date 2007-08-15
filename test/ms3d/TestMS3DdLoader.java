package ms3d;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jagatoo.loaders.models.ms3d.MS3DPrototypeLoader;
import org.jagatoo.loaders.models.ms3d.MS3DPrototypeModel;

public class TestMS3DdLoader {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		MS3DPrototypeModel model = MS3DPrototypeLoader.load( new FileInputStream("test\\ms3d\\model.ms3d") );


		model.initAnimation( 0 );
		model.animate( 0.1f );
	}

}

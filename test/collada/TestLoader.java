package collada;

import org.jagatoo.loaders.models.collada.COLLADALoader;

public class TestLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		COLLADALoader loader = new COLLADALoader();
		loader.load( "test\\collada\\fantassin_cape.dae" );

	}

}

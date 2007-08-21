package org.jagatoo.loaders.models.collada.exceptions;

/**
 * Exception that can be throw while a COLLADA file is being
 * loaded
 *
 * @author Matias Leone
 */
public class ColladaLoaderException extends RuntimeException {

	public ColladaLoaderException( String msg, Throwable t ) {
		super( msg, t );
	}

	public ColladaLoaderException( String msg ) {
		super( msg );
	}

}

package org.jagatoo.util.errorhandling;

/**
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus) [code cleaning]
 */
public class UnsupportedFunction extends Error
{
    private static final long serialVersionUID = 8706886090988483003L;
    
    /**
     * @param message error message
     */
    public UnsupportedFunction( String message )
    {
        super( message );
    }
    
    public UnsupportedFunction()
    {
        this( "This functionality is not yet supported" );
    }
}

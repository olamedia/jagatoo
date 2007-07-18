package org.jagatoo.loaders.models.collada.jibx;

/**
 * An Input contains instructions on how to read
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class Input {

    public int offset = 0;

    public static enum Semantic {
        VERTEX,
        NORMAL,
        TEXCOORD,
        COLOR,
        INPUT,
        OUTPUT,
        INTERPOLATION,
        JOINT,
        INV_BIND_MATRIX,
        WEIGHT,
        POSITION,
        OTHER
    }

    public String semantic;

    public String source;

    /**
     * Try to recognize the semantic name.
     * If unknown, return Semantic.OTHER
     * @return
     */
    public Semantic recognizeSemantic() {

        Semantic sem = Semantic.valueOf(semantic);
        if(sem == null) {
            sem = Semantic.OTHER;
        }

        return sem;

    }

}

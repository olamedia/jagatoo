package org.jagatoo.loaders.models.collada.jibx;

/**
 * A param is instruction on how to interpret
 * a part of a Source. It has a type and a name.
 * The name contains the "use" of the param, e.g.
 * "TIME", "ANGLE", "X", "Y", "Z"
 * Child of Accessor.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLParam {
    
    public static enum Type {
        /** Boolean */ _bool,
        /** Float */ _float,
        /** Integer */ _int,
        /** Name (String) */ _Name,
        /** IDREF (String) */ _IDREF,
        /** 4x4 float matrix */ _float4x4
    }
    
    public XMLParam.Type type;
    
    public static enum Name {
        /** X coordinate */ X,
        /** Y coordinate */ Y,
        /** T coordinate */ Z,
        /** U coordinate for texture mapping */ S,
        /** V coordinate for texture mapping */ T,
        /** Red component for color */ R,
        /** Green component for color */ G,
        /** Blue component for color */ B,
        /** Alpha component for color */ A,
        /** Time. Used for anims */ TIME,
        /** Angle. In degrees. */ ANGLE,
        /** Weight */ WEIGHT,
        /** Joint */ JOINT
    }
    
    public Name name;
    
    public static Type readTypeString(String typeString) {
        return Type.valueOf("_"+typeString);
    }
    
}

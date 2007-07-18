package org.jagatoo.loaders.models.collada.jibx;

/**
 * A Technique used to define the Appearance of an object in
 * the ProfileCOMMON.
 * Child of ProfileCOMMON.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class ProfileCOMMON_Technique {

    public String id = null;
    public String sid = null;

    public ShadingParameters constant = null;
    public ShadingParameters lambert = null;
    public ShadingParameters phong = null;
    public ShadingParameters blinn = null;

}

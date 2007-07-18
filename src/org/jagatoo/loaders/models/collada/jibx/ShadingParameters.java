package org.jagatoo.loaders.models.collada.jibx;

/**
 * Parameters for a Constan, Lambert, Phong, or Blinn shading.
 * Child of ProfileCOMMON_Technique
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class ShadingParameters {

    public ColorOrTexture emission = null;
    public ColorOrTexture ambient = null;
    public ColorOrTexture diffuse = null;
    public ColorOrTexture specular = null;
    public _Float shininess = null;
    public ColorOrTexture reflective = null;
    public _Float reflectivity = null;
    public ColorOrTexture transparent = null;
    public _Float transparency = null;

}

package org.jagatoo.loaders.models.collada.jibx;

import java.util.Date;

/**
 * Asset information about a COLLADA file, e.g.
 * the author, the contributor, the creation/modification
 * dates, units, etc..
 * Child of Camera, COLLADA, Light, Material, Technique,
 * Source, Geometry, Image, Animation, AnimationClip,
 * Controller, Extra, Node, VisualScene, Library_*, Effect,
 * ForceField, PhysicsMaterial, PhysicsScene, PhysicsModel
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class Asset {

    public Contributor contributor = null;
    public Date created = null;
    public Date modified = null;
    public Unit unit = null;
    public static enum UpAxis {
        X_UP,
        Y_UP,
        Z_UP
    }
    public UpAxis upAxis = null;

}

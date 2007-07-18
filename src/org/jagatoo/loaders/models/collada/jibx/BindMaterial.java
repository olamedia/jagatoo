package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;

/**
 * A Binding to a Material.
 * Child of InstanceGeometry and InstanceController.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class BindMaterial {

    /**
     * A TechniqueCommon, as a child of BindMaterial contains
     * one or more of InstanceMaterial
     * Child of BindMaterial.
     *
     * @author Amos Wenger (aka BlueSky)
     */
    public static class TechniqueCommon {

        public ArrayList<InstanceMaterial> instanceMaterials = null;

    }

    public BindMaterial.TechniqueCommon techniqueCommon;

}

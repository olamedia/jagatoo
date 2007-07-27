package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A visual scene. It contains a hierarchy of nodes.
 * It's a bit like a scenegraph. Each node can have a
 * transform, and can instance geometries or controllers
 * (for skeletal animation).
 * Child of LibraryVisualScenes.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLVisualScene {

    public String id = null;
    public String name = null;

    private ArrayList<XMLNode> nodesList = null;
    public HashMap<String, XMLNode> nodes = null;

    public void readNodes() {
        nodes = new HashMap<String, XMLNode>();
        for (XMLNode node : nodesList) {
            nodes.put(node.id, node);
            if(node.type == null) { node.type = XMLNode.Type.NODE; };
        }
    }

}

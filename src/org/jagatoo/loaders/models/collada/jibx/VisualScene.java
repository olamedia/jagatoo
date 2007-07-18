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
public class VisualScene {

    public String id = null;
    public String name = null;

    private ArrayList<Node> nodesList = null;
    public HashMap<String, Node> nodes = null;

    public void readNodes() {
        nodes = new HashMap<String, Node>();
        for (Node node : nodesList) {
            nodes.put(node.id, node);
            if(node.type == null) { node.type = Node.Type.NODE; };
        }
    }

}

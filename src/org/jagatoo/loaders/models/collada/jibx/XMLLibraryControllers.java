package org.jagatoo.loaders.models.collada.jibx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Library of Controllers.
 * Child of COLLADA.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class XMLLibraryControllers {
    
    /**
     * This field is written by JiBX and then parsed by the
     * readControllers() method and then the controllerMap HashMap
     * is written.
     */
    private ArrayList<XMLController> controllersList = null;
    
    /**
     * A map of all controllers, which is filled by the readControllers()
     * method just after the controllers ArrayList has been written.
     * key = ID
     * value = Controller
     */
    public HashMap<String, XMLController> controllers = null;
    
    /**
     * Called just after controllers has been read, fill
     * the controllerMap.
     */
    public void readControllers() {
        controllers = new HashMap<String, XMLController>();
        for (XMLController controller : controllersList) {
            controllers.put(controller.id, controller);
        }
        controllersList = null;
    }
    
}

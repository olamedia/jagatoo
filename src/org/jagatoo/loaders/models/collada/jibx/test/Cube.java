package org.jagatoo.loaders.models.collada.jibx.test;

import java.io.File;
import java.io.FileInputStream;

import org.jagatoo.loaders.models.collada.jibx.XMLCOLLADA;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class Cube {

    /**
     * @param args
     * @throws JiBXException
     */
    public static void main(String[] args) throws Exception {

        IBindingFactory factory = BindingDirectory.getFactory(XMLCOLLADA.class);

        long t1 = System.nanoTime();
        IUnmarshallingContext uc = factory.createUnmarshallingContext();
        long t2 = System.nanoTime();
        XMLCOLLADA coll = (XMLCOLLADA) uc.unmarshalDocument(
                //Thread.currentThread().getContextClassLoader().getResourceAsStream("org/jagatoo/loaders/models/collada/jibx/models/cube.dae")
                new FileInputStream(new File("/doc/dev/workspace/stratagemengine/flavors/middleage/models/units/fantassin_cape/fantassin_cape.dae"))
                , null);
        long t3 = System.nanoTime();

        System.out.println("Unmarshalling context creation time = "+((t2 - t1) / 1000000)+" ms");
        System.out.println("Unmarshalling time                  = "+((t3 - t2) / 1000000)+" ms");

        System.exit(0);

    }
}

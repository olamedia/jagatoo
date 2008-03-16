/**
 * Copyright (c) 2007-2008, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
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
        /*XMLCOLLADA coll = (XMLCOLLADA) */uc.unmarshalDocument(
                //Thread.currentThread().getContextClassLoader().getResourceAsStream("org/jagatoo/loaders/models/collada/jibx/models/cube.dae")
                new FileInputStream(new File("/doc/dev/workspace/stratagemengine/flavors/middleage/models/units/fantassin_cape/fantassin_cape.dae"))
                , null);
        long t3 = System.nanoTime();
        
        System.out.println("Unmarshalling context creation time = "+((t2 - t1) / 1000000)+" ms");
        System.out.println("Unmarshalling time                  = "+((t3 - t2) / 1000000)+" ms");
        
        System.exit(0);
        
    }
}

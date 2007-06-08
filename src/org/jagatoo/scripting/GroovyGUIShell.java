package org.jagatoo.scripting;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;

/**
 * Groovy Shell : a small experiment to discover the Groovy
 * embedding API.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class GroovyGUIShell {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);

        /*InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("/GroovyGUIShell probleImpl.groovy");
        System.out.println("resourceAsStream : "+resourceAsStream);
        shell.evaluate(resourceAsStream);*/

        try {
            shell.evaluate(new File("/doc/dev/workspace/jagatoo/src/org/jagatoo/scripting/GroovyGUIShellImpl.groovy"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

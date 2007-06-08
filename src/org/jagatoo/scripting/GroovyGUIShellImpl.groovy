package org.jagatoo.scripting

import javax.swing.*
import java.awt.*

/**
 * Implementation of a basic groovy shell
 *
 */
class GroovyShellImpl implements EventListener {

  public GroovyShellImpl() {
      
      def frame = new JFrame()
      
      frame.visible = true
      frame.size = new Dimension(800,600)
      frame.keyPressed = {
          println it
      }
      
  }
  
  static void main(String[] argv) {}

}
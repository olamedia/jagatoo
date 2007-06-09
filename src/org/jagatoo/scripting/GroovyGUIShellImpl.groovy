package org.jagatoo.scripting

import javax.swing.*
import java.awt.*
import java.awt.event.*

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
		if(it.keyCode == KeyEvent.VK_ESCAPE) {
			frame.visible = false
			System.exit(0)
		} else {
		    println("Key event : "+it.keyCode)
		}
	}

  }

  static void main(String[] argv) {}

}